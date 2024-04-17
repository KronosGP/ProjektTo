package com.example.ProjektTO.Statemant;

public class SetStatemant extends Statemant {
    public SetStatemant(String columnName, Object value) {
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
