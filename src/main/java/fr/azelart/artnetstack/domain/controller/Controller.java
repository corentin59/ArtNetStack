/**
 * 
 */
package fr.azelart.artnetstack.domain.controller;

import java.util.Map;

import fr.azelart.artnetstack.domain.artnet.ArtNetObject;
import fr.azelart.artnetstack.domain.enums.ArtPollReplyStrategyEnum;
import fr.azelart.artnetstack.domain.enums.DiagnosticMessageEnum;
import fr.azelart.artnetstack.domain.enums.DiagnosticMessageEnumAvailable;

/**
 * General controler.
 * @author Corentin Azelart.
 */
public class Controller extends ArtNetObject {
	
	/** Diffusion for diagnostic messages. */
	private DiagnosticMessageEnum diagnosticMessageDiffusion;

	/** Send diagnostic message. */
	private DiagnosticMessageEnumAvailable diagnosticMessageAvailable;
	
	/** ArtPoll reply strategy. */
	private  ArtPollReplyStrategyEnum artPollStrategie;
	
	/** Port mapping. */
	private Map<Integer,ControllerPortType> portTypeMap;
	
	/** GoodInput mapping. */
	private Map<Integer,ControllerGoodInput> goodInputMapping;
	
	/** GoodOutput mapping. */
	private Map<Integer,ControllerGoodOutput> goodOutputMapping;
	
	/**
	 * Set to false when video display is showing local data.
	 * Set to true when video is showing ethernet data.
	 */
	private Boolean screen;
	
	/**
	 * @return the goodInputMapping
	 */
	public Map<Integer, ControllerGoodInput> getGoodInputMapping() {
		return goodInputMapping;
	}

	/**
	 * @param goodInputMapping the goodInputMapping to set
	 */
	public void setGoodInputMapping(
			Map<Integer, ControllerGoodInput> goodInputMapping) {
		this.goodInputMapping = goodInputMapping;
	}

	/**
	 * Controler.
	 */
	public Controller() {
		super();
	}

	/**
	 * @return the portTypeMap
	 */
	public Map<Integer,ControllerPortType> getPortTypeMap() {
		return portTypeMap;
	}

	/**
	 * @param portTypeMap the portTypeMap to set
	 */
	public void setPortTypeMap(Map<Integer,ControllerPortType> portTypeMap) {
		this.portTypeMap = portTypeMap;
	}

	/**
	 * @return the goodOutputMapping
	 */
	public Map<Integer, ControllerGoodOutput> getGoodOutputMapping() {
		return goodOutputMapping;
	}

	/**
	 * @param goodOutputMapping the goodOutputMapping to set
	 */
	public void setGoodOutputMapping(
			Map<Integer, ControllerGoodOutput> goodOutputMapping) {
		this.goodOutputMapping = goodOutputMapping;
	}

	/**
	 * @return the screen
	 */
	public Boolean getScreen() {
		return screen;
	}

	/**
	 * Set to false when video display is showing local data.
	 * Set to true when video is showing ethernet data.
	 * @param screen the screen to set
	 */
	public void setScreen(Boolean screen) {
		this.screen = screen;
	}
}