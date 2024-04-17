package com.example.ProjektTO.Table;

import com.example.ProjektTO.Enums;

public class FieldClass {
    private boolean IsPrimeryKey=false;
    private boolean IsForeignKey=false;
    private boolean Unique=false;
    private Enums.eFieldType FieldType= Enums.eFieldType.INTIGER;
    private int FieldSize1;
    private int FieldSize2;

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
        return FieldType;
    }

    public void setFieldType(Enums.eFieldType fieldType) {
        FieldType = fieldType;
    }

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

    private String FieldName;

    public void setFieldType(int nextInt) {
        FieldType=Enums.eFieldType.values()[nextInt];
    }
}
