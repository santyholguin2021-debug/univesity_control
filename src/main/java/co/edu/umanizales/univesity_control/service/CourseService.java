package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Course;
import co.edu.umanizales.univesity_control.repository.impl.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;

    public List<Course> findAll() {
        return repository.findAll();
    }

    public Course findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    public Course save(Course course) {
        return repository.save(course);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
