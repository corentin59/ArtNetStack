/**
 * 
 */
package fr.azelart.artnetstack.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.BitSet;
import java.util.Map;

import fr.azelart.artnetstack.constants.Constantes;
import fr.azelart.artnetstack.constants.MagicNumbers;
import fr.azelart.artnetstack.domain.arttimecode.ArtTimeCode;
import fr.azelart.artnetstack.domain.controller.Controller;
import fr.azelart.artnetstack.domain.controller.ControllerGoodInput;
import fr.azelart.artnetstack.domain.controller.ControllerGoodOutput;
import fr.azelart.artnetstack.domain.controller.ControllerPortType;
import fr.azelart.artnetstack.domain.enums.PortInputOutputEnum;
import fr.azelart.artnetstack.domain.enums.PortTypeEnum;


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
	public static byte[] encodeArtPollPacket( Controller pControler ) throws IOException {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayOutputStream.write(ByteUtils.toByta( Constantes.ID ) );
		byteArrayOutputStream.write(MagicNumbers.MAGIC_NUMBER_0);
		byteArrayOutputStream.write(MagicNumbers.MAGIC_NUMBER_0);
		byteArrayOutputStream.write(32);
		byteArrayOutputStream.write(MagicNumbers.MAGIC_NUMBER_0);
		byteArrayOutputStream.write(new Integer(Constantes.ART_NET_VERSION).byteValue());
		byteArrayOutputStream.write(6);	// TalkToMe
		byteArrayOutputStream.write(MagicNumbers.MAGIC_NUMBER_0);	// Filler
		
		return byteArrayOutputStream.toByteArray();
	}
	
	public static byte[] encodeArtTimeCodePacket( ArtTimeCode artTimeCode ) throws IOException {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		// ID.
		byteArrayOutputStream.write( ByteUtils.toByta( Constantes.ID ) );
		byteArrayOutputStream.write( 0 );
		
		// OpTimeCode
		byteArrayOutputStream.write( ByteUtilsArt.in16toByte( 38656 ) );
		
		// Version
		byteArrayOutputStream.write(MagicNumbers.MAGIC_NUMBER_0);
		byteArrayOutputStream.write(new Integer(Constantes.ART_NET_VERSION).byteValue());
		
		// Filler 1 and 2
		byteArrayOutputStream.write(MagicNumbers.MAGIC_NUMBER_0);
		byteArrayOutputStream.write(MagicNumbers.MAGIC_NUMBER_0);
		
		// Frame
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( artTimeCode.getFrameTime() ) );
		
		// Seconds
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( artTimeCode.getSeconds() ) );
		
		// Minutes
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( artTimeCode.getMinutes() ) );
		
		// Hours
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( artTimeCode.getHours() ) );
		
		// Type
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( artTimeCode.getArtTimeCodeType().ordinal() ) );
		
		return byteArrayOutputStream.toByteArray();
	}
	
	/**
	 * Encode a ArtPollReply packet.
	 * @param pControler is a controller mapping.
	 * @return the raw we can send
	 * @throws IOException in error with byte array
	 */
	public static byte[] encodeArtPollReplyPacket( Controller pControler ) throws IOException {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		// Prepare newt trame
		artPollCounter++;
		
		// ID.
		byteArrayOutputStream.write( ByteUtils.toByta( Constantes.ID ) );
		byteArrayOutputStream.write( 0 );
		
		// ArtPollReply
		byteArrayOutputStream.write( 0 );
		byteArrayOutputStream.write( 33 );

		// IP
		final InetAddress inetAdress = InetAddress.getByName( Constantes.SERVER_IP );
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
		final Map<Integer, ControllerPortType> portsTypesMap = pControler.getPortTypeMap();
		ControllerPortType controlerPortType = null;
		BitSet bitSet = null;
		for ( int i=0; i!=Constantes.MAX_PORT; i++ ) {
			controlerPortType = portsTypesMap.get(i);
			// No port
			if ( controlerPortType == null ) {
				byteArrayOutputStream.write(ByteUtilsArt.in8toByte( 0 ));
			} else {
				bitSet = new BitSet(8);
				// First 4 bits (PROCOTOL)
				if (controlerPortType.getType().equals( PortTypeEnum.DMX512 )) {
					bitSet.set(0, 4, false);	// DMX
				} else if ( controlerPortType.getType().equals( PortTypeEnum.MIDI )) {
					bitSet.set(0, 3, false);	// MIDI
					bitSet.set(4, true);		// MIDI
				} else if ( controlerPortType.getType().equals( PortTypeEnum.AVAB )) {
					bitSet.set(0, 2, false);	// AVAB
					bitSet.set(3, true);		// AVAB
					bitSet.set(4, false);		// AVAB
				} else if ( controlerPortType.getType().equals( PortTypeEnum.COLORTRANCMX )) {
					bitSet.set(0, 2, false);	// COLORTRAN
					bitSet.set(3, 4, true);		// COLORTRAN
				} else if ( controlerPortType.getType().equals( PortTypeEnum.ADB )) {
					bitSet.set(0, 1, false);	// ADB
					bitSet.set(2, true);		// ADB
					bitSet.set(3, 4, false);	// ADB
				} else if ( controlerPortType.getType().equals( PortTypeEnum.ARTNET )) {
					bitSet.set(0, 1, false);	// ARTNET
					bitSet.set(2, true);		// ARTNET
					bitSet.set(3, false);		// ARTNET
					bitSet.set(4, true);		// ARTNET
				}
				// Set if this channel can input onto the Art-NetNetwork
				bitSet.set(5, controlerPortType.isInputArtNet());
				// Set is this channel can output data from the Art-Net Network
				bitSet.set(6, controlerPortType.isOutputArtNet());
				byteArrayOutputStream.write(bitSet.toByteArray());
			}
		}
		
		// Good Input
		final Map<Integer, ControllerGoodInput> portsGoodInputsMap = pControler.getGoodInputMapping();
		ControllerGoodInput controlerGoodInput = null;
		for ( int i=0; i!=Constantes.MAX_PORT; i++ ) {
			controlerGoodInput = portsGoodInputsMap.get(i);
			// No port
			if ( controlerGoodInput == null ) {
				byteArrayOutputStream.write(ByteUtilsArt.in8toByte( 0 ));
			} else {
				bitSet = new BitSet(8);
				bitSet.set(0, 1, false);	// Unused and transmitted as zero
				bitSet.set(2, controlerGoodInput.getReceivedDataError() );
				bitSet.set(3, controlerGoodInput.getDisabled() );
				bitSet.set(4, controlerGoodInput.getIncludeDMXTextPackets() );
				bitSet.set(5, controlerGoodInput.getIncludeDMXSIPsPackets() );
				bitSet.set(6, controlerGoodInput.getIncludeDMXTestPackets() );
				bitSet.set(7, controlerGoodInput.getDataReceived() );
				byteArrayOutputStream.write(bitSet.toByteArray());
			}
		}
		
		// Good Ouput
		final Map<Integer, ControllerGoodOutput> portsGoodOutputsMap = pControler.getGoodOutputMapping();
		ControllerGoodOutput controlerGoodOutput = null;
		for ( int i=0; i!=Constantes.MAX_PORT; i++ ) {
			controlerGoodOutput = portsGoodOutputsMap.get(i);
			// No port
			if ( controlerGoodOutput == null ) {
				byteArrayOutputStream.write(ByteUtilsArt.in8toByte( 0 ));
			} else {
				bitSet = new BitSet(8);
				bitSet.set(0, 1, false);	// Unused and transmitted as zero
				bitSet.set(1, controlerGoodOutput.getMergeLTP() );
				bitSet.set(2, controlerGoodOutput.getOutputPowerOn() );
				bitSet.set(3, controlerGoodOutput.getOutputmergeArtNet() );
				bitSet.set(4, controlerGoodOutput.getIncludeDMXTextPackets() );
				bitSet.set(5, controlerGoodOutput.getIncludeDMXSIPsPackets() );
				bitSet.set(6, controlerGoodOutput.getIncludeDMXTestPackets() );
				bitSet.set(7, controlerGoodOutput.getDataTransmited() );
				byteArrayOutputStream.write(bitSet.toByteArray());
			}
		}
		
		// Directions
		BitSet bitSetIn; 
		BitSet bitSetOut;
		final ByteArrayOutputStream byteArrayInTempOutputStream = new ByteArrayOutputStream();
		final ByteArrayOutputStream byteArrayOutTempOutputStream = new ByteArrayOutputStream();
		for ( int i=0; i!=Constantes.MAX_PORT; i++ ) {
			controlerPortType = portsTypesMap.get(i);
			bitSetIn = new BitSet(8);
			bitSetOut = new BitSet(8);
			
			// No port
			if ( controlerPortType == null || controlerPortType.getDirection() == null ) {
				bitSetIn.set(i, false);
				bitSetOut.set(i, false);
			} else if ( controlerPortType.getDirection().equals( PortInputOutputEnum.INPUT ) ) {
				bitSetIn.set(i, true);
			} else if ( controlerPortType.getDirection().equals( PortInputOutputEnum.OUTPUT ) ) {
				bitSetOut.set(i, true);
			} else if ( controlerPortType.getDirection().equals( PortInputOutputEnum.BOTH ) ) {
				bitSetIn.set(i, true);
				bitSetOut.set(i, true);
			} else {
				bitSetIn.set(i, false);
				bitSetOut.set(i, false);
			}
			
			
			if ( bitSetIn.isEmpty() ) {
				byteArrayInTempOutputStream.write(ByteUtilsArt.in8toByte( 0 ));
			} else {
				byteArrayInTempOutputStream.write(bitSetIn.toByteArray());
			}
			
			if ( bitSetOut.isEmpty() ) {
				byteArrayOutTempOutputStream.write(ByteUtilsArt.in8toByte( 0 ));
			} else {
				byteArrayOutTempOutputStream.write(bitSetOut.toByteArray());
			}
		}
		byteArrayOutputStream.write( byteArrayInTempOutputStream.toByteArray() );
		byteArrayOutputStream.write( byteArrayOutTempOutputStream.toByteArray() );
		
		// Screen
		bitSet = new BitSet(8);
		if ( pControler.getScreen() ) {
			// Ethernet data display
			bitSet.set(1, true);
			byteArrayOutputStream.write( bitSet.toByteArray() );
			
		} else {
			// Local data display
			byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		}
		
		// SwMacro (not implemented)
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		
		// SwRemote (not implemented)
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		
		// Spare (1+2+3), Not used, set to zero
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		
		// Style
		/*
		if ( pControler instanceof ControllerNode ) {
			byteArrayOutputStream.write( ByteUtilsArt.in8toByte( StyleConstants.ST_NODE ) );
		} else if ( pControler instanceof ControllerController ) {
			byteArrayOutputStream.write( ByteUtilsArt.in8toByte( StyleConstants.ST_CONTROLLER ) );
		} else if ( pControler instanceof ControllerMedia ) {
			byteArrayOutputStream.write( ByteUtilsArt.in8toByte( StyleConstants.ST_MEDIA ) );
		} else if ( pControler instanceof ControllerRoute ) {
			byteArrayOutputStream.write( ByteUtilsArt.in8toByte( StyleConstants.ST_ROUTE ) );
		} else if ( pControler instanceof ControllerBackup ) {
			byteArrayOutputStream.write( ByteUtilsArt.in8toByte( StyleConstants.ST_BACKUP ) );
		} else if ( pControler instanceof ControllerConfig ) {
			byteArrayOutputStream.write( ByteUtilsArt.in8toByte( StyleConstants.ST_CONFIG ) );
		} else if ( pControler instanceof ControllerVisual ) {
			byteArrayOutputStream.write( ByteUtilsArt.in8toByte( StyleConstants.ST_VISUAL ) );
		}*/
		
		// MAC
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		byteArrayOutputStream.write( ByteUtilsArt.in8toByte( 0 ) );
		
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
