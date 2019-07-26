package com.github.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class UtilTest {

    @Test
    public void encode() {
        Assert.assertEquals("", U.urlEncode(null));
        Assert.assertEquals("", U.urlEncode(""));

        String source = "name=中文&id=123&n=1+2   &n=2";
        String encode = U.urlEncode(source);
        Assert.assertNotEquals(source, encode);
        Assert.assertEquals(source, U.urlDecode(encode));
    }

    @Test
    public void chinese() {
        Assert.assertFalse(U.checkChinese(null));
        Assert.assertFalse(U.checkChinese(""));
        Assert.assertFalse(U.checkChinese("wqiroewfds123$%^&*("));

        Assert.assertTrue(U.checkChinese("wqiroewfds中123$%^&*("));
    }

    @Test
    public void phone() {
        Assert.assertFalse(U.checkPhone(null));
        Assert.assertFalse(U.checkPhone(""));
        Assert.assertFalse(U.checkPhone("131-1234-5678"));
        Assert.assertFalse(U.checkPhone("131 1234 5678"));
        Assert.assertFalse(U.checkPhone("131-1234 5678"));
        Assert.assertFalse(U.checkPhone("1311234678"));

        Assert.assertTrue(U.checkPhone("13112345678"));
        Assert.assertTrue(U.checkPhone("12112345678"));
    }

    @Test
    public void image() {
        Assert.assertFalse(U.checkImage(null));
        Assert.assertFalse(U.checkImage(""));
        Assert.assertFalse(U.checkImage("/tmp/image/fdwqrewqiofds.giff"));
        Assert.assertFalse(U.checkImage("afdwruewqrewq.abc"));

        Assert.assertTrue(U.checkImage("/tmp/ufio1u8231/abc.png"));
        Assert.assertTrue(U.checkImage("http://abc.xyz.com/uire4ui231.jpg"));
        Assert.assertTrue(U.checkImage("D:\\image\\中.bmp"));
        Assert.assertTrue(U.checkImage(".bmp"));
    }

    @Test
    public void email() {
        Assert.assertFalse(U.checkEmail(null));
        Assert.assertFalse(U.checkEmail(""));
        Assert.assertFalse(U.checkEmail("1$%^&*23-iurew@xyz.13s-rew.com"));
        Assert.assertFalse(U.checkEmail("-123@xyz.com"));

        Assert.assertTrue(U.checkEmail("abc-xyz@126.com"));
        Assert.assertTrue(U.checkEmail("abc@126.com"));
        Assert.assertTrue(U.checkEmail("10010@qq.com"));
        Assert.assertTrue(U.checkEmail("_abc-def@123-hij.uvw_xyz.com"));
        Assert.assertTrue(U.checkEmail("123-iurew@xyz.13s-rew.com"));
    }

    @Test
    public void sign() {
        Map<String, String[]> paramMap = A.linkedMaps(
                "id", new String[] { "123" },
                "type", new String[] { "2" },
                "phone", new String[] { "13012345678" },
                "name", new String[] { "xx" },
                "desc", new String[] { "中文" }
        );
        System.out.println("准备发送的数据: " + U.formatParam(paramMap) + "\n");

        paramMap = SignUtil.handleSign(paramMap);
        System.out.println("使用默认 key 真正发送的数据: " + U.formatParam(paramMap));
        SignUtil.checkSign(paramMap);
        System.out.println("使用默认 key 检查通过");

        System.out.println();

        String key = "123";
        paramMap = SignUtil.handleSign(paramMap, key);
        System.out.println("使用指定 key 真正发送的数据: " + U.formatParam(paramMap));
        SignUtil.checkSign(paramMap, key);
        System.out.println("使用指定 key 检查通过");
    }
}
