package co.edu.umanizales.univesity_control.controller;

import co.edu.umanizales.univesity_control.model.Professor;
import co.edu.umanizales.univesity_control.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService service;

    @GetMapping
    public ResponseEntity<List<Professor>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Professor> create(@RequestBody Professor professor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(professor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> update(@PathVariable String id, @RequestBody Professor professor) {
        professor.setId(id);
        return ResponseEntity.ok(service.update(professor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
