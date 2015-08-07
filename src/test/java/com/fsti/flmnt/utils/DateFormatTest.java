 /*****************************
 * Copyright (c) 2015 by China Youke Communication Co. Ltd.  All rights reserved.
 ****************************/
package com.fsti.flmnt.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/** 
 * @{#} DateFormatTest.java Create on 2015年6月10日 下午10:47:27
 * <p>
 * 	
 * </p>
 * @author <a href="mailto:cwx714@126.com">崔卫翔</a>   
 * @version v 0.1 
 */
public class DateFormatTest {

	@Test
	public void dateTest() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");//"yyyy/MM/dd HH:mm:ss"
		String beginTime = "2015052615063122";
		Date date = format.parse(beginTime.trim());//, new ParsePosition("yyyy-MM-dd HH:mm:ss")
		System.out.println("Date--->" + date);
		System.out.println("DateStr--->" + format.format(date));
		
		SimpleDateFormat fmt0 = new SimpleDateFormat("Gyyyy年MM月dd日 HH时mm分ss秒");
		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt3 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
		SimpleDateFormat fmt4 = new SimpleDateFormat("yyyy/MM/dd E");
		
		Date now = new Date();
        System.out.println(fmt0.format(now));
        System.out.println(fmt1.format(now));
        System.out.println(fmt2.format(now));
        System.out.println(fmt3.format(now));
        System.out.println(fmt4.format(now));
	}
	
	@Test
	public void normalTest() {
		Long sizeSum = 0L;//fileSize Sum
		int fileCount = 0;//fileNumber Count
		for (int i = 0; i < 10; i++) {
//			sizeSum = sizeSum + fileCount;
			sizeSum += fileCount;
			fileCount += i;
		}
		System.out.println(sizeSum);
		System.out.println(fileCount);
	}
}
