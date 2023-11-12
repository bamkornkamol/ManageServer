package com.sop.manageserver.repository;

import com.sop.manageserver.pojo.Member;
import com.sop.manageserver.pojo.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {
    @Autowired
    private ServerRepository repository;

    public ServerService(ServerRepository repository){
        this.repository = repository;
    }

    public List<Server> allServer(){
        return repository.findAll();
    }

    public Server findById(String id){
        return repository.findByID(id);
    }

    public Server serverByName(String name){
        return repository.findByName(name);
    }

    public List<Server> findByMembers(String id){
        return repository.findByMembers(id);
    }

    public Server createServer(Server server){
        return repository.insert(server);
    }

    public Server updateServer(Server server){
        return repository.save(server);
    }

    public boolean deleteServer(Server server){
        try{
            repository.delete((server));
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
