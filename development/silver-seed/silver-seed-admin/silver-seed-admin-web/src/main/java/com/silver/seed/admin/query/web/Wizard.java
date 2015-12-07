package com.silver.seed.admin.query.web;


import com.silver.wheel.common.exception.CodedRuntimeException;

import java.util.LinkedList;

class Step {

    private WizardNode wizardNode;

    private Step nextStep;

    private Step previousStep;

    public Step(WizardNode wizardNode) {
        this.wizardNode = wizardNode;
    }

    public WizardNode getWizardNode() {
        return wizardNode;
    }

    public void setWizardNode(WizardNode wizardNode) {
        this.wizardNode = wizardNode;
    }

    public Step getNextStep() {
        return nextStep;
    }

    public void setNextStep(Step nextStep) {
        this.nextStep = nextStep;
    }

    public Step getPreviousStep() {
        return previousStep;
    }

    public void setPreviousStep(Step previousStep) {
        this.previousStep = previousStep;
    }
}

/**
 * Created by Liaojian on 2015/3/26.
 */
public class Wizard {

    private LinkedList<Step> steps = new LinkedList<>();

    private Step currentStep;

    private String label;

    public Wizard(String[] nodeNames) {

        if(nodeNames.length == 0) {
            throw new CodedRuntimeException("node names must be not empty!");
        }

        Step index = null;

        for(String nodeName : nodeNames) {
            if(index == null) {
                index = new Step(new WizardNode(nodeName));
            }
            else {
                index.setNextStep(new Step(new WizardNode(nodeName)));
                index.getNextStep().setPreviousStep(index);
                index = index.getNextStep();
            }
            steps.add(index);
        }

        this.currentStep = steps.getFirst();
    }

    public Wizard(WizardNode[] nodes) {
        Step index = null;
        for(WizardNode node : nodes) {
            if(index == null) {
                index = new Step(node);
            }
            else {
                index.setNextStep(new Step(node));
                index.getNextStep().setPreviousStep(index);
                index = index.getNextStep();
            }
            steps.add(index);
        }

        this.currentStep = steps.getFirst();
    }

    public WizardNode getBeginNode() {
        if(steps.isEmpty()) {
            return null;
        }
        else {
            return steps.getFirst().getWizardNode();
        }
    }

    public WizardNode goForward() {
        Step step = currentStep.getNextStep();
        if(step != null) {
            this.currentStep = step;
            return currentStep.getWizardNode();
        }

        return currentStep.getWizardNode();
    }

    public WizardNode getCurrentWizardNode() {
        if(currentStep != null) {
            return currentStep.getWizardNode();
        }
        return null;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
