package com.bookroles.tool;

import java.io.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;
/**
 * @Author: dlus91
 * @Date: 2023/9/23 0:28
 */
public class RSAEntryDemo {



    public static void main(String[] args) {
        try {
            //1.从密钥库中取私钥
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream ksfis = new FileInputStream("H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\teststore");
            BufferedInputStream ksbufin = new BufferedInputStream(ksfis);

            // open keystore and get private key
            // alias is 'signLeal', kpasswd/spasswd is 'vagrant'
            ks.load(ksbufin, "teststore".toCharArray());
            PrivateKey prikey = (PrivateKey) ks.getKey("signLegal", "teststore".toCharArray());

            //2.根据命令行参数取公钥
            FileInputStream certfis = new FileInputStream("H:\\programmer\\Interviewspace\\bookroles\\bookroles-business-data\\test.crt");
            java.security.cert.CertificateFactory cf =
                    java.security.cert.CertificateFactory.getInstance("X.509");
            java.security.cert.Certificate cert = cf.generateCertificate(certfis);
            PublicKey pubKey = cert.getPublicKey();


            //3.使用公钥进行加密
            String data = "测试数据";
            //构建加密解密类
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);//设置为加密模式
            byte[] jmdata = cipher.doFinal(data.getBytes());
            //打印加密后数据
            System.out.println(bytesToHexString(jmdata));
            //改为解密模式进行解密
            cipher.init(Cipher.DECRYPT_MODE, prikey);//会用私钥解密
            jmdata = cipher.doFinal(jmdata);
            System.out.println(new String(jmdata));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    //这个方法用于把二进制转换成ASCII字符串。
    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null)
            return "null!";
        int len = bytes.length;
        StringBuilder ret = new StringBuilder(2 * len);

        for (int i = 0; i < len; ++i) {
            int b = 0xF & bytes[(i)] >> 4;
            ret.append("0123456789abcdef".charAt(b));
            b = 0xF & bytes[(i)];
            ret.append("0123456789abcdef".charAt(b));
        }

        return ret.toString();
    }
}
