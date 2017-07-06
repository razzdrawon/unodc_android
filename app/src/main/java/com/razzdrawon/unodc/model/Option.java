package com.razzdrawon.unodc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by mapadi3 on 02/07/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Option {
    private String opt;
    private String optStr;

    public Option() {
    }

    public Option(String opt, String strOpt) {
        this.opt = opt;
        this.optStr = strOpt;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getOptStr() {
        return optStr;
    }

    public void setOptStr(String strOpt) {
        this.optStr = strOpt;
    }
}
