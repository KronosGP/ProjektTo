package com.example.ProjektTO.Service;

import com.example.ProjektTO.DataBase.DatabaseConnectionParams;
import com.example.ProjektTO.Table.FieldClass;
import com.example.ProjektTO.Table.TableClass;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {
    private Connection connection;
    public boolean connect(DatabaseConnectionParams params) {
        String url = "jdbc:mysql://" + params.getIp() + ":" + params.getPort();
        String username = params.getUsername();
        String password = params.getPassword();
        try {
            // Utworzenie połączenia z bazą danych
            Connection connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean select(String sql) {
        try {
            connection.nativeSQL(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        DatabaseMetaData metaData = null;
        try {
            metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
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
        List<String> primaryKeys = (List<String>) metaData.getPrimaryKeys(null, null, tableName).getArray("COLUMN_NAME");
        List<String> foreignKeys = (List<String>) metaData.getImportedKeys(null, null, tableName).getArray("COLUMN_NAME");
        List<String> indexes = (List<String>) metaData.getIndexInfo(null, null, tableName, true, false).getArray("COLUMN_NAME");
        while (resultSet.next()) {
            FieldClass columnInfo = new FieldClass();
            columnInfo.setFieldName(resultSet.getString("COLUMN_NAME"));
            //columnInfo.setFieldType(Enums.eFieldType.valueOf(resultSet.getString("TYPE_NAME")));
            columnInfo.setFieldSize1(resultSet.getInt("COLUMN_SIZE"));
            if(primaryKeys.contains(columnInfo.getFieldName()))
                columnInfo.setPrimeryKey(true);
            if(foreignKeys.contains(columnInfo.getFieldName()))
                columnInfo.setForeignKey(true);
            if(indexes.contains(columnInfo.getFieldName()))
                columnInfo.setUnique(true);
            Table.addField(columnInfo);
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
