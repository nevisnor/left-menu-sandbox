package com.rsiven.leftmenu.core.busobj;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class User extends RecursiveTreeObject<User> {
    
    private String username;
    private String system;
    
    public User(String username, String system) {
        this.username = username;
        this.system = system;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getSystem() {
        return system;
    }
    
    public void setSystem(String system) {
        this.system = system;
    }
    
   
}
