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

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ServerController {
    @Autowired
    private ServerService serverService;
    @Autowired
    private MemberService memberService;
    private Servers servers = new Servers();
    private Members members = new Members();
    private ArrayList<String> categories = new ArrayList<String>();
    private ArrayList<Member> startMember = new ArrayList<Member>();
    private ArrayList<Member> oldMember = new ArrayList<Member>();
    private ArrayList<Member> AllMember = new ArrayList<Member>();

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

    @GetMapping("/myserver/{userId}")
    public List<Server> getServersByMembers(@PathVariable("userId") String userId){
        List<Server> serversList = serverService.findByMembers(userId);
        this.servers.setModel((ArrayList<Server>) serversList);
        return this.servers.getModel();
    }

    @PostMapping("/createServer")
    public ResponseEntity<Server> createServer(@RequestBody MultiValueMap<String, String> formdata){
        Map<String, String> d = formdata.toSingleValueMap();
//  builder member
        Member member = memberService.createMember(
                new Member(null,d.get("userId"), d.get("username"), null, "builder")
        );
        this.startMember.add(member);
        Server server =serverService.createServer(
                new Server(null, d.get("name"), d.get("description"), null, this.categories , this.startMember)
        );
        return ResponseEntity.ok(server);
    }

    @PostMapping("/joinServer/{serverId}")
    public String joinServer(@PathVariable("serverId") String serverId, @RequestBody MultiValueMap<String, String> formdata){
        Server server = serverService.findById(serverId);
        if (server != null) {
            Map<String, String> d = formdata.toSingleValueMap();
            Member member = memberService.createMember(
                    new Member(null,d.get("userId"), d.get("username"), null, "member")
            );
            this.oldMember = server.getMember();
            this.oldMember.add(member);
            Server serverNew = serverService.updateServer((
                    new Server(serverId, server.getName(), server.getDescription(), server.getImage(), server.getCategories(), this.oldMember)
                    ));
            return "join success";
        }
        System.out.println(0);
        return "not found";
    };

    @PostMapping("/updateServer/{serverId}")
    public ResponseEntity<Server> updateServer(@PathVariable("serverId") String serverId,@RequestBody MultiValueMap<String, String> formdata){
        Server server = serverService.findById(serverId);
        if (server != null){
            Map<String, String> d = formdata.toSingleValueMap();
            this.categories = server.getCategories();
            Server servernew =serverService.updateServer(
                    new Server(d.get("id"), d.get("name"), d.get("description"), d.get("img"), this.categories , server.getMember())
            );
            return ResponseEntity.ok(servernew);
        }
        return ResponseEntity.ok(null);

    };

    @PostMapping("/deleteServer/{serverId}")
    public ResponseEntity<Boolean> deleteServer(@PathVariable("serverId") String serverId){
        Server server = serverService.findById(serverId);
        boolean status =serverService.deleteServer(server);
        if (status){
            servers.getModel().remove(server);
        }
        return ResponseEntity.ok(status);
    }

    @PostMapping("/deleteeMember/{serverId}")
    public ResponseEntity<Server> deleteMember(@PathVariable("serverId") String serverId,@RequestBody MultiValueMap<String, String> formdata){
        Server server = serverService.findById(serverId);
        if(server != null){
            Map<String, String> d = formdata.toSingleValueMap();
            AllMember = server.getMember();
            for (int i = 0; i < AllMember.size(); i++) {
                if (AllMember.get(i).getId().equals(d.get("userId"))) {
                    AllMember.remove(i);
                }
            }
            System.out.println(AllMember);
            this.categories = server.getCategories();
            Server servernew =serverService.updateServer(
                    new Server(serverId, server.getName(), server.getDescription(), server.getImage(), this.categories , AllMember)
            );
            return ResponseEntity.ok(servernew);
        }
        return ResponseEntity.ok(null);
    };
}
