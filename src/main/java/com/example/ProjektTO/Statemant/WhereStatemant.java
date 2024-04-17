package com.example.ProjektTO.Statemant;

public class WhereStatemant extends Statemant {

    public WhereStatemant(String columnName, Object value, String sign) {
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
