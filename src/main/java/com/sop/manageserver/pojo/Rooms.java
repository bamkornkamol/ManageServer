package com.sop.manageserver.pojo;

import java.util.ArrayList;

public class Rooms {
    private ArrayList<Room> model;

    public Rooms(){
        model = new ArrayList<>();
    }

    public ArrayList<Room> getModel(){
        return model;
    }

    public void setModel(ArrayList<Room> model) {
        this.model = model;
    }
}
