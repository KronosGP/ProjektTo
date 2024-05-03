package com.example.ProjektTO.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class TableClass {
    @JsonProperty("tableName")
    private String TableName;
    @JsonProperty("columns")
    private List<FieldClass> Fields=new ArrayList<>();

    public TableClass(String tableName, List<FieldClass> fields) {
        TableName = tableName;
        Fields = fields;
    }

    public TableClass(String TableName){
        this.TableName=TableName;
    }
    public void addField(FieldClass Field){
        Fields.add(Field);
    }

    public String getTableName() {
        return TableName;
    }

    public List<FieldClass> getFields(){
        return Fields;
    }

}
