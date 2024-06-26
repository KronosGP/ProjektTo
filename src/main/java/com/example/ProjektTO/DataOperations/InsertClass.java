package com.example.ProjektTO.DataOperations;

import com.example.ProjektTO.Statemant.SetStatemant;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class InsertClass extends OperationClass{

    public InsertClass(@JsonProperty("tableName") String tableName,@JsonProperty("columns") List<SetStatemant> set) {
        this.setTableName(tableName);
        this.setSetStatemant(set);
    }

    public InsertClass(OperationClass operation) {
        this.setTableName(operation.getTableName());
        this.setSetStatemant(operation.getSetStatemant());
    }
    public String getSetString() {
        String returnetString="(";
        for (int i = 0; i < getSetStatemant().size() - 1; i++) {
            if(getSetStatemant().get(i).getValue()!="")
                returnetString += getSetStatemant().get(i).getColumnName() + ", ";
        }
        returnetString += getSetStatemant().get(getSetStatemant().size() - 1).getColumnName()+")";
        return returnetString;
    }

    @Override
    public String toString() {
        String returnetString="Insert into "+getTableName()+" ";
        if(!getSetStatemant().isEmpty())
            returnetString+=getSetString();
        returnetString+=" values("+getColumnValues()+");";
        return returnetString;
    }

    private String getColumnValues() {
        String returnetString="";
        for(int i=0;i<getSetStatemant().size()-1;i++)
            if(getSetStatemant().get(i).getValue()!="")
                try{
                    Double.parseDouble((String) getSetStatemant().get(i).getValue());
                    returnetString += getSetStatemant().get(i).getValue() + ", ";
                }catch (Exception e) {
                    returnetString += getSetStatemant().get(i).getValue()!=""?"'"+getSetStatemant().get(i).getValue() + "', ":"";
                    System.out.println(getSetStatemant().get(i).getValue());
                }
        try{
            Double.parseDouble((String) getSetStatemant().get(getSetStatemant().size()-1).getValue());
            returnetString += getSetStatemant().get(getSetStatemant().size()-1).getValue();
        }catch (Exception e) {
            returnetString += getSetStatemant().get(getSetStatemant().size()-1).getValue()!=""?"'"+getSetStatemant().get(getSetStatemant().size()-1).getValue() + "'":"";
            System.out.println(getSetStatemant().get(getSetStatemant().size()-1).getValue());
        }
        return returnetString;
    }


}
