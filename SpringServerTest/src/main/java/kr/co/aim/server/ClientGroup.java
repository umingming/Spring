package kr.co.aim.server;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/*
 	ClientGroup; 클라이언트들의 그룹을 정의함.
 */
@Component
@Getter @Setter
public class ClientGroup {
	private Map<String, OutputStream> clientMap;
	private String name;
	private int total;
	
	/*
		total값을 정의하지 않으면 그룹을 무한대로 만듦.
	 */
	public ClientGroup() {
		clientMap = new HashMap<>();
	}
	
	/*
		total값을 매개로 받아, 해당 크기 만큼 map을 생성함.
	 */
	public ClientGroup(int total) {
		this.total = total;
		clientMap = new HashMap<>(total);
	}
	
	/*
	  	isFull()
	  	1. 존재하는 요소와 total의 크기가 같은지를 리턴함.
	 */
	public boolean isFull() {
		return clientMap.size() == total;
	}

	@Override
	public String toString() {
		String[] clients = new String[clientMap.size()];
		int index = 0;
		
		for(String client : clientMap.keySet()) {
			clients[index] = client;
			index++;
		}
		return String.format("%s,%d%s", name, total, Arrays.toString(clients));
	}
}
