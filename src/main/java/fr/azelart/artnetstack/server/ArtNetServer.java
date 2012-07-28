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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import fr.azelart.artnetstack.constants.Constants;
import fr.azelart.artnetstack.domain.artnet.ArtNetObject;
import fr.azelart.artnetstack.domain.artpoll.ArtPoll;
import fr.azelart.artnetstack.domain.artpollreply.ArtPollReply;
import fr.azelart.artnetstack.domain.arttimecode.ArtTimeCode;
import fr.azelart.artnetstack.listeners.ArtNetPacketListener;
import fr.azelart.artnetstack.listeners.ServerListener;
import fr.azelart.artnetstack.utils.ArtNetPacketDecoder;

/**
 * A Thread for the server.
 * @author Corentin Azelart.
 *
 */
public class ArtNetServer extends Thread implements Runnable {
	
	/**
	 * Socket communication.
	 */
	private DatagramSocket datagramSocket;

	/**
	 * Listeners for packets.
	 */
	private List<ArtNetPacketListener> listenersListPacket;

	/**
	 * Listeners for server.
	 */
	private List<ServerListener> listenersListServer;

	/**
	 * Constructor of server.
	 * @throws SocketException if socket error
	 * @throws UnknownHostException if we can't find the host.
	 */
	public ArtNetServer() throws SocketException, UnknownHostException {
		listenersListPacket = new ArrayList<ArtNetPacketListener>();
		listenersListServer = new ArrayList<ServerListener>();
		datagramSocket = new DatagramSocket(Constants.SERVER_PORT);
	}

	/**
	 * Server execution.
	 */
	public void run() {
		// Define inputDatagramPacket
		DatagramPacket inputDatagramPacket = null;

		// Define input byte buffer
		byte[] inputBuffer = new byte[1024];

		// We inform than server is ready
		fireServerConnect();
		
		// ArtNet object
		ArtNetObject vArtNetObject = null;

		while (isAlive()) {
			inputDatagramPacket = new DatagramPacket(inputBuffer, inputBuffer.length);
			try {
				datagramSocket.receive(inputDatagramPacket);
				vArtNetObject = ArtNetPacketDecoder.decodeArtNetPacket(inputDatagramPacket.getData());
				
				// It's realy an artnet packet.
				if (vArtNetObject != null) {
					if (vArtNetObject instanceof ArtPoll) {
						// ArtPollPacket
						fireArtPoll((ArtPoll) vArtNetObject);
					} else if (vArtNetObject instanceof ArtTimeCode) {
						// ArtTimeCodePacket
						fireArtTimeCode((ArtTimeCode) vArtNetObject);
					} else if (vArtNetObject instanceof ArtPollReply) {
						// ArtPollReply
						fireArtPollReply((ArtPollReply) vArtNetObject);
					}
				}
			} catch (Exception e) {
				
			}
		}
		
		// Server is die
		fireServerTerminate();
	}

	/**
	 * Send a packet.
	 * @throws IOException
	 */
	public final void sendPacket(  byte[] bytes ) throws IOException {
		final DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(Constants.SERVER_IP_BROADCAST), Constants.SERVER_PORT);
		datagramSocket.send( packet );
	}

	/**
	 * We add an listener.
	 * @param artNetPacketListener
	 */
	public final void addListenerServer( final ServerListener serverListener ) {
		this.listenersListServer.add(serverListener);
	}

	/**
	 * Server is connected.
	 */
	public final void fireServerConnect() {
		for (ServerListener listener : this.listenersListServer) {
			listener.onConnect();
		}
	}
	
	/**
	 * Server is die.
	 */
	public final void fireServerTerminate() {
		for (ServerListener listener : this.listenersListServer) {
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
	 * A new ArtPollPacket incomming.
	 * @param artPoll is the artPollPacket
	 */
	public final void fireArtPoll(final ArtPoll artPoll) {
		for (ArtNetPacketListener listener : this.listenersListPacket) {
			listener.onArtPoll(artPoll);
		}
	}

	/**
	 * A new ArtTimeCode incomming.
	 * @param artTimeCode is the instance of the artTimeCodePacket
	 */
	public final void fireArtTimeCode(final ArtTimeCode artTimeCode) {
		for (ArtNetPacketListener listener : this.listenersListPacket) {
			listener.onArtTimeCode(artTimeCode);
		}
	}
	
	/**
	 * A new ArtPollReply incomming.
	 * @param artPollReply is the instance of the artPollReplyPacket
	 */
	public final void fireArtPollReply(final ArtPollReply artPollReply) {
		for (ArtNetPacketListener listener : this.listenersListPacket) {
			listener.onArtPollReply(artPollReply);
		}
	}
}
