package com.example.ProjektTO.Table;

import com.example.ProjektTO.Enums;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FieldClass {
    @JsonProperty("fieldName")
    private String FieldName;
    @JsonProperty("fieldSize1")
    private int FieldSize1;
    @JsonProperty("fieldSize2")
    private int FieldSize2;
    @JsonProperty("fieldType")
    private /*Enums.eFieldType*/ String FieldType="integer" /*Enums.eFieldType.INTIGER*/;
    @JsonProperty("foreignField")
    private String foreignField;
    @JsonProperty("foreignTable")
    private String foreignTable;
    @JsonProperty("isAutoincrement")
    private boolean IsAutoincrement=false;
    @JsonProperty("isForeignKey")
    private boolean IsForeignKey=false;
    @JsonProperty("isNotNull")
    private boolean IsNotNUll=false;
    @JsonProperty("isPrimaryKey")
    private boolean IsPrimeryKey=false;

    @JsonProperty("isUnique")
    private boolean Unique=false;
    @JsonProperty("editMode")
    private int edit=1;

    public FieldClass() {
    }

    public FieldClass(String fieldName, int fieldSize1, int fieldSize2, String fieldType, String foreignField, String foreignTable, boolean isAutoincrement, boolean isForeignKey, boolean isNotNUll, boolean isPrimeryKey, boolean unique, int edit) {
        FieldName = fieldName;
        FieldSize1 = fieldSize1;
        FieldSize2 = fieldSize2;
        FieldType = fieldType;
        this.foreignField = foreignField;
        this.foreignTable = foreignTable;
        IsAutoincrement = isAutoincrement;
        IsForeignKey = isForeignKey;
        IsNotNUll = isNotNUll;
        IsPrimeryKey = isPrimeryKey;
        Unique = unique;
        this.edit=edit;
    }

    public void setFieldType(String fieldType) {
        FieldType = fieldType;
    }

    public String getForeignField() {
        return foreignField;
    }

    public void setForeignField(String foreignField) {
        this.foreignField = foreignField;
    }

    public String getForeignTable() {
        return foreignTable;
    }

    public void setForeignTable(String foreignTable) {
        this.foreignTable = foreignTable;
    }

    public boolean isAutoincrement() {
        return IsAutoincrement;
    }

    public void setAutoincrement(boolean autoincrement) {
        IsAutoincrement = autoincrement;
    }

    public boolean isNotNUll() {
        return IsNotNUll;
    }

    public void setNotNUll(boolean notNUll) {
        IsNotNUll = notNUll;
    }

    public boolean isPrimeryKey() {
        return IsPrimeryKey;
    }

    public void setPrimeryKey(boolean primeryKey) {
        IsPrimeryKey = primeryKey;
    }

    public boolean isForeignKey() {
        return IsForeignKey;
    }

    public void setForeignKey(boolean foreignKey) {
        IsForeignKey = foreignKey;
    }

    public boolean isUnique() {
        return Unique;
    }

    public void setUnique(boolean unique) {
        Unique = unique;
    }

    public Enums.eFieldType getFieldType() {
        return Enums.eFieldType.valueOf(FieldType.toUpperCase());
    }

    /*public void setFieldType(Enums.eFieldType fieldType) {
        FieldType = fieldType.name();
    }*/

    public int getFieldSize1() {
        return FieldSize1;
    }

    public void setFieldSize1(int fieldSize1) {
        FieldSize1 = fieldSize1;
    }

    public int getFieldSize2() {
        return FieldSize2;
    }

    public void setFieldSize2(int fieldSize2) {
        FieldSize2 = fieldSize2;
    }

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public int getEdit() {
        return edit;
    }

    public void setEdit(int edit) {
        this.edit = edit;
    }
/*public void setFieldType(int nextInt) {
        FieldType=Enums.eFieldType.values()[nextInt].name();
    }*/

    public String getPrimeryInfo(){
        String respone=getFieldName()+" "
                +getFieldType().toString();

        switch (getFieldType()){
            case DECIMAL -> respone+="("+getFieldSize1()+","+getFieldSize2()+")";
            case VARCHAR -> respone+="("+getFieldSize1()+")";
        }
        return respone;
    }
    public String getSpecyficInfo(){
        String respone=(isNotNUll()?"NOT NULL ":"")
                +(isAutoincrement()?"AUTO_INCREMENT ":"")
                +(isUnique()?"UNIQUE ":"");
        return respone;
    }

    public String getForeignInfo() {
        return getFieldName()+" FOREIGN KEY ("+getFieldName()+") REFERENCES "+getForeignTable()+"("+getForeignField()+")";
    }
}
