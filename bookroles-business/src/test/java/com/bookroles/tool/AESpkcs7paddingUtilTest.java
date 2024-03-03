package com.bookroles.tool;

import com.bookroles.Constant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

/**
 * @Author: dlus91
 * @Date: 2023/9/21 9:28
 */
public class AESpkcs7paddingUtilTest {
    @Mock
    Logger logger;
    @InjectMocks
    AESpkcs7paddingUtil aespkcs7paddingUtil;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEncrypt() throws Exception {
        long currentMs = System.currentTimeMillis();
        String result = aespkcs7paddingUtil.encrypt("{\"row\":1}",Constant.getKeystoreMap(currentMs));
        Assert.assertEquals("NAjerHAXgnQi4Sc6OWSs+A==", result);
    }

    @Test
    public void testDecrypt() throws Exception {
        long currentMs = System.currentTimeMillis();
        String result = aespkcs7paddingUtil.decrypt("NAjerHAXgnQi4Sc6OWSs+A==",Constant.getKeystoreMap(currentMs));
        Assert.assertEquals("{\"row\":1}", result);
    }

    @Test
    public void testMain(){
        // 加密
        long lStart = System.currentTimeMillis();
//        String enString = new AESpkcs7paddingUtil().encrypt("abcd中文测试加标点符号！@#￥%……&*（+——）（*&~，。，；,,/;lkk;ki;'[p]./,'\\467646789");

        String enString = new AESpkcs7paddingUtil().encrypt("{\"row\":1}", Constant.getKeystoreMap(lStart));
        System.out.println("加密后的字串是：" + enString);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");

        // 解密
        lStart = System.currentTimeMillis();
        String deString = new AESpkcs7paddingUtil().decrypt(enString,Constant.getKeystoreMap(lStart));
        System.out.println("解密后的字串是：" + deString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时：" + lUseTime + "毫秒");
    }


}
