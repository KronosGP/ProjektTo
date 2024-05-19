package com.example.ProjektTO.Statemant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WhereStatemant extends Statemant {

    public WhereStatemant(@JsonProperty("whereColumnName") String columnName, @JsonProperty("whereColumnValue") Object value, @JsonProperty("whereColumnSign") String sign) {
        super();
        setColumnName(columnName);
        setValue(value);
        setSign(sign);
    }

    @Override
    public String toString() {
        return  getString();
    }
}
