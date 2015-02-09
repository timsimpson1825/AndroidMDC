package com.havertys.androidmdc.signin;

import java.io.Serializable;

/**
 * Created by TSimpson on 1/22/2015.
 */
public class HvtOption implements Serializable {

    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
