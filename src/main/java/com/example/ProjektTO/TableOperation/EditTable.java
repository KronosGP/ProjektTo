package com.example.ProjektTO.TableOperation;

import com.example.ProjektTO.Table.FieldClass;
import com.example.ProjektTO.Table.TableClass;

public class EditTable {
    public String getString(TableClass table) {
        String respone="Alter table "+table.getTableName()+"\n";
        String FKconst="";
        boolean first=true;
        for (int i=0;i<table.getFields().size();i++) {
            FieldClass field =table.getFields().get(i);
            switch (field.getEdit()){
                case 0:break;
                case 1:respone+=!first?",\n":"";first=false;
                        respone+="add column "+field.getPrimeryInfo()+" "+(field.isPrimeryKey()?"PRIMARY KEY ":"")+field.getSpecyficInfo();
                        FKconst+=(field.isForeignKey()? "\nADD CONSTRAINT "+table.getTableName()+"_"+field.getForeignInfo() : "");break;
                case 2:respone+=!first?",\n":"";first=false;
                        respone+="modify column "+field.getPrimeryInfo()+" "+(field.isPrimeryKey()?"PRIMARY KEY ":"")+field.getSpecyficInfo();
                        FKconst+=(field.isForeignKey()? ",\nADD CONSTRAINT "+table.getTableName()+"_"+field.getForeignInfo() : "");break;
                case 3:respone+=!first?",\n":"";first=false;
                        respone+="drop column "+field.getFieldName();break;
            }
        }
        if(first) {//jeśli nic nie jest zmieniane w tabeli nic nie rób
            respone="";
            FKconst="";
        }
        return respone+" "+FKconst;
    }
}
