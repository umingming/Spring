package kr.co.aim;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 	ClientGroup; 클라이언트들의 그룹을 정의함.
 */
@Component
public class ClientGroup {
    private Map<String, OutputStream> map;
    private String name;
    private int total;

    /*
        total값을 정의하지 않으면 그룹을 무한대로 만듦.
     */
    public ClientGroup() {
        map = new HashMap<>();
    }

    /*
        total값을 매개로 받아, 해당 크기 만큼 map을 생성함.
     */
    public ClientGroup(int total) {
        this.total = total;
        map = new HashMap<>(total);
    }

    public Map<String, OutputStream> getClientMap() {
        return map;
    }

    public void setMap(Map<String, OutputStream> clientMap) {
        this.map = clientMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
          isFull()
          1. 존재하는 요소와 total의 크기가 같은지를 리턴함.
     */
    public boolean isFull() {
        return map.size() == total;
    }

    @Override
    public String toString() {
        String[] clients = new String[map.size()];
        int index = 0;

        for(String client : map.keySet()) {
            clients[index] = client;
            index++;
        }
        return String.format("%s,%d%s", name, total, Arrays.toString(clients));
    }
}
