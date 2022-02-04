package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleMessage;

public class ConvertibleMessageImpl implements ConvertibleMessage {

    private String elementId;
    private String value;

    @Override
    public String getElement(String elementId) {
        return value;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
