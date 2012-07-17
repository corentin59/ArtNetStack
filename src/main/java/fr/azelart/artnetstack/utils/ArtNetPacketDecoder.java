/**
 * 
 */
package fr.azelart.artnetstack.utils;

import fr.azelart.artnetstack.constants.Constantes;
import fr.azelart.artnetstack.constants.OpCodeConstants;
import fr.azelart.artnetstack.domain.artnet.ArtNetObject;
import fr.azelart.artnetstack.domain.artpoll.ArtPoll;
import fr.azelart.artnetstack.domain.arttimecode.ArtTimeCode;

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
		return new ArtTimeCode();
	}
	
	/**
	 * Decode an artPollPacket.
	 * @param packet is the packet data
	 * @return the ArtPollPacketObject
	 */
	private static ArtPoll decodeArtPollPacket( byte[] bytes, String hexaBrut ) {	
		return new ArtPoll();
	}
	
	/**
	 * Check the version of artnet.
	 * @return
	 */
	private static boolean checkVersion( byte[] packet, String hexaBrut ) {
		final int version = (int) packet[11];
		return ( version >= Constantes.ART_NET_VERSION );
	}

	/**
	 * Convert byte to hexa.	
	 * @param barray is the byte array.
	 * @return the String in hexa value
	 */
	private static String byteArrayToHex(byte[] barray)
	{
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
