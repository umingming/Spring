package kr.co.aim.test;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import lombok.Data;

//@Data
public class AppleRepository {
	private EntityManager em;
	
	public int save(Apple apple) {
		em.persist(apple);
		return apple.getSeq();
	}
	
	public Apple find(int seq) {
		return em.find(Apple.class, seq);
	}
}
