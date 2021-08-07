package com.project.garden.app.gate.controller;

import com.project.garden.app.gate.GateSpecification;
import com.project.garden.app.gate.model.Gate;
import com.project.garden.app.gate.service.GateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/gate")
public class AdminGateController {

    private final GateService gateService;
    @Autowired
    public AdminGateController(GateService gateService) {
        this.gateService = gateService;
    }

    @PostMapping
    public ResponseEntity<Gate> create(@RequestBody Gate gate) {
        return ResponseEntity.ok(gateService.create(gate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gate> update(@RequestBody Gate gate, @PathVariable Long id) {
        return ResponseEntity.ok(gateService.update(id, gate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gateService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<Gate>> get(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Long userId,
            Pageable pageable) {
        return ResponseEntity.ok(gateService.find(new GateSpecification(
                id,
                from,
                to,
                ids,
                name,
                phone,
                userId
        ), pageable));
    }
}
