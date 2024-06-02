package com.example.ProjektTO.Service;

import com.example.ProjektTO.DataBase.DatabaseConnectionParams;
import com.example.ProjektTO.Table.FieldClass;
import com.example.ProjektTO.Table.TableClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public boolean select(String sql) {
        try {
            /*String url = "jdbc:mysql://" + params.getIp() + ":" + params.getPort()+"/"+params.getName();
            System.out.println(url);
            String username = params.getUsername();
            String password = params.getPassword();
            Connection connection = DriverManager.getConnection(url, username, password);*/
            Statement stmt = connection.createStatement();
                // Wykonaj zapytanie SQL
                System.out.println(stmt.isClosed());
                stmt.execute("use "+params.getName()+";");
                sql=sql.replace("\\n","");
                System.out.println(sql);
                stmt.execute(sql+";");
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
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
