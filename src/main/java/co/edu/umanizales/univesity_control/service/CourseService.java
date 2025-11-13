package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private final List<Course> courses = new ArrayList<>();

    public List<Course> findAll() {
        return new ArrayList<>(courses);
    }

    public Course findById(String id) {
        for (Course course : courses) {
            if (course.getId() != null && course.getId().equals(id)) {
                return course;
            }
        }
        throw new RuntimeException("Course not found with id: " + id);
    }

    public Course save(Course course) {
        for (int i = 0; i < courses.size(); i++) {
            Course current = courses.get(i);
            if (current.getId() != null && current.getId().equals(course.getId())) {
                courses.set(i, course);
                return course;
            }
        }
        courses.add(course);
        return course;
    }

    public void deleteById(String id) {
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            if (course.getId() != null && course.getId().equals(id)) {
                courses.remove(i);
                return;
            }
        }
    }
}
