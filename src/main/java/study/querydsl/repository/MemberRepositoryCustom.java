package study.querydsl.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.dto.MembersearchCondition;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MembersearchCondition condition);
    Page<MemberTeamDto> searchPageSimple(MembersearchCondition condition, Pageable pageable);
    Page<MemberTeamDto> searchPageComplex(MembersearchCondition condition, Pageable pageable);
}
