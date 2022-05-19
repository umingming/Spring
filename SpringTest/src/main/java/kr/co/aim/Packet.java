package kr.co.aim;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.ByteBuffer;

/*
	Packet; 헤더와 바디로 구분
 */
@Component
public class Packet {
    private InputStream stream;
    private byte[] header;
    private byte[] body;

    private final int HEADER_LENGTH = 4;

    public Packet() {
        this.header = new byte[HEADER_LENGTH];
    }

    /*
         생성자 정의
         1. 메시지를 인자로 받을 경우 형변환에 body에 초기화함.
         2. ByteBuffer를 이용해 body의 길이를 값으로 하는 배열을 선언함.
     */
    public Packet(String msg) {
        this.body = msg.getBytes();
        this.header = ByteBuffer.allocate(HEADER_LENGTH).putInt(body.length).array();
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    /*
         hasNewData; 새로운 데이터 확인
         1. stream으로 부터 읽어 올 수 있는 데이터 크기를 변수에 초기화
         2. 해당 값이 0이상이면 true 반환
     */
    public boolean hasNewData() {
        try {
            int newDataSize = stream.available();
            return (newDataSize > 0) ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    /*
         toString; 패킷을 문자열로 반환함.
         1. 스트림을 헤더의 크기만큼 읽어 할당함.
         2. 길이 변수에 헤더 배열을 정수로 변환해 초기화함.
         3. body 배열을 해당 길이로 지정함.
         4. 스트림을 읽어 바디에 할당함.
         5. 해당 내용을 문자열로 반환함.
     */
    public String toString() {
        try {
            stream.read(header);

            int length = ByteBuffer.wrap(header).getInt();
            body = new byte[length];
            stream.read(body);

            return new String(body);

        } catch (Exception e) {
            System.out.println("[메시지 수신 오류]");
            return null;
        }
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
