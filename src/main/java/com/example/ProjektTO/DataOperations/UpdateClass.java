package com.example.ProjektTO.DataOperations;

import java.util.ArrayList;
import java.util.List;

public class UpdateClass extends OperationClass{
    private List<String> values;

    public UpdateClass() {
        values=new ArrayList<>();
    }
    @Override
    public String toString() {
        String returnetString="Update "+getTableName();
        if(!getSetStatemant().isEmpty())
            returnetString+=getSetString();
        returnetString+= getWhereString();
        return returnetString;
    }
}
