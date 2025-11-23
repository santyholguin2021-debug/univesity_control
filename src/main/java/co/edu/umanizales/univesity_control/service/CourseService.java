package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Course;
import co.edu.umanizales.univesity_control.repository.impl.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository repository;
    private final List<Course> courses = new ArrayList<>();

    public void loadData() {
        courses.clear();
        courses.addAll(repository.findAll());
    }

    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    public Course findById(String id) {
        for (Course course : courses) {
            if (course.getId() != null && course.getId().equals(id)) {
                return course;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found with id: " + id);
    }

    public Course create(Course course) {
        // Verificar si el ID ya existe
        for (Course current : courses) {
            if (current.getId() != null && current.getId().equals(course.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course with id: " + course.getId() + " already exists");
            }
        }
        if (course.getDepartment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "department is required for Course");
        }
        // sync ID from related object
        course.setDepartmentId(course.getDepartment() != null ? course.getDepartment().getId() : null);
        courses.add(course);
        repository.saveAll(courses);
        return course;
    }

    public Course update(Course course) {
        if (course.getDepartment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "department is required for Course");
        }
        for (int i = 0; i < courses.size(); i++) {
            Course current = courses.get(i);
            if (current.getId() != null && current.getId().equals(course.getId())) {
                course.setDepartmentId(course.getDepartment() != null ? course.getDepartment().getId() : null);
                courses.set(i, course);
                repository.saveAll(courses);
                return course;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found with id: " + course.getId());
    }

    public Course save(Course course) {
        for (int i = 0; i < courses.size(); i++) {
            Course current = courses.get(i);
            if (current.getId() != null && current.getId().equals(course.getId())) {
                course.setDepartmentId(course.getDepartment() != null ? course.getDepartment().getId() : null);
                courses.set(i, course);
                repository.save(course);
                return course;
            }
        }
        course.setDepartmentId(course.getDepartment() != null ? course.getDepartment().getId() : null);
        courses.add(course);
        repository.save(course);
        return course;
    }

    public void deleteById(String id) {
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            if (course.getId() != null && course.getId().equals(id)) {
                courses.remove(i);
                repository.deleteById(id);
                return;
            }
        }
    }
}
