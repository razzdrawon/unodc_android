package com.razzdrawon.unodc.model;

/**
 * Created by chabelitl261186 on 11/07/17.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectSync {

    private Integer id;
    private String json;
    private Boolean sync;

    public ObjectSync() {
    }

    public ObjectSync(Integer id, String json) {
        this.id = id;
        this.json = json;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Boolean getSync() {
        return sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }

    @Override
    public String toString() {
        return( id + ". " + sync);
    }
}