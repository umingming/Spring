package kr.co.aim.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.Random;

import kr.co.aim.domain.Member;
import kr.co.aim.domain.MemberRoom;
import kr.co.aim.domain.Message;
import kr.co.aim.domain.Room;
import kr.co.aim.repository.MemberRepository;
import kr.co.aim.repository.MemberRoomRepository;
import kr.co.aim.repository.MessageRepository;
import kr.co.aim.repository.RoomRepository;
import lombok.Getter;
import lombok.Setter;
import kr.co.aim.domain.Packet;

@Getter @Setter
public class ServerThread implements Runnable {
	private Socket client;
	private InputStream in;
	private OutputStream out;
	private Member member;
	private Room room;
	
	private MemberRoomRepository memberRoomRepository;
	private MemberRepository memberRepository;
	private MessageRepository messageRepository;
	private RoomRepository roomRepository;
	
	/*
		가야할때가 언제인지를 알고 가는자의 뒷모습은 얼마나 아름다운가
		봄한 철 격정을 인내한 나의 사랑은 지고있다
		분분한 낙화
		
		
	 */

	public ServerThread() {
		this.client = null;
	}
	
	public ServerThread(Socket client, Room room) {
		this.client = client;
		this.room = room;
	}

	/*
	 	run; 클라이언트가 그룹에 연결되어, 메시지를 보냄.
	 	1. 스트림 변수에 client 소켓 스트림 값을 초기화함.
	 	2. registerMember 호출
	 	3. while문 스트림이 존재하면 반복.
	 		> 메시지 패킷 생성
	 		> if문 읽을 내용이 있는지?
	 			> 메시지 패킷 초기화
	 			> 해당 메시지를 room에 보내기 위해 send 메소드 호출함.
	 */
	@Override
	public void run() {
		try {
			in = client.getInputStream();
			out = client.getOutputStream();

			while(in != null) {
				Packet packet = new Packet(in);
				
				if(packet.isAvailable()) {
					packet.init();
					operateByType(packet);
				}
			}
		} catch (Exception e) {
			System.out.println("[사용자 접속 실패]");
		}
	}

	/*
	 	operateByType; 패킷 타입에 따라 메소드 분류
	 	1. switch문 type을 조건
	 		> RM; RegisterMember
	 		> CR; CreateRoom
	 		> JR; JoinRoom
	 		> LR; LeaveRoom
	 		> SM; SendMessage
	 */
	private void operateByType(Packet packet) {
		String type = new String(packet.getType());
		System.out.println(type);
		
		switch(type) {
			case "RM" :
				registerMember(packet);
				break;
			case "CR" :
				createRoom(packet);
				break;
			case "JR" :
				joinRoom(packet);
				break;
			case "LR" :
				leaveRoom(packet);
				break;
			case "SM" :
				sendMessage(packet);
				break;
		}
	}

	/*
	 	registerMember; 사용자 설정
		1. 이름 패킷 생성 후 초기화
		2. 패킷을 문자열로 변환한 값으로 멤버의 이름 설정
		3. 멤버 데이터 저장함.
		4. 해당 대화방에 멤버 추가
			> 멤버룸 객체 생성 후, 사용자와 대화방 할당
			> DB 연동
	 	5. 사용자 접속 안내
	 */
	private void registerMember(Packet packet) {
		member = new Member();
		member.setName(packet.toString());
		member.setOut(out);
		memberRepository.save(member);
		
		System.out.printf("[사용자 접속 성공] %s님이 접속했습니다.%n", member.getName());
	}
	
	private void createRoom(Packet packet) {
		int capacity = ByteBuffer.wrap(packet.getOption()).getInt();
		String name = new String(packet.getBody());
		
		Room room = new Room(capacity);
		room.setName(name);
		roomRepository.save(room);
		
		System.out.printf("[대화방 생성 성공] %s (0/%d명)", name, capacity);
	}
	
	private void joinRoom(Packet packet) {
		int roomId = Integer.parseInt(packet.toString());
		Room room = roomRepository.findById(roomId).get();
		room.getMemberList().add(member);
		
		MemberRoom memberRoom = new MemberRoom();
		memberRoom.setMember(member);
		memberRoom.setRoom(room);
		memberRoomRepository.save(memberRoom);
	}
	
	private void leaveRoom(Packet packet) {
//		int roomId = Integer.parseInt(packet.toString());
//		Room room = roomRepository.findById(roomId).get();
//		room.getMemberList().remove(member);
//		
//		MemberRoom memberRoom = new MemberRoom();
//		memberRoom.setMember(member);
//		memberRoom.setRoom(room);
//		memberRoomRepository.save(memberRoom);
	}
	
	/*
		send; 메시지를 대화방에 전송함.
		1. 해당 메시지 출력
		2. 메시지 객체 생성 후, 발신인, 대화방, 내용 설정
		3. 메시지 데이터 추가
		4. for문 해당 대화방의 인원 수 만큼 반복
		  > 대화방 멤버들의 OutputStream을 변수에 초기화함.
		  > 해당 메시지의 바이트배열을 스트림으로 전송함.
	 */
	private void sendMessage(Packet packet) {
		try {
			System.out.println(packet.toString());
			
			int roomId = ByteBuffer.wrap(packet.getOption()).getInt();
			Room room = roomRepository.findById(roomId).get();
			
			Message msg = new Message();
			msg.setMember(member);
			msg.setRoom(room);
			msg.setContent(packet.toString());
			messageRepository.save(msg);
			
			for(int i=0; i<room.getMemberList().size(); i++) {
				OutputStream out = room.getMemberList().get(i).getOut();
				out.write(packet.toByteArr());
				out.flush();
			}
		} catch (Exception e) {
			System.out.println("[메시지 송신 오류]");
		}
	}
}
