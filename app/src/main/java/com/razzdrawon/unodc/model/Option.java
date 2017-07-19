package com.razzdrawon.unodc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by mapadi3 on 02/07/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Option {

    //Params to show the option
    private String opt;
    private String optStr;

    //Params to show dependent text
    @JsonProperty("openOptFlag")
    private Boolean openDepAnswerFlag = false;
    @JsonProperty("openOptStr")
    private String openDepAnswer;

    //Param to show dependent options
    @JsonProperty("options")
    private List<DepOption> depOptions;

    //Param to check if the option is checked
    private Boolean chosen = false;


    private Integer nextQstn;
    private List<Integer> blocks;
    private List<Integer> enables;



    public Option() {
    }

    public Option(String opt, String strOpt) {
        this.opt = opt;
        this.optStr = strOpt;
    }

    public Option(String opt, String optStr, Boolean chosen, Boolean openAnswerFlag, String openDepAnswer, List<DepOption> depOptions) {
        this.opt = opt;
        this.optStr = optStr;
        this.chosen = chosen;
        this.openDepAnswerFlag = openAnswerFlag;
        this.openDepAnswer = openDepAnswer;
        this.depOptions = depOptions;
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

    public Boolean getDepOpenAnswerFlag() {
        return openDepAnswerFlag;
    }

    public void setDepOpenAnswerFlag(Boolean openDepAnswerFlag) {
        this.openDepAnswerFlag = openDepAnswerFlag;
    }

    public String getDepOpenAnswer() {
        return openDepAnswer;
    }

    public void setDepOpenAnswer(String openDepAnswer) {
        this.openDepAnswer = openDepAnswer;
    }

    public List<DepOption> getDepOptions() {
        return depOptions;
    }

    public void setDepOptions(List<DepOption> depOptions) {
        this.depOptions = depOptions;
    }

    public Integer getNextQstn() {
        return nextQstn;
    }

    public void setNextQstn(Integer nextQstn) {
        this.nextQstn = nextQstn;
    }

    public List<Integer> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Integer> blocks) {
        this.blocks = blocks;
    }

    public List<Integer> getEnables() {
        return enables;
    }

    public void setEnables(List<Integer> enables) {
        this.enables = enables;
    }
}
