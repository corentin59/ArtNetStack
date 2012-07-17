/**
 * 
 */
package fr.azelart.artnetstack.listeners;

import java.util.EventListener;

import fr.azelart.artnetstack.domain.artpoll.ArtPoll;

/**
 * The list of availables methods on recept ArtNetPacket.
 * @author Corentin Azelart.
 */
public interface ArtNetPacketListener extends EventListener {

	/**
	 * We have receive an ArtPollPacket.
	 * @param artPoll is the artpollpacket.
	 */
	void onArtPoll( ArtPoll artPoll );
	
}
