package com.test.spring.aop;

import java.util.Calendar;

import org.aspectj.lang.ProceedingJoinPoint;

public class Logger {
	public void log() {
		Calendar now = Calendar.getInstance();
		System.out.printf("[log %tF %tT]보조 업무가 실행됩니다.\n", now, now);
	}
	
	public void logTime(ProceedingJoinPoint joinPoint){
		long begin = System.currentTimeMillis();
		System.out.println("[log] 시간 기록을 시작합니다.");
		
		//주업무 코드?
		
		try {
			joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println("설명에 부합되는 코드만 기입합니다.");
		long end = System.currentTimeMillis();
		System.out.println("[log] 시간 기록을 종료합니다.");
		System.out.printf("[log] 주업무 실행 소요 시간: %,dms\n", end - begin);
		
		
	}
}
