package fr.azelart.artnetstack.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import fr.azelart.artnetstack.constants.Constantes;
import fr.azelart.artnetstack.constants.OpCodeConstants;
import fr.azelart.artnetstack.listeners.ArtNetPacketListener;
import fr.azelart.artnetstack.listeners.ServerListener;
import fr.azelart.artnetstack.packets.ArtNetPacket;
import fr.azelart.artnetstack.packets.ArtPollPacket;
import fr.azelart.artnetstack.utils.ArtNetPacketDecoder;

public class ArtNetServer extends Thread implements Runnable {
	
	private DatagramSocket datagramSocket;
	
	/**
	 * List of observator.
	 */
	private ArrayList<ArtNetPacketListener> listenersListPacket;
	
	private ArrayList<ServerListener> listenersListServer;
	
	/**
	 * Constructor of server.
	 * @throws SocketException
	 * @throws UnknownHostException 
	 */
	public ArtNetServer() throws SocketException, UnknownHostException {
		listenersListPacket = new ArrayList<ArtNetPacketListener>();
		listenersListServer = new ArrayList<ServerListener>();
		datagramSocket = new DatagramSocket( Constantes.SERVER_PORT );
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
		
		while(true) {
			inputDatagramPacket = new DatagramPacket(inputBuffer, inputBuffer.length);
			try {
				datagramSocket.receive( inputDatagramPacket );
				final ArtNetPacket vArtNetPacket = ArtNetPacketDecoder.decodeArtNetPacket( inputDatagramPacket.getData() );
				
				// It's realy an artnet packet.
				if ( vArtNetPacket != null ) {
					// ArtPollPacket
					if ( vArtNetPacket instanceof ArtPollPacket ) {
						fireArtPollPacket( (ArtPollPacket) vArtNetPacket );
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Send a packet.
	 * @throws IOException
	 */
	public void sendPacket(  byte[] bytes ) throws IOException {
		final DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("192.168.1.255"), Constantes.SERVER_PORT);
		datagramSocket.send( packet );
	}
	
	/**
	 * We add an listener.
	 * @param artNetPacketListener
	 */
	public void addListenerServer( ServerListener serverListener ) {
		this.listenersListServer.add( serverListener );
	}
	
	/**
	 * Server is connected.
	 */
	public void fireServerConnect() {
		for ( ServerListener listener : this.listenersListServer ) {
			listener.onConnect();
		}
	}

	/**
	 * We add an listener.
	 * @param artNetPacketListener
	 */
	public void addListenerPacket( ArtNetPacketListener artNetPacketListener ) {
		this.listenersListPacket.add( artNetPacketListener );
	}
	
	/**
	 * A new ArtPollPacket incomming.
	 * @param artPollPacket is the instance of the artPollPacket
	 */
	public void fireArtPollPacket( ArtPollPacket artPollPacket ) {
		for ( ArtNetPacketListener listener : this.listenersListPacket ) {
			listener.onArtPollPacket( artPollPacket );
		}
	}
}
