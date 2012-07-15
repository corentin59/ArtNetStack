/**
 * 
 */
package fr.azelart.artnetstack.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.azelart.artnetstack.domain.enums.ArtPollReplyStrategyEnum;
import fr.azelart.artnetstack.domain.enums.DiagnosticMessageEnum;
import fr.azelart.artnetstack.domain.enums.DiagnosticMessageEnumAvailable;

/**
 * General controler.
 * @author Corentin Azelart.
 */
public class Controler {
	
	/** Diffusion for diagnostic messages. */
	private DiagnosticMessageEnum diagnosticMessageDiffusion;

	/** Send diagnostic message. */
	private DiagnosticMessageEnumAvailable diagnosticMessageAvailable;
	
	/** ArtPoll reply strategy. */
	private  ArtPollReplyStrategyEnum artPollStrategie;
	
	/** Port mapping. */
	private Map<Integer,ControlerPortType> portTypeMap;
	
	/**
	 * Controler.
	 */
	public Controler() {
		super();
	}

	/**
	 * @return the portTypeMap
	 */
	public Map<Integer,ControlerPortType> getPortTypeMap() {
		return portTypeMap;
	}

	/**
	 * @param portTypeMap the portTypeMap to set
	 */
	public void setPortTypeMap(Map<Integer,ControlerPortType> portTypeMap) {
		this.portTypeMap = portTypeMap;
	}


}