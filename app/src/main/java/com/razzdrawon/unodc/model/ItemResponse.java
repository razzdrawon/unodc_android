package com.razzdrawon.unodc.model;

import java.util.List;

/**
 * Created by mapadi3 on 09/07/17.
 */

public class ItemResponse {

    private Integer qstnNbr;
    private String openAns;
    private List<String> optsAns;
    private String depOpenAns;
    private Integer depOpt;

    public ItemResponse() {
    }

    public ItemResponse(Integer qstnNbr, String openAns, List<String> optsAns, String depOpenAns, Integer depOpt) {
        this.qstnNbr = qstnNbr;
        this.openAns = openAns;
        this.optsAns = optsAns;
        this.depOpenAns = depOpenAns;
        this.depOpt = depOpt;
    }

    public Integer getQstnNbr() {
        return qstnNbr;
    }

    public void setQstnNbr(Integer qstnNbr) {
        this.qstnNbr = qstnNbr;
    }

    public String getOpenAns() {
        return openAns;
    }

    public void setOpenAns(String openAns) {
        this.openAns = openAns;
    }

    public List<String> getOptsAns() {
        return optsAns;
    }

    public void setOptsAns(List<String> optsAns) {
        this.optsAns = optsAns;
    }

    public String getDepOpenAns() {
        return depOpenAns;
    }

    public void setDepOpenAns(String depOpenAns) {
        this.depOpenAns = depOpenAns;
    }

    public Integer getDepOpt() {
        return depOpt;
    }

    public void setDepOpt(Integer depOpt) {
        this.depOpt = depOpt;
    }
}
