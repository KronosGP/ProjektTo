package com.example.ProjektTO.DataOperations;

import com.example.ProjektTO.IdontKnowHowToNamedIt.JoinClass;
import com.example.ProjektTO.Statemant.WhereStatemant;

import java.util.ArrayList;
import java.util.List;

public class SelectClass extends OperationClass{

    List<JoinClass> joins;

    public SelectClass() {
        this.joins = new ArrayList<>();
    }

    public void addColumnName(String nazwa){
        addColumn(nazwa);
    }

    public void addJoin(String tableName,String[] connString,String joinType){
        JoinClass newJoin=new JoinClass();
        newJoin.setJoinType(joinType);
        newJoin.setTableName(tableName);
        newJoin.setNewConnection(connString[0],connString[1],connString[2]);
        joins.add(newJoin);
    }

    @Override
    public String toString() {
        String returnetString="Select ";
        if(getColumnName().isEmpty())
            returnetString+="* ";
        else {
            returnetString+=getColumnsString()+" ";
        }

        returnetString+="from "+getTableName()+" ";
        for (JoinClass join: joins) {
            returnetString+= join.toString()+"";
        }
        returnetString+=(!getWhereString().isEmpty())?getWhereString():"";
        return returnetString;
    }
}
