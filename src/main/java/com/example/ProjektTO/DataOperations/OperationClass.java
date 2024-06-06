package com.example.ProjektTO.DataOperations;

import com.example.ProjektTO.Statemant.SetStatemant;
import com.example.ProjektTO.Statemant.WhereStatemant;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OperationClass {
    @JsonProperty("tableName")
    private String tableName;
    private List<String> columnName;

    @JsonProperty("whereStatement")
    private List<WhereStatemant> whereStatemant;
    private List<String> whereConn;

    @JsonProperty("setStatement")
    private List<SetStatemant> setStatemant;

    public OperationClass() {
        this.columnName = new ArrayList<>();
        this.whereStatemant=new ArrayList<>();
        this.setStatemant=new ArrayList<>();
        this.whereConn=new ArrayList<>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected void addColumn(String nazwa){
        this.columnName.add(nazwa);
    }
    protected void addColumns(List<String> nazwa){
        this.columnName=nazwa;
    }

    public void addWhereConn(String conn){
        this.whereConn.add(conn);
    }

    public List<String> getColumnName() {
        return columnName;
    }

    public String getColumnsString() {
        String returnetString="";
        for(int i=0;i<columnName.size()-1;i++)
            returnetString += getColumnName().get(i) + ", ";
        returnetString += getColumnName().get(getColumnName().size()-1);
        return returnetString;
    }

    public String getWhereString() {
        String returnetString="";
        if(!whereStatemant.isEmpty()) {
            returnetString = " where ";
            for (int i = 0; i < whereStatemant.size() - 1; i++)
                try{
                    Double.parseDouble((String) getWhereStatemant().get(i).getValue());
                    returnetString += getWhereStatemant().get(i).getColumnName() + getWhereStatemant().get(i).getSign()+getWhereStatemant().get(i).getValue();
                }catch (Exception e) {
                    returnetString += getWhereStatemant().get(i).getColumnName() + getWhereStatemant().get(i).getSign()+"'"+getWhereStatemant().get(i).getValue() + "', ";
                    System.out.println(getWhereStatemant().get(i).getValue());
                }
            try{
                Double.parseDouble((String) getWhereStatemant().get(getWhereStatemant().size()-1).getValue());
                returnetString += getWhereStatemant().get(getWhereStatemant().size()-1).getColumnName() + getWhereStatemant().get(getWhereStatemant().size()-1).getSign()+getWhereStatemant().get(getWhereStatemant().size()-1).getValue();
            }catch (Exception e) {
                returnetString += getWhereStatemant().get(getWhereStatemant().size()-1).getColumnName() + getWhereStatemant().get(getWhereStatemant().size()-1).getSign()+"'"+getWhereStatemant().get(getWhereStatemant().size()-1).getValue() + "'";
                System.out.println(getWhereStatemant().get(getWhereStatemant().size()-1).getValue());
            }
        }
        return returnetString;
    }

    public String getSetString() {
        String returnetString=" set ";
        for (int i = 0; i < setStatemant.size() - 1; i++)
            returnetString += setStatemant.get(i).toString() + ", ";
        returnetString += setStatemant.get(setStatemant.size() - 1).toString();
        return returnetString;
    }

    public List<WhereStatemant> getWhereStatemant() {
        return whereStatemant;
    }
    public List<SetStatemant> getSetStatemant() {
        return setStatemant;
    }
    public void setSetStatemant(List<SetStatemant> set) {
        this.setStatemant=set;
    }

    public void addWhere(String Column,String Sign, Object value) {
        whereStatemant.add(new WhereStatemant(Column,value,Sign));
    }
    public void addSet(String Column, Object value) {
        setStatemant.add(new SetStatemant(Column,value));
    }

    protected void setWhereStatement(List<WhereStatemant> whereStatemant) {
        this.whereStatemant=whereStatemant;
    }
}
