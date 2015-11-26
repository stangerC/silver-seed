package com.silver.seed.query.showcase.web;

/**
 * Created by Liaojian on 2015/3/26.
 */
public class WizardNode {

    private String name;

    private String code;

    private String label;

    private String path;

    public WizardNode(String name) {
        this.name = name;
    }

    public WizardNode() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
