package com.fushun.framework.util.util;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class AuthUtil {

    /**
     * 微信验证
     */
    //private static String token ="canyuer" ;

    public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException {
        CloseableHttpClient client = null;
        try{
            client = HttpClientBuilder.create().build();//获取DefaultHttpClient请求
            HttpGet httpGet = new HttpGet(url);
            JSONObject jsonObject= client.execute(httpGet, response -> {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity, "UTF-8");
                    return JSONObject.parseObject(result);

                }
                return null;
            });
            client.close();
            return jsonObject;
        }catch (Exception e){
            log.error("",e);
        }
        return null;
    }


    /* 先获取access_token
     * @param appId
     * @param appSecret
     * @return
     */
    public static String getAccessToken(String appId , String appSecret){
        // 网页授权接口
        String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;

        CloseableHttpClient client = null;
        String access_token = null;
        int expires_in = 0;
        try {
            client = HttpClientBuilder.create().build();//获取DefaultHttpClient请求
            HttpGet httpget = new HttpGet(GetPageAccessTokenUrl);
            BasicHttpClientResponseHandler responseHandler = new BasicHttpClientResponseHandler();
            String response = client.execute(httpget, responseHandler);
            JSONObject OpenidJSONO = JSONObject.parseObject(response);
            access_token = String.valueOf(OpenidJSONO.get("access_token"));//获取access_token
            expires_in = Integer.parseInt(String.valueOf(OpenidJSONO.get("expires_in")));//获取时间
            client.close();
        } catch (Exception e) {
            log.error("",e);
            throw new RuntimeException("获取AccessToken出错！");//CommonRuntimeException
        }
        return access_token;
    }
    /**
     * 获取jsapi_ticket
     * @param accessToken
     * @return
     */
    public static String getTicket(String accessToken) {
        // 网页授权接口
        String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
        CloseableHttpClient client = null;
        String ticket = "";
        int expires_in = 0;
        System.out.println("accesstoken = " + accessToken);
        try {
            client = HttpClientBuilder.create().build();//获取DefaultHttpClient请求
            HttpGet httpget = new HttpGet(GetPageAccessTokenUrl);
            BasicHttpClientResponseHandler responseHandler = new BasicHttpClientResponseHandler();
            String response = client.execute(httpget, responseHandler);
            JSONObject OpenidJSONO = JSONObject.parseObject(response);
            ticket = String.valueOf(OpenidJSONO.get("ticket"));//获取ticket
            expires_in = Integer.parseInt(String.valueOf(OpenidJSONO.get("expires_in")));//获取时间
            client.close();
        } catch (Exception e) {
            throw new RuntimeException("获取Ticket出错！");//CommonRuntimeException
        }
        return ticket;
    }
    /**
     * SHA1加密，参数是由url、jsapi_ticket、noncestr、timestamp组合而成
     * @param str
     * @return
     */
    public static String SHA1(String str) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1"); //如果是SHA加密只需要将"SHA-1"改成"SHA"即可
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexStr = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }
            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Map<String,String> getSignatureFromMap(String url,String accesstoken) {
        Map<String,String> map = new HashMap<>();
        String signature = "";

        //获取noncestr
        String noncestr = UUID.randomUUID().toString();
        //获取timestamp
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        //获取access_token
        //String access_token = getAccessToken(appid , appsecret);
        //获取jspai_ticket
        String jsapi_ticket = getTicket(accesstoken);
        //将四个数据进行组合，传给SHA1进行加密
        //这里有个坑，页面是nonceStr，但是签名的字段是noncestr，注意大小写
        //sha1加密
        map.put("jsapi_ticket",jsapi_ticket);
        map.put("nonceStr",noncestr);
        map.put("timestamp",timestamp);
        map.put("url",url);
        return map;
    }
    /**
     * 过滤微信名称有表情的
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (source != null && source.length() > 0) {
            return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        } else {
            return source;
        }
    }


    /**
     * 校验签名
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce,String token) {
        System.out.println("signature:" + signature + "timestamp:" + timestamp + "nonc:" + nonce);
        String[] arr = new String[] { token, timestamp, nonce };
        System.out.println(token);
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
// 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        System.out.println(tmpStr.equals(signature.toUpperCase()));
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }
    /**
     * 获取当前时间 yyyyMMddHHmmss
     */
    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     * @param length
     *            int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 生成随机字符串
     */
    public static String getNonceStr() {
        String currTime = getCurrTime();
        String strTime = currTime.substring(8, currTime.length());
        String strRandom = buildRandom(4) + "";
        return strTime + strRandom;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
    /**
     * 获取临时素材
     */
    private static InputStream getMediaStream(String mediaId,String appId,String appsecret)throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/media/get";
        String access_token = getAccessToken(appId,appsecret);
        String params = "access_token=" + access_token + "&media_id=" + mediaId;
        InputStream is = null;
        try {
            String urlNameString = url + "?" + params;
            URL urlGet = new URL(urlNameString);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            // 获取文件转化为byte流
            is = http.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

//    public static String saveImageToDisk(String mediaId,String httpUrl){
//        System.out.println(mediaId);
//        String filename = "";
//        FileOutputStream fileOutputStream = null;
//        InputStream inputStream=null;
//        String path =Constants.UPLOAD_PATH;
//        try {
//             inputStream = getMediaStream(mediaId);
//            byte[] data = new byte[1024];
//            int len ;
//            new File("");
//            //服务器存图路径
//            filename = System.currentTimeMillis() + getNonceStr() + ".jpg";
//            //获取保存上传图片的文件路径
//            String tomcat_path = System.getProperty( "user.dir" );
//            String pic_path;
//            pic_path = tomcat_path.substring(0,System.getProperty( "user.dir" ).lastIndexOf("\\")) +"\\webapps"+"\\picFile";
//            File file = new File(pic_path);
//            if (!file.exists()){
//                file.mkdirs();
//            }
//            fileOutputStream = new FileOutputStream(pic_path+"/" + filename);
//            while ((len = inputStream.read(data)) != -1) {
//                fileOutputStream.write(data, 0, len);
//            }
//            fileOutputStream.flush();
//            System.out.println(fileOutputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (fileOutputStream != null) {
//                try {
//                    fileOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return httpUrl+filename;
//    }

}
