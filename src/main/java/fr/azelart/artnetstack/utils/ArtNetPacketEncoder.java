/**
 * 
 */
package fr.azelart.artnetstack.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;

import fr.azelart.artnetstack.constants.Constantes;
import fr.azelart.artnetstack.constants.OpCodeConstants;
import fr.azelart.artnetstack.domain.Controler;
import fr.azelart.artnetstack.packets.ArtPollPacket;
import fr.azelart.artnetstack.packets.ArtPollReplyPacket;


/**
 * Encoder for ArtNet Packets
 * @author Corentin Azelart.
 *
 */
public final class ArtNetPacketEncoder {
	
	private static Integer artPollCounter = 1;

	/**
	 * Build an ArtPollPacket.
	 * @throws IOException 
	 */
	public static byte[] encodeArtPollPacket( Controler pControler ) throws IOException {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayOutputStream.write( ByteUtils.toByta( Constantes.ID ) );
		byteArrayOutputStream.write( 0 );
		byteArrayOutputStream.write( 0 );
		byteArrayOutputStream.write( 32 );
		byteArrayOutputStream.write( 0 );
		byteArrayOutputStream.write( new Integer( Constantes.ART_NET_VERSION ).byteValue() );
		byteArrayOutputStream.write( 6 );	// TalkToMe
		byteArrayOutputStream.write( 0 );	// Filler
		
		return byteArrayOutputStream.toByteArray();
	}
	
	
	public static byte[] encodeArtPollReplyPacket( Controler pControler ) throws IOException {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		// ID.
		byteArrayOutputStream.write( ByteUtils.toByta( Constantes.ID ) );
		byteArrayOutputStream.write( 0 );
		
		// ArtPollReply
		byteArrayOutputStream.write( 0 );
		byteArrayOutputStream.write( 33 );

		// IP
		final InetAddress inetAdress = InetAddress.getByName( "192.168.1.10" );
		byteArrayOutputStream.write( inetAdress.getAddress() );
		
		// Port
		byteArrayOutputStream.write( ByteUtilsArt.in16toByte( Constantes.SERVER_PORT_HUMANS ) );
		
		// Version Hight
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( Constantes.VERSION_LIB_HIGHT ));
		
		// Version Low
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( Constantes.VERSION_LIB_LOW ));
		
		// Net Switch
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 2 ) );

		// Oem and UBEA
		byteArrayOutputStream.write( ByteUtilsArt.hexStringToByteArray( "0x00ff" ) );
		
		// Status1
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 199 ) );
		
		// Manufactor code
		byteArrayOutputStream.write( ByteUtils.toByta( "CZ" ) );
		
		// ShotName
		byteArrayOutputStream.write( ByteUtils.toByta( encodeString(Constantes.SHORT_NAME, 18 ) ) );
		
		// LongName
		byteArrayOutputStream.write( ByteUtils.toByta( encodeString(Constantes.LONG_NAME, 64 ) ) );
		
		//Node report
		final int vArtPollCounter = artPollCounter + 1;
		final StringBuffer nodeReport = new StringBuffer();
		nodeReport.append( "#" ).append( "0x0000" );	// Debug mode, see table 3
		nodeReport.append( "[" ).append( vArtPollCounter ).append( "]" );
		nodeReport.append( "ok" );
		byteArrayOutputStream.write( ByteUtils.toByta( encodeString( nodeReport.toString(), 64 ) ) );
		
		// NumPortHi (0, future evolution of ArtNet protocol)
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		
		// NumPortLo (Between 0 and 4, max is 4)
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 4 ) );
		
		// Port Type
		
		
		artPollCounter++;
		return byteArrayOutputStream.toByteArray();
	}

	/**
	 * Encode string with finals white spaces.
	 * @param text is text
	 * @param size is max size
	 * @return the string
	 */
	private static String encodeString( String text, int size ) {
		final StringBuffer sb = new StringBuffer();
		sb.append( text );
		for ( int i=text.length(); i!=size; i++ ) {
			sb.append( " " );
		}
		return sb.toString();
	}

}
