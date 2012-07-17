/**
 * 
 */
package fr.azelart.artnetstack.domain.controller;

/**
 * Good input.
 * @author Corentin Azelart.
 *
 */
public class ControllerGoodInput {

	/** Input receive data in error. */
	private Boolean receivedDataError;
	
	/** Input is disabled. */
	private Boolean disabled;
	
	/** This input accept DMX Text packets. */
	private Boolean includeDMXTextPackets;
	
	/** This input accept DMX SIPs packets. */
	private Boolean includeDMXSIPsPackets;
	
	/** This input accept DMX test packets. */
	private Boolean includeDMXTestPackets;
	
	/** This input receive data. */
	private Boolean dataReceived;
	
	/**
	 * Constructor.
	 */
	public ControllerGoodInput() {
		super();
	}

	/**
	 * @return the receivedDataError
	 */
	public Boolean getReceivedDataError() {
		return receivedDataError;
	}

	/**
	 * @param receivedDataError the receivedDataError to set
	 */
	public void setReceivedDataError(Boolean receivedDataError) {
		this.receivedDataError = receivedDataError;
	}

	/**
	 * @return the disabled
	 */
	public Boolean getDisabled() {
		return disabled;
	}

	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * @return the includeDMXTextPackets
	 */
	public Boolean getIncludeDMXTextPackets() {
		return includeDMXTextPackets;
	}

	/**
	 * @param includeDMXTextPackets the includeDMXTextPackets to set
	 */
	public void setIncludeDMXTextPackets(Boolean includeDMXTextPackets) {
		this.includeDMXTextPackets = includeDMXTextPackets;
	}

	/**
	 * @return the includeDMXSIPsPackets
	 */
	public Boolean getIncludeDMXSIPsPackets() {
		return includeDMXSIPsPackets;
	}

	/**
	 * @param includeDMXSIPsPackets the includeDMXSIPsPackets to set
	 */
	public void setIncludeDMXSIPsPackets(Boolean includeDMXSIPsPackets) {
		this.includeDMXSIPsPackets = includeDMXSIPsPackets;
	}

	/**
	 * @return the includeDMXTestPackets
	 */
	public Boolean getIncludeDMXTestPackets() {
		return includeDMXTestPackets;
	}

	/**
	 * @param includeDMXTestPackets the includeDMXTestPackets to set
	 */
	public void setIncludeDMXTestPackets(Boolean includeDMXTestPackets) {
		this.includeDMXTestPackets = includeDMXTestPackets;
	}

	/**
	 * @return the dataReceived
	 */
	public Boolean getDataReceived() {
		return dataReceived;
	}

	/**
	 * @param dataReceived the dataReceived to set
	 */
	public void setDataReceived(Boolean dataReceived) {
		this.dataReceived = dataReceived;
	}
}
