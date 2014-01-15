package com.tsmile.codeGenerator.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 含有一些Android View成员变量的类，如Activity<br>
 * 
 * @author Tsimle
 * 
 */
public class ViewEntity {
    private String className;
    private String packageName;
    private String resourceName;
    private boolean clickAble;

    private List<ViewProperty> viewProperties = new ArrayList<ViewProperty>();
    private Set<String> importSet = new HashSet<String>();

    public void addViewProperty(ViewProperty viewProperty) {
        viewProperties.add(viewProperty);
        if (viewProperty.isClickAble()) {
            clickAble = true;
        }
    }

    public void genImportSet() {
        importSet.clear();
        for (ViewProperty viewProperty : viewProperties) {
            String importprefix;
            if (viewProperty.getType().equals("View") || viewProperty.getType().equals("ViewGroup")) {
                importprefix = "android.view.";
            } else {
                importprefix = "android.widget.";
            }
            importSet.add(importprefix + viewProperty.getType());
        }
        if (clickAble) {
            importSet.add("android.view.View");
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        int startIndex = resourceName.lastIndexOf("/");
        int endIndex = resourceName.lastIndexOf(".xml");
        if (startIndex < 0) {
            startIndex = 0;
        } else {
            startIndex = startIndex + 1;
        }
        if (endIndex < 0) {
            endIndex = resourceName.length();
        }
        this.resourceName = resourceName.substring(startIndex, endIndex);
    }

    public List<ViewProperty> getViewProperties() {
        return viewProperties;
    }

    public void setViewProperties(List<ViewProperty> viewProperties) {
        this.viewProperties = viewProperties;
    }

    public Set<String> getImportSet() {
        return importSet;
    }

    public void setImportSet(Set<String> importSet) {
        this.importSet = importSet;
    }

    public boolean isClickAble() {
        return clickAble;
    }

    public void setClickAble(boolean clickAble) {
        this.clickAble = clickAble;
    }
}
