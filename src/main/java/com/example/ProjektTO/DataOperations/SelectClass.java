package com.example.ProjektTO.DataOperations;

import com.example.ProjektTO.IdontKnowHowToNamedIt.JoinClass;
import com.example.ProjektTO.Statemant.SetStatemant;
import com.example.ProjektTO.Statemant.WhereStatemant;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SelectClass extends OperationClass{

    List<JoinClass> joins=new ArrayList<>();
    private String orderby="";
    private String orderbyColumn;
    private int limit=100;

    public SelectClass(@JsonProperty("tableName") String tableName,
                       @JsonProperty("selectedColumns") List<String> col,
                       @JsonProperty("whereStatementValues") List<WhereStatemant> where,
                       @JsonProperty("limit") String limit,
                       @JsonProperty("orderBy") String orderby,
                        @JsonProperty("orderByColumn") String orderbyColumn) {
        this.setTableName(tableName);
        this.addColumns(col);
        this.setWhereStatement(where);
        try{
            this.limit=Integer.parseInt(limit);
        }catch (Exception e){
            this.limit=100;
        }
        this.orderbyColumn=orderbyColumn;
        this.orderby=orderby;
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
        returnetString+=!orderby.equals("none")?"order by "+orderbyColumn+" "+orderby:"";
        returnetString+=" limit "+limit;
        return returnetString;
    }
}
