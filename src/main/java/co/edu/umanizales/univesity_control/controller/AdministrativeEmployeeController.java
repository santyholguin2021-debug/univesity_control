package co.edu.umanizales.univesity_control.controller;

import co.edu.umanizales.univesity_control.model.AdministrativeEmployee;
import co.edu.umanizales.univesity_control.service.AdministrativeEmployeeService;

import co.edu.umanizales.univesity_control.model.AdministrativeEmployee;
import co.edu.umanizales.univesity_control.service.AdministrativeEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrative-employees")
@RequiredArgsConstructor
public class AdministrativeEmployeeController {

    private final AdministrativeEmployeeService service;

    @GetMapping
    public ResponseEntity<List<AdministrativeEmployee>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministrativeEmployee> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<AdministrativeEmployee> create(@RequestBody AdministrativeEmployee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministrativeEmployee> update(@PathVariable String id, @RequestBody AdministrativeEmployee employee) {
        employee.setId(id);
        return ResponseEntity.ok(service.update(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
