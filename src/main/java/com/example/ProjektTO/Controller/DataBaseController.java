package com.example.ProjektTO.Controller;

import com.example.ProjektTO.DataBase.DatabaseConnectionParams;
import com.example.ProjektTO.DataOperations.DeleteClass;
import com.example.ProjektTO.DataOperations.InsertClass;
import com.example.ProjektTO.DataOperations.OperationClass;
import com.example.ProjektTO.DataOperations.UpdateClass;
import com.example.ProjektTO.Enums;
import com.example.ProjektTO.Service.DatabaseService;
import com.example.ProjektTO.Table.FieldClass;
import com.example.ProjektTO.Table.TableClass;
import com.example.ProjektTO.TableOperation.CreateTable;
import com.example.ProjektTO.TableOperation.DropTable;
import com.example.ProjektTO.TableOperation.EditTable;
import jdk.dynalink.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/db")
//@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/tables")
    public ResponseEntity<List<TableClass>> GetTables() throws SQLException {
        List<String> tableNames = /*List.of(new String[]{"asd", "cda"});*/databaseService.getTableNames();
        List<TableClass> tableclass=new ArrayList<>();
        for (String t: tableNames) {
             tableclass.add(databaseService.getTableColumns(t));
        }
        //FieldClass f = new FieldClass("test",0,0,"integer","","",false,false,false,false,false,0);
        //List <FieldClass> ff =new ArrayList<FieldClass>();
        //ff.add(f);
        //tableclass.add(new TableClass("test", ff));
        return ResponseEntity.ok(tableclass);
    }

    @GetMapping("/tablesname")
    public ResponseEntity<List<String>> GetTablesName(){
        List<String> tableNames = List.of(new String[]{"asd", "cda"});/*databaseService.getTableNames();*/
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
    public ResponseEntity<String> newSql(@RequestBody String sql) {
        System.out.println(sql);
        return (databaseService.select(sql.replace("\"","")))?ResponseEntity.ok("Udało się"):ResponseEntity.ok("Nie udało się");
    }
    @PostMapping("/newtable")
    public ResponseEntity<Map<String,String>> newTable(@RequestBody TableClass table) {
        Map<String, String> data = new HashMap<>();
        //System.out.println(table.getFields().get(0).getFieldName());
        String Respone=new CreateTable().getString(table);
        data.put("response",Respone);
        return ResponseEntity.ok(data);
    }
    @PatchMapping("/edittable")
    public ResponseEntity<String> editTable(@RequestBody TableClass table) {
        System.out.println(table.getFields().get(0).getFieldName());
        String Respone=new EditTable().getString(table);
        return ResponseEntity.ok(Respone);
    }

    @DeleteMapping("/deltable")
    public ResponseEntity<Map<String,String>> deleteTable(@RequestBody String table){
        String Respone=new DropTable().getString(table.replace("\"",""));
        Map<String, String> data = new HashMap<>();
        databaseService.select(Respone);
        data.put("response",Respone);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/insert")
    public ResponseEntity<Map<String,String>> insert(@RequestBody InsertClass ins)
    {
        Map<String, String> data = new HashMap<>();
        String Respone=ins.toString();
        data.put("response",Respone);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String,String>> update(@RequestBody UpdateClass upd)
    {
        Map<String, String> data = new HashMap<>();
        String Respone=upd.toString();
        databaseService.select(Respone);
        data.put("response",Respone);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/delrows")
    public ResponseEntity<Map<String,String>> delrows(@RequestBody DeleteClass del){

        Map<String, String> data = new HashMap<>();
        String Respone=del.toString();
        data.put("response",Respone);
        return ResponseEntity.ok(data);
    }
}