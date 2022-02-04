package com.test.spring.aop;

public interface IMemo {
	void auth_add(String memo);
	void auth_edit(int seq, String memo);
	void auth_del(int seq);
	void read(int seq) throws Exception;
}
