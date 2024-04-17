package com.example.ProjektTO.IdontKnowHowToNamedIt;

import com.example.ProjektTO.Statemant.WhereStatemant;

import java.util.ArrayList;
import java.util.List;

public class JoinClass {
    //todo przenieść do folderu statemant, podpiąć klasę statemant oraz napisać toString aby rozróżniał kolumne od wartość
    private String joinType;
    private String tableName;
    private List<WhereStatemant> Connect=new ArrayList<>();

    public void setTableName(String name){
        this.tableName=name;
    }
    public void setJoinType(String joinType){
        this.joinType=joinType;
    }
    public void setNewConnection(String firstColumn,String sing,String secColumn){
        Connect.add(new WhereStatemant(firstColumn,secColumn,sing));
    }

    @Override
    public String toString() {
        String returned="\n"+joinType+" join "+tableName+" on ";
        int i=0;
        for (WhereStatemant conn: Connect) {
            returned+=conn.toString();
            if (i<Connect.size()-1)
                returned+=" and ";
            i++;
        }
        return returned;
    }
}
