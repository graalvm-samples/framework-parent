package com.fushun.framework.filestorage.qiniu;

import com.fushun.framework.base.SpringContextUtil;
import com.fushun.framework.filestorage.aliyun.OSSConfig;
import com.fushun.framework.filestorage.aliyun.OSSUtil;
import com.fushun.framework.filestorage.exception.FileResourceException;
import com.fushun.framework.util.util.DateUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.DownloadUrl;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;

public class QiNiuUtil {



    private static OSSConfig ossConfig = SpringContextUtil.getBean(OSSConfig.class);

    private volatile static UploadManager instance;

    private volatile static Auth auth;

//    private volatile static OSSClient internalInstance;

    private QiNiuUtil() {
    }

    /**
     * 单例
     * @return  OSS工具类实例
     */
    private static UploadManager getOSSClient() {
        if (instance == null) {
            synchronized (QiNiuUtil.class) {
                if (instance == null) {
                    //密钥配置
                    auth = Auth.create(ossConfig.getOssAccessKeyId(), ossConfig.getOssAccessKeySecret());

                    //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
                    Zone z = Zone.autoZone();
                    Configuration c = new Configuration(z);

                    //创建上传对象
                    instance = new UploadManager(c);

                }
            }
        }
        return instance;
    }



    /**
     * 内网的 链接对象
     * @return
     */
    private static UploadManager getInternalOSSClient() {
        if(ossConfig.getDevelopment()){
            return getOSSClient();
        }
        if (instance == null) {
            synchronized (OSSUtil.class) {
                if (instance == null) {
                    instance = getOSSClient();
                }
            }
        }
        return instance;
    }

    //定义日志
    private final static Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    //设置URL过期时间为10年
    private final static LocalDateTime OSS_URL_EXPIRATION = DateUtil.addDays(LocalDateTime.now(), 365 * 10);

    public static String upload(InputStream file, String fileName, String filePath) {
        QiNiuUtil.uploadFile(file,fileName, filePath);
        return QiNiuUtil.getImgUrl(fileName, filePath);
    }


    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static String getUpToken() {
        return auth.uploadToken(ossConfig.getOssBucketName());
    }
    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     * @param inputStream 文件
     * @param filePath  上传到OSS上文件的路径
     * @return 文件的访问地址
     */
    public static String uploadFile(InputStream inputStream, String fileName, String filePath) {
        try{
            QiNiuUtil.getOSSClient().put(inputStream, filePath+ fileName, getUpToken(),null,null);
            return fileName;
        } catch (Exception e) {
            logger.error("{}", "上传文件失败");
            throw new FileResourceException(e,FileResourceException.FileResourceExceptionEnum.GET_OSS_URL_ERROR,"上传文件失败");
        }
    }



    public static String getImgUrl(String fileName, String filePath) {
        // domain   下载 domain, eg: qiniu.com【必须】
// useHttps 是否使用 https【必须】
// key      下载资源在七牛云存储的 key【必须】

//        "static.proteryhouseprivate.aiworkhelper.com"
        DownloadUrl url = new DownloadUrl(ossConfig.getOssEndPoint(), true, filePath+ fileName);
//        url.setAttname(attname) // 配置 attname
//                .setFop(fop) // 配置 fop
//                .setStyle(style, styleSeparator, styleParam) // 配置 style
// 带有效期
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        long deadline = System.currentTimeMillis()/1000 + expireInSeconds;
        String urlString = null;
        try {
            urlString = url.buildURL(auth, deadline);
            logger.info(urlString);
        } catch (QiniuException e) {
            logger.error("ddd",e);
            throw new QiNiuExceptin(QiNiuExceptin.QiNiuExceptinEnum.UPLOAD_IMAGE_ERROR);
        }
        return urlString;
    }

    public static String getDownloadUrl(String fileName, String filePath) {
        // domain   下载 domain, eg: qiniu.com【必须】
// useHttps 是否使用 https【必须】
// key      下载资源在七牛云存储的 key【必须】

//        "static.proteryhouseprivate.aiworkhelper.com"
        DownloadUrl url = new DownloadUrl(ossConfig.getOssEndPoint(), true, filePath+ fileName);
        url.setAttname(fileName); // 配置 attname
//                .setFop(fop) // 配置 fop
//                .setStyle(style, styleSeparator, styleParam) // 配置 style
// 带有效期
        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
        long deadline = System.currentTimeMillis()/1000 + expireInSeconds;
        String urlString = null;
        try {
            urlString = url.buildURL(auth, deadline);
            logger.info(urlString);
        } catch (QiniuException e) {
            logger.error("ddd",e);
            throw new QiNiuExceptin(QiNiuExceptin.QiNiuExceptinEnum.UPLOAD_IMAGE_ERROR);
        }
        return urlString;
    }

}
