/**
 * 
 */
package fr.azelart.artnetstack.exceptions;

/**
 * Wrong Packet Exception.
 * @author Corentin Azelart.
 *
 */
public class WrongPacketException extends Exception {

	/**
	 * Serial Version.
	 */
	private static final long serialVersionUID = 10648748293721072L;

	/**
	 * Constructor.
	 * @param message is the message.
	 */
	public WrongPacketException( String message ) {
		super( message );
	}
	
}
