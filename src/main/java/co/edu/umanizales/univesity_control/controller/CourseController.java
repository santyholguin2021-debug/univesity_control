package co.edu.umanizales.univesity_control.controller;

import co.edu.umanizales.univesity_control.model.Course;
import co.edu.umanizales.univesity_control.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable String id, @RequestBody Course course) {
        course.setId(id);
        return ResponseEntity.ok(service.save(course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
