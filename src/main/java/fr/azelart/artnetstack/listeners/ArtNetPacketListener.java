/**
 * 
 */
package fr.azelart.artnetstack.listeners;

import java.util.EventListener;

import fr.azelart.artnetstack.packets.ArtPollPacket;

/**
 * The list of availables methods on recept ArtNetPacket.
 * @author Corentin Azelart.
 */
public interface ArtNetPacketListener extends EventListener {

	/**
	 * We have receive an ArtPollPacket.
	 * @param artPollPacket is the artpollpacket.
	 */
	void onArtPollPacket( ArtPollPacket artPollPacket );
	
}
