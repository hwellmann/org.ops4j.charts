package org.ops4j.charts.component.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Series implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private List<Number> data = new ArrayList<>();


    public Series() {
    }

    public Series(String name) {
        this.name = name;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the data
     */
    public List<Number> getData() {
        return data;
    }


    /**
     * @param data the data to set
     */
    public void setData(List<Number> data) {
        this.data = data;
    }



}
