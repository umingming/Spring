package kr.co.aim.server;

import java.io.InputStream;
import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
	Packet; 헤더와 바디로 구분
 */
public class Packet {
	private InputStream stream;
	private byte[] header;
	private byte[] body;
	
	private final int HEADER_LENGTH = 4;
	
	/*
	 	기본 생성자
	 	1. 헤더 배열 초기화
	 */
	public Packet() {
		this.header = new byte[HEADER_LENGTH];
	}
	
	/*
	 	스트림 매개 생성자
	 	1. 헤더 배열, 스트림 초기화
	 */
	public Packet(InputStream stream) {
		this.header = new byte[HEADER_LENGTH];
		this.stream = stream;
	}
	
	/*
	 	메시지 매개 생성자
	 	1. 메시지를 인자로 받을 경우 형변환에 body에 초기화함.
	 	2. ByteBuffer를 이용해 body의 길이를 값으로 하는 배열을 선언함.
	 */
	public Packet(String msg) {
		this.body = msg.getBytes();
		this.header = ByteBuffer.allocate(HEADER_LENGTH).putInt(body.length).array();
	}
	
	/*
	    isAvailable; 사용 가능한 패킷인지 확인
	    1. stream으로 부터 읽어 올 수 있는 데이터 크기가 0이상이면 true 반환
	*/
	public boolean isAvailable() {
	   try {
	       return (stream.available() > 0) ? true : false;
	   } catch (Exception e) {
	       return false;
	   }
	}
	
	/*
		init; 스트림을 읽어 패킷 초기화
		1. 스트림을 헤더의 크기만큼 읽어 할당함.
	 	2. 길이 변수에 헤더 배열을 정수로 변환해 초기화함.
	 	3. body 배열을 해당 길이로 지정함.
	 	4. 스트림을 읽어 바디에 할당함.
	 */
	public void init() {
		try {
			stream.read(header);
			
			int length = ByteBuffer.wrap(header).getInt();
			body = new byte[length];
			stream.read(body);
			
		} catch (Exception e) {
			System.out.println("[메시지 수신 오류]");
		}
	}
	
	/*
	 	toString; 패킷을 문자열로 반환함.
	 */
	public String toString() {
		return new String(body);
	}
	
	/*
	 	toByteArr; 패킷을 바이트 배열로 반환함.
	 	1. 헤더와 바디를 합칠 배열을 선언함.
	 	2. 헤더를 카피해 해당 배열에 저장
	 	3. 이후 요소에 바디를 카피함.
	 	4. 배열 반환
	 */
	public byte[] toByteArr() {
		byte[] byteArr = new byte[header.length + body.length];
		System.arraycopy(header, 0, byteArr, 0, header.length);
		System.arraycopy(body, 0, byteArr, header.length, body.length);
		
		return byteArr;
	}
}
