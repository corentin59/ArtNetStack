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
package fr.azelart.artnetstack.utils;

import java.util.BitSet;

import fr.azelart.artnetstack.constants.Constants;
import fr.azelart.artnetstack.constants.OpCodeConstants;
import fr.azelart.artnetstack.domain.artnet.ArtNetObject;
import fr.azelart.artnetstack.domain.artpoll.ArtPoll;
import fr.azelart.artnetstack.domain.arttimecode.ArtTimeCode;
import fr.azelart.artnetstack.domain.arttimecode.ArtTimeCodeType;
import fr.azelart.artnetstack.domain.enums.NetworkCommunicationTypeEnum;

/**
 * ArtNetPacket decoder.
 * @author Corentin Azelart.
 */
public class ArtNetPacketDecoder {

	public static ArtNetObject decodeArtNetPacket( byte[] packet ) {
		
		// The ArtNetPacket.
		ArtNetObject artNetObject = null;
		
		// Set generals infos
		final String hexaBrut = byteArrayToHex( packet );
		final String id = new String (packet, 0, 7 );
		final String opCode = hexaBrut.substring(16, 20);
		
		// Yes, it's a ArtNetPacket
		if ( !"Art-Net".equals( id ) || !checkVersion( packet, hexaBrut ) ) {
			return null; 
		}

		/*
		 * Dicover the type of the packet.
		 * Please refer to OpcodeTable.
		 */
		if ( OpCodeConstants.OPPOLL.equals( opCode ) ) {
			// ArtPollPacket : This is an ArtPoll packet, no other data is contained in this UDP packet
			return decodeArtPollPacket( packet, hexaBrut );
		} else if ( OpCodeConstants.OPTIMECODE.equals( opCode ) ) {
			// ArtTimePacket : OpTimeCode This is an ArtTimeCode packet. It is used to transport time code over the network.
			return decodeArtTimeCodePacket( packet, hexaBrut );
		}

		return artNetObject;
	}
	
	/**
	 * Decode an artTimeCodePacket.
	 * @param packet is the packet data
	 * @return the ArtPollPacketObject
	 */
	private static ArtTimeCode decodeArtTimeCodePacket( byte[] bytes, String hexaBrut ) {		
		final ArtTimeCode artTimeCode = new ArtTimeCode();
		artTimeCode.setFrameTime( (int) bytes[14] );
		artTimeCode.setSeconds( (int) bytes[15] );
		artTimeCode.setMinutes( (int) bytes[16] );
		artTimeCode.setHours( (int) bytes[17] );
		final int typeTimecode = (int) bytes[18];
		
		if ( typeTimecode == 0 ) {
			artTimeCode.setArtTimeCodeType( ArtTimeCodeType.FILM );
		} else if ( typeTimecode == 1 ) {
			artTimeCode.setArtTimeCodeType( ArtTimeCodeType.EBU );
		} else if ( typeTimecode == 2 ) {
			artTimeCode.setArtTimeCodeType( ArtTimeCodeType.DF );
		} else if ( typeTimecode == 3 ) {
			artTimeCode.setArtTimeCodeType( ArtTimeCodeType.SMPTE );
		}
		
		return artTimeCode;
	}
	
	/**
	 * Decode an artPollPacket.
	 * @param packet is the packet data
	 * @return the ArtPollPacketObject
	 */
	private static ArtPoll decodeArtPollPacket( byte[] bytes, String hexaBrut ) {	
		final ArtPoll artPoll = new ArtPoll();
			
		artPoll.setArtPollReplyWhenConditionsChanges( ByteUtilsArt.bitIsSet(bytes[12], 1) );
		artPoll.setSendMeDiagnosticsMessage( ByteUtilsArt.bitIsSet(bytes[12], 2) );

		if ( ByteUtilsArt.bitIsSet(bytes[12], 3) ) {
			artPoll.setNetworkCommunicationTypeDiagnosticsMessages( NetworkCommunicationTypeEnum.UNICAST );
		} else {
			artPoll.setNetworkCommunicationTypeDiagnosticsMessages( NetworkCommunicationTypeEnum.BROADCAST );
		}
		
		return artPoll;
	}
	
	/**
	 * Check the version of artnet.
	 * @return
	 */
	private static boolean checkVersion( byte[] packet, String hexaBrut ) {
		final int version = (int) packet[11];
		return ( version >= Constants.ART_NET_VERSION );
	}

	/**
	 * Convert byte to hexa.	
	 * @param barray is the byte array.
	 * @return the String in hexa value
	 */
	private static String byteArrayToHex(byte[] barray) {
	    char[] c = new char[barray.length * 2];
	    byte b;
	    for (int i = 0; i < barray.length; ++i)
	    {
	        b = ((byte)(barray[i] >> 4));
	        c[i * 2] = (char)(b > 9 ? b + 0x37 : b + 0x30);
	        b = ((byte)(barray[i] & 0xF));
	        c[i * 2 + 1] = (char)(b > 9 ? b + 0x37 : b + 0x30);
	    }

	    return new String(c);
	}
	
}
