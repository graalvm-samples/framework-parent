//package com.fushun.framework.util.util;
//
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.model.ObjectMetadata;
//import com.aliyun.oss.model.PutObjectResult;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Random;
//
///**
// * @author admin
// */
//
//public class OSSUtil {
//
//    //文件存储目录
//    private static String filedir = "APP/";
//
//    /**
//     *
//     * 上传图片
//     * @param file
//     * @return
//     */
//    public static String uploadImg2Oss(MultipartFile file,String ossPath,String accessKeyId,String accessKeySecret,String bucketName) {
//        if (file.getSize() > 1024 * 1024 *20) {
//            return "图片太大";//RestResultGenerator.createErrorResult(ResponseEnum.PHOTO_TOO_MAX);
//        }
//        String originalFilename = file.getOriginalFilename();
//        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
//        Random random = new Random();
//        String name ="";
//        try {
//           name = random.nextInt(10000) + System.currentTimeMillis() + substring;
//            InputStream inputStream = file.getInputStream();
//            uploadFile2OSS(inputStream, name, ossPath, accessKeyId, accessKeySecret, bucketName);
//            return name;//RestResultGenerator.createSuccessResult(name);
//        } catch (Exception e) {
//          //  return "上传失败";//RestResultGenerator.createErrorResult(ResponseEnum.PHOTO_UPLOAD);
//           // throw new CommunitOwnerException(CommunitOwnerException.CommunitOwnerExceptionEnum.FAILED_TO_UPLOAD_IMAGE);
//            e.printStackTrace();
//        }
//        return name;
//    }
//
//    /**
//     * 上传图片获取fileUrl
//     * @param instream
//     * @param fileName
//     * @return
//     */
//    private static String uploadFile2OSS(InputStream instream, String fileName,String ossPath,String accessKeyId,String accessKeySecret,String bucketName) {
//        String ret = "";
//        try {
//            //创建上传Object的Metadata
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(instream.available());
//            objectMetadata.setCacheControl("no-cache");
//            objectMetadata.setHeader("Pragma", "no-cache");
//            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
//            objectMetadata.setContentDisposition("inline;filename=" + fileName);
//            //上传文件
//
//            OSSClient ossClient = new OSSClient(ossPath, accessKeyId, accessKeySecret);
//                PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
//            ret = putResult.getETag();
//        } catch (IOException e) {
//
//        } finally {
//            try {
//                if (instream != null) {
//                    instream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return ret;
//    }
//
//    public static   String getcontentType(String FilenameExtension) {
//        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
//            return "image/bmp";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".gif")) {
//            return "image/gif";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
//                FilenameExtension.equalsIgnoreCase(".jpg") ||
//                FilenameExtension.equalsIgnoreCase(".png")) {
//            return "image/jpeg";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".html")) {
//            return "text/html";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".txt")) {
//            return "text/plain";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
//            return "application/vnd.visio";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
//                FilenameExtension.equalsIgnoreCase(".ppt")) {
//            return "application/vnd.ms-powerpoint";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".docx") ||
//                FilenameExtension.equalsIgnoreCase(".doc")) {
//            return "application/msword";
//        }
//        if (FilenameExtension.equalsIgnoreCase(".xml")) {
//            return "text/xml";
//        }
//        return "image/jpeg";
//    }
//
//    /**
//     * 获取图片路径
//     * @param fileUrl
//     * @return
//     */
//    public static String getImgUrl(String fileUrl,String ossPath,String accessKeyId,String accessKeySecret,String bucketName) {
//        if (!StringUtils.isEmpty(fileUrl)) {
//            String[] split = fileUrl.split("/");
//            String url =  getUrl(filedir + split[split.length - 1],ossPath, accessKeyId, accessKeySecret,bucketName);
////                log.info(url);
////                String[] spilt1 = url.split("\\?");
////                return spilt1[0];
//            return url;
//        }
//        return null;
//    }
//
//    /**
//     * 获得url链接
//     *
//     * @param key
//     * @return
//     */
//    public static String getUrl(String key,String ossPath,String accessKeyId,String accessKeySecret,String bucketName) {
//        // 设置URL过期时间为10年  3600l* 1000*24*365*10
//        // 获取10年以后的时间
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.YEAR, 10);
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        // 生成URL
//        OSSClient ossClient = new OSSClient(ossPath, accessKeyId, accessKeySecret);
//        URL url = ossClient.generatePresignedUrl(bucketName, key, calendar.getTime());
//        if (url != null) {
//            return url.toString();
//        }
//        return null;
//    }
//
//
//    /**
//     * 多图片上传
//     * @param fileList
//     * @return
//     */
//    public static String checkList(List<MultipartFile> fileList,String ossPath,String accessKeyId,String accessKeySecret,String bucketName) {
//        String  fileUrl = "";
//        String  str = "";
//        String  photoUrl = "";
//        for(int i = 0;i< fileList.size();i++){
//            fileUrl = uploadImg2Oss(fileList.get(i), ossPath, accessKeyId, accessKeySecret, bucketName);
//            str = getImgUrl(fileUrl, ossPath, accessKeyId, accessKeySecret, bucketName);
//            if(i == 0){
//                photoUrl = str;
//            }else {
//                photoUrl += "," + str;
//            }
//        }
//        return photoUrl.trim();
//    }
//
//    /**
//     * 单个图片上传
//     * @param file
//     * @return
//     */
//    public static String checkImage(MultipartFile file,String ossPath,String accessKeyId,String accessKeySecret,String bucketName){
//        String fileUrl = uploadImg2Oss(file,ossPath,accessKeyId,accessKeySecret ,bucketName);
//        String str = getImgUrl(fileUrl, ossPath,accessKeyId, accessKeySecret, bucketName);
//        return str.trim();
//    }
//}
