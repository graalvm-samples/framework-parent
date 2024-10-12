package com.fushun.framework.filestorage.aliyun;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.model.*;
import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.exception.DynamicBaseException;
import com.fushun.framework.filestorage.config.FilesStorageEnum;
import com.fushun.framework.filestorage.exception.FileResourceException;
import com.fushun.framework.filestorage.qiniu.QiNiuUtil;
import com.fushun.framework.util.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * OSS上传工具类-张晗-2017/10/10
 */
public class OSSUtil {

    private static OSSConfig ossConfig = SpringContextUtil.getBean(OSSConfig.class);

    private volatile static OSSClient instance;

    private volatile static OSSClient internalInstance;

    private OSSUtil() {
    }

    /**
     * 单例
     * @return  OSS工具类实例
     */
    private static OSSClient getOSSClient() {
        if (instance == null) {
            synchronized (OSSUtil.class) {
                if (instance == null) {
                    ClientBuilderConfiguration clientBuilderConfiguration=new ClientBuilderConfiguration();
                    clientBuilderConfiguration.setProtocol(Protocol.HTTPS);
                    instance = (OSSClient) new OSSClientBuilder().build(ossConfig.getOssEndPoint(), ossConfig.getOssAccessKeyId(), ossConfig.getOssAccessKeySecret(),clientBuilderConfiguration);
                }
            }
        }
        return instance;
    }

    /**
     * 单例
     * @return  OSS工具类实例
     */
    private static OSSClient getOSSClient(ClientBuilderConfiguration clientBuilderConfiguration) {
        if (instance == null) {
            synchronized (OSSUtil.class) {
                if (instance == null) {
                    instance = (OSSClient) new OSSClientBuilder().build(ossConfig.getOssEndPoint(), ossConfig.getOssAccessKeyId(), ossConfig.getOssAccessKeySecret(),clientBuilderConfiguration);
                }
            }
        }
        return instance;
    }

    /**
     * 内网的 链接对象
     * @return
     */
    private static OSSClient getInternalOSSClient() {
        if(ossConfig.getDevelopment()){
            return getOSSClient();
        }
        if (instance == null) {
            synchronized (OSSUtil.class) {
                if (instance == null) {
                    instance = (OSSClient) new OSSClientBuilder().build(ossConfig.getOssInternalEndPoint(), ossConfig.getOssAccessKeyId(), ossConfig.getOssAccessKeySecret());
                }
            }
        }
        return instance;
    }

    //定义日志
    private final static Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    //设置URL过期时间为10年
    private final static LocalDateTime OSS_URL_EXPIRATION = DateUtil.addDays(LocalDateTime.now(), 365 * 10);

    /**
     * 上传文件---去除URL中的？后的时间戳
     * @param file 文件
     * @param filePath 上传到OSS上文件的路径
     * @return 文件的访问地址
     */
    public static String upload(InputStream file,String fileName, String filePath) {
        if (ossConfig.getFilesStorageEnum()== FilesStorageEnum.QiNIU_OSS){
            return QiNiuUtil.upload(file,fileName,filePath);
        }
        OSSUtil.uploadFile(file,fileName, filePath);
        return OSSUtil.getImgUrl(fileName, filePath);
    }




    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     * @param inputStream 文件
     * @param filePath  上传到OSS上文件的路径
     * @return 文件的访问地址
     */
    private static String uploadFile(InputStream inputStream,String fileName,String filePath) {
        try{
            if (ossConfig.getFilesStorageEnum()== FilesStorageEnum.QiNIU_OSS){
                return QiNiuUtil.uploadFile(inputStream,fileName,filePath);
            }
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(ContentTypeMap.getContentType(fileName));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = OSSUtil.getInternalOSSClient().putObject(ossConfig.getOssBucketName(), filePath+ fileName, inputStream, objectMetadata);
            return fileName;
        } catch (Exception e) {
            logger.error("{}", "上传文件失败");
            throw new FileResourceException(e,FileResourceException.FileResourceExceptionEnum.GET_OSS_URL_ERROR,"上传文件失败");
        }
    }

    /**
     * 文件下载
     * @param fileName 文件名称
     * @param bucketName
     * @return
     * @throws IOException
     */
    public static byte[] downloadOssFile(String fileName,String bucketName) throws IOException {
        OSSClient client = null;
        InputStream is = null;
        ByteArrayOutputStream os = null;

        byte[] temp = new byte[1024];
        try {
            client = getOSSClient();
            OSSObject ossObj = client.getObject(bucketName, fileName);
            is = ossObj.getObjectContent();
            os = new ByteArrayOutputStream();
            int length = -1;
            while ((length = is.read(temp)) != -1) {
                os.write(temp, 0, length);
            }
            return os.toByteArray();
        }
        finally {
            if (is != null) {
                is.close();
            }

            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * 获得文件路径
     * @param urlStr https://static.proteryhouseprivate.aiworkhelper.com/community/merchants_account/image/20240806142014867/%E7%8E%8B%E7%A6%8F%E9%A1%BA-%E6%AD%A3%E9%9D%A2-%E5%B0%8F.jpeg?e=1722957615&token=zVLJMMxPcavC0DGvrjIBL1KQ80yCeTLhNxax_g8w:4BwYEgAyBz1rCAtLyHnJY2Wl-Ak=
     * @return
     */
    public static String getImageUrl(String urlStr){
        try {
            URL url = new URL(urlStr);
            String path = url.getPath(); // 获取路径
            String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8); // 解码路径

            // 获取不包含文件名的路径
            String pathWithoutFileName = decodedPath.substring(0, decodedPath.lastIndexOf('/'));
            String fileName = decodedPath.substring(path.lastIndexOf('/') + 1); // 获取文件名
            logger.info("pathWithoutFileName:{},fileName:{}", pathWithoutFileName, fileName);
            return getImgUrl(fileName,pathWithoutFileName+"/");
        } catch (Exception e) {
            logger.error("e",e);
        }
        return urlStr;
    }

    /**
     * 获得文件路径
     * @param fileName  文件名
     * @param filePath  文件在OSS上的路径
     * @return 文件的路径
     */
    public static String getImgUrl(String fileName, String filePath) {
        if (ossConfig.getFilesStorageEnum()== FilesStorageEnum.QiNIU_OSS){
            return QiNiuUtil.getImgUrl(fileName,filePath);
        }
        if (StringUtils.isEmpty(fileName)) {
            logger.error("{}", "文件地址为空");
            throw new RuntimeException("文件地址为空");
        }


        //获取oss图片URL失败
        URL url = OSSUtil.getOSSClient().generatePresignedUrl(ossConfig.getOssBucketName(), filePath + fileName, DateUtil.localDateTimeToDate(OSS_URL_EXPIRATION));
        if (url == null) {
            logger.error("{}", "获取oss文件URL失败");
            throw new FileResourceException(FileResourceException.FileResourceExceptionEnum.GET_OSS_URL_ERROR,"获取oss文件URL失败");
        }
        return url.toString();
    }

    /**
     * 获取图片地址，缩放后的宽度
     * @param fileName 文件名
     * @param filePath 文件在OSS上的路径
     * @param width 缩放后的宽度 等比例 大于0有效
     * @param height 缩放后的高度 等比例 大于0有效
     * @return
     */
    public static String getImgUrl(String fileName, String filePath,int width,int height) {
        if (ossConfig.getFilesStorageEnum()== FilesStorageEnum.QiNIU_OSS){
            return QiNiuUtil.getImgUrl(fileName,filePath);
        }
        if (StringUtils.isEmpty(fileName)) {
            logger.error("{}", "文件地址为空");
            throw new RuntimeException("文件地址为空");
        }

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(ossConfig.getOssBucketName(), filePath + fileName);
        request.setExpiration(DateUtil.localDateTimeToDate(OSS_URL_EXPIRATION));
        request.setMethod( HttpMethod.GET);
        Map<String,String> map=new HashMap<>();
        String str="image/resize";
        if(width<=0 && height<=0){
            logger.error("height:{},width:{}", height,width);
            throw new RuntimeException("缩放后的宽度和高度不能同时小于0");
        }
        if(width>0){
            str+=",w_"+width;
        }
        if(height>0){
            str+=",h_"+height;
        }
        map.put("x-oss-process",str);
        request.setQueryParameter(map);
        URL url = OSSUtil.getOSSClient().generatePresignedUrl(request);
        return url.toString();
    }

    /**
     * 获取图片地址，缩放后的宽度
     * @param fullFileName 文件在OSS上的路径+文件名
     * @param width 缩放后的宽度 等比例 大于0有效
     * @param height 缩放后的高度 等比例 大于0有效
     * @return
     */
    public static String getImgUrl(String fullFileName,int width,int height) {
        if (StringUtils.isEmpty(fullFileName)) {
            logger.error("{}", "文件地址为空");
            throw new RuntimeException("文件地址为空");
        }
        if (ossConfig.getFilesStorageEnum()== FilesStorageEnum.QiNIU_OSS){
            String url= QiNiuUtil.getImgUrl(fullFileName,"");
            url+="imageView2/1/";
            if(width>0){
                url+="w/"+width;
            }
            if(height>0){
                url+="h/"+height;
            }
            return url;
        }

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(ossConfig.getOssBucketName(), fullFileName);
        request.setExpiration(DateUtil.localDateTimeToDate(OSS_URL_EXPIRATION));
        request.setMethod( HttpMethod.GET);
        Map<String,String> map=new HashMap<>();
        String str="image/resize";
        if(width<=0 && height<=0){
            logger.error("height:{},width:{}", height,width);
            throw new RuntimeException("缩放后的宽度和高度不能同时小于0");
        }
        if(width>0){
            str+=",w_"+width;
        }
        if(height>0){
            str+=",h_"+height;
        }
//        map.put("x-oss-process",str);
        request.setProcess(str);
//        request.setQueryParameter(map);
        URL url = OSSUtil.getOSSClient().generatePresignedUrl(request);
        return url.toString();
    }


    public static String getDownloadUrl(String fileName, String filePath) {
        if (ossConfig.getFilesStorageEnum()== FilesStorageEnum.QiNIU_OSS){
            return QiNiuUtil.getDownloadUrl(fileName,filePath);
        }
        String fullFileName=filePath + fileName;
        if (StringUtils.isEmpty(fullFileName)) {
            logger.error("{}", "文件地址为空");
            throw new RuntimeException("文件地址为空");
        }


//        String str="image/resize";
//        if(width<=0 && height<=0){
//            logger.error("height:{},width:{}", height,width);
//            throw new RuntimeException("缩放后的宽度和高度不能同时小于0");
//        }
//        if(width>0){
//            str+=",w_"+width;
//        }
//        if(height>0){
//            str+=",h_"+height;
//        }
//        map.put("x-oss-process",str);
//        request.setProcess(str);
//        request.setQueryParameter(map);
        File file=new File(fullFileName);
        Date expiration = new Date(new Date().getTime() + 1800 * 1000);
        GeneratePresignedUrlRequest generatePresignedUrlRequest;
//        String attachment = "?response-content-disposition=" + file.getName();
//        String attachmentEncoder = encoder(attachment);

//        fullFileName  = fullFileName + attachment;
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(ossConfig.getOssBucketName(), fullFileName);
        request.setExpiration(DateUtil.localDateTimeToDate(OSS_URL_EXPIRATION));
        request.setMethod( HttpMethod.GET);
        HashMap<String,String> heads=new HashMap<>();
        heads.put("response-content-disposition",file.getName());
        ResponseHeaderOverrides responseHeaderOverrides=new ResponseHeaderOverrides();
        responseHeaderOverrides.setContentDisposition("attachment; filename="+file.getName());
        responseHeaderOverrides.setContentType(ContentTypeMap.getContentType(file.getName()));
        request.setResponseHeaders(responseHeaderOverrides);
        String url=OSSUtil.getOSSClient().generatePresignedUrl(request).toString();
        return url;//url.replace(attachmentEncoder + "?", attachment + "&");
    }
    private static String encoder(String s) {
        String encoder = null;
        try {
            encoder = URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new DynamicBaseException("ENCODER_ERROR","encoder错误");
        }
        encoder = encoder.replaceAll("\\+", "%20");
        encoder = encoder.replaceAll("\\*", "%2A");
        return encoder;
    }
}