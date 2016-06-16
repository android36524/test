package utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/*
 * 文件名：DesUtil
 * 描    述：Des加密解密工具类
 * 作    者：费振龙
 * 时    间：2015-09-18
 * 版    本：V1.0
 */
public class DesUtil {
    // 密钥
    private final static String secretKey = "q2ZBnl7Ef5p1*@JQq2ZBnl7Ef5p1*@JQ";
    // 向量
    private final static String iv = "01234567";
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";
    /*
     * 方法名：encode(String plainText) 
     * 功  能：对字符串进行DES加密 
     * 参  数：String plainText - 待加密字符串 
     * 返回值：加密后字符串
     */
    public static String encode(String plainText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64.encode(encryptData);
    }
    /*
     * 方法名：decode(String plainText) 
     * 功  能：对字符串进行DES解密 
     * 参  数：String plainText - 待解密字符串 
     * 返回值：解密后字符串
     */
    public static String decode(String encryptText) throws Exception {

        //增加加密文本 如果有空格 替换为 +
        encryptText = encryptText.replace(" ", "+");
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
        return new String(decryptData, encoding);
    }
}
