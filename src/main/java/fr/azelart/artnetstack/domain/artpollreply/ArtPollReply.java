/*
 * Copyright 2012 Corentin Azelart.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.azelart.artnetstack.domain.artpollreply;

import fr.azelart.artnetstack.domain.artnet.ArtNetObject;

/**
 * This is an ArtPollReply object.
 * @author Corentin Azelart
 *
 */
public class ArtPollReply extends ArtNetObject {
	
	/** Adress IP. */
	private String ip;
	
	/** Port. */
	private int port;
	
	/** Version High. */
	private int versionH;
	
	/** Version Low. */
	private int versionL;
	
	/** Hexa Oem. */
	private String oemHexa;
	
	/** UBEA Version. */
	private int ubeaVersion;
	
	/** ArtPollReply status. */
	private ArtPollReplyStatus artPollReplyStatus;
	
	/** Network. */
	private String subNet;
	
	/** Adress in network. */
	private String subSwitch;
	
	/** Real ip address. */
	private String physicalIp;

	/** The ESTA manufacturer code. These codes are used to 
	 * represent equipment manufacturer. They are assigned 
	 * by ESTA. This field can be interpreted as two ASCII 
	 * bytes representing the manufacturer initials.
	 */
	private String esta;
	
	/**
	 * The array represents a null terminated short name for 
	 * the Node. The Controller uses the ArtAddress packet 
	 * to program this string. Max length is 17 characters 
	 * plus the null. This is a fixed length field, although the 
	 * string it contains can be shorter than the field.
	 */
	private String shortName;
	
	/**
	 * The array represents a null terminated long name for 
	 * the Node. The Controller uses the ArtAddress packet 
	 * to program this string. Max length is 63 characters 
	 * plus the null. This is a fixed length field, although the 
	 * string it contains can be shorter than the field.
	 */
	private String longName;
	
	/**
	 * The Style code defines the equipment style of the 
	 * device. See Table 4 for current Style codes. 
	 */
	private ArtPollReplyStyle artPollReplyStyle;
	
	/**
	 * toString
	 */
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("ArtPollReply[ip=").append( ip ).append(",longName").append(longName).append("]");
		return sb.toString();
	}

	/**
	 * @return the artPollReplyStyle
	 */
	public ArtPollReplyStyle getArtPollReplyStyle() {
		return artPollReplyStyle;
	}

	/**
	 * @param artPollReplyStyle the artPollReplyStyle to set
	 */
	public void setArtPollReplyStyle(ArtPollReplyStyle artPollReplyStyle) {
		this.artPollReplyStyle = artPollReplyStyle;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

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
	 * @return the versionH
	 */
	public int getVersionH() {
		return versionH;
	}

	/**
	 * @param versionH the versionH to set
	 */
	public void setVersionH(int versionH) {
		this.versionH = versionH;
	}

	/**
	 * @return the versionL
	 */
	public int getVersionL() {
		return versionL;
	}

	/**
	 * @param versionL the versionL to set
	 */
	public void setVersionL(int versionL) {
		this.versionL = versionL;
	}

	/**
	 * @return the oemHexa
	 */
	public String getOemHexa() {
		return oemHexa;
	}

	/**
	 * @param oemHexa the oemHexa to set
	 */
	public void setOemHexa(String oemHexa) {
		this.oemHexa = oemHexa;
	}

	/**
	 * @return the ubeaVersion
	 */
	public int getUbeaVersion() {
		return ubeaVersion;
	}

	/**
	 * @param ubeaVersion the ubeaVersion to set
	 */
	public void setUbeaVersion(int ubeaVersion) {
		this.ubeaVersion = ubeaVersion;
	}

	public ArtPollReplyStatus getArtPollReplyStatus() {
		return artPollReplyStatus;
	}

	public void setArtPollReplyStatus(ArtPollReplyStatus artPollReplyStatus) {
		this.artPollReplyStatus = artPollReplyStatus;
	}

	/**
	 * @return the subNet
	 */
	public String getSubNet() {
		return subNet;
	}

	/**
	 * @param subNet the subNet to set
	 */
	public void setSubNet(String subNet) {
		this.subNet = subNet;
	}

	/**
	 * @return the subSwitch
	 */
	public String getSubSwitch() {
		return subSwitch;
	}

	/**
	 * @param subSwitch the subSwitch to set
	 */
	public void setSubSwitch(String subSwitch) {
		this.subSwitch = subSwitch;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	/**
	 * @return the esta
	 */
	public String getEsta() {
		return esta;
	}

	/**
	 * @param esta the esta to set
	 */
	public void setEsta(String esta) {
		this.esta = esta;
	}
	
	
	/**
	 * @return the physicalIp
	 */
	public String getPhysicalIp() {
		return physicalIp;
	}

	/**
	 * @param physicalIp the physicalIp to set
	 */
	public void setPhysicalIp(String physicalIp) {
		this.physicalIp = physicalIp;
	}
}
