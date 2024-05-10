package com.example.ProjektTO.Controller;

import com.example.ProjektTO.DataBase.DatabaseConnectionParams;
import com.example.ProjektTO.Enums;
import com.example.ProjektTO.Service.DatabaseService;
import com.example.ProjektTO.Table.TableClass;
import com.example.ProjektTO.TableOperation.CreateTable;
import com.example.ProjektTO.TableOperation.EditTable;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/db")
@CrossOrigin(origins = "http://localhost:3000")
public class DataBaseController {
    DatabaseService databaseService;

    public DataBaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostMapping("/connect")
    public ResponseEntity<String> connectToDatabase(@RequestBody DatabaseConnectionParams params) {
        //System.out.println(params.getIp());
        return databaseService.connect(params)?ResponseEntity.ok("Udało się połączyć"):ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("ERROR");

    }
    @PostMapping("/disconnect")
    public String disconnectDatabase() {
        databaseService.disconnect();
        return null;
    }

    @GetMapping("/tablename")
    public ResponseEntity<List<String>> GetTableName() {
        List<String> tableNames = /*List.of(new String[]{"asd", "cda"});*/databaseService.getTableNames();
        return ResponseEntity.ok(tableNames);
    }
    @GetMapping("/fieldsname")
    public ResponseEntity<List<String>> GetFieldName(@RequestParam("tablename") String TableName)  {
        try {
            return ResponseEntity.ok(databaseService.getColumnsName(TableName));
        } catch (SQLException e) {
            return (ResponseEntity<List<String>>) ResponseEntity.notFound();
        }
    }
    @GetMapping("/fields")
    public ResponseEntity<TableClass> GetFields(@RequestParam("tablename") String TableName) {
        try {
            TableClass table = databaseService.getTableColumns(TableName);
            return ResponseEntity.ok(table);
        } catch (SQLException e) {
            return (ResponseEntity<TableClass>) ResponseEntity.notFound();
        }
    }
    @GetMapping("/fieldtype")
    public ResponseEntity<List<String>> GetFieldType() {
        List<String> fieldsType = Stream.of(Enums.eFieldType.values())
                .map(Enum::name)
                .collect(Collectors.toList());;
        return ResponseEntity.ok(fieldsType);
    }
    @PostMapping("/newsql")
    public ResponseEntity<String> newSql(@RequestParam("sql") String sql) {
        return (databaseService.select(sql))?ResponseEntity.ok("Udało się"):ResponseEntity.ok("Nie udało się");
    }
    @PostMapping("/newtable")
    public ResponseEntity<String> newTable(@RequestBody TableClass table) {
        System.out.println(table.getFields().get(0).getFieldName());
        String Respone=new CreateTable().getString(table);
        return ResponseEntity.ok(Respone);
    }
    @PostMapping("/edittable")
    public ResponseEntity<String> editTable(@RequestBody TableClass table) {
        System.out.println(table.getFields().get(0).getFieldName());
        String Respone=new EditTable().getString(table);
        return ResponseEntity.ok(Respone);
    }
}