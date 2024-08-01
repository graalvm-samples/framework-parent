package com.fushun.framework.util.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采用MD5加密解密
 *
 * @datetime 2011-10-13
 */
public class MD5Util {

    private static Logger logger= LoggerFactory.getLogger(MD5Util.class);



    /**
     * 获取一个文件的md5值(可处理大文件)
     * @return md5 value
     */
    public static String getMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            return getMD5(fileInputStream);
        } catch (Exception e) {
            logger.error("文件错误",e);
            return null;
        }
    }

    /**
     * 获取 MD5
     * @param inputStream
     * @return
     */
    public static String getMD5(InputStream inputStream) {
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (Exception e) {
            logger.error("获取文件流",e);
            return null;
        } finally {
            try {
                if (inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.error("错误信息 getMD5 ",e);
            }
        }
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String getMD5(String target) {
        return DigestUtils.md5Hex(target);
    }

    /**
     * 参数倒排
     * MD5(key=value&key=value)
     * @param parameter
     * @return
     */
    public static String generateSignature (Map<String,Object> parameter) {
        if (parameter != null && !parameter.isEmpty()) {
            //倒叙
            List<String> sortKey = new ArrayList<>(parameter.keySet());
            sortKey.sort(Comparator.reverseOrder());
            StringBuilder builder = new StringBuilder();
            for (String key : sortKey) {
                builder.append(key).append("=").append(parameter.get(key)).append("&");
            }
            builder.append("renan").append("=").append("haoshenghuo");
            return getMD5(builder.toString());
        }
        return null;
    }

}
