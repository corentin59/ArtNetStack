/**
 * 
 */
package fr.azelart.artnetstack.packets;

import fr.azelart.artnetstack.constants.MagicNumbers;

/**
 * ArtPollReply Packet.
 * @author Corentin Azelart
 *
 */
public class ArtPollReplyPacket extends ArtNetPacket implements ArtNetPacketImp {
	
	/**
	 * Array containing the Node’s IP address. First array 
	 * entry is most significant byte of address.
	 */
	private String ipAdress;
	
	/**
	 * The Port is always 0x1936
	 * Transmitted low byte first.
	 */
	private int port = 0x1936;
	
	/**
	 * High byte of Node’s firmware revision number. The 
	 * Controller should only use this field to decide if a 
	 * firmware update should proceed. The convention is 
	 * that a higher number is a more recent release of firmware.
	 */
	private byte[] versInfoH = new byte[8];
	
	/**
	 * Low byte of Node’s firmware revision number
	 */
	private byte[] versInfo = new byte[8];
	
	/**
	 * Bits 14-8 of the 15 bit Port-Address are encoded into 
	 * the bottom 7 bits of this field. This is used in 
	 * combination with SubSwitch and SwIn[] or Sw”ut[] to 
	 * produce the full universe address.
	 */
	private byte[] netSwitch = new byte[8];
	
	/**
	 * Bits 7-4 of the 15 bit Port-Address are encoded into 
	 * the bottom 4 bits of this field. This is used in 
	 * combination with NetSwitch and SwIn[] or Sw”ut[] to 
	 * produce the full universe address.
	 */
	private byte[] subSwitch = new byte[8];
	
	/**
	 * The high byte of the oem value. 
	 */
	private byte[] oemHi = new byte[8];
	
	/**
	 * The low byte of the oem value. 
	 * The oem word describes the equipment vendor and the feature set available. Bit 15 high indicates extended features available.
	 * Current registered codes are defined in Table 2.
	 */
	private byte[] oem = new byte[8];
	
	/**
	 * This field contains the firmware version of the User 
	 * Bios Extension Area (UBEA). If the UBEA is not programmed, this field contains zero.
	 */
	private byte[] ubeaVersion = new byte[8];
	
	/**
	 * General Status register containing bit fields as follows.
	 */
	private byte[] status1 = new byte[8];
	
	/**
	 * The ESTA manufacturer code. These codes are used to 
	 * represent equipment manufacturer. They are assigned by ESTA.
	 * This field can be interpreted as two ASCII bytes representing the manufacturer initials.
	 */
	private byte[] estaManLo = new byte[8];
	
	/**
	 * Hi byte of above.
	 */
	private byte[] estaManHi = new byte[8];
	
	/**
	 * The array represents a null terminated short name for the Node. The Controller uses the ArtAddress packet 
	 * to program this string. Max length is 17 characters plus the null. This is a fixed length field, although the 
	 * string it contains can be shorter than the field.
	 */
	private char[] shortName = new char[18];
	
	/**
	 * 	The array represents a null terminated long name for the Node. The Controller uses the ArtAddress packet 
	 * to program this string. Max length is 63 characters plus the null. This is a fixed length field, although the 
	 * string it contains can be shorter than the field.
	 */

	private char[] longName = new char[64];
	
	/**
	 * The array is a textual report of the Node’s operating status or operational errors. It is primarily intended 
	 * for ‘engineering’ data rather than ‘end user’ data. The field is formatted as: “#xxxx [yyyy..] zzzzz…”
	 * xxxx is a hex status code as defined in Table 3. yyyy is a decimal counter that increments every time the 
	 * Node sends an ArtPollResponse. This allows the controller to monitor event changes in the Node. 
	 * zzzz is an English text string defining the status. This is a fixed length field, although the string it 
	 * contains can be shorter than the field.
	 */
	private char[] nodeReport = new char[64];
	
	/**
	 * The high byte of the word describing the number of input or output ports.
	 * The high byte is for future expansion and is currently zero.
	 */
	private byte[] numPortsHi = new byte[8];
	
	/**
	 * The low byte of the word describing the number of input or output ports. If number of inputs is not equal 
	 * to number of outputs, the largest value is taken. Zero is a legal value if no input or output ports are 
	 * implemented. The maximum value is 4.
	 */
	private byte[] numPortsLo = new byte[8];
	
	/**
	 * The low byte of the word describing the number of input or output ports.
	 * If number of inputs is not equal to number of outputs, the largest value is taken.
	 * Zero is a legal value if no input or output ports are implemented.
	 * The maximum value is 4.
	 */
	private byte[] portsTypes = new byte[4];
	
	/**
	 * This array defines input status of the node.
	 */
	private byte[] goodInput = new byte[4];
	
	/**
	 * This array defines output status of the node.
	 */
	private byte[] goodOutput = new byte[4];
	
	/**
	 * Bits 3-0 of the 15 bit Port-Address for each of the 4 
	 * possible input ports are encoded into the low nibble.
	 */
	private byte[] swIn = new byte[4];
	
	/**
	 * Bits 3-0 of the 15 bit Port-Address for each of the 4 
	 * possible output ports are encoded into the low nibble
	 */
	private byte[] swOut = new byte[4];
	
	/**
	 * Set to 00 when video display is showing local data.
	 * Set to 01 when video is showing ethernet data.
	 */
	private byte[] swVideo = new byte [8];
	
	/**
	 * If the Node supports macro key inputs, this byte represents the trigger values. The Node is responsible
	 * for ‘debouncing’ inputs. When the ArtPollReply is set to transmit automatically, (TalkToMe Bit 1), the 
	 * ArtPollReply will be sent on both key down and key up events. However, the Controller should not assume 
	 * that only one bit position has changed. The Macro inputs are used for remote event triggering or cueing.
	 * Bit fields are active high.
	 */
	private byte[] swMacro = new byte[8];
	
	/**
	 * If the Node supports remote trigger inputs, this byte represents the trigger values. The Node is responsible 
	 * for ‘debouncing’ inputs. When the ArtPollReply is set to transmit automatically, (TalkToMe Bit 1), the 
	 * ArtPollReply will be sent on both key down and key up events. However, the Controller should not assume 
	 * that only one bit position has changed. The Remote inputs are used for remote event triggering or cueing.
	 * Bit fields are active high.
	 */
	private byte[] swRemote = new byte[8];
	
	/**
	 * Not used, set to zero
	 */
	private int spare1 = 0;
	
	/**
	 * Not used, set to zero
	 */
	private int spare2 = 0;
	
	/**
	 * Not used, set to zero
	 */
	private int spare3 = 0;
	
	/**
	 * The Style code defines the equipment style of the 
	 * device. See Table 4 for current Style codes.
	 */
	private int style;
	
	/**
	 * MAC Address Hi Byte. Set to zero if node cannot supply this information.
	 */
	private int macHi;
	
	/**
	 * MAC Address.
	 */
	private int mac1;
	
	/**
	 * MAC Address.
	 */
	private int mac2;
	
	/**
	 * MAC Address.
	 */
	private int mac3;
	
	/**
	 * MAC Address.
	 */
	private int mac4;
	
	/**
	 * MAC Address Lo Byte.
	 */
	private int macLo;
	
	/**
	 * If this unit is part of a larger or modular product, this 
	 * is the IP of the root device.
	 */
	private byte[] bindIp = new byte[4];
	
	/**
	 * Set to zero if no binding, otherwise this number represents the order of bound devices. A lower 
	 * number means closer to root device. A value of 1 means root device.
	 */
	private int bindIndex;
	
	/**
	 * Product supports web browser configuration.
	 */
	private byte[] status2 = new byte[4];
	
	/**
	 * Transmit as zero. For future expansion.
	 */
	private int filler = MagicNumbers.MAGIC_NUMBER_0;
}

