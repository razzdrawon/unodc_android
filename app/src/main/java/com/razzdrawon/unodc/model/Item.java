package com.razzdrawon.unodc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mapadi3 on 02/07/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private String qstnNbr;
    private String qstnStr;

    @JsonProperty("openOptFlag")
    private Boolean openAnswerFlag = false;
    @JsonProperty("openOptStr")
    private String openAnswer;

    private List<Option> options;
    private Map<Integer, Option> optionsChosen = new HashMap<>();

    private String dependentOpenAnswer;
    private List<Option> dependentOptions;

    private Boolean isShown = false;

    public Item() {
    }

    public Item(String qstnNbr, String qstnStr) {
        this.qstnNbr = qstnNbr;
        this.qstnStr = qstnStr;
    }

    public Item(String qstnNbr, String qstnStr, Boolean openAnswerFlag, String openAnswer, List<Option> options, String dependentOpenAnswer, List<Option> dependentOptions) {
        this.qstnNbr = qstnNbr;
        this.qstnStr = qstnStr;
        this.openAnswerFlag = openAnswerFlag;
        this.openAnswer = openAnswer;
        this.options = options;
        this.dependentOpenAnswer = dependentOpenAnswer;
        this.dependentOptions = dependentOptions;
    }

    public String getQstnNbr() {
        return qstnNbr;
    }

    public void setQstnNbr(String qstnNbr) {
        this.qstnNbr = qstnNbr;
    }

    public String getQstnStr() {
        return qstnStr;
    }

    public void setQstnStr(String qstnStr) {
        this.qstnStr = qstnStr;
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

    public Map<Integer, Option> getOptionsChosen() {
        return optionsChosen;
    }

    public void setOptionsChosen(Map<Integer, Option> optionsChosen) {
        this.optionsChosen = optionsChosen;
    }

    public String getDependentOpenAnswer() {
        return dependentOpenAnswer;
    }

    public void setDependentOpenAnswer(String dependentOpenAnswer) {
        this.dependentOpenAnswer = dependentOpenAnswer;
    }

    public List<Option> getDependentOptions() {
        return dependentOptions;
    }

    public void setDependentOptions(List<Option> dependentOptions) {
        this.dependentOptions = dependentOptions;
    }

    public List<String> getOptionsByStrings() {
        List<String> options = new ArrayList<String>();
        for (Option opt : this.getOptions()) {
            options.add(opt.getOpt() + ") " + opt.getOptStr());
        }
        return options;
    }

    public Boolean getShown() {
        return isShown;
    }

    public void setShown(Boolean shown) {
        isShown = shown;
    }
}
