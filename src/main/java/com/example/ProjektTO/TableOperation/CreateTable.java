package com.example.ProjektTO.TableOperation;

import com.example.ProjektTO.Enums;
import com.example.ProjektTO.Table.FieldClass;
import com.example.ProjektTO.Table.TableClass;

import static com.example.ProjektTO.Enums.eFieldType;

public class CreateTable {
    public String getString(TableClass newTable){

        String respone="Create table "+newTable.getTableName()+"(\n";
        String PKconst="";
        String FKconst="";
        String UNIconst="";
        for (int i=0;i<newTable.getFields().size();i++) {
            FieldClass f=newTable.getFields().get(i);
            if(i>0)
                respone+=",\n";
            respone+=f.getFieldName()+" "
                    +f.getFieldType().toString();

            switch (f.getFieldType()){
                case DECIMAL -> respone+="("+f.getFieldSize1()+","+f.getFieldSize2()+")";
                case VARCHAR -> respone+="("+f.getFieldSize1()+")";
            }

            if(f.isPrimeryKey())
                PKconst+=",\nPRIMARY KEY ("+f.getFieldName()+")";
            if(f.isForeignKey())
                FKconst+=",\nCONSTRAINT FK_"+f.getFieldType()+
                            " FOREIGN KEY ("+f.getFieldName()+")"+
                        "REFERENCES TABELA(POLE)";//fieldtype należy zamienić na tabele do połączenia
            if(f.isUnique())
                UNIconst+=",\nUNIQUE ("+f.getFieldName()+")";
        }
        respone+=PKconst;
        respone+=FKconst;
        respone+=UNIconst;
        respone+=")";
        return respone;
    }
}
