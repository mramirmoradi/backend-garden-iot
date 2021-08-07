package com.project.garden.app.data.controller;

import com.project.garden.app.data.model.Data;
import com.project.garden.app.data.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/gate/data")
public class GateDataController {

    private final DataService dataService;
    @Autowired
    public GateDataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<Data> submit(@RequestBody Data data) {
        return ResponseEntity.ok(dataService.submit(data));
    }
}
