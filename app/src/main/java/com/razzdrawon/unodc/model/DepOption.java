package com.razzdrawon.unodc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by mapadi3 on 10/07/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepOption {

    //Params to show dependent options
    @JsonProperty("opt")
    private Integer depOpt;
    @JsonProperty("optStr")
    private String depOptStr;

    //Param to check if the dependent option is checked
    private Boolean chosen = false;

    public DepOption() {
    }

    public DepOption(Integer depOpt, String depOptStr) {
        this.depOpt = depOpt;
        this.depOptStr = depOptStr;
    }

    public Integer getDepOpt() {
        return depOpt;
    }

    public void setDepOpt(Integer depOpt) {
        this.depOpt = depOpt;
    }

    public String getDepOptStr() {
        return depOptStr;
    }

    public void setDepOptStr(String depOptStr) {
        this.depOptStr = depOptStr;
    }

    public Boolean getChosen() {
        return chosen;
    }

    public void setChosen(Boolean chosen) {
        this.chosen = chosen;
    }

    @Override
    public String toString() {
        return( depOpt + ". " + depOptStr);
    }
}
