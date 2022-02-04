package com.test.spring.aop;

public class Memo implements IMemo{

	@Override
	public void auth_add(String memo) {
		System.out.printf("메모 쓰기: %s\n", memo);
		
	}

	@Override
	public void read(int seq) throws Exception {
		if(seq >= 5 && seq <= 10) {
			System.out.printf("메모 읽기: %d번 읽기\n", seq);
		} else {
			throw new Exception("존재하지 않는 메모 번호");
		}
	}

	@Override
	public void auth_edit(int seq, String memo) {
		System.out.printf("메모 수정: %d번 메모를 '%s'로 수정\n", seq, memo);
	}

	@Override
	public void auth_del(int seq) {
		System.out.printf("메모 수정: %d번 메모 삭제\n", seq);
	}

}
