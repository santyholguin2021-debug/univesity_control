package co.edu.umanizales.univesity_control.controller;

import co.edu.umanizales.univesity_control.model.Enrollment;
import co.edu.umanizales.univesity_control.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService service;

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Enrollment> create(@RequestBody Enrollment enrollment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(enrollment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> update(@PathVariable String id, @RequestBody Enrollment enrollment) {
        enrollment.setId(id);
        return ResponseEntity.ok(service.save(enrollment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
