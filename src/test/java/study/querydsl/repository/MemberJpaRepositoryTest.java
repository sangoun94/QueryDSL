package study.querydsl.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.MembersearchCondition;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void 기본테스트() throws Exception{
        Member member1 = new Member("member1", 10);

        memberJpaRepository.save(member1);

        Member member = memberJpaRepository.findById(member1.getId()).get();
        assertThat(member).isEqualTo(member1);

        List<Member> result1 = memberJpaRepository.findAll();
        assertThat(result1).containsExactly(member1);

        List<Member> byUsername1 = memberJpaRepository.findByUsername(member1.getUsername());
        assertThat(byUsername1).containsExactly(member1);
    }

    @Test
    public void 기본Querydsl() throws Exception{
        Member member1 = new Member("member1", 10);

        memberJpaRepository.save(member1);

        Member member = memberJpaRepository.findById(member1.getId()).get();
        assertThat(member).isEqualTo(member1);

        List<Member> result1 = memberJpaRepository.findAll_QueryDSL();
        assertThat(result1).containsExactly(member1);

        List<Member> byUsername1 = memberJpaRepository.findByUsername_QueryDSL(member1.getUsername());
        assertThat(byUsername1).containsExactly(member1);
    }

    
    @Test
    public void searchTest() throws Exception{
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MembersearchCondition membersearchCondition = new MembersearchCondition();
        membersearchCondition.setAgeGoe(35);
        membersearchCondition.setAgeLoe(40);
        membersearchCondition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(membersearchCondition);

        assertThat(result).extracting("username").containsExactly("member4");
    }

    @Test
    public void search() throws Exception{
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MembersearchCondition membersearchCondition = new MembersearchCondition();
        membersearchCondition.setAgeGoe(35);
        membersearchCondition.setAgeLoe(40);
        membersearchCondition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.search(membersearchCondition);

        assertThat(result).extracting("username").containsExactly("member4");
    }
}