package com.sop.manageserver.pojo;

import java.util.ArrayList;

public class Members {
    private ArrayList<Member> model;

    public Members(){
        model = new ArrayList<>();
    }

    public ArrayList<Member> getModel(){
        return model;
    }

    public void setModel(ArrayList<Member> model) {
        this.model = model;
    }
}
