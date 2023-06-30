package com.example.myapplication;

public class NumberData {
    private String name;
    private String number;

    public NumberData(String name, String number){
        this.name = name;
        this.number = number;
    }

    public String getName(){
        return name;
    }
    public String getNumber(){
        return number;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setNumber(String number){
        this.number = number;
    }
}
