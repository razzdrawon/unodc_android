package com.razzdrawon.unodc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by mapadi3 on 02/07/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Option {
    private String opt;
    private String optStr;
    private Boolean chosen = false;
    @JsonProperty("openOptFlag")
    private Boolean openAnswerFlag = false;
    @JsonProperty("openOptStr")
    private String openAnswer;
    private List<DepOption> options;
    private Integer dependentChosen;


    public Option() {
    }

    public Option(String opt, String strOpt) {
        this.opt = opt;
        this.optStr = strOpt;
    }

    public Option(String opt, String optStr, Boolean chosen, Boolean openAnswerFlag, String openAnswer, List<DepOption> options, Integer dependentChosen) {
        this.opt = opt;
        this.optStr = optStr;
        this.chosen = chosen;
        this.openAnswerFlag = openAnswerFlag;
        this.openAnswer = openAnswer;
        this.options = options;
        this.dependentChosen = dependentChosen;
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

    public void setOptStr(String optStr) {
        this.optStr = optStr;
    }

    public Boolean getChosen() {
        return chosen;
    }

    public void setChosen(Boolean chosen) {
        this.chosen = chosen;
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

    public List<DepOption> getOptions() {
        return options;
    }

    public void setOptions(List<DepOption> options) {
        this.options = options;
    }

    public Integer getDependentChosen() {
        return dependentChosen;
    }

    public void setDependentChosen(Integer dependentChosen) {
        this.dependentChosen = dependentChosen;
    }
}
