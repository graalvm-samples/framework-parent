//package com.fushun.framework.util.util;
//
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//import static com.fushun.framework.util.util.DateUtil.getCurrentDayStartTime;
//
//public class DateUtilTest {
//
//    @Test
//    public  void test() {
//
//		// 使用虚拟线程工厂创建一个 ExecutorService
//		ExecutorService es = Executors.newCachedThreadPool(Thread.ofVirtual().factory());
//
////		ExecutorService es = new ThreadPoolExecutor(20, 500,
////                60L, TimeUnit.SECONDS,
////                new LinkedBlockingDeque<>(), new ThreadPoolExecutor.CallerRunsPolicy());
//
//		LocalDateTime date=LocalDateTime.now();
//
//		LocalDateTime date2= DateUtil.addMonths(date, 10);
//		date2=DateUtil.addDays(date2, 11);
//		date2=DateUtil.addMinute(date2, 11);
//
//		System.out.println(DateUtil.getDateStr(date, DateUtil.FORMAT_STR));
//		System.out.println(DateUtil.getDateStr(date2, DateUtil.FORMAT_STR));
//
//		int month=DateUtil.getMonthSpace(date,date2);
//		int day =DateUtil.getDaySpace(date, date2);
//		long minute =DateUtil.getMinuteSpace(date, date2);
//		String t =DateUtil.getDayOrMinuteSpace(date, date2);
//		System.out.println(month);
//		System.out.println(day);
//		System.out.println(minute);
//		System.out.println(t);
//
//		LocalDateTime date5= LocalDateTime.now();
//		LocalDateTime date4= LocalDateTime.now();
//		System.out.println(DateUtil.doDateFormat(date2, DateUtil.FORMAT_STR));
//		LocalDate date3=DateUtil.getLastDayOfYearAndMonth(2016, 2);
//		System.out.println(DateUtil.doLocalDateFormat(date3, DateUtil.FORMAT_DATE_STR));
//
//		long minuteSpace= DateUtil.getMinuteSpace(date4,date5);
//		System.out.println(minuteSpace);
//
//		for(int i=0;i<10000;i++) {
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			CompletableFuture.runAsync(new Runnable() {
//				@Override
//				public void run() {
//					System.out.println(getCurrentDayStartTime());
//				}
//			},es);
//		}
//		// 关闭 ExecutorService
//		es.shutdown();
//		try {
//			if (!es.awaitTermination(60, TimeUnit.SECONDS)) {
//				es.shutdownNow();
//			}
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//			es.shutdownNow();
//		}
//		for(int i=0;i<10000;i++) {
//
//			Thread t1 = new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					System.out.println(getCurrentDayStartTime());
//					System.out.println(getCurrentDayStartTime());
//				}
//			});
//			t1.start();
//		}
//    }
//}