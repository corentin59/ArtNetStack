package fr.azelart.artnetstack.utils;

public final class ByteUtilsArt {
	
	public static byte[] in8toByte( int i ) {
		return new byte[] {(byte)((i >> 0) & 0xff)};
	}
	
	public static byte[] in16toByte( int data ) {
		return new byte[] {(byte)((data >> 0) & 0xff),(byte)((data >> 8) & 0xff)};
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}
