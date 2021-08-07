package com.project.garden.app.Sensor.controller;

import com.project.garden.app.Sensor.SensorService;
import com.project.garden.app.Sensor.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/sensor")
public class AdminSensorController {

    private final SensorService service;
    @Autowired
    public AdminSensorController(SensorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Sensor> create(@RequestBody Sensor sensor) {
        return ResponseEntity.ok(service.create(sensor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sensor> update(@PathVariable Long id, @RequestBody Sensor sensor) {
        return ResponseEntity.ok(service.update(id, sensor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Sensor>> getAll() {
        return ResponseEntity.ok(service.find());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.find(id));
    }
}
