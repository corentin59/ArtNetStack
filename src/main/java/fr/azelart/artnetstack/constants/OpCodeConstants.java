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
package fr.azelart.artnetstack.constants;

/**
 * OpCodes file.
 * @author Corentin Azelart.
 */
public final class OpCodeConstants {

	/**
	 * Private constructor.
	 */
	private OpCodeConstants() {
		super();
	}

	/**
	 * This is an ArtPoll packet,
	 * no other data is contained in this UDP packet.
	 */
	public static final String OPPOLL = "0020";

	/**
	 * This is an ArtPollReply packet,.
	 */
	public static final String OPPOLLREPLY = "0021";

	/**
	 * This is an ArtTimeCode packet.
	 * It is used to transport time code over the network.
	 */
	public static final String OPTIMECODE = "00)7";

	/**
	 * This is an ArtDMX packet.
	 * It is used to transport DMX over the network.
	 */
	public static final String OPOUTPUT = "0050";
}
