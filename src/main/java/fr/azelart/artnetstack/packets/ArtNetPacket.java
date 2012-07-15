package fr.azelart.artnetstack.packets;

public class ArtNetPacket implements ArtNetPacketImp {

	/** 
	 * Array of 8 characters, the final character is a null termination.
	 */
	private String id;
	
	/**
	 * The OpCode defines the class of data following ArtPoll within this UDP packet.
	 * Transmitted low byte first. See Table 1 for the OpCode listing.
	 */
	private String opCode;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the opCode
	 */
	public String getOpCode() {
		return opCode;
	}

	/**
	 * @param opCode the opCode to set
	 */
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
}
