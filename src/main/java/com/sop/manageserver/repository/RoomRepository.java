package com.sop.manageserver.repository;

//import com.sop.manageserver.pojo.Member;
import com.sop.manageserver.pojo.Room;
import com.sop.manageserver.pojo.Server;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    @Query(value = "{_id:  '?0'}")
    public Room findByID(String id);

    @Query(value = "{name: '?0'}")
    public Room findByName(String name);

    @Query(value = "{type: '?0'}")
    public Room findByType(String type);
}
