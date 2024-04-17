package com.example.ProjektTO.Table;

import java.util.ArrayList;
import java.util.List;

public class TableClass {
    private String TableName;
    private List<FieldClass> Fields=new ArrayList<>();

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
