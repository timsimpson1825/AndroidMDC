package com.havertys.androidmdc.signin;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by TSimpson on 1/22/2015.
 */
public class HvtMenu implements Serializable {

    private String name;
    private String id;
    private ArrayList<HvtOption> hvtOptionList;

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

    public ArrayList<HvtOption> getHvtOptionList() {
        return hvtOptionList;
    }

    public void setHvtOptionList(ArrayList<HvtOption> hvtOptionList) {
        this.hvtOptionList = hvtOptionList;
    }
}
