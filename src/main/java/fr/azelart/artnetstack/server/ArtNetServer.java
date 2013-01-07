/*
 * Copyright 2012 Corentin Azelart.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.azelart.artnetstack.server;

import fr.azelart.artnetstack.constants.Constants;
import fr.azelart.artnetstack.domain.artaddress.ArtAddress;
import fr.azelart.artnetstack.domain.artdmx.ArtDMX;
import fr.azelart.artnetstack.domain.artnet.ArtNetObject;
import fr.azelart.artnetstack.domain.artpoll.ArtPoll;
import fr.azelart.artnetstack.domain.artpollreply.ArtPollReply;
import fr.azelart.artnetstack.domain.arttimecode.ArtTimeCode;
import fr.azelart.artnetstack.listeners.ArtNetPacketListener;
import fr.azelart.artnetstack.listeners.ServerListener;
import fr.azelart.artnetstack.utils.ArtNetPacketDecoder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * A Thread for the server.
 * @author Corentin Azelart.
 *
 */
public class ArtNetServer extends Thread implements Runnable {

	/**
	 * Socket communication.
	 */
	private final DatagramSocket datagramSocket;

	/**
	 * Listeners for packets.
	 */
	private final List<ArtNetPacketListener> listenersListPacket;

	/**
	 * Listeners for server.
	 */
	private final List<ServerListener> listenersListServer;

	/**
	 * Broadcast IP.
	 */
	private InetAddress listenAddress;

	/**
	 * Broadcast IP.
	 */
	private InetAddress broadcastAddress;

	/**
	 * Port.
	 */
	private final int port;

	/**
	 * Running.
	 */
	private boolean running = false;

	/**
	 * Creates an ArtNet server for the given addresses.
	 *
	 * @param listenAddress The address to bind the receiving socket to. {@code null} will bind to the local wildcard
	 *                      address (0.0.0.0).
	 *
	 * @param broadcastAddress The address to send ArtNet packets to, either a specific IP or the broadcast address
	 *                         for the interface connected to other nodes. Use {@link #broadcastAddressFor(java.net.InetAddress)}
	 *                         to determine the broadcast address associated with a local ip.
	 *
	 * @param port The UDP port to send packets to. The default ArtNet port is {@value Constants#DEFAULT_ART_NET_UDP_PORT}.
	 *
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public ArtNetServer(
		final InetAddress listenAddress,
		final InetAddress broadcastAddress,
		final int port
	) throws IOException {
		this.port = port;
		this.listenAddress = listenAddress;
		this.broadcastAddress = broadcastAddress;

		listenersListPacket = new ArrayList<ArtNetPacketListener>();
		listenersListServer = new ArrayList<ServerListener>();

		datagramSocket = new MulticastSocket(port);
	}

	/**
	 * Server execution.
	 */
	@Override
	public final void run() {
		// Define inputDatagramPacket
		DatagramPacket inputDatagramPacket = null;

		// Define input byte buffer
		final byte[] inputBuffer = new byte[Constants.SERVER_BUFFER_INPUT];

		// We inform than server is ready
		running = true;
		fireServerConnect();

		// ArtNet object
		ArtNetObject vArtNetObject = null;

		while (running) {
			inputDatagramPacket = new DatagramPacket(inputBuffer, inputBuffer.length);
			try {
				datagramSocket.receive(inputDatagramPacket);
				vArtNetObject = ArtNetPacketDecoder.decodeArtNetPacket(inputDatagramPacket.getData(), inputDatagramPacket.getAddress());

				// It's realy an artnet packet.
				if (vArtNetObject != null) {
					fireArtNet(vArtNetObject);
					if (vArtNetObject instanceof ArtPoll) {
						// ArtPollPacket
						fireArtPoll((ArtPoll) vArtNetObject);
					} else if (vArtNetObject instanceof ArtTimeCode) {
						// ArtTimeCodePacket
						fireArtTimeCode((ArtTimeCode) vArtNetObject);
					} else if (vArtNetObject instanceof ArtPollReply) {
						// ArtPollReply
						fireArtPollReply((ArtPollReply) vArtNetObject);
					} else if (vArtNetObject instanceof ArtDMX) {
						// ArtDMX
						fireArtDMXReply((ArtDMX) vArtNetObject);
					} else if (vArtNetObject instanceof ArtAddress) {
						// ArtAddress
						fireArtAddressReply((ArtAddress) vArtNetObject);
					}
				}
			} catch (final Exception e) {
				e.getMessage();
				e.printStackTrace();
			}
		}
	}

	/**
	 * Determines the broadcast address used by the interface which owns the given ip address. The given IP must
	 * be one representing the local machine.
	 *
	 * @param inetAddress The address
	 * @return The broadcast address for the interface owning the given address
	 * @throws SocketException
	 */
	public static InetAddress broadcastAddressFor(final InetAddress inetAddress) throws SocketException {
		final Enumeration<NetworkInterface> allInterfaces = NetworkInterface.getNetworkInterfaces();

		while (allInterfaces.hasMoreElements()) {
			final NetworkInterface networkInterface = allInterfaces.nextElement();

			for (final InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
				if (interfaceAddress.getAddress().getHostAddress().equals(inetAddress.getHostAddress())) {

					if (interfaceAddress.getBroadcast() == null) {
						throw new IllegalArgumentException(
							"Broadcast address for " + networkInterface + ", which owns " + inetAddress + " is null. " +
								"Try a different interface."
						);
					}

					return interfaceAddress.getBroadcast();
				}
			}
		}

		throw new IllegalArgumentException("No interface has address " + inetAddress);
	}

	/**
	 * Stop server.
	 */
	public final void stopServer() {
		running = false;
		datagramSocket.disconnect();
		datagramSocket.close();
		fireServerTerminate();
	}

	/**
	 * Check if server is running.
	 * @return yes if he run
	 */
	public final boolean isRunning() {
		return running;
	}

	/**
	 * Send a packet.
	 * @param bytes is the packet data
	 * @throws IOException if we can't send packet
	 */
	public final void sendPacket(  byte[] bytes ) throws IOException {
		if(datagramSocket!=null) {
			final DatagramPacket packet = new DatagramPacket(
				bytes,
				bytes.length,
				broadcastAddress,
				port
			);
			datagramSocket.send( packet );
		}
	}

	/**
	 * We add an listener.
	 * @param serverListener is a server listener
	 */
	public final void addListenerServer(final ServerListener serverListener) {
		this.listenersListServer.add(serverListener);
	}

	/**
	 * Server is connected.
	 */
	public final void fireServerConnect() {
		for (final ServerListener listener : this.listenersListServer) {
			listener.onConnect();
		}
	}

	/**
	 * Server is die.
	 */
	public final void fireServerTerminate() {
		for (final ServerListener listener : this.listenersListServer) {
			listener.onTerminate();
		}
	}

	/**
	 * We add a listener.
	 * @param artNetPacketListener is the artnet packet.
	 */
	public final void addListenerPacket(final ArtNetPacketListener artNetPacketListener) {
		this.listenersListPacket.add(artNetPacketListener);
	}

	/**
	 * A new ArtNetObject incoming.
	 * @param artNetObject is the artPollPacket
	 */
	private void fireArtNet(final ArtNetObject artNetObject) {
		for (final ArtNetPacketListener listener : this.listenersListPacket) {
			listener.onArt(artNetObject);
		}
	}

	/**
	 * A new ArtPollPacket incoming.
	 * @param artPoll is the artPollPacket
	 */
	public final void fireArtPoll(final ArtPoll artPoll) {
		for (final ArtNetPacketListener listener : this.listenersListPacket) {
			listener.onArtPoll(artPoll);
		}
	}

	/**
	 * A new ArtTimeCode incoming.
	 * @param artTimeCode is the instance of the artTimeCodePacket
	 */
	public final void fireArtTimeCode(final ArtTimeCode artTimeCode) {
		for (final ArtNetPacketListener listener : this.listenersListPacket) {
			listener.onArtTimeCode(artTimeCode);
		}
	}

	/**
	 * A new ArtPollReply incoming.
	 * @param artPollReply is the instance of the artPollReplyPacket
	 */
	public final void fireArtPollReply(final ArtPollReply artPollReply) {
		for (final ArtNetPacketListener listener : this.listenersListPacket) {
			listener.onArtPollReply(artPollReply);
		}
	}

	/**
	 * A new ArtDMX incoming.
	 * @param artDMX is the instance of the artDMX pakcet
	 */
	public final void fireArtDMXReply(final ArtDMX artDMX) {
		for (final ArtNetPacketListener listener : this.listenersListPacket) {
			listener.onArtDMX(artDMX);
		}
	}
	
	/**
	 * A new ArtAddress incoming.
	 * @param artAddress is the instance of the artAddress pakcet
	 */
	public final void fireArtAddressReply(final ArtAddress artAddress) {
		for (final ArtNetPacketListener listener : this.listenersListPacket) {
			listener.onArtAddress(artAddress);
		}
	}

	/**
	 * @return the port
	 */
	public final int getPort() {
		return port;
	}
}
