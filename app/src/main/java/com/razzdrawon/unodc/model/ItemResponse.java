package com.razzdrawon.unodc.model;

import java.util.List;

/**
 * Created by mapadi3 on 09/07/17.
 */

public class ItemResponse {

    private Integer qstnNbr;
    private String openAns;
    private List<String> optsAns;
//    private String depOpenAns

    public ItemResponse() {
    }

    public ItemResponse(Integer qstnNbr, String openAns) {
        this.qstnNbr = qstnNbr;
        this.openAns = openAns;
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

}
