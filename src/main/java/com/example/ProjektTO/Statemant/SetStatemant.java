package com.example.ProjektTO.Statemant;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SetStatemant extends Statemant {
    public SetStatemant(@JsonProperty("columnName") String columnName, @JsonAlias({"columnValue","value"}) Object value) {
        super();
        setColumnName(columnName);
        setValue(value);
        setSign("=");
    }
    @Override
    public String toString() {
        return  getString();
    }
}
