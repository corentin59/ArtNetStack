package fr.azelart.artnetstack.constants;

/**
 * OpCode Table.
 * @author Corentin Azelart
 */
public final class OpCodeConstants {

	/** This is an ArtPoll packet, no other data is contained in this UDP packet. */
	public static final String OPPOLL = "0020";
	
	public static final String OPPOLLREPLY = "0021";
	
	/** This is an ArtTimeCode packet. It is used to transport time code over the network. */
	public static final String OPTIMECODE = "9700";
	
}
