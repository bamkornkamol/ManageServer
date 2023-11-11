package com.sop.manageserver.repository;

import com.sop.manageserver.pojo.Member;
import com.sop.manageserver.pojo.Server;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
    @Query(value = "{id:  '?0'}")
    public Member findByID(String id);

    @Query(value = "{role: '?0'}")
    public Member findByRole(String role);
}
