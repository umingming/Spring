package jpabook.jpashop;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class MemberRepositoryTest {
	@Autowired 
	MemberRepository memeberRepository;
	
	@Test
	@Transactional
	private void testMember() {
		Member member = new Member();
		member.setUsername("memberA");
		
		Long savedId = memeberRepository.save(member);
		Member findMember = memeberRepository.find(savedId);
		
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		Assertions.assertThat(findMember).isEqualTo(member);
		System.out.println("findMember == member : " + (findMember == member));
	}
}
