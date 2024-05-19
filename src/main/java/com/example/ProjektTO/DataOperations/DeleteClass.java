package com.example.ProjektTO.DataOperations;

import com.example.ProjektTO.Statemant.WhereStatemant;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DeleteClass extends OperationClass{
    public DeleteClass() {
    }
    public DeleteClass(@JsonProperty("tableName") String tableName, @JsonProperty("whereStatementValues") List<WhereStatemant> where) {
        this.setTableName(tableName);
        this.setWhereStatement(where);
    }

    @Override
    public String toString() {
        String returnetString="Delete from "+getTableName();
        if(!getWhereStatemant().isEmpty())
            returnetString+=getWhereString()+" ";
        return returnetString;
    }
}
