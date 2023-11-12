package com.sop.manageserver.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@Document(collection = "Server")
public class Server {
    @Id
    private String _id;
    private String name;
    private String description;
    private String image;
    private ArrayList<String> categories;
    private ArrayList<Member> member;

    public Server(){}
    public Server(String _id, String name, String description, String image, ArrayList<String> categories, ArrayList<Member> member){
        this._id = _id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.categories = categories;
        this.member = member;
    }
}
