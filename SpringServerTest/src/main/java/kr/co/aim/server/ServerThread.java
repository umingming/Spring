package kr.co.aim.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;

import org.springframework.stereotype.Component;

@Component
public class ServerThread implements Runnable {
	private Socket client;
	private ClientGroup group;
	private InputStream in;
	private OutputStream out;
	
	private String name;
	
	public ServerThread(Socket client, ClientGroup group) {
		this.client = client;
		this.group = group;
	}

	/*
	 	connect; 클라이언트가 그룹에 연결되어, 메시지를 보냄.
	 	1. 스트림 변수에 client 소켓 스트림 값을 초기화함.
	 	2. registerName 호출
	 	3. while문 스트림이 존재하면 반복.
	 		> 메시지 패킷 생성
	 		> if문 읽을 내용이 있는지?
	 			> 메시지 변수에, 이름과 메시지를 저장함.
	 			> 해당 메시지를 그룹에게 보내기 위해 send 메소드 호출함.
	 */
	@Override
	public void run() {
		try {
			in = client.getInputStream();
			out = client.getOutputStream();
			
			registerName();

			while(in != null) {
				Packet msgPacket = new Packet(in);
				
				if(msgPacket.isAvailable()) {
					msgPacket.init();
					send(msgPacket, group);
				}
			}
		} catch (Exception e) {
			System.out.println("[사용자 접속 실패]");
		}
	}

	/*
	 	registerName; 이름 설정
		1. 패킷 생성 후 초기화
	 	2. 해당 패킷의 내용을 이름에 할당함.
	 	3. 클라이언트의 이름과, Output 스트림을 그룹에 저장함.
	 	4. 사용자 접속 안내
	 */
	private void registerName() {
		Packet namePacket = new Packet(in);
		
		namePacket.init();
		name = namePacket.toString();
		
		group.getClientMap().put(name, out);
		System.out.printf("[사용자 접속 성공] %s님이 접속했습니다.%n", name);
	}
	
	/*
		send; 메시지를 그룹에게 전송함.
		1. group을 클라이언트 이름으로 탐색하고자, Iterator 사용
		2. 탐색이 가능한지를 조건으로 while문 반복
		  > 해당 반복자의 이름을 key로 스트림을 얻어 out 변수에 초기화함.
		  > 메시지를 인자로 패킷 생성
		  > 해당 메시지의 바이트배열을 스트림으로 전송함.
	 */
	private void send(Packet msgPacket, ClientGroup group) {
		try {
			System.out.println(msgPacket.toString());
			Iterator<String> iterator = group.getClientMap().keySet().iterator();
			
			while(iterator.hasNext()) {
				OutputStream out = group.getClientMap().get(iterator.next());
				out.write(msgPacket.toByteArr());
				out.flush();
			}
			
		} catch (Exception e) {
			System.out.println("[메시지 송신 오류]");
		}
	}
}
