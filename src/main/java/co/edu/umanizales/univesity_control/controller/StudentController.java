package co.edu.umanizales.univesity_control.controller;

import co.edu.umanizales.univesity_control.model.Student;
import co.edu.umanizales.univesity_control.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable String id, @RequestBody Student student) {
        student.setId(id);
        return ResponseEntity.ok(service.save(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
