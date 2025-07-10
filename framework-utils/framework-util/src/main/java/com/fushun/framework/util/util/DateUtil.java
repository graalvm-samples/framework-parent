package com.fushun.framework.util.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zpcsa
 */
public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * 锁对象
     */
    private static final Object lockObj = new Object();
    private static final String shortSdf = "yyyy-MM-dd";
    private static final String longHourSdf = "yyyy-MM-dd HH";
    private static final String longSdf = "yyyy-MM-dd HH:mm:ss";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static String FORMAT_ST2 = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * yyyy-MM-dd
     */
    public static String FORMAT_DATE_STR = "yyyy-MM-dd";
    /***
     * yyyyMMdd
     */
    public static String FORMAT_DATE_STR2 = "yyyyMMdd";
    /**
     * yyyy年MM月dd日
     */
    public static String FORMAT_DATE_STR3 = "yyyy年MM月dd日";
    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    public static String FORMAT_ISO_DATE_STR = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static String FORMAT_ISO_DATE_STR3="yyyy-MM-dd'T'HH:mm:ss'Z'";
    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     */
    public static String FORMAT_ISO_DATE_STR2 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * HH:mm:ss
     */
    public static String FORMAT_TIME_STR = "HH:mm:ss";
    /**
     * HH
     */
    public static String FORMAT_TIME_STR2 = "HH";
    /**
     * yyyy.MM.dd
     */
    public static String FORMAT_DATE_STR4 = "yyyy.MM.dd";
    /**
     * yyyy.MM.dd HH:mm
     */
    public static String FORMAT_DATE_STR5 = "yyyy.MM.dd HH:mm";
    /**
     * yyyy.MM.dd HH:mm:ss
     */
    public static String FORMAT_DATE_STR6 = "yyyy.MM.dd HH:mm:ss";
    /**
     * yyyy.MM.dd HH:mm:ss
     */
    public static String FORMAT_DATE_STR7 = "MM-dd HH:mm";
    public static String FORMAT_DATE_STR8 = "yyyyMMddHHmmssSSS";
    /**
     * 存放不同的日期模板格式的SimpleDateFormat的Map
     */
    private static Map<String, DateTimeFormatter> simpleDateFormatMap = new HashMap<>();

    /**
     * 返回一个ThreadLocal的SimpleDateFormat,每个线程只会new一次SimpleDateFormat
     */
    private static DateTimeFormatter getSimpleDateFormat(final String pattern) {
        DateTimeFormatter tl = simpleDateFormatMap.get(pattern);
        if (tl == null) {
            tl = DateTimeFormatter.ofPattern(pattern);
            simpleDateFormatMap.put(pattern,tl);
        }
        return tl;
    }

    /**
     * 返回一个ThreadLocal的SimpleDateFormat,每个线程只会new一次SimpleDateFormat
     *
     * @param pattern
     * @return
     */
    private static DateTimeFormatter getSimpleDateFormat1(final String pattern) {
        DateTimeFormatter tl = simpleDateFormatMap.get(pattern);
        if (tl == null) {
            tl = DateTimeFormatter.ofPattern(pattern);
            simpleDateFormatMap.put(pattern,tl);
        }
        return tl;
    }

    /**
     * 转换指定的时间
     *
     * @param localDateTime
     * @param formatType
     * @return
     */
    public static String getDateStr(LocalDateTime localDateTime, String formatType) {
        DateTimeFormatter myFmt = getSimpleDateFormat(formatType);
        String timeStr = myFmt.format(localDateTime);
        return timeStr;
    }

    /**
     * 获取时间字段的值
     * @param dateTime 要处理的日期时间对象
     * @param type ChronoField 类型，例如 ChronoField.MONTH_OF_YEAR
     * @return 时间字段的值
     */
    public static int getDateNo(LocalDateTime dateTime, ChronoField type) {
        return dateTime.get(type);
    }

    /***
     * @Title: toDate
     * @Description: 转换为日期
     * @param fmt
     *            格式
     * @param dateStr
     *            日期串
     * @return Date
     * @throws
     */
    public static LocalDateTime toDate(String fmt, String dateStr) {
        DateTimeFormatter sdf = getSimpleDateFormat(fmt);
        try {
            LocalDateTime date = LocalDateTime.parse(dateStr, sdf);
            return date;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    /**
     * 判断系统当前时间是否大于等于时间date
     *
     * @param date       时间字符串
     *                   date时间类型必须已经包括formatType要求的格式的了,比如把“12/25/2009”转化成“yyyy-MM-dd”
     *                   这种是不支持的,转换要求是在相同format下的
     * @param formatType 日期格式类型,例如:"yyyy-MM-dd" "yyyy-MM-dd HH:mm"
     * @return boolean 系统时间大于等于参数时间,则返回TRUE,否则返回false
     * @throws Exception      异常
     * @throws ParseException 异常
     */
    public static boolean isDateBefore(String date, String formatType) throws IllegalArgumentException, ParseException {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(formatType)) {
            return false;
        }
        String result;

        LocalDateTime systemDate = LocalDateTime.now();
        DateTimeFormatter df = getSimpleDateFormat(formatType);

        result = df.format(LocalDateTime.parse(date, df));
        if (date.startsWith(result)) {
            if (df.format(systemDate).equals(df.format(df.parse(date)))) {
                return false;
            }
            return systemDate.isAfter(LocalDateTime.parse(date, df));
        } else {
            throw new IllegalArgumentException(String.format("错误的日期参数：%1$s，不满足[%2$s]格式要求", date, formatType));
        }

    }

    /**
     * 判断系统当前时间是否大于时间date
     *
     * @param date       时间字符串
     *                   date时间类型必须已经包括formatType要求的格式的了,比如把“12/25/2009”转化成“yyyy-MM-dd”
     *                   这种是不支持的,转换要求是在相同format下的
     * @param formatType 日期格式类型,例如:"yyyy-MM-dd" "yyyy-MM-dd HH:mm"
     * @return boolean 系统时间大于参数时间,则返回FALSE,否则返回TRUE
     * @throws Exception      异常
     * @throws ParseException 异常
     */
    public static boolean isDateBefore2(String date, String formatType) throws Exception, ParseException {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(formatType)) {
            return false;
        }
        String result;

        LocalDateTime systemDate = LocalDateTime.now();
        DateTimeFormatter df = getSimpleDateFormat(formatType);
        result = df.format(LocalDateTime.parse(date, df));
        if (date.startsWith(result)) {
            return systemDate.isAfter(LocalDateTime.parse(date, df));
        } else {
            throw new IllegalArgumentException(String.format("错误的日期参数：%1$s，不满足[%2$s]格式要求", date, formatType));
        }

    }

    /**
     * 格式化日期
     *
     * @param date       时间
     * @param formatType 日期格式类型,例如:"yyyy-MM-dd" "yyyy-MM-dd HH:mm"
     * @return String 格式转换成功后的字符串
     */
    public static String doDateFormat(LocalDateTime date, String formatType) {
        DateTimeFormatter df = getSimpleDateFormat(formatType);
        return df.format(date);

    }

    public static String doLocalDateFormat(LocalDate date, String formatType) {
        DateTimeFormatter df = getSimpleDateFormat(formatType);
        return date.format(df);

    }

    /**
     * @param dateOneBef 被比较时间起
     * @param dateTwoBef 被比较时间止
     * @param dateOneAft 比较时间起
     * @param dateTwoAft 比较时间止
     * @return boolean
     * @Title: compareTwoDate
     * @Description: 判断一个时间区间是否在另一个时间区间内
     */
    public static boolean compareTwoGrane(String dateOneBef, String dateTwoBef, String dateOneAft, String dateTwoAft) {
        int befCompare = dateOneBef.compareTo(dateOneAft);
        int aftCompare = dateTwoBef.compareTo(dateTwoAft);
        if (befCompare >= 0 && aftCompare <= 0) {
            return true;
        }
        return false;
    }

    /**
     * @param dateOne       第一个日期
     * @param formatTypeOne 第一个日期的格式
     * @param dateTwo       第二个日期
     * @param formatTypeTwo 第二个日期的格式
     * @return -1：第一个日期小于第二个日期；0：第一个日期等于第二个日期；1：第一个日期大于第二个日期
     * @throws Exception
     * @Title: compareDate
     * @Description: 比较两个日期的大小
     */
    public static int compareTwoDate(String dateOne, String formatTypeOne, String dateTwo, String formatTypeTwo) {
        DateTimeFormatter dfOne = DateTimeFormatter.ofPattern(formatTypeOne);
        DateTimeFormatter dfTwo = DateTimeFormatter.ofPattern(formatTypeTwo);

        LocalDateTime dtTimeOne = null;
        LocalDateTime dtTimeTwo = null;
        boolean errorOccurred = false;

        try {
            dtTimeOne = LocalDateTime.parse(dateOne, dfOne);
        } catch (DateTimeParseException e) {
            System.err.println("解析 dateOne 时发生错误: " + e.getMessage());
            errorOccurred = true;
        }

        try {
            dtTimeTwo = LocalDateTime.parse(dateTwo, dfTwo);
        } catch (DateTimeParseException e) {
            System.err.println("解析 dateTwo 时发生错误: " + e.getMessage());
            errorOccurred = true;
        }

        if (errorOccurred) {
            if (dtTimeOne == null && dtTimeTwo == null) {
                return 0;  // 如果两个日期都无法解析，视为相等。
            } else if (dtTimeOne == null) {
                return -1;  // 将无法解析的 dateOne 视为较小。
            } else {
                return 1;   // 将无法解析的 dateTwo 视为较小。
            }
        }

        return Integer.compare(dtTimeOne.compareTo(dtTimeTwo), 0);
    }

    /**
     * 获取当前日期时间的字符串表示形式
     *
     * @param formatType 日期格式类型, 例如: "yyyy-MM-dd" "yyyy-MM-dd HH:mm"
     * @return String 格式转换成功后的字符串，如果格式类型为空或无效，返回 null
     */
    public static String getNowDateStr(String formatType) {
        if (formatType == null || formatType.isEmpty()) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatType);
            return now.format(formatter);
        } catch (IllegalArgumentException e) {
            System.err.println("日期格式错误: " + e.getMessage());
            return null;  // 返回 null 如果格式不正确或无法创建格式化器
        }
    }

    /**
     * @param date 日期
     * @return boolean 是否符合格式
     * @throws
     * @Title: isDateYYYYMMDD
     * @Description: 判断格式
     */
    public static boolean isDateYYYYMMDD(String date) {
        String regEx = "^(1|2)\\d{3}-((0{0,1}[1-9])|(1[012]))-\\d{1,2}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(date);
        return m.find();
    }

    /**
     * 判断一个日期格式是否合法
     *
     * @param date 日期字符串
     * @return boolean 返回 true 如果日期格式有效，否则返回 false
     */
    public static boolean isDateYYYYMMDD2(String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }
        String pattern = null;
        if (date.indexOf('-') != -1) {
            pattern = "yyyy-MM-dd";
        } else if (date.indexOf('/') != -1) {
            pattern = "yyyy/MM/dd";
        } else {
            pattern = "yyyyMMdd";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * @param date 日期
     * @return boolean 是否符合格式
     * @throws
     * @Title: isDateYYYYMM
     * @Description: 判断格式
     */
    public static boolean isDateYYYYMM(String date) {
        String regEx = "^(1|2)\\d{3}-((0{0,1}[1-9])|(1[012]))$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(date);
        return m.find();
    }

    /**
     * @param dateStr 日期
     * @return boolean 是否符合格式
     * @throws
     * @Title: isDateYYYYMM
     * @Description: 判断格式
     */
    public static boolean isDateYYYY(String dateStr) {
        String regEx = "^(19|20)\\d{2}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(dateStr);
        return m.find();
    }

    /**
     * 根据日期的格式校验是否为合理的日期
     *
     * @param pattern 日期的格式
     * @param dateStr 值
     * @return boolean
     */
    public static boolean isValidateDate(String pattern, String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * 获取某月的最后一天
     *
     * @param dateStr
     * @param informat
     * @param outformat
     * @return
     * @author fushun
     * @version VS1.3
     * @creation 2016年5月19日
     */
    public static String getLastDayOfMonth(String dateStr, String informat, String outformat) {
        try {
            // 解析输入日期
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(informat);
            LocalDate date = LocalDate.parse(dateStr, inputFormatter);

            // 获取月份的最后一天
            LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

            // 格式化输出日期
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outformat);
            return outputFormatter.format(lastDayOfMonth);
        } catch (Exception e) {
            // 记录或处理异常
            logger.error("输入日期格式不合法: " + dateStr + "，错误信息: " ,e);
            return null;
        }
    }

    /**
     * 获取某月的最后一天
     * @param date 日期
     * @return LocalDate
     */
    public static LocalDate getLastDayOfMonth2(LocalDate date) {
        return date.withDayOfMonth(1).plusMonths(1).minusDays(1);
    }

    /**
     * 获取某月的第一天
     * @param date 日期
     * @return LocalDate
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.withDayOfMonth(1);
    }

    /**
     * 增加年
     * @param date 日期
     * @param years 要增加的年数
     * @return LocalDate
     */
    public static LocalDate addYears(LocalDate date, int years) {
        return date.plusYears(years);
    }

    public static Date localDateTimeToDate(LocalDateTime date){
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public static LocalDateTime dateToLocalDateTime(Date date){
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;
    }

    /**
     * @param date   日期串
     * @param months 月数
     * @return String
     * @throws
     * @Title: addMonths
     * @Description: 获取N月后日期字符串
     */
    public static LocalDateTime addMonths(LocalDateTime date, int months) {
        // Add months using LocalDateTime API
        LocalDateTime newDate = date.plusMonths(months);
        return newDate;
    }

    /**
     * @param date 日期串
     * @param days 天数
     * @return String
     * @throws
     * @Title: addDays
     * @Description: 获取N天后日期字符串
     */
    public static LocalDateTime addDays(LocalDateTime date, int days) {
        return date.plusDays(days);
    }

    /**
     * 获取N分钟后日期字符串
     *
     * @param date
     * @param minutes
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2016年12月22日
     * @records <p>  fushun 2016年12月22日</p>
     */
    public static LocalDateTime addMinute(LocalDateTime date, int minutes) {
        return date.plusMinutes(minutes);
    }

    /**
     * @param date    给定的时间
     * @param seconds 秒数
     * @return String 结果
     * @throws
     * @Title: getDateAfterMinusSeconds
     * @Description: 获得给定时间N秒前的时间
     */
    public static LocalDateTime addSeconds(LocalDateTime date, int seconds) {
        return date.plusSeconds(seconds);
    }

    /**
     * 判断日期是否比系统时间大指定的月数
     *
     * @param date   指定的日期
     * @param months 相加的月数
     * @return true/false
     */
    public static boolean isDateBeforeMonths(LocalDateTime date, int months) {
        // 获取当前系统时间的 LocalDateTime
        LocalDateTime systemDate = LocalDateTime.now();

        // 在当前时间上加上指定的月数
        LocalDateTime dateAfterMonths = systemDate.plusMonths(months);

        // 比较日期，如果指定日期在'系统日期+月数'之后，返回true
        return date.isAfter(dateAfterMonths);
    }

    /**
     * @param n -1：前一月，0：当月，1：后一月
     * @return String
     * @throws
     * @Title: getFirstDayOfMonth
     * @Description: 获取前后N月第一天
     */
    public static LocalDateTime getFirstDayOfMonth(int n) {
        // 获取当前日期时间
        LocalDateTime now = LocalDateTime.now();

        // 添加或减去月份，并设置日期为当月的第一天
        return now.plusMonths(n).withDayOfMonth(1);
    }

    /**
     * 获取前后n季第一天
     *
     * @param n -1：前一季，0：当季，1：后一季
     * @return
     * @author fushun
     * @version VS1.3
     * @creation 2016年5月21日
     */
    public static LocalDateTime getFirstDayOfSeason(int n) {
        LocalDateTime now = LocalDateTime.now();  // 获取当前日期时间
        int currentMonth = now.getMonthValue();    // 获取当前月份

        // 计算当前季度的开始月份
        int currentSeasonStartMonth = (currentMonth - 1) / 3 * 3 + 1;

        // 使用 withMonth() 设置到季度的开始月份，并设置天为1
        LocalDateTime startOfSeason = now.withMonth(currentSeasonStartMonth).withDayOfMonth(1);

        // 加上 n 季的偏移量，每季3个月
        return startOfSeason.plusMonths(3L * n);
    }

    /**
     * @param n -1：前一季，0：当季，1：后一季
     * @return String
     * @throws
     * @Title: getFirstDayOfSeason
     * @Description: 获取前后n季最后一天
     */
    public static LocalDate getLastDayOfSeason(int n) {
        LocalDate today = LocalDate.now();
        // 确定当前日期是哪一季
        int currentMonth = today.getMonthValue();
        int currentQuarter = (currentMonth - 1) / 3 + 1;

        // 根据n计算目标季
        int targetQuarter = currentQuarter + n;

        // 计算年份的偏移
        int year = today.getYear() + (targetQuarter - 1) / 4;
        targetQuarter = ((targetQuarter - 1) % 4) + 1; // 修正季度在1到4之间

        // 根据计算出的季度，确定该季度的最后一个月
        int lastMonthOfQuarter = targetQuarter * 3;
        LocalDate firstDayOfLastMonth = LocalDate.of(year, lastMonthOfQuarter, 1);

        // 获取该月的最后一天
        return firstDayOfLastMonth.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * @param n -1：前一季，0：当季，1：后一季
     * @return String
     * @throws
     * @Title: getFirstDayOfHalfYear
     * @Description: 获取前后n半年第一天
     */
    public static LocalDate getFirstDayOfHalfYear(int n) {
        LocalDate today = LocalDate.now();
        // 确定当前日期是哪一半年
        int currentMonth = today.getMonthValue();
        int currentHalf = (currentMonth - 1) / 6 + 1;

        // 根据n计算目标半年
        int targetHalf = currentHalf + n;

        // 计算年份的偏移
        int year = today.getYear() + (targetHalf - 1) / 2;
        targetHalf = ((targetHalf - 1) % 2) + 1; // 修正半年在1到2之间

        // 根据计算出的半年，确定该半年的第一个月
        int firstMonthOfHalfYear = (targetHalf - 1) * 6 + 1;
        return LocalDate.of(year, firstMonthOfHalfYear, 1);
    }

    /**
     * @param n -1：前一季，0：当季，1：后一季
     * @return String
     * @throws
     * @Title: getLastDayOfHalfYear
     * @Description: 获取前后n半年最后一天
     */
    public static LocalDate getLastDayOfHalfYear(int n) {
        LocalDate today = LocalDate.now();
        // 确定当前日期是哪一半年
        int currentMonth = today.getMonthValue();
        int currentHalf = (currentMonth - 1) / 6 + 1;

        // 根据n计算目标半年
        int targetHalf = currentHalf + n;

        // 计算年份的偏移
        int year = today.getYear() + (targetHalf - 1) / 2;
        targetHalf = ((targetHalf - 1) % 2) + 1; // 修正半年在1到2之间

        // 根据计算出的半年，确定该半年的最后一个月
        int lastMonthOfHalfYear = targetHalf * 6;
        LocalDate firstDayOfLastMonth = LocalDate.of(year, lastMonthOfHalfYear, 1);

        // 获取该月的最后一天
        return firstDayOfLastMonth.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * @param n -1：前一月，0：当月，1：后一月
     * @return String
     * @throws
     * @Title: getLastDayOfMonth
     * @Description: 获取前后N月最后一天
     */
    public static LocalDate getLastDayOfMonth(int n) {
        LocalDate today = LocalDate.now();
        // 添加n个月
        LocalDate targetMonth = today.plusMonths(n);
        // 获取该月的最后一天
        return targetMonth.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 根据输入的日期字符串及格式，计算前后n个月的最后一天，并按指定格式输出。
     *
     * @param dateStr 输入的日期字符串
     * @param n 前后月份数（负数表示前几个月，正数表示后几个月）
     * @param inFormat 输入日期的格式
     * @param outFormat 输出日期的格式
     * @return String 格式化后的日期字符串，日期不合法时返回null
     */
    public static String getLastDayOfMonth(String dateStr, int n, String inFormat, String outFormat) {
        DateTimeFormatter inDf = DateTimeFormatter.ofPattern(inFormat);
        DateTimeFormatter outDf = DateTimeFormatter.ofPattern(outFormat);
        try {
            // 解析输入的日期字符串
            LocalDate date = LocalDate.parse(dateStr, inDf);
            // 计算目标月份
            LocalDate targetMonth = date.plusMonths(n);
            // 获取该月的最后一天
            LocalDate lastDay = targetMonth.with(TemporalAdjusters.lastDayOfMonth());
            // 格式化输出
            return outDf.format(lastDay);
        } catch (Exception e) {
            System.err.println("输入日期格式不合法。" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取当前年份前后n年的第一天。
     *
     * @param n 前后年份数（负数表示前几年，正数表示后几年）
     * @return String 表示年份第一天的日期字符串
     */
    public static String getFirstDayOfYear(int n) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 计算目标年份的第一天
        LocalDate firstDayOfYear = today.plusYears(n).withDayOfYear(1);
        // 格式化并返回日期
        return df.format(firstDayOfYear);
    }

    /**
     * 获取当前年份前后n年的最后一天。
     *
     * @param n 前后年份数（负数表示前几年，正数表示后几年）
     * @return String 表示年份最后一天的日期字符串
     */
    public static String getLastDayOfYear(int n) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 计算目标年份的最后一天
        LocalDate lastDayOfYear = today.plusYears(n).with(TemporalAdjusters.lastDayOfYear());
        // 格式化并返回日期
        return df.format(lastDayOfYear);
    }

    /**
     * 获取当前系统时间。
     *
     * @return String 当前系统时间，格式为"yyyy-MM-dd HH:mm:ss"
     */
    public static String getSysTime() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 获取当前日期和时间
        LocalDateTime now = LocalDateTime.now();
        // 格式化并返回日期时间字符串
        return df.format(now);
    }

    /**
     * 获取当前系统日期。
     *
     * @return String 当前系统日期，格式为"yyyy-MM-dd"
     */
    public static String getSysDate() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 格式化并返回日期字符串
        return df.format(today);
    }

    /**
     * @param format 格式
     * @return String
     * @throws
     * @Title: getSysDate
     * @Description: 获取当前系统日期
     */
    public static String getSysDate(String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(LocalDateTime.now());
    }

    /**
     * 获取当前日期的前后N天。
     *
     * @param n 前后天数（负数表示前几天，正数表示后几天）
     * @return LocalDate 表示调整后的日期
     */
    public static LocalDate getDate(int n) {
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 添加或减去天数
        return today.plusDays(n);
    }

    /**
     * 获取指定日期的前后N天。
     *
     * @param date 指定日期
     * @param n    前后天数（负数表示前一天，0表示当天，正数表示后一天）
     * @return Date 返回调整后的日期
     */
    public static Date getDate(Date date, int n) {
        // Convert Date to LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Add or subtract days
        LocalDate adjustedDate = localDate.plusDays(n);
        // Convert LocalDate back to Date
        return Date.from(adjustedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 根据起始日期获取期间中的所有月份
     *
     * @param kssj 开始时间，格式为yyyy-MM-dd
     * @param jssj 结束时间，格式为yyyy-MM-dd
     * @return 以年为键，包含该年所有月份的列表为值的Map
     */
    public static Map<String, List<String>> getSjqj(String kssj, String jssj) {
        Map<String, List<String>> result = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 解析开始和结束日期
        LocalDate start = LocalDate.parse(kssj, formatter);
        LocalDate end = LocalDate.parse(jssj, formatter);

        // 遍历从开始日期到结束日期的每个月
        LocalDate current = start.withDayOfMonth(1);  // 设置为每月的第一天以便获取完整月份
        while (current.isBefore(end) || current.equals(end)) {
            String year = String.valueOf(current.getYear());
            String monthYear = current.format(DateTimeFormatter.ofPattern("yyyy-MM"));

            result.computeIfAbsent(year, k -> new ArrayList<>()).add(monthYear);
            current = current.plusMonths(1);  // 移到下一个月的第一天
        }

        return result;
    }

    /**
     * @param kssj 开始时间 格式为yyyy-mm
     * @param jssj 结束时间 格式为yyyy-mm
     * @return Map<String,List<String>>
     * @Title: getSjqj2
     * @Description: 根据起始日期获取期间中的所有月份
     */
    public static List<String> getSjqj2(String kssj, String jssj) {
        List<String> result = new ArrayList<String>();
        Collection<List<String>> monthList = DateUtil.getSjqj(kssj + "-01", jssj + "-01").values();
        for (List<String> list : monthList) {
            for (String string : list) {
                if (string.length() == 6) {
                    result.add(string.replace("-", "-0"));
                } else {
                    result.add(string);
                }
            }
        }
        return result;
    }

    /**
     * 获取当前年份
     *
     * @return 当前年份字符串
     */
    public static String getYear() {
        LocalDateTime now = LocalDateTime.now();
        return String.valueOf(now.getYear());
    }

    /**
     * 获取当前月份
     *
     * @return 当前月份，格式为两位数字的字符串（例如："01", "12"）
     */
    public static String getMon() {
        LocalDateTime now = LocalDateTime.now();
        return String.format("%02d", now.getMonthValue());  // Ensure the month is two digits
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期的日部分，格式为两位数字的字符串（例如："01", "31"）
     */
    public static String getDay() {
        LocalDateTime now = LocalDateTime.now();
        return String.format("%02d", now.getDayOfMonth());  // Ensure the day is two digits
    }

    /**
     * 获取传入日期的 天
     *
     * @param dateTime
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2017年1月10日
     * @records <p>  fushun 2017年1月10日</p>
     */
    public static String getDay(LocalDateTime dateTime) {
        return String.format("%02d", dateTime.getDayOfMonth());
    }

    /**
     * 计算两个日期之间相隔的月份数
     *
     * @param timeFirst  时间一
     * @param timeSecond 时间二
     * @return 两个日期之间相隔的月份数
     */
    public static int getMonthSpace(LocalDateTime timeFirst, LocalDateTime timeSecond) {
        // Using ChronoUnit.MONTHS.between to directly calculate the difference in months
        return (int) ChronoUnit.MONTHS.between(timeFirst, timeSecond);
    }

    /**
     * 获取两个日期相差的天
     *
     * @param startDate
     * @param endDate
     * @return
     * @author fushun
     * @version VS1.3
     * @creation 2016年5月18日
     */
    public static int getDaySpace(LocalDateTime startDate, LocalDateTime endDate) {
        // Use ChronoUnit.DAYS to calculate the difference in days
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * 获取两个日期相差的分
     *
     * @param startDateTime
     * @param endDateTime
     * @return
     * @author fushun
     * @version VS1.3
     * @creation 2016年5月18日
     */
    public static long getMinuteSpace(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.MINUTES.between(startDateTime, endDateTime);
    }

    /**
     * 获取两个时间相差 N天N分
     *
     * @param startDateTime
     * @param endDateTime
     * @return
     * @author fushun
     * @version V3.0商城
     * @creation 2016年12月22日
     * @records <p>  fushun 2016年12月22日</p>
     */
    public static String getDayOrMinuteSpace(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        long totalMinutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime);
        long days = totalMinutes / (60 * 24);
        long minutes = totalMinutes % 60; // Remaining minutes after days

        StringBuilder result = new StringBuilder();
        if (days > 0) {
            result.append(days).append("天");
        }
        if (minutes != 0) {
            result.append(minutes).append("分钟");
        }
        return result.toString();
    }

    /**
     * @return String
     * @throws
     * @Title: getLastMonthOfFirstDay
     * @Description: 返回上一个月的第一天
     */
    public static LocalDate getLastMonthOfFirstDay() {
        return LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 返回上一个月的最后一天
     *
     * @return LocalDate
     */
    public static LocalDate getLastMonthLastDay() {
        return LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 返回指定日期上一个月的第一天
     *
     * @param date 指定日期
     * @return LocalDate
     */
    public static LocalDate getLastMonthOfFirstDay(LocalDate date) {
        return date.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 返回指定日期上一个月的最后一天
     *
     * @param date 指定日期
     * @return LocalDate
     */
    public static LocalDate getLastMonthLastDay(LocalDate date) {
        return date.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 返回指定日期所在月份的第一天
     *
     * @param date 指定日期
     * @return LocalDate
     */
    public static LocalDate getMonthOfFirstDayByDate(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 返回指定日期所在月份的最后一天
     *
     * @param date 指定日期
     * @return LocalDate
     */
    public static LocalDate getMonthLastDayByDate(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获得本周的第一天，周一
     *
     * @return
     */
    public static LocalDateTime getCurrentWeekDayStartTime() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = LocalDateTime.of(today, LocalTime.MIN);
        return startOfDay.with(DayOfWeek.MONDAY);
    }

    /**
     * 获得本周的最后一天，周日
     *
     * @return
     */
    public static LocalDateTime getCurrentWeekDayEndTime() {
        LocalDate today = LocalDate.now();
        LocalDateTime endOfDay = LocalDateTime.of(today, LocalTime.MAX);
        return endOfDay.with(DayOfWeek.SUNDAY);
    }

    /**
     * 获得本天的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public static LocalDateTime getCurrentDayStartTime() {
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, LocalTime.MIN);
    }

    /**
     * 获得本天的结束时间，即2012-01-01 23:59:59
     *
     * @return
     */
    public static LocalDateTime getCurrentDayEndTime() {
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, LocalTime.MAX);
    }

    /**
     * 获得指定日期当天的开始时间，即2012-01-01 00:00:00
     *
     * @param date 日期对象
     * @return
     */
    public static LocalDateTime getDayStartTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * 获得指定日期当天的结束时间，即2012-01-01 23:59:59
     *
     * @param date 日期对象
     * @return
     */
    public static LocalDateTime getDayEndTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    public static LocalDateTime getMillisecondStartTime(LocalDateTime dateTime){
        return dateTime.withNano(0);
    }

    public static LocalDateTime getMillisecondEndTime(LocalDateTime dateTime){
        return dateTime.withNano(999999999);
    }

    public static LocalDateTime getCurrentHourStartTime() {
        return LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalDateTime getCurrentHourEndTime() {
        return LocalDateTime.now().withMinute(59).withSecond(59).withNano(999999999);
    }

    public static LocalDateTime getCurrentMonthStartTime() {
        return LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    public static LocalDateTime getCurrentMonthEndTime() {
        return LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }

    public static LocalDateTime getCurrentYearStartTime() {
        return LocalDateTime.now().with(TemporalAdjusters.firstDayOfYear()).with(LocalTime.MIN);
    }

    public static LocalDateTime getCurrentYearEndTime() {
        return LocalDateTime.now().with(TemporalAdjusters.lastDayOfYear()).with(LocalTime.MAX);
    }

    public static LocalDateTime getCurrentQuarterStartTime() {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();
        month = (month - 1) / 3 * 3 + 1;
        return LocalDateTime.now().withMonth(month).with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    public static LocalDateTime getCurrentQuarterEndTime() {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();
        month = (month + 2) / 3 * 3;
        return now.withMonth(month).with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
    }

    public static LocalDateTime getHalfYearStartTime() {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();
        if (month <= 6) {
            return LocalDateTime.now().withMonth(1).with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        } else {
            return LocalDateTime.now().withMonth(7).with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        }
    }

    public static LocalDateTime getHalfYearEndTime() {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();
        if (month <= 6) {
            return LocalDateTime.now().withMonth(6).with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        } else {
            return LocalDateTime.now().withMonth(12).with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        }
    }

    /**
     * @return String
     * @throws
     * @Title: getPrevFirstDayOfWeek
     * @Description: 获取前一周第一天yyyy-MM-dd
     */
    public static Date getPrevFirstDayOfWeek() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.WEEK_OF_YEAR, -1);
        ca.set(Calendar.DAY_OF_WEEK, 2);
        return ca.getTime();
    }

    /**
     * @return String
     * @throws
     * @Title: getPrevLastDayOfWeek
     * @Description: 获取前一周最后一天yyyy-MM-dd
     */
    public static Date getPrevLastDayOfWeek() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.WEEK_OF_YEAR, -1);
        ca.set(Calendar.DAY_OF_WEEK, 1);
        ca.add(Calendar.WEEK_OF_YEAR, 1);
        ca.add(Calendar.DAY_OF_WEEK, 0);
        return ca.getTime();
    }

    /**
     * 得到某年某月的第一天 yyyy-MM-dd
     *
     * @param year 年份
     * @param month 月份
     * @return 该月的第一天
     */
    public static LocalDate getFirstDayOfYearAndMonth(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    /**
     * 根据日期得到同年同月的第一天 yyyy-MM-dd
     *
     * @param date 日期
     * @return 同年同月的第一天
     */
    public static LocalDate getFirstDayOfYearAndMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 得到某年某月的最后一天 yyyy-MM-dd
     *
     * @param year 年份
     * @param month 月份
     * @return 该月的最后一天
     */
    public static LocalDate getLastDayOfYearAndMonth(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 根据日期得到同年同月的最后一天 yyyy-MM-dd
     *
     * @param date 日期
     * @return 同年同月的最后一天
     */
    public static LocalDate getLastDayOfYearAndMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }


    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    public static long getTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    public static long getTimestamp() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 添加分钟
     *
     * @param minutes 要添加的分钟数
     * @return 添加分钟后的日期时间
     */
    public static LocalDateTime addMinute(int minutes) {
        return LocalDateTime.now().plusMinutes(minutes);
    }

    /**
     * 秒转换为时间字符串
     * <p>格式1（如果有天数）: 1d24h59m59s
     * <p>格式2（无天数）: 24:59:59
     *
     * @param mss 毫秒数
     * @return 转换后的时间字符串
     * @date 2017-09-22
     * @author wangfushun
     * @version 1.0
     */
    public static String secondToDateTimeString(long mss) {
        // 将毫秒转换为秒，并计算时长
        Duration duration = Duration.ofMillis(mss);
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        if (days > 0) {
            return String.format("%dd%02dh%02dm%02ds", days, hours, minutes, seconds);
        } else if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            return String.format("%02d", seconds);
        }
    }

    /**
     * 根据开始时间计算到期时间(月)
     * 续费，按月计算周期的时候，
     * @param startTime
     * @param monthNum
     * @return
     */
    public static LocalDate addBusinessDateMonth(LocalDate startTime,int monthNum){
        LocalDate endTime = startTime.plusMonths(monthNum);
        //购买到期的日期为2月，并且计算的到期日小于购买的开始日，则直接赠送到2月月底的到期时间。一个月从1号开始购买
        //31开始，结束日期2月28日
        //30开始，结束日期2月28日
        //29开始，结束日期2月28日
        //29开始，结束日期2月28日(闰年也是)
        //28开始，结束日期2月27日
        if(endTime.getMonth().getValue()==2 && endTime.getDayOfMonth()<startTime.getDayOfMonth()){
            endTime=endTime.plusDays(1);
        }
        endTime=endTime.plusDays(-1);
        return endTime;
    }

}
