package com.sop.manageserver.controller;

import com.sop.manageserver.pojo.Member;
import com.sop.manageserver.pojo.Members;
import com.sop.manageserver.pojo.Server;
import com.sop.manageserver.pojo.Servers;
import com.sop.manageserver.repository.MemberService;
import com.sop.manageserver.repository.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ServerController {
    @Autowired
    private ServerService serverService;
    private MemberService memberService;
    private Servers servers = new Servers();
    private Members members = new Members();
    private ArrayList<String> categories = new ArrayList<String>();

    @GetMapping("/servers")
    public List<Server> getServers(){
        List<Server> serversList = serverService.allServer();
        this.servers.setModel((ArrayList<Server>) serversList);
        return this.servers.getModel();
    }

    @GetMapping("/server/{name}")
    public ResponseEntity<Server> getServerByName(@PathVariable("name") String name){
        System.out.println(name);
        Server server = serverService.serverByName(name);
        return ResponseEntity.ok(server);
    }

    //แก้
    @GetMapping("/myserver/{id}")
    public List<Server> getServersByMembers(@PathVariable("id") int id){
        List<Server> serversList = serverService.findByMembers(id);
        this.servers.setModel((ArrayList<Server>) serversList);
        return this.servers.getModel();
    }

    //แก้
    @PostMapping("/createServer")
    public ResponseEntity<Server> createServer(@RequestBody MultiValueMap<String, String> formdata){
        Map<String, String> d = formdata.toSingleValueMap();
        Member member = memberService.createMember(
                new Member(d.get("userId"), d.get("username"), null, "builder")
        );
        Server server =serverService.createServer(
                new Server(null, d.get("name"), d.get("description"), null, this.categories , member)
        );
        return ResponseEntity.ok(server);
    }

    //แก้
    @PostMapping("/joinServer/{serverId}/{userId}")
    public String joinServer(@PathVariable("serverId") String serverId, @PathVariable("userId") String userId){
        Server server = serverService.findById(serverId);
        if (server != null) {
            return "mee";
        }
        System.out.println(0);
        return "not found";
    };


}
