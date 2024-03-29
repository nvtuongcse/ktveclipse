package vn.com.sonca.Lyric;

import vn.com.sonca.MyLog.MyLog;

public class ConvertData {
	
	public static int ByteToInt(byte[] bytes){
		// bytesToHex(bytes);
		bytes = new byte[] {bytes[3] , bytes[2] , bytes[1] , bytes[0]};
		// bytesToHex(bytes);
		return ((((int) bytes[0] & 0xff) << 24) | (((int) bytes[1] & 0xff) << 16)
				| (((int) bytes[2] & 0xff) << 8) | (((int) bytes[3] & 0xff) << 0));
	}
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
		String re = new String(hexChars);
		MyLog.e("ConvertData", "bytesToHex : " + re);
	    return re;
	}

}
