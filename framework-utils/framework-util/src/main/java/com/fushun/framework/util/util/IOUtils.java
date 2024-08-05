package com.fushun.framework.util.util;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import com.fushun.framework.exception.DynamicBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Io 工具类
 */
public class IOUtils extends org.apache.commons.io.IOUtils{

    private static Logger logger= LoggerFactory.getLogger(IOUtils.class);

    public static File createFileDir(String fileName) {
        //解决文件目录没有创建， System.getProperty("user.dir")
        //获取用户目录
        File dir=new File(fileName);
        boolean bool=!dir.exists();
        logger.info("文件夹dir:[{}],exists:[{}]",fileName,!bool);
        if(bool){
            bool=!dir.mkdirs();
            logger.info("创建文件夹;bool:[{}]",bool);
            if(bool){
                logger.error("创建文件夹错误;dir:[{}]",fileName);
            }
        }
        return dir;
    }
    /**
     * 下载url为File文件
     * @param url
     * @param dirPath
     * @return
     * @throws Exception
     */
    public static File getFile(String url,String dirPath) throws Exception {


        File file = null;
        URL urlfile;
        InputStream inStream = null;
        OutputStream os = null;
        try {

            //下载
            urlfile = new URL(url);
            URLConnection connection = urlfile.openConnection();

            String fileName=getFileName(url,connection);

            File dir=createFileDir(dirPath);
            logger.info("创建文件;name:[{}],dir:[{}]",fileName,dir.getAbsolutePath());
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

    /**
     *
     * @param url
     * @return
     * fileName: 文件名称
     * memoryInputStream： 文件流
     */
    public static Map<String,Object> downloadFileInputStream(String url){
        InputStream inStream=null;
        ByteArrayOutputStream byteStream=null;
        InputStream memoryInputStream=null;
        try {
            // 建立连接
            URL urlfile = new URL(url);
//            URLConnection connection = urlfile.openConnection();
            HttpURLConnection connection = (HttpURLConnection) urlfile.openConnection();
            connection.setRequestMethod("GET");
            String fileName=getFileName(url,connection);

            // 从连接中打开输入流
            inStream = connection.getInputStream();

            // 使用 ByteArrayOutputStream 在内存中存储数据
            byteStream = new ByteArrayOutputStream();

            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                byteStream.write(buffer, 0, bytesRead);
            }

            // 将字节数组输出流转换为字节数组
            byte[] fileData = byteStream.toByteArray();

            // 从字节数组创建 InputStream
            memoryInputStream = new ByteArrayInputStream(fileData);

            // 现在可以使用 memoryInputStream 作为内存中的 InputStream 来处理数据

//            // 示例：从内存输入流中读取数据
//            int data;
//            while ((data = memoryInputStream.read()) != -1) {
//                // 处理数据...
//            }

            // 关闭流
            inStream.close();
            byteStream.close();
            memoryInputStream.close();

            Map<String,Object> map=new HashMap<>();
            map.put("fileName",fileName);
            map.put("memoryInputStream",memoryInputStream);
            return map;
        } catch (Exception e) {
            logger.error("",e);
        } finally {
            try {
                if (null != inStream) {
                    inStream.close();
                }
                if (null != byteStream) {
                    byteStream.close();
                }
                if (null != memoryInputStream) {
                    memoryInputStream.close();
                }
            } catch (Exception e) {
                logger.error("关闭流失败",e);
            }
        }
        return null;
    }

    /**
     *
     * 获取文件名
     * @param url
     * @param connection
     * @return
     */
    public static String getFileName(String url,URLConnection connection){
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

        if(fileName!=null && fileName.contains("/")){
            logger.info("创建文件夹;fileName:[{}]",fileName);
            throw new DynamicBaseException("GET_FILE_ERROR","获取图片失败");
        }

        if(ObjectUtil.isNull(fileName)){
            throw new DynamicBaseException("GET_FILE_ERROR","获取图片失败");
        }

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
        return fileName;
    }
}
