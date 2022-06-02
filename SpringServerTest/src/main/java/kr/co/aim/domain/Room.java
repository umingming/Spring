package kr.co.aim.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Room {
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "roomId")
	private int id;
	
	@Column(nullable = false, length = 30)
	private String name;
	
	@Column(nullable = false)
	private int capacity;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate = new Date();
	
	@OneToMany(mappedBy = "room")
	private List<MemberRoom> members = new ArrayList<>();
	
	@OneToMany(mappedBy = "room")
	private List<Message> messages = new ArrayList<>();
	
	@Transient
	private List<Member> memberList;
	
	/*
		capacity 값을 정의하지 않으면 그룹을 무한대로 만듦.
	 */
	public Room() {
		memberList = new ArrayList<>();
	}
	
	/*
		capacity 값을 매개로 받아, 해당 크기 만큼 List을 생성함.
	 */
	public Room(int capacity) {
		this.capacity = capacity;
		memberList = new ArrayList<>(capacity);
	}
	
	/*
	  	isFull()
	  	1. 방에 존재하는 멤버와 capacity의 크기가 같은지를 리턴함.
	 */
	public boolean isFull() {
		return memberList.size() == capacity;
	}
}
