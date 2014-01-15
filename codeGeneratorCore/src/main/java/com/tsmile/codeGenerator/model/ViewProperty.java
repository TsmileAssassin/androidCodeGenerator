package com.tsmile.codeGenerator.model;

/**
 * 对应一个Android View类型的成员变量<br>
 * 
 * @author Tsimle
 * 
 */
public class ViewProperty {
    private String type;
    private String name;
    private String resourceName;
    private boolean clickAble;

    private String parentname;

    public ViewProperty(String type, String name, String resourceName) {
        super();
        this.type = type;
        this.name = name;
        this.resourceName = resourceName;
        if (type.equals("Button") || type.equals("ImageButton")) {
            clickAble = true;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public boolean isClickAble() {
        return clickAble;
    }

    public void setClickAble(boolean clickAble) {
        this.clickAble = clickAble;
    }
}
