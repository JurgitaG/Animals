package com.animals.requests;

import lombok.Data;

import java.util.Date;

@Data
public class AnimalRequest {
    private String ownerId;
    private String name;
    private String num;
    private Date birthDate;

    public String getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getNum(){
        return num;
    }
    public void setNum(String num){
        this.num = num;
    }
    public Date getBirthDate(){
        return birthDate;
    }
    public void setBirthDate(Date birthDate){
        this.birthDate = birthDate;
    }
}
