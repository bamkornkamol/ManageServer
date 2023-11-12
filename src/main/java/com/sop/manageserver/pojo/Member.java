package com.sop.manageserver.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Member")
public class Member {
    @Id
    private String _id;
    private String id;
    private String username;
    private String image;
    private String role;

    public Member(){}
    public Member(String _id, String id, String username, String image, String role){
        this._id = _id;
        this.id = id;
        this.username = username;
        this.image = image;
        this.role = role;
    }
}
