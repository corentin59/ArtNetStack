/**
 * 
 */
package fr.azelart.artnetstack.packets;

/**
 * ArtPollPacket
 * @author Corentin Azelart
 *
 */
public class ArtPollPacket extends ArtNetPacket implements ArtNetPacketImp {
	
	/**
	 * High byte of the Art-Net protocol revision number.
	 */
	private int protVerHi;
	
	/**
	 * Low byte of the Art-Net protocol revision number. 
	 * Current value 14. Controllers should ignore communication with nodes using a protocol version 
	 * lower than 14.
	 */
	private int protVerLo;
	
	/**
	 * Set behaviour of Node.
	 */
	private int talkToMe;
	
	/**
	 * The lowest priority of diagnostics message that should 
	 * be sent. See Table 5.
	 */
	private int priority;

	/**
	 * @return the protVerHi
	 */
	public int getProtVerHi() {
		return protVerHi;
	}

	/**
	 * @param protVerHi the protVerHi to set
	 */
	public void setProtVerHi(int protVerHi) {
		this.protVerHi = protVerHi;
	}

	/**
	 * @return the protVerLo
	 */
	public int getProtVerLo() {
		return protVerLo;
	}

	/**
	 * @param protVerLo the protVerLo to set
	 */
	public void setProtVerLo(int protVerLo) {
		this.protVerLo = protVerLo;
	}

	/**
	 * @return the talkToMe
	 */
	public int getTalkToMe() {
		return talkToMe;
	}

	/**
	 * @param talkToMe the talkToMe to set
	 */
	public void setTalkToMe(int talkToMe) {
		this.talkToMe = talkToMe;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
}