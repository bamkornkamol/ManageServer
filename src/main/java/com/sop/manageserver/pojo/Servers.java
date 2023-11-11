package com.sop.manageserver.pojo;

import java.util.ArrayList;

public class Servers {
    private ArrayList<Server> model;

    public Servers(){
        model = new ArrayList<>();
    }

    public ArrayList<Server> getModel() {
        return model;
    }

    public void setModel(ArrayList<Server> model) {
        this.model = model;
    }
}
