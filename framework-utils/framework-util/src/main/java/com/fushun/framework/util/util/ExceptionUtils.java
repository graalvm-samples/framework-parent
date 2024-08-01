package com.fushun.framework.util.util;

import com.fushun.framework.exception.BaseException;
import com.fushun.framework.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * 异常堆栈打印类
 *
 * @author fushun
 * @version V3.0商城
 * @creation 2017年1月17日
 */
public class ExceptionUtils {

    public static void rethrow(Throwable e, Logger logger, String messageTemplate,
                               Object... objects) {
        String message = messageTemplate;
        FormattingTuple formattingTuple = buildMessages(messageTemplate, objects);
        if (null != formattingTuple) {
            message = formattingTuple.getMessage();
            if (null != formattingTuple.getThrowable()) {
                e.addSuppressed(formattingTuple.getThrowable());
            }
        }
        rethrow(e, logger, message);
    }

    private static String buildMessage(String messageTemplate, Object[] objects) {
        if (messageTemplate == null || objects == null || objects.length == 0 || (
                !messageTemplate.contains("{}"))) {
            return messageTemplate;
        }
        return MessageFormatter.arrayFormat(messageTemplate, objects).getMessage();
    }

    private static FormattingTuple buildMessages(String messageTemplate, Object[] objects) {
        if (messageTemplate == null || objects == null || objects.length == 0) {
            return null;
        }
        return MessageFormatter.arrayFormat(messageTemplate, objects);
    }

    /**
     * 用于重新抛出异常
     *
     * @param message 在日志中记录的信息，建议输入重要的参数
     */
    public static void rethrow(Throwable e, Logger logger, String message) {

        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            if (!baseException.isPrinted()) {
                logger.error(message, e);
            } else {
                logger.error(message);
            }
        }

        if (e instanceof BaseException) {
            throw (BaseException) e;
        }else {
            throw new SystemException(e, SystemException.SystemExceptionEnum.UNKNOWN,"系统中抛出未知异常，需要检查处理异常错误");
        }
    }

//    /**
//     * 异常类堆栈打印
//     *
//     * @param e
//     * @return
//     * @author fushun
//     * @version V3.0商城
//     * @creation 2017年1月17日
//     * @records <p>  fushun 2017年1月17日</p>
//     */
//    public static String getPrintStackTrace(Exception e) {
//        StringWriter sw = null;
//        PrintWriter pw = null;
//        try {
//            sw = new StringWriter();
//            pw = new PrintWriter(sw);
//            //将出错的栈信息输出到printWriter中
//            e.printStackTrace(pw);
//            pw.flush();
//            sw.flush();
//        } finally {
//            if (sw != null) {
//                try {
//                    sw.close();
//                } catch (IOException e1) {
//                }
//            }
//            if (pw != null) {
//                pw.close();
//            }
//        }
//        return sw.toString();
//    }
}
