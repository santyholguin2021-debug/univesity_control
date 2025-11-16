package co.edu.umanizales.univesity_control.controller;

import co.edu.umanizales.univesity_control.model.Classroom;
import co.edu.umanizales.univesity_control.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService service;

    @GetMapping
    public ResponseEntity<List<Classroom>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Classroom> create(@RequestBody Classroom classroom) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(classroom));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classroom> update(@PathVariable String id, @RequestBody Classroom classroom) {
        classroom.setId(id);
        return ResponseEntity.ok(service.update(classroom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
