/**
 * 
 */
package fr.azelart.artnetstack.domain;

import fr.azelart.artnetstack.domain.enums.PortTypeEnum;

/**
 * Controler Port Type.
 * @author Corentin Azelart.
 *
 */
public class ControlerPortType {

	/** Port Number. */
	private int port;
	
	/** Port type. */
	private PortTypeEnum type;
	
	/** Specify if we can communicate */
	private boolean inputArtNet;
	
	/** Specify if we can communicate */
	private boolean outputArtNet;

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the type
	 */
	public PortTypeEnum getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(PortTypeEnum type) {
		this.type = type;
	}

	/**
	 * @return the inputArtNet
	 */
	public boolean isInputArtNet() {
		return inputArtNet;
	}

	/**
	 * @param inputArtNet the inputArtNet to set
	 */
	public void setInputArtNet(boolean inputArtNet) {
		this.inputArtNet = inputArtNet;
	}

	/**
	 * @return the outputArtNet
	 */
	public boolean isOutputArtNet() {
		return outputArtNet;
	}

	/**
	 * @param outputArtNet the outputArtNet to set
	 */
	public void setOutputArtNet(boolean outputArtNet) {
		this.outputArtNet = outputArtNet;
	}
	
	
	
}
