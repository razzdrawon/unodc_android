package com.razzdrawon.unodc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mapadi3 on 02/07/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    //params to show the question
    private Integer qstnNbr;
    private String qstnStr;

    //Params to show open answer if exists
    @JsonProperty("openOptFlag")
    private Boolean openAnswerFlag = false;
    @JsonProperty("openOptStr")
    private String openAnswer;

    //Params to show options to choose
    private List<Option> options;

    //Param to show if it is already shown in the screen
    private Boolean isAlreadyShown = false;
    private Boolean isBlocked = false;


    @JsonProperty("check")
    private Integer maxCheck;

    public Item() {
    }

    public Item(Integer qstnNbr, String qstnStr) {
        this.qstnNbr = qstnNbr;
        this.qstnStr = qstnStr;
    }

    public Item(Integer qstnNbr, String qstnStr, Boolean openAnswerFlag, String openAnswer, List<Option> options) {
        this.qstnNbr = qstnNbr;
        this.qstnStr = qstnStr;
        this.openAnswerFlag = openAnswerFlag;
        this.openAnswer = openAnswer;
        this.options = options;
    }

    public Integer getQstnNbr() {
        return qstnNbr;
    }

    public void setQstnNbr(Integer qstnNbr) {
        this.qstnNbr = qstnNbr;
    }

    public String getQstnStr() {
        return qstnStr;
    }

    public void setQstnStr(String qstnStr) {
        this.qstnStr = qstnStr;
    }

    public Integer getMaxCheck() {
        return maxCheck;
    }

    public void setMaxCheck(Integer maxCheck) {
        this.maxCheck = maxCheck;
    }

    public Boolean getOpenAnswerFlag() {
        return openAnswerFlag;
    }

    public void setOpenAnswerFlag(Boolean openAnswerFlag) {
        this.openAnswerFlag = openAnswerFlag;
    }

    public String getOpenAnswer() {
        return openAnswer;
    }

    public void setOpenAnswer(String openAnswer) {
        this.openAnswer = openAnswer;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<String> getOptionsByStrings() {
        List<String> options = new ArrayList<String>();
        for (Option opt : this.getOptions()) {
            options.add(opt.getOpt() + ") " + opt.getOptStr());
        }
        return options;
    }

    public Boolean getAlreadyShown() {
        return isAlreadyShown;
    }

    public void setAlreadyShown(Boolean alreadyShown) {
        isAlreadyShown = alreadyShown;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }
}
