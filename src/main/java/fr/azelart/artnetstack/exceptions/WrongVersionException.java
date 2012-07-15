/**
 * 
 */
package fr.azelart.artnetstack.exceptions;

/**
 * The ArtNet version is not good.
 * @author Corentin Azelart
 *
 */
public class WrongVersionException extends Exception {

	/**
	 * Id.
	 */
	private static final long serialVersionUID = -1317233481315337822L;

	/**
	 * Constructor.
	 * @param message is the message.
	 */
	public WrongVersionException( String message ) {
		super( message );
	}
	
}
