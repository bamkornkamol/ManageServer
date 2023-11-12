package com.sop.manageserver.controller;

import com.sop.manageserver.pojo.*;
import com.sop.manageserver.repository.CategoriesService;
import com.sop.manageserver.repository.MemberService;
import com.sop.manageserver.repository.RoomService;
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
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private RoomService roomService;
    private Servers servers = new Servers();
    private Members members = new Members();
    private Categoriess categoriess =new Categoriess();
    private Rooms rooms = new Rooms();
    private ArrayList<String> categories = new ArrayList<String>();
    private ArrayList<Member> startMember = new ArrayList<Member>();
    private ArrayList<Member> oldMember = new ArrayList<Member>();
    private ArrayList<Member> AllMember = new ArrayList<Member>();
    private ArrayList<Server> Myserver = new ArrayList<Server>();
    private ArrayList<Room> Roomlist = new ArrayList<Room>();
    private ArrayList<Room> Roomold = new ArrayList<Room>();
    private ArrayList<Categories> Categorieslist = new ArrayList<Categories>();

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

    @PostMapping("/createServer")
    public ResponseEntity<Server> createServer(@RequestBody MultiValueMap<String, String> formdata){
        Map<String, String> d = formdata.toSingleValueMap();
//  builder member
        Member member = memberService.createMember(
                new Member(null,d.get("userId"), d.get("username"), null, "builder")
        );
        this.startMember.add(member);
        Server server =serverService.createServer(
                new Server(null, d.get("name"), d.get("description"), null, Categorieslist , this.startMember)
        );
        return ResponseEntity.ok(server);
    }

    @PostMapping("/createCategories/{serverId}")
    public ResponseEntity<Server> createCategories(@RequestBody MultiValueMap<String, String> formdata, @PathVariable("serverId") String serverId){
        Map<String, String> d = formdata.toSingleValueMap();
        Server server = serverService.findById(serverId);
        Categories categories = categoriesService.createCategories(
                new Categories(null, d.get("name"), Roomlist)
        );
        Categorieslist.add(categories);
        Server servernew =serverService.updateServer(
                new Server(serverId, server.getName(), server.getDescription(), server.getImage(), Categorieslist , server.getMember())
        );
        return ResponseEntity.ok(servernew);
    }

    @PostMapping("/createRoom/{serverId}")
    public ResponseEntity<Server> createRoom(@RequestBody MultiValueMap<String, String> formdata, @PathVariable("serverId") String serverId){
        Map<String, String> d = formdata.toSingleValueMap();
        Server server = serverService.findById(serverId);
        Room room = roomService.createRoom(
                new Room(null, d.get("name"), d.get("type"))
        );
        Categorieslist = server.getCategories();
        Roomlist.add(room);
        for (int i = 0; i < Categorieslist.size(); i++) {
            if (Categorieslist.get(i).get_id().equals(d.get("categoriesId"))) {
                Categorieslist.get(i).setRoom(Roomlist);
            }
        }
        Server servernew =serverService.updateServer(
                new Server(serverId, server.getName(), server.getDescription(), server.getImage(), Categorieslist , server.getMember())
        );
        return ResponseEntity.ok(servernew);
    }

    @PostMapping("/updateRoom/{serverId}")
    public ResponseEntity<Server> updateRoom(@RequestBody MultiValueMap<String, String> formdata, @PathVariable("serverId") String serverId){
        Map<String, String> d = formdata.toSingleValueMap();
        Server server = serverService.findById(serverId);
        Categorieslist = server.getCategories();
        for (int i = 0; i < Categorieslist.size(); i++) {
            if (Categorieslist.get(i).get_id().equals(d.get("categoriesId"))) {
                Roomlist = Categorieslist.get(i).getRoom();
                for (int j = 0; j < Roomlist.size(); j++){
                    if (Roomlist.get(j).get_id().equals(d.get("roomId"))){
                        Roomlist.get(i).setName(d.get("name"));
                        Roomlist.get(i).setType(d.get("type"));
                    }
                }
                Categorieslist.get(i).setRoom(Roomlist);
            }
        }
        Server servernew =serverService.updateServer(
                new Server(serverId, server.getName(), server.getDescription(), server.getImage(), Categorieslist , server.getMember())
        );
        return ResponseEntity.ok(servernew);
    }

    @PostMapping("/deleteRoom/{serverId}")
    public ResponseEntity<Server> deleteRoom(@RequestBody MultiValueMap<String, String> formdata, @PathVariable("serverId") String serverId){
        Map<String, String> d = formdata.toSingleValueMap();
        Server server = serverService.findById(serverId);
        Categorieslist = server.getCategories();
        for (int i = 0; i < Categorieslist.size(); i++) {
            if (Categorieslist.get(i).get_id().equals(d.get("categoriesId"))) {
                Roomlist = Categorieslist.get(i).getRoom();
                for (int j = 0; j < Roomlist.size(); j++){
                    if (Roomlist.get(j).get_id().equals(d.get("roomId"))){
                        Roomlist.remove(i);
                    }
                }
                Categorieslist.get(i).setRoom(Roomlist);
            }
        }
        Server servernew =serverService.updateServer(
                new Server(serverId, server.getName(), server.getDescription(), server.getImage(), Categorieslist , server.getMember())
        );
        return ResponseEntity.ok(servernew);
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
            this.Categorieslist = server.getCategories();
            Server servernew =serverService.updateServer(
                    new Server(d.get("id"), d.get("name"), d.get("description"), d.get("img"), this.Categorieslist , server.getMember())
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

    @PostMapping("/deleteMember/{serverId}")
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
            this.Categorieslist = server.getCategories();
            Server servernew =serverService.updateServer(
                    new Server(serverId, server.getName(), server.getDescription(), server.getImage(), this.Categorieslist , AllMember)
            );
            return ResponseEntity.ok(servernew);
        }
        return ResponseEntity.ok(null);
    };

    @PostMapping("/updateCategories/{serverId}")
    public ResponseEntity<Server> updateCategories(@PathVariable("serverId") String serverId,@RequestBody MultiValueMap<String, String> formdata){
        Server server = serverService.findById(serverId);
        if(server != null){
            Map<String, String> d = formdata.toSingleValueMap();
            Categorieslist = server.getCategories();
            for (int i = 0; i < Categorieslist.size(); i++) {
                if (Categorieslist.get(i).get_id().equals(d.get("categoriesId"))) {
                    Categorieslist.get(i).setName(d.get("newName"));
                }
            }
            System.out.println(Categorieslist);
            this.AllMember = server.getMember();
            Server servernew =serverService.updateServer(
                    new Server(serverId, server.getName(), server.getDescription(), server.getImage(), this.Categorieslist , AllMember)
            );
            return ResponseEntity.ok(servernew);
        }
        return ResponseEntity.ok(null);
    };

    @PostMapping("/deleteCategories/{serverId}")
    public ResponseEntity<Server> deleteCategories(@PathVariable("serverId") String serverId,@RequestBody MultiValueMap<String, String> formdata){
        Server server = serverService.findById(serverId);
        if(server != null){
            Map<String, String> d = formdata.toSingleValueMap();
            Categorieslist = server.getCategories();
            for (int i = 0; i < Categorieslist.size(); i++) {
                if (Categorieslist.get(i).get_id().equals(d.get("categoriesId"))) {
                    Categorieslist.remove(i);
                }
            }
            System.out.println(Categorieslist);
            this.AllMember = server.getMember();
            Server servernew =serverService.updateServer(
                    new Server(serverId, server.getName(), server.getDescription(), server.getImage(), this.Categorieslist , AllMember)
            );
            return ResponseEntity.ok(servernew);
        }
        return ResponseEntity.ok(null);
    };

    @GetMapping("/server/user/{userId}")
    public ArrayList<Server> getServersByMembers(@PathVariable("userId") String userId){
        List<Server> serversList = serverService.allServer();
        for(int i =0; i < serversList.size(); i++){
            for (int j = 0; j < serversList.get(i).getMember().size(); j++){
                if (serversList.get(i).getMember().get(j).getId().equals(userId)){
                    this.Myserver.add(serversList.get(i));
                }
            }
        }
        return Myserver;
    }


}
