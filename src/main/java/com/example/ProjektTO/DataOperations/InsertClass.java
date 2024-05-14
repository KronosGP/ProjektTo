package com.example.ProjektTO.DataOperations;

import java.util.ArrayList;
import java.util.List;

public class InsertClass extends OperationClass{


    public InsertClass() {
    }
    public String getSetString() {
        String returnetString="(";
        for (int i = 0; i < getSetStatemant().size() - 1; i++) {
            returnetString += getSetStatemant().get(i).getColumnName() + ", ";
        }
        returnetString += getSetStatemant().get(getSetStatemant().size() - 1).getColumnName()+")";
        return returnetString;
    }

    @Override
    public String toString() {
        String returnetString="Insert into "+getTableName()+" (";
        if(!getSetStatemant().isEmpty())
            returnetString+=getSetString();
        returnetString+=") values("+getColumnValues()+");";
        return returnetString;
    }

    private String getColumnValues() {
        String returnetString="";
        for(int i=0;i<getSetStatemant().size()-1;i++)
            returnetString += getSetStatemant().get(i).getValue() + ", ";
        returnetString += getSetStatemant().get(getSetStatemant().size()-1).getValue();
        return returnetString;
    }


}
