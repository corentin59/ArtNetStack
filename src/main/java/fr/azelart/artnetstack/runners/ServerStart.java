/**
 * 
 */
package fr.azelart.artnetstack.runners;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import fr.azelart.artnetstack.domain.Controler;
import fr.azelart.artnetstack.domain.ControlerPortType;
import fr.azelart.artnetstack.domain.enums.PortTypeEnum;
import fr.azelart.artnetstack.listeners.ArtNetPacketListener;
import fr.azelart.artnetstack.listeners.ServerListener;
import fr.azelart.artnetstack.packets.ArtPollPacket;
import fr.azelart.artnetstack.server.ArtNetServer;
import fr.azelart.artnetstack.utils.ArtNetPacketEncoder;

/**
 * Server Runner.
 * @author Corentin Azelart
 *
 */
public class ServerStart {
	
	private static Controler thisControler;
	
	private static void createControler() {
		thisControler = new Controler();
		
		// Create one port
		final Map<Integer,ControlerPortType> vPorts = new HashMap<Integer, ControlerPortType>();
		ControlerPortType vPort1 = new ControlerPortType();
		vPort1.setInputArtNet( true );
		vPort1.setOutputArtNet( true );
		vPort1.setPort( 0 );
		vPort1.setType( PortTypeEnum.DMX512 );
		vPorts.put(0, vPort1);
		
		thisControler.setPortTypeMap(vPorts);
	}
	
	/**
	 * Start program method.
	 * @param args
	 */
	public static void main(String[] args) {
		final ArtNetServer artNetServer;
		createControler();
		
		
		try {
			artNetServer = new ArtNetServer();
			
			// Server listener
			artNetServer.addListenerServer( new ServerListener() {
				
				public void onConnect() {
					System.out.println("Connected");
					
				}
			} );
			
			// Packet Listener
			artNetServer.addListenerPacket( new ArtNetPacketListener() {
				
				/**
				 * Recept ArtPollPacket, we send ArtPollReply
				 */
				public void onArtPollPacket(ArtPollPacket artPollPacket) {
					
					
					
					try {
						artNetServer.sendPacket( ArtNetPacketEncoder.encodeArtPollReplyPacket( thisControler ) );
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			} );
			
			
			
			artNetServer.start();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
