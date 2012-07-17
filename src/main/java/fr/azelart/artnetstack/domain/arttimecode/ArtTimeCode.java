/**
 * 
 */
package fr.azelart.artnetstack.domain.arttimecode;

import fr.azelart.artnetstack.domain.artnet.ArtNetObject;

/**
 * ArtTimeCode
 * @author Corentin Azelart.
 *
 */
public class ArtTimeCode extends ArtNetObject {
	
	/** Frames time. 0 – 29 depending on mode. */
	private int frameTime;
	
	/** Seconds. 0 - 59. */
	private int seconds;
	
	/** Minutes. 0 - 59. */
	private int minutes;
	
	/** Hours. 0 - 23. */
	private int hours;
	
	/** Type of ArtTimeCode. */
	private ArtTimeCodeType artTimeCodeType;

	/**
	 * Constructor.	
	 */
	public ArtTimeCode() {
		super();
	}

	/**
	 * @return the frameTime
	 */
	public int getFrameTime() {
		return frameTime;
	}

	/**
	 * @param frameTime the frameTime to set
	 */
	public void setFrameTime(int frameTime) {
		this.frameTime = frameTime;
	}

	/**
	 * @return the seconds
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * @param seconds the seconds to set
	 */
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @param minutes the minutes to set
	 */
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	/**
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}

	/**
	 * @return the artTimeCodeType
	 */
	public ArtTimeCodeType getArtTimeCodeType() {
		return artTimeCodeType;
	}

	/**
	 * @param artTimeCodeType the artTimeCodeType to set
	 */
	public void setArtTimeCodeType(ArtTimeCodeType artTimeCodeType) {
		this.artTimeCodeType = artTimeCodeType;
	}
}
