package com.bookroles.tool;

import com.bookroles.vo.LocalProvider;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.*;

/**
 * @Author: dlus91
 * @Date: 2023/9/13 11:32
 */
//todo jdk8加解密受限制 需升级jdk
public class AESpkcs7paddingUtil {

    final static Logger logger = LoggerFactory.getLogger(AESpkcs7paddingUtil.class);

    /**
     * 密钥算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     * Java 6支持PKCS5Padding填充方式
     * Bouncy Castle支持PKCS7Padding填充方式
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    /**
     * 偏移量，只有CBC模式才需要
     */
//    private final static String ivParameter = "0000000000000000";
//    private final static String ivParameter = "bookrolesABcDEFh";

    /**
     * AES要求密钥长度为128位或192位或256位，java默认限制AES密钥长度最多128位
     */
//    public static String sKey="a90e7d2eb24c4b8b88994ab065e377d3";

    /**
     * 编码格式
     */
    public static final String ENCODING = "utf-8";


    static {
        BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
        //如果是PKCS7Padding填充方式，则必须加上下面这行
        Security.addProvider(bouncyCastleProvider);
    }

    /**
     * AES加密
     * @param source    源字符串
     * @return  加密后的密文
     * @throws Exception
     */
    public String encrypt(String source,Map<String,String> keyMap) {
        byte[] decrypted = new byte[0];
        try {
            byte[] sourceBytes = source.getBytes(ENCODING);
            byte[] keyBytes = keyMap.get("key").getBytes(ENCODING);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            IvParameterSpec iv = new IvParameterSpec(keyMap.get("iv").getBytes(ENCODING));

            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM),iv);
            decrypted = cipher.doFinal(sourceBytes);
        } catch (UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException |
                 InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException |
                 NoSuchPaddingException e) {
            logger.error("加密失败，密文为：{}。 异常信息：{}" ,source,e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("加密失败");
        }
        return encodeBase64String(decrypted);
    }

    /**
     * AES解密
     * @param encryptStr    加密后的密文
     * @return  源字符串
     * @throws Exception
     */
    public String decrypt(String encryptStr, Map<String,String> keyMap) {
        String decryptBody = "";
        try {
            byte[] sourceBytes = decodeBase64(encryptStr);
            byte[] keyBytes = keyMap.get("key").getBytes(ENCODING);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            IvParameterSpec iv = new IvParameterSpec(keyMap.get("iv").getBytes(ENCODING));
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM),iv);
            byte[] decoded = cipher.doFinal(sourceBytes);
            decryptBody = new String(decoded, ENCODING);
        } catch (UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException |
                 InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException |
                 NoSuchPaddingException e) {
            logger.error("解密失败，密文为：{}。 异常信息：{}" ,encryptStr,e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("解密失败");
        }
        return decryptBody;
    }

}
