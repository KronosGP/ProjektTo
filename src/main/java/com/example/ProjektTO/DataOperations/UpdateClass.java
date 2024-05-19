package com.example.ProjektTO.DataOperations;

import com.example.ProjektTO.Statemant.SetStatemant;
import com.example.ProjektTO.Statemant.WhereStatemant;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class UpdateClass extends OperationClass{

    public UpdateClass(@JsonProperty("tableName") String tableName,
                       @JsonProperty("columnValues") List<SetStatemant> set,
                       @JsonProperty("whereStatementValues") List<WhereStatemant> where) {
        this.setTableName(tableName);
        this.setSetStatemant(set);
        this.setWhereStatement(where);
    }

    public void setSet(List<SetStatemant> set){
        this.setSetStatemant(set);
    }
    public UpdateClass(OperationClass upd) {
        this.setTableName(upd.getTableName());
        this.setSetStatemant(upd.getSetStatemant());
        this.setWhereStatement(upd.getWhereStatemant());

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
