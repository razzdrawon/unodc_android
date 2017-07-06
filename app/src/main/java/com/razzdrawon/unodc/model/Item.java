package com.razzdrawon.unodc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("options")
    private Set<Option> multiChoiceAnwers;
    private String dependentOpenAnswer;
    private Set<Option> dependentMultiChoiceAnwers;

    public Item() {
    }

    public Item(String qstnNbr, String qstnStr) {
        this.qstnNbr = qstnNbr;
        this.qstnStr = qstnStr;
    }

    public Item(String qstnNbr, String qstnStr, Boolean openAnswerFlag, String openAnswer, Set<Option> multiChoiceAnwers, String dependentOpenAnswer, Set<Option> dependentMultiChoiceAnwers) {
        this.qstnNbr = qstnNbr;
        this.qstnStr = qstnStr;
        this.openAnswerFlag = openAnswerFlag;
        this.openAnswer = openAnswer;
        this.multiChoiceAnwers = multiChoiceAnwers;
        this.dependentOpenAnswer = dependentOpenAnswer;
        this.dependentMultiChoiceAnwers = dependentMultiChoiceAnwers;
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

    public Set<Option> getMultiChoiceAnwers() {
        return multiChoiceAnwers;
    }

    public void setMultiChoiceAnwers(Set<Option> multiChoiceAnwers) {
        this.multiChoiceAnwers = multiChoiceAnwers;
    }

    public String getDependentOpenAnswer() {
        return dependentOpenAnswer;
    }

    public void setDependentOpenAnswer(String dependentOpenAnswer) {
        this.dependentOpenAnswer = dependentOpenAnswer;
    }

    public Set<Option> getDependentMultiChoiceAnwers() {
        return dependentMultiChoiceAnwers;
    }

    public void setDependentMultiChoiceAnwers(Set<Option> dependentMultiChoiceAnwers) {
        this.dependentMultiChoiceAnwers = dependentMultiChoiceAnwers;
    }
}
