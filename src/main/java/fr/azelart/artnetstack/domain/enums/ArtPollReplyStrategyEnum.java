/**
 * 
 */
package fr.azelart.artnetstack.domain.enums;

/**
 * Diagnostic messages enumeration
 * @author Corentin Azelart.
 *
 */
public enum ArtPollReplyStrategyEnum {

	ALWAYS,		// 0 = Only send ArtPollReply in response to an ArtPoll or ArtAddress.
	ONLYCHANGE	// 1 = Send ArtPollReply whenever Node conditions change. This selection allows the Controller to be informed of changes without the need to continuously poll.
	
}
