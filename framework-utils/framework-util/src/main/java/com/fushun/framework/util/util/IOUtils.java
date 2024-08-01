package com.fushun.framework.util.util;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import com.fushun.framework.exception.DynamicBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Io 工具类
 */
public class IOUtils extends org.apache.commons.io.IOUtils{

    private static Logger logger= LoggerFactory.getLogger(IOUtils.class);

    /**
     * 下载url为File文件
     * @param url
     * @return
     * @throws Exception
     */
    public static File getFile(String url) throws Exception {
        //对本地文件命名
        String tempUrl=URLUtil.decode(URLUtil.decode(URLUtil.decode(url)));
        int lastLength=tempUrl.lastIndexOf("?");
        if(lastLength==-1){
            lastLength=tempUrl.length();
        }else{
            //剪裁？号后面的数据
            tempUrl=tempUrl.substring(0,lastLength);
        }
        int index=tempUrl.lastIndexOf("/");
        String fileName=null;
        if(index!=-1 && (index+1)!=lastLength){
            fileName = tempUrl.substring((index+1),lastLength);
        }

        if(fileName.contains("/")){
            logger.info("创建文件夹;fileName:[{}]",fileName);
            throw new DynamicBaseException("GET_FILE_ERROR","获取图片失败");
        }

        if(ObjectUtil.isNull(fileName)){
            throw new DynamicBaseException("GET_FILE_ERROR","获取图片失败");
        }


        File file = null;
        URL urlfile;
        InputStream inStream = null;
        OutputStream os = null;
        try {

            //下载
            urlfile = new URL(url);
            URLConnection connection = urlfile.openConnection();

            //解决文件扩展后缀问题
            if(!fileName.contains(".")){
                String mimeType = connection.getContentType();
                index=mimeType.lastIndexOf("/");
                if(index==-1 || index==mimeType.length()){
                    throw new DynamicBaseException("FILE_MIMETYPE_ERROR","文件扩张名称错误");
                }
                fileName="."+mimeType.substring(index+1,mimeType.length());
                logger.info("创建文件夹;fileName:[{}]",fileName);
            }

            //解决文件目录没有创建，
            //获取用户目录
            File dir=new File(System.getProperty("user.dir"));
            boolean bool=!dir.exists();
            logger.info("文件夹dir:[{}],exists:[{}]",System.getProperty("user.dir"),!bool);
            if(bool){
                bool=!dir.mkdirs();
                logger.info("创建文件夹;bool:[{}]",bool);
                if(bool){
                    logger.error("创建文件夹错误;dir:[{}]",System.getProperty("user.dir"));
                }
            }
            logger.info("创建文件;name:[{}],dir:[{}]",fileName,System.getProperty("user.dir"));
            file = File.createTempFile("net_url", fileName,dir);


            inStream = connection.getInputStream();
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            logger.error("下载url为File文件 getFile 错误",e);
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }

            } catch (Exception e) {
                logger.error("关闭流失败",e);
            }
        }

        return file;
    }

    /**
     * 下载url为文件
     * @param url
     * @return
     * @throws Exception
     */
    public static ByteArrayOutputStream downloadFile(String url) throws Exception {
        File file = null;
        URL urlfile;
        InputStream inStream = null;
        ByteArrayOutputStream os = null;
        try {

            //下载
            urlfile = new URL(url);
            URLConnection connection = urlfile.openConnection();
            inStream = connection.getInputStream();
            os = new ByteArrayOutputStream();

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            return os;
        } catch (Exception e) {
            logger.error("下载url为File文件 getFile 错误",e);
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }

            } catch (Exception e) {
               logger.error("关闭流失败",e);
            }
        }

        return null;
    }
}
