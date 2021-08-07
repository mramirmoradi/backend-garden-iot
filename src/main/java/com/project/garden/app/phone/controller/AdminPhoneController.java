package com.project.garden.app.phone.controller;

import com.project.garden.app.phone.model.Phone;
import com.project.garden.app.phone.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/phone")
public class AdminPhoneController {

    private final PhoneService service;
    @Autowired
    public AdminPhoneController(PhoneService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Phone> create(@RequestBody Phone phone) {
        return ResponseEntity.ok(service.create(phone));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Phone> update(@PathVariable Long id, @RequestBody Phone phone) {
        return ResponseEntity.ok(service.update(id, phone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<Phone>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.find(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phone> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.find(id));
    }
}
