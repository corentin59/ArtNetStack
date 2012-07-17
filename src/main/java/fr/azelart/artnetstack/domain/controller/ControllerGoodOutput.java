/**
 * 
 */
package fr.azelart.artnetstack.domain.controller;

/**
 * Controler Input Good.
 * @author Corentin Azelart.
 *
 */
public class ControllerGoodOutput {

	/** The merge mode is LTP. */
	private Boolean mergeLTP;
	
	/** DMX output short detected on power up. */
	private Boolean outputPowerOn;
	
	/** Output is merging ArtNet data. */
	private Boolean outputmergeArtNet;
	
	/** This output accept DMX Text packets. */
	private Boolean includeDMXTextPackets;
	
	/** This output accept DMX SIPs packets. */
	private Boolean includeDMXSIPsPackets;
	
	/** This output accept DMX test packets. */
	private Boolean includeDMXTestPackets;
	
	/** This output transmit data. */
	private Boolean dataTransmited;
	
	/**
	 * Constructor.
	 */
	public ControllerGoodOutput() {
		super();
	}

	/**
	 * @return the mergeLTP
	 */
	public Boolean getMergeLTP() {
		return mergeLTP;
	}

	/**
	 * @param mergeLTP the mergeLTP to set
	 */
	public void setMergeLTP(Boolean mergeLTP) {
		this.mergeLTP = mergeLTP;
	}

	/**
	 * @return the outputPowerOn
	 */
	public Boolean getOutputPowerOn() {
		return outputPowerOn;
	}

	/**
	 * @param outputPowerOn the outputPowerOn to set
	 */
	public void setOutputPowerOn(Boolean outputPowerOn) {
		this.outputPowerOn = outputPowerOn;
	}

	/**
	 * @return the outputmergeArtNet
	 */
	public Boolean getOutputmergeArtNet() {
		return outputmergeArtNet;
	}

	/**
	 * @param outputmergeArtNet the outputmergeArtNet to set
	 */
	public void setOutputMergeArtNet(Boolean outputmergeArtNet) {
		this.outputmergeArtNet = outputmergeArtNet;
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
	 * @return the dataTransmited
	 */
	public Boolean getDataTransmited() {
		return dataTransmited;
	}

	/**
	 * @param dataTransmited the dataTransmited to set
	 */
	public void setDataTransmited(Boolean dataTransmited) {
		this.dataTransmited = dataTransmited;
	}
}
