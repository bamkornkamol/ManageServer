package com.sop.manageserver.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
public class Room {
    @Id
    private String _id;
    private String name;
    private String type;

    public Room(){}
    public Room(String _id, String name, String type){
        this._id = _id;
        this.name = name;
        this.type = type;
    }
};
