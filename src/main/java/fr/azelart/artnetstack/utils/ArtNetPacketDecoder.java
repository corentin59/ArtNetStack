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

import java.net.InetAddress;
import java.net.UnknownHostException;

import fr.azelart.artnetstack.constants.Constants;
import fr.azelart.artnetstack.constants.OpCodeConstants;
import fr.azelart.artnetstack.domain.artdmx.ArtDMX;
import fr.azelart.artnetstack.domain.artnet.ArtNetObject;
import fr.azelart.artnetstack.domain.artpoll.ArtPoll;
import fr.azelart.artnetstack.domain.artpollreply.ArtPollReply;
import fr.azelart.artnetstack.domain.artpollreply.ArtPollReplyStatus;
import fr.azelart.artnetstack.domain.arttimecode.ArtTimeCode;
import fr.azelart.artnetstack.domain.arttimecode.ArtTimeCodeType;
import fr.azelart.artnetstack.domain.enums.IndicatorStateEnum;
import fr.azelart.artnetstack.domain.enums.NetworkCommunicationTypeEnum;
import fr.azelart.artnetstack.domain.enums.UniverseAddressProgrammingAuthorityEnum;

/**
 * ArtNetPacket decoder.
 * @author Corentin Azelart.
 */
public class ArtNetPacketDecoder {

	public static ArtNetObject decodeArtNetPacket(byte[] packet, InetAddress ip) {
		
		// The ArtNetPacket.
		ArtNetObject artNetObject = null;
		
		// Set generals infos
		final String hexaBrut = byteArrayToHex(packet);
		final String id = new String(packet, 0, 7);
		final String opCode = hexaBrut.substring(16, 20);

		// Yes, it's a ArtNetPacket
		if (!"Art-Net".equals(id)) {
			return null;
		}

		/*
		 * Dicover the type of the packet.
		 * Please refer to OpcodeTable.
		 */
		if (OpCodeConstants.OPPOLL.equals(opCode)) {
			/*
			 * ArtPollPacket : This is an ArtPoll packet,
			 * no other data is contained in this UDP packet
			 */
			if (!checkVersion(packet, hexaBrut)) {
				return null;
			}
			return decodeArtPollPacket(packet, hexaBrut);
		} else if (OpCodeConstants.OPTIMECODE.equals(opCode)) {
			/*
			 * ArtTimePacket : OpTimeCode
			 * This is an ArtTimeCode packet.
			 * It is used to transport time code over the network.
			 */
			if (!checkVersion(packet, hexaBrut)) {
				return null;
			}
			return decodeArtTimeCodePacket(packet, hexaBrut);
		} else if (OpCodeConstants.OPPOLLREPLY.equals(opCode)) {
			// ArtPollReply : This is a ArtPollReply packet.
			return decodeArtPollReplyPacket(packet, hexaBrut, ip);
		} else if (OpCodeConstants.OPOUTPUT.equals(opCode)) {
			// ArtDMX
			return decodeArtDMXPacket(packet, hexaBrut);
		}

		return artNetObject;
	}

	/**
	 * Decode an artPollReplyPacket.
	 * @param bytes is the packet data
	 * @param hexaBrut is the ascii data
	 * @return ArtPollReply
	 */
	private static ArtPollReply decodeArtPollReplyPacket( byte[] bytes, String hexaBrut, InetAddress ip ) {
		final ArtPollReply artPollReply = new ArtPollReply();

		// IP Adress (4*8)
		final byte[] address = new byte[4];
		System.arraycopy(bytes, 10, address, 0, 4);
		try {
			final InetAddress inetAdress = InetAddress.getByAddress(address);
			artPollReply.setIp(inetAdress.getHostAddress());
		} catch (UnknownHostException e) {
			artPollReply.setIp(null);
		}

		// Port (2*8)
		artPollReply.setPort(ByteUtilsArt.byte2toIn(bytes, 14));

		// Version High (1*8)
		artPollReply.setVersionH(bytes[16]);

		// Version Low (1*8)
		artPollReply.setVersionL(bytes[15]);
		
		// Subnet (1*8) and subswtich (1*8)
		artPollReply.setSubNet(String.format("%02X", bytes[18]));
		artPollReply.setSubSwitch(String.format("%02X", bytes[19]));
		
		// Oem Hi (1*8) + Oem (1*8)
		artPollReply.setOemHexa(String.format("%02X", bytes[20]) + String.format("%02X", bytes[21]));
		
		// UBEA Version (1*8) / 0 if not programmed
		artPollReply.setUbeaVersion( bytes[22] );
		
		// Status area
		final ArtPollReplyStatus artPollReplyStatus = new ArtPollReplyStatus();
		artPollReplyStatus.setUbeaPresent(ByteUtilsArt.bitIsSet(bytes[23] , 0));
		artPollReplyStatus.setRdmCapable(ByteUtilsArt.bitIsSet(bytes[23] , 1));
		artPollReplyStatus.setBootRom(ByteUtilsArt.bitIsSet(bytes[23] , 2));

		if (ByteUtilsArt.bitIsSet(bytes[23] , 5) && ByteUtilsArt.bitIsSet(bytes[23] , 4)) {
			artPollReplyStatus.setProgrammingAuthority( UniverseAddressProgrammingAuthorityEnum.NOT_USED );
		} else if (!ByteUtilsArt.bitIsSet(bytes[23] , 5) && ByteUtilsArt.bitIsSet(bytes[23] , 4)) {
			artPollReplyStatus.setProgrammingAuthority( UniverseAddressProgrammingAuthorityEnum.FRONT_PANEL );
		} else if (ByteUtilsArt.bitIsSet(bytes[23] , 5) && !ByteUtilsArt.bitIsSet(bytes[23] , 4)) {
			artPollReplyStatus.setProgrammingAuthority( UniverseAddressProgrammingAuthorityEnum.NETWORK );
		} else if (!ByteUtilsArt.bitIsSet(bytes[23] , 5) && !ByteUtilsArt.bitIsSet(bytes[23] , 4)) {
			artPollReplyStatus.setProgrammingAuthority( UniverseAddressProgrammingAuthorityEnum.UNKNOW );
		}
		
		if (ByteUtilsArt.bitIsSet(bytes[23] , 7) && ByteUtilsArt.bitIsSet(bytes[23] , 6)) {
			artPollReplyStatus.setIndicatorState( IndicatorStateEnum.NORMAL_MODE );
		} else if (!ByteUtilsArt.bitIsSet(bytes[23] , 7) && ByteUtilsArt.bitIsSet(bytes[23] , 6)) {
			artPollReplyStatus.setIndicatorState( IndicatorStateEnum.LOCATE_MODE );
		} else if (ByteUtilsArt.bitIsSet(bytes[23] , 7) && !ByteUtilsArt.bitIsSet(bytes[23] , 6)) {
			artPollReplyStatus.setIndicatorState( IndicatorStateEnum.MUTE_MODE );
		} else if (!ByteUtilsArt.bitIsSet(bytes[23] , 7) && !ByteUtilsArt.bitIsSet(bytes[23] , 6)) {
			artPollReplyStatus.setIndicatorState( IndicatorStateEnum.UNKNOW );
		}

		artPollReply.setArtPollReplyStatus(artPollReplyStatus);

		// EstaManHi (1*8) + EstaManLow (1*8)
		artPollReply.setEsta(new String (bytes, 24, 2));

		// Short Name
		artPollReply.setShortName(new String (bytes, 26, 18));

		// Long Name
		artPollReply.setLongName(new String (bytes, 44, 64));

		// Real ip
		artPollReply.setPhysicalIp( ip.getHostAddress() );
		
		return artPollReply;
	}

	/**
	 * Decode an artTimeCodePacket.
	 * @param bytes is the packet data
	 * @return the ArtPollPacketObject
	 */
	private static ArtTimeCode decodeArtTimeCodePacket(byte[] bytes, String hexaBrut) {
		final ArtTimeCode artTimeCode = new ArtTimeCode();
		artTimeCode.setFrameTime((int) bytes[14]);
		artTimeCode.setSeconds((int) bytes[15]);
		artTimeCode.setMinutes((int) bytes[16]);
		artTimeCode.setHours((int) bytes[17]);
		final int typeTimecode = (int) bytes[18];

		if (typeTimecode == 0) {
			artTimeCode.setArtTimeCodeType(ArtTimeCodeType.FILM);
		} else if (typeTimecode == 1) {
			artTimeCode.setArtTimeCodeType(ArtTimeCodeType.EBU);
		} else if (typeTimecode == 2) {
			artTimeCode.setArtTimeCodeType(ArtTimeCodeType.DF);
		} else if (typeTimecode == 3) {
			artTimeCode.setArtTimeCodeType(ArtTimeCodeType.SMPTE);
		}
		return artTimeCode;
	}

	/**
	 * Decode an artPollPacket.
	 * @param bytes is the packet data
	 * @return the ArtPollPacketObject
	 */
	private static ArtPoll decodeArtPollPacket(byte[] bytes, String hexaBrut) {	
		final ArtPoll artPoll = new ArtPoll();

		artPoll.setArtPollReplyWhenConditionsChanges(ByteUtilsArt.bitIsSet(bytes[12], 1));
		artPoll.setSendMeDiagnosticsMessage(ByteUtilsArt.bitIsSet(bytes[12], 2));

		if (ByteUtilsArt.bitIsSet(bytes[12], 3)) {
			artPoll.setNetworkCommunicationTypeDiagnosticsMessages(NetworkCommunicationTypeEnum.UNICAST);
		} else {
			artPoll.setNetworkCommunicationTypeDiagnosticsMessages(NetworkCommunicationTypeEnum.BROADCAST);
		}

		return artPoll;
	}

	/**
	 * Decode an artDMX packet.
	 * @param bytes is the packet data
	 * @param hexaBrut is hexa packet
	 * @return an ArtDMX packet.
	 */
	private static ArtDMX decodeArtDMXPacket(byte[] bytes, String hexaBrut) {
		final ArtDMX artDMX = new ArtDMX();

		// Sequence (1*8)
		artDMX.setSequence(bytes[12] & Constants.INT_ESCAP);

		// Physical (1*8)
		artDMX.setPhysicalPort(bytes[13] & Constants.INT_ESCAP);

		// Subnet (1*8) and subswtich (1*8)
		artDMX.setSubNet(String.format("%02X", bytes[14]));
		artDMX.setSubSwitch(String.format("%02X", bytes[15]));

		// Length of DMX data (1*8)
		artDMX.setLengthHi(bytes[16] & Constants.INT_ESCAP);

		// Low Byte of above. (1*8)
		artDMX.setLength(bytes[17] & Constants.INT_ESCAP);

		// An variable length array of DMX512 lighting data
		final byte[] dmx = new byte[Constants.DMX_512_SIZE];
		System.arraycopy(bytes, 18, dmx, 0, Constants.DMX_512_SIZE);
		artDMX.setData(byteArrayToIntArray(dmx));

		return artDMX;
	}

	/**
	 * Check the version of artnet.
	 * @return
	 */
	private static boolean checkVersion(byte[] packet, String hexaBrut) {
		final int version = (int) packet[11];
		return (version >= Constants.ART_NET_VERSION);
	}

	/**
	 * Convert byte to hexa.
	 * @param barray is the byte array.
	 * @return the String in hexa value
	 */
	private static String byteArrayToHex(final byte[] barray) {
	    char[] c = new char[barray.length * 2];
	    byte b;
	    for (int i = 0; i < barray.length; ++i) {
	        b = ((byte)(barray[i] >> 4));
	        c[i * 2] = (char)(b > 9 ? b + 0x37 : b + 0x30);
	        b = ((byte)(barray[i] & 0xF));
	        c[i * 2 + 1] = (char)(b > 9 ? b + 0x37 : b + 0x30);
	    }

	    return new String(c);
	}

	/**
	 * Transform a byte array to int array.
	 * @param in a array in bytes
	 * @return a array in int
	 */
	private static int[] byteArrayToIntArray(final byte[] in) {
		final int[] output = new int[in.length];
		for (int i = 0; i != in.length; i++) {
			output[i] = in[i] & Constants.INT_ESCAP;
		}
		return output;
	}
}
