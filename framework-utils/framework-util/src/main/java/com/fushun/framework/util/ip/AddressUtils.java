package com.fushun.framework.util.ip;

import com.fushun.framework.util.http.HttpClient;
import com.fushun.framework.util.util.JsonUtil;
import com.fushun.framework.util.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 获取地址类
 * 
 * @author ruoyi
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip)  {
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip))
        {
            return "内网IP";
        }
        if (true)
        {
//            try {
//                HttpClient httpClient = new HttpClient(IP_URL+"?ip="+ip, "utf-8");
//                String rspStr = httpClient.get();
//                if (StringUtils.isEmpty(rspStr))
//                {
//                    log.error("获取地理位置异常 {}", ip);
//                    return address;
//                }
//                HashMap<String, Object> obj = JsonUtil.jsonToHashMap(rspStr);
//                HashMap<String, Object> data = (HashMap<String, Object>) obj.get("data");
//                String region = data.get("region").toString();
//                String city = data.get("city").toString();
//                address = region + " " + city;
//            }catch (Exception e){
//                e.printStackTrace();
//            }

        }
        return address;
    }
}
