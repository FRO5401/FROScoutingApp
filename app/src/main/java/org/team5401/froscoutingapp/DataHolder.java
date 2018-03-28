package org.team5401.froscoutingapp;

import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;

public class DataHolder {

    EditText E;
    Switch S;
    NumberPicker NP;
    String N;
    String D;

    public DataHolder (EditText e) {
        E = e;
    }

    public DataHolder (Switch s) {
        S = s;
    }

    public DataHolder (NumberPicker np) {
        NP = np;
    }

    public DataHolder (String n, String d) {
        N = n;
        D = d;
    }

    public void setEditText(EditText e) {
        E = e;
    }

    public void setSwitch(Switch s) {
        S = s;
    }

    public void setNumberPicker(NumberPicker np) {
        NP = np;
    }

    public EditText getEditText() {
        return E;
    }

    public Switch getSwitch() {
        return S;
    }

    public NumberPicker getNumberPicker() {
        return NP;
    }

    public void setName(String n) {
        N = n;
    }

    public void setData(String d) {
        D = d;
    }

    public String getName() {
        return N;
    }

    public String getData() {
        return D;
    }
}
