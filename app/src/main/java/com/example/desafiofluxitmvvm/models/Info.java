package com.example.desafiofluxitmvvm.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("seed")
    @Expose
    private String seed;
    @SerializedName("results")
    @Expose
    private Long results;
    @SerializedName("page")
    @Expose
    private Long page;
    @SerializedName("version")
    @Expose
    private String version;

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public Info withSeed(String seed) {
        this.seed = seed;
        return this;
    }

    public Long getResults() {
        return results;
    }

    public void setResults(Long results) {
        this.results = results;
    }

    public Info withResults(Long results) {
        this.results = results;
        return this;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Info withPage(Long page) {
        this.page = page;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Info withVersion(String version) {
        this.version = version;
        return this;
    }

}
