package com.example.desafiofluxitmvvm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultResponse {

    @SerializedName("results")
    @Expose
    private List<RamdomUserResponse> ramdomUserResponses = null;
    @SerializedName("info")
    @Expose
    private Info info;

    public List<RamdomUserResponse> getRamdomUserResponses() {
        return ramdomUserResponses;
    }

    public void setRamdomUserResponses(List<RamdomUserResponse> ramdomUserResponses) {
        this.ramdomUserResponses = ramdomUserResponses;
    }

    public ResultResponse withResults(List<RamdomUserResponse> ramdomUserResponses) {
        this.ramdomUserResponses = ramdomUserResponses;
        return this;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public ResultResponse withInfo(Info info) {
        this.info = info;
        return this;
    }
}
