package com.example.desafiofluxitmvvm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Street {

    @SerializedName("number")
    @Expose
    private Long number;
    @SerializedName("name")
    @Expose
    private String name;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Street withNumber(Long number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Street withName(String name) {
        this.name = name;
        return this;
    }

}
