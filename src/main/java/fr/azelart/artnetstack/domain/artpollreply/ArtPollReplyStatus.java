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

import fr.azelart.artnetstack.domain.enums.IndicatorStateEnum;
import fr.azelart.artnetstack.domain.enums.UniverseAddressProgrammingAuthorityEnum;

/**
 * ArtPolLReply status.
 * @author Corentin Azelart.
 */
public class ArtPollReplyStatus {
	
	/** UBEA is present. */
	private Boolean ubeaPresent;
	
	/** RDM Available. */
	private Boolean rdmCapable;
	
	/** Device is booted from ROM. */
	private Boolean bootRom;
	
	/** Programming authority. */
	private UniverseAddressProgrammingAuthorityEnum programmingAuthority;

	/** Indicator state. */
	private IndicatorStateEnum indicatorState;
	
	/**
	 * Constructor.
	 */
	public ArtPollReplyStatus() {
		super();
	}

	/**
	 * @return the ubeaPresent
	 */
	public Boolean getUbeaPresent() {
		return ubeaPresent;
	}

	/**
	 * @param ubeaPresent the ubeaPresent to set
	 */
	public void setUbeaPresent(Boolean ubeaPresent) {
		this.ubeaPresent = ubeaPresent;
	}

	/**
	 * @return the rdmCapable
	 */
	public Boolean getRdmCapable() {
		return rdmCapable;
	}

	/**
	 * @param rdmCapable the rdmCapable to set
	 */
	public void setRdmCapable(Boolean rdmCapable) {
		this.rdmCapable = rdmCapable;
	}

	/**
	 * @return the bootRom
	 */
	public Boolean getBootRom() {
		return bootRom;
	}

	/**
	 * @param bootRom the bootRom to set
	 */
	public void setBootRom(Boolean bootRom) {
		this.bootRom = bootRom;
	}

	/**
	 * @return the programmingAuthority
	 */
	public UniverseAddressProgrammingAuthorityEnum getProgrammingAuthority() {
		return programmingAuthority;
	}

	/**
	 * @param programmingAuthority the programmingAuthority to set
	 */
	public void setProgrammingAuthority(
			UniverseAddressProgrammingAuthorityEnum programmingAuthority) {
		this.programmingAuthority = programmingAuthority;
	}

	/**
	 * @return the indicatorState
	 */
	public IndicatorStateEnum getIndicatorState() {
		return indicatorState;
	}

	/**
	 * @param indicatorState the indicatorState to set
	 */
	public void setIndicatorState(IndicatorStateEnum indicatorState) {
		this.indicatorState = indicatorState;
	}
}
