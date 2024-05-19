package com.example.ProjektTO.Statemant;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Statemant {


    private String columnName;


    private String value="";

    private String sign;


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value.toString();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getString() {
        String returntingValue=columnName+" "+ sign;
        if(value.getClass().getSimpleName().equals("String"))
            returntingValue+=" '"+value+"'";
        else if (value.getClass().getSimpleName().equals("Integer")) {
            returntingValue+=" "+value;
        }
        else
        {
            returntingValue+=" ("+value.toString()+")";
        }
        return  returntingValue;
    }
}
