package com.lvshen.demo.sms;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/9/18 15:48
 * @since JDK 1.8
 */
@Slf4j
public class MD5Util {
    public static String MD5(String s) {
        if (StringUtils.isEmpty(s) || StringUtils.isBlank(s)) {
            return null;
        }

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getHashCode(Object object) throws IOException {
        if (object == null) {
            return "";
        }

        String ss = null;
        ObjectOutputStream s = null;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        try {
            s = new ObjectOutputStream(bo);
            s.writeObject(object);
            s.flush();
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException error", e);
        } catch (IOException e) {
            log.error("IOException error", e);

        } finally {
            if (s != null) {
                s.close();
                s = null;
            }
        }
        ss = MD5(bo.toString());
        return ss;
    }

    /**
     * 获取字符utf-8编码格式内容的md5值
     *
     * @param value
     * @return
     */
    public static byte[] getStringMd5(String value) {
        return getStringMessageDigestByte(value, "UTF-8", "MD5");
    }

    /**
     * 获取字符的摘要信息值，摘要方式可以为"MD5,SHA1,SHA-256,SHA-384,SHA-512"中的一种，该字符以系统默认编码方式表示。
     *
     * @param value
     * @param charsetName
     *            字符串在计算时候采用的编码方式
     * @param hashType
     *            摘要方式可以为"MD5,SHA1,SHA-256,SHA-384,SHA-512"中的一种
     * @return
     */
    public static byte[] getStringMessageDigestByte(String value, String charsetName, String hashType) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(hashType);
            md5.update(value.getBytes(charsetName));
            return md5.digest();
        } catch (Exception e) {
            log.error("getStringMessageDigestByte error", e);
            return null;
        }
    }
}
