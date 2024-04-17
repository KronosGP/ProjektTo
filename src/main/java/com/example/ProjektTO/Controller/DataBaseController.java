package com.example.ProjektTO.Controller;

import com.example.ProjektTO.DataBase.DatabaseConnectionParams;
import com.example.ProjektTO.Service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/db")
public class DataBaseController {
    @Autowired
    private DatabaseService databaseService;

    @PostMapping("/connect")
    public ResponseEntity<String> connectToDatabase(@RequestBody DatabaseConnectionParams params) {
        // params to obiekt zawierający parametry połączenia z frontendu
        boolean connected = databaseService.connect(params);
        if (connected) {
            return ResponseEntity.ok("Connected to database successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to connect to database");
        }
    }

}