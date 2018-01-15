package com.example.ldsdk_xh.utils;

/**
 * Created by Administrator on 2016-06-27.
 */
public class ByteUtil {

    public static String byte2HexString(byte b[]) {
        return byte2HexString(b,"");
    }

    public static String byte2HexString(byte b[],String div) {
        return byte2HexString(b,b.length,div);
    }

    public static String byte2HexString(byte b[],int len,String div) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < len; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
            if (i < len - 1)
                hexString.append(div);
        }
        return hexString.toString().toUpperCase();
    }

    public static byte[] hexString2Byte(String ss) {
        return hexString2Byte(ss, "");
    }

    public static byte[] hexString2Byte(String ss,String div) {
        if (div != null && !div.equals("")) {
            ss = ss.replaceAll(div, "");
        }
        byte digest[] = new byte[ss.length() / 2];
        for(int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte)byteValue;
        }
        return digest;
    }

}
