package co.edu.umanizales.univesity_control.controller;

import co.edu.umanizales.univesity_control.model.ProfessorAssignment;
import co.edu.umanizales.univesity_control.service.ProfessorAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professor-assignments")
@RequiredArgsConstructor
public class ProfessorAssignmentController {

    private final ProfessorAssignmentService service;

    @GetMapping
    public ResponseEntity<List<ProfessorAssignment>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorAssignment> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProfessorAssignment> create(@RequestBody ProfessorAssignment assignment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(assignment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorAssignment> update(@PathVariable String id, @RequestBody ProfessorAssignment assignment) {
        assignment.setId(id);
        return ResponseEntity.ok(service.update(assignment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
