package com.sop.manageserver.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Data
    public class Categories {
        @Id
        private String _id;
        private String name;
        private ArrayList<Room> room;

        public Categories(){}
        public Categories(String _id, String name, ArrayList<Room> room){
            this._id = _id;
            this.name = name;
            this.room = room;
        }
}

