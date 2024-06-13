package com.example.ProjektTO.Service;

import com.example.ProjektTO.DataBase.DatabaseConnectionParams;
import com.example.ProjektTO.Dtos.ExequteResponse;
import com.example.ProjektTO.Table.FieldClass;
import com.example.ProjektTO.Table.TableClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseService {

    DatabaseConnectionParams params;
    private Connection connection=null;
    public boolean connect(DatabaseConnectionParams params) {
        if(connection!=null) disconnect();
        this.params=params;
        String url = "jdbc:mysql://" + params.getIp() + ":" + params.getPort()+"/"+params.getName();
        System.out.println(url);
        String username = params.getUsername();
        String password = params.getPassword();
        try {
            // Utworzenie połączenia z bazą danych
            connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResponseEntity<ExequteResponse>  execute(String sql) {
        try {
            Statement stmt = connection.createStatement();
                stmt.execute("use "+params.getName()+";");
                sql=sql.replace("\\n","");
                List<Map<String,String>>rows=new ArrayList<>();
                Map<String,String>row=new HashMap<>();
                if(sql.trim().toLowerCase().startsWith("select")){
                    ResultSet s=stmt.executeQuery(sql+";");
                    ResultSetMetaData md = s.getMetaData();
                    while (s.next()) {
                        row=new HashMap<>();
                        for(int i=1;i<=md.getColumnCount();i++) {
                            row.put(md.getColumnName(i), s.getString(i));
                        }
                        rows.add(row);
                    }
                }
                else{
                    stmt.execute(sql);
                    row.put("not","select");
                    rows.add(row);
                }

            return ResponseEntity.ok(new ExequteResponse(true,rows));
        } catch (SQLException e) {
            System.out.println(e);
            Map<String,String>res = new HashMap<>();
            res.put("statusText",e.toString());
            List<Map<String,String>> rpp=new ArrayList<>();
            rpp.add(res);
            return ResponseEntity.ok(new ExequteResponse(false, rpp));
        }
    }

    public List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        DatabaseMetaData metaData = null;
        try {
            metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(params.getName(), null, "%", new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                tableNames.add(tableName);}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tableNames;
    }

    public TableClass getTableColumns(String tableName) throws SQLException {
        TableClass Table = new TableClass(tableName);
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
        while (resultSet.next()) {
            ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);
            ResultSet primaryKeys =metaData.getPrimaryKeys(null, null, tableName);
            ResultSet unique = metaData.getIndexInfo(null, null, tableName,true,false);
            FieldClass columnInfo = new FieldClass();
            columnInfo.setFieldName(resultSet.getString("COLUMN_NAME"));
            columnInfo.setFieldType(resultSet.getString("TYPE_NAME"));
            //System.out.println(columnInfo.getFieldType());
            columnInfo.setFieldSize1(resultSet.getInt("COLUMN_SIZE"));
            columnInfo.setFieldSize1(resultSet.getInt("DECIMAL_DIGITS"));
            columnInfo.setAutoincrement( resultSet.getBoolean("IS_AUTOINCREMENT"));
            columnInfo.setNotNUll( resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNoNulls);
            //columnInfo.setPrimeryKey( resultSet.getBoolean("IS_PRIMARY_KEY"));
            //columnInfo.setUnique( resultSet.getBoolean("IS_UNIQUE"));
            while (foreignKeys.next()) {
                String foreignColumnName = foreignKeys.getString("FKCOLUMN_NAME");
                //System.out.println(Table.getTableName()+" "+columnInfo.getFieldName()+" "+foreignColumnName);
                if (columnInfo.getFieldName().equals(foreignColumnName)) {
                    columnInfo.setForeignTable(foreignKeys.getString("PKTABLE_NAME"));
                    columnInfo.setForeignField(foreignKeys.getString("PKCOLUMN_NAME"));
                    columnInfo.setForeignKey(true);
                }
            }
            while (primaryKeys.next()) {
                String primaryColumnName = primaryKeys.getString("COLUMN_NAME");
                if (columnInfo.getFieldName().equals(primaryColumnName)) {
                    columnInfo.setPrimeryKey(true);
                }
            }
            while (unique.next()) {
                String uniqueColumnName = unique.getString("COLUMN_NAME");
                if (columnInfo.getFieldName().equals(uniqueColumnName)) {
                    columnInfo.setUnique(true);
                }
            }
            //System.out.println(Table.getTableName()+" "+columnInfo.getFieldName()+" "+columnInfo.isForeignKey()+" "+columnInfo.getForeignTable()+" "+columnInfo.getForeignField());
            columnInfo.setEdit(0);
            Table.addField(columnInfo);
            columnInfo=null;
        }
        return Table;
    }

    public List<String> getColumnsName(String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
        while (resultSet.next()) {
            columns.add(resultSet.getString("COLUMN_NAME"));
        }
        return columns;
    }

    public void disconnect() {
        // Zamknięcie połączenia
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
