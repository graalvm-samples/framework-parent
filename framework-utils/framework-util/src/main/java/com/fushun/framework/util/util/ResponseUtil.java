package com.fushun.framework.util.util;

import com.fushun.framework.json.config.JsonGraalVMNativeBean;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author Exrickx
 */
@Slf4j
public class ResponseUtil {

    /**
     *  使用response输出JSON
     * @param response
     * @param resultMap
     */
    public static void out(HttpServletResponse response, JsonGraalVMNativeBean resultMap){

        ServletOutputStream out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            out = response.getOutputStream();
            out.write(JsonUtil.toJson(resultMap).getBytes());
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        } finally{
            if(out!=null){
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    log.error(e + "输出JSON出错");
                }
            }
        }
    }
}
