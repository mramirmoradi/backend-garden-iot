package com.project.garden.app.data.controller;

import com.project.garden.app.data.DataSpecification;
import com.project.garden.app.data.model.Data;
import com.project.garden.app.data.service.DataService;
import com.project.garden.core.context.ContextHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/customer/data")
public class CustomerDataController {

    private final DataService dataService;
    private final ContextHolderService contextHolderService;
    @Autowired
    public CustomerDataController(DataService dataService, ContextHolderService contextHolderService) {
        this.dataService = dataService;
        this.contextHolderService = contextHolderService;
    }

    @GetMapping
    public ResponseEntity<Page<Data>> get(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to,
            @RequestParam(required = false) Double min_hin,
            @RequestParam(required = false) Double max_hin,
            @RequestParam(required = false) Double min_hout,
            @RequestParam(required = false) Double max_hout,
            @RequestParam(required = false) Double min_tin,
            @RequestParam(required = false) Double max_tin,
            @RequestParam(required = false) Double min_tout,
            @RequestParam(required = false) Double max_tout,
            @RequestParam(required = false) Double min_co,
            @RequestParam(required = false) Double max_co,
            @RequestParam(required = false) Double min_iux,
            @RequestParam(required = false) Double max_iux,
            @RequestParam(required = false) Long gateId,
            @RequestParam(required = false) String gateName,
            Pageable pageable) {
        return ResponseEntity.ok(dataService.find(new DataSpecification(
                id,
                from,
                to,
                ids,
                min_hin,
                max_hin,
                min_hout,
                max_hout,
                min_tin,
                max_tin,
                min_tout,
                max_tout,
                min_co,
                max_co,
                min_iux,
                max_iux,
                gateId,
                gateName,
                contextHolderService.getUserState().getUserId(),
                null
        ), pageable));
    }
}
