package com.sop.manageserver.repository;

import com.sop.manageserver.pojo.Server;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepository extends MongoRepository<Server, String> {
    @Query(value = "{_id:  '?0'}")
    public Server findByID(String id);

    @Query( value = "{name:'?0'}")
    public Server findByName(String name);

    @Query( value = "{member: {$elemMatch:  {id: '?0'}}}")
    public List<Server> findByMembers(String id);

}
