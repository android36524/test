package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/*
 * 文件名：Base64
 * 描    述：Base64编码工具类
 * 作    者：费振龙
 * 时    间：2015-09-18
 * 版    本：V1.0
 */
public class Base64 {

    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    /*
     * 方法名：encode(byte[] data) 
     * 功  能：对指定的字符数组进行Base64反编码 
     * 参  数：byte[] data - 字符数组 
     * 返回值：反编码后的字符串
     */
    public static String encode(byte[] data) {
        int start = 0;
        int len = data.length;
        StringBuilder buf = new StringBuilder(data.length * 3 / 2);
        int end = len - 3;
        int i = start;
        int n = 0;
        while (i <= end) {
            int d = (((data[i]) & 0x0ff) << 16) | (((data[i + 1]) & 0x0ff) << 8) | ((data[i + 2]) & 0x0ff);
            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append(legalChars[d & 63]);
            i += 3;
            if (n++ >= 14) {
                n = 0;
                buf.append(" ");
            }
        }
        if (i == start + len - 2) {
            int d = (((data[i]) & 0x0ff) << 16) | (((data[i + 1]) & 255) << 8);
            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = ((data[i]) & 0x0ff) << 16;
            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append("==");
        }
        return buf.toString();
    }
 /*
     * 方法名：decode(char ) 
     * 功  能：对指定的字符进行Base64编码 
     * 参  数：char c - 字符 
     * 返回值：无
     */
    private static int decode(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (c) - 65;
        } else if (c >= 'a' && c <= 'z') {
            return (c) - 97 + 26;
        } else if (c >= '0' && c <= '9') {
            return (c) - 48 + 26 + 26;
        } else {
            switch (c) {
                case '+':
                    return 62;
                case '/':
                    return 63;
                case '=':
                    return 0;
                default:
                    throw new RuntimeException("unexpected code: " + c);
            }
        }
    }
     /*
     * 方法名：decode(String s) 
     * 功  能：对指定的字符串进行Base64编码 
     * 参  数：String s - 字符串 
     * 返回值：字符串byte数组
     */
    public static byte[] decode(String s) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            decode(s, bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        byte[] decodedBytes = bos.toByteArray();
        try {
            bos.close();
            bos = null;
        } catch (IOException ex) {
            System.err.println("Error while decoding BASE64: " + ex.toString());
        }
        return decodedBytes;
    }
     /*
     * 方法名：decode(String s, OutputStream os) 
     * 功  能：对指定的字符串进行Base64编码写入输出流 
     * 参  数：String s - 字符串 
     * 参  数：OutputStream os - 输出流对象 
     * 返回值：无
     */
    private static void decode(String s, OutputStream os) throws IOException {
        int i = 0;
        int len = s.length();
        while (true) {
            while (i < len && s.charAt(i) <= ' ') {
                i++;
            }
            if (i == len) {
                break;
            }
            int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6) + (decode(s.charAt(i + 3)));
            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=') {
                break;
            }
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=') {
                break;
            }
            os.write(tri & 255);
            i += 4;
        }
    }
}
