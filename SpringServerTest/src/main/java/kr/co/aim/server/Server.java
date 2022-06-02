package kr.co.aim.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.co.aim.domain.Room;
import kr.co.aim.repository.MemberRepository;
import kr.co.aim.repository.MemberRoomRepository;
import kr.co.aim.repository.MessageRepository;
import kr.co.aim.repository.RoomRepository;

/*
	에코 서버
	- 서버를 생성하고 클라이언트의 접근을 확인해 스레드 생성
 */
@Component
public class Server {
	private ServerSocket server;
	private Socket client;
	private ArrayList<Room> roomList = new ArrayList<>();
	private int index;
	
	@Value("${port}")
	private int port;
	
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private MemberRoomRepository memberRoomRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MessageRepository messageRepository;
	
	/*
		initServer; 서버를 실행함.
		1. registerServer() 호출
		2. if문 server가 할당될 경우를 조건
			> communicateWithClient() 호출
	 */	
	@PostConstruct
	public void initServer() {
		registerServer();
		
		if(server != null) {
			createRoom(2);
			createRoom(2);
			communicateWithClient();
		}
	}
	
	/*
		registerServer(); 서버 생성
		1. port를 매개로 서버소켓 생성 후 안내 메시지 출력
		2. 예외처리
			> port 넘버를 잘못 입력했을 경우
			> 이미 존재하는 port의 경우
	 */
	private void registerServer() {
		try {
			server = new ServerSocket(port);
			System.out.printf("[서버 생성 성공] Port 번호는 %d입니다.%n"
								, server.getLocalPort());
		} catch (Exception e) {
			System.out.println("[서버 생성 실패] 잘못된 입력입니다.");
		} 
	}
	
	/*
	 	createRoom; 대화방 생성
	 	1. 매개변수를 크기로 하는 Room 객체 생성함.
	 	2. 랜덤 이름 설정
	 	3. Room 데이터 추가
	 	4. 대화방 객체 리스트에 할당
	 */
	private void createRoom(int capacity) {
		Room room = new Room(capacity);
		room.setName("유미네" + new Random().nextInt(9999));
		roomRepository.save(room);
		roomList.add(room);
		
		System.out.println("[대화방 생성 성공]");
	}
	
	/*
		communicateWithClient(); 클라이언트의 접속을 확인하고 스레드 생성
		1. while문 그룹이 2개 이하일 경우 반복
			> 클라이언트 접근 확인 후 client 소켓에 초기화
			> if 해당 그룹이 만원인지?
				> index++
			> 서버 스레드 생성 후 멤버와 채팅방 설정
			> 레퍼지토리 인자로 할당해줌.
			> 스레드를 시작함.
	 */
	private void communicateWithClient() {
		try {
			while(roomList.size() < 3) {
				client = server.accept();
				System.out.println("[사용자 접속 대기]");

				if(roomList.get(index).isFull()) {
					index++;
				}
				
				ServerThread serverThread = new ServerThread(client, roomList.get(index));
				serverThread.setMemberRepository(memberRepository);
				serverThread.setMemberRoomRepository(memberRoomRepository);
				serverThread.setMessageRepository(messageRepository);
				serverThread.setRoomRepository(roomRepository);
				
				Thread thread = new Thread(serverThread);
				thread.start();
			}
		} catch (IOException e) {
			System.out.println("[사용자 접속 실패]");
		}
	}
}