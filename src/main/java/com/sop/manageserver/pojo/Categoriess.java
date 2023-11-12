package com.sop.manageserver.pojo;

import java.util.ArrayList;

public class Categoriess {
    private ArrayList<Categories> model;

    public Categoriess(){
        model = new ArrayList<>();
    }

    public ArrayList<Categories> getModel(){
        return model;
    }

    public void setModel(ArrayList<Categories> model) {
        this.model = model;
    }
}
