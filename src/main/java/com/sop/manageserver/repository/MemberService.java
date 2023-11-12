package com.sop.manageserver.repository;

import com.sop.manageserver.pojo.Member;
import com.sop.manageserver.pojo.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberRepository repository;
    public Member createMember(Member member){
        repository.insert(member);
        return member;
    }

    public Member findById(String id){
        return repository.findByID(id);
    }

    public Member findByRole(String role){
        return  repository.findByRole(role);
    }

    public boolean deleteMember(Member member){
        try{
            repository.delete((member));
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
