package com.sop.manageserver.repository;


import com.sop.manageserver.pojo.Room;
import com.sop.manageserver.pojo.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository repository;

    public RoomService(RoomRepository repository){
        this.repository=repository;
    }

    public List<Room> allRoom(){
        return repository.findAll();
    }

    public Room findById(String id){
        return repository.findByID(id);
    }

    public Room findByName(String name){
        return repository.findByName(name);
    }

    public Room findByType(String type){
        return repository.findByType(type);
    }

    public  Room createRoom(Room room){
        return repository.insert(room);
    }

    public Room updateRoom(Room room){
        return repository.save(room);
    }

    public boolean deleteRoom(Room room){
        try{
            repository.delete((room));
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
