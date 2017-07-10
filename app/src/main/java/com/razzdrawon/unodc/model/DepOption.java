package com.razzdrawon.unodc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mapadi3 on 10/07/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepOption {

    private Integer opt;
    private String optStr;

    public DepOption() {
    }

    public DepOption(Integer opt, String optStr) {
        this.opt = opt;
        this.optStr = optStr;
    }

    public Integer getOpt() {
        return opt;
    }

    public void setOpt(Integer opt) {
        this.opt = opt;
    }

    public String getOptStr() {
        return optStr;
    }

    public void setOptStr(String optStr) {
        this.optStr = optStr;
    }

    @Override
    public String toString() {
        return( opt + ". " + optStr);
    }
}
