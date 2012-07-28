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


	
	
	
}
