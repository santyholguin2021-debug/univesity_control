package co.edu.umanizales.univesity_control.controller;

import co.edu.umanizales.univesity_control.model.Faculty;
import co.edu.umanizales.univesity_control.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService service;

    @GetMapping
    public ResponseEntity<List<Faculty>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(faculty));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> update(@PathVariable String id, @RequestBody Faculty faculty) {
        faculty.setId(id);
        return ResponseEntity.ok(service.update(faculty));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
