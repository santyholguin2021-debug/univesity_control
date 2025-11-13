package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    public Student findById(String id) {
        for (Student student : students) {
            if (student.getId() != null && student.getId().equals(id)) {
                return student;
            }
        }
        throw new RuntimeException("Student not found with id: " + id);
    }

    public Student save(Student student) {
        for (int i = 0; i < students.size(); i++) {
            Student current = students.get(i);
            if (current.getId() != null && current.getId().equals(student.getId())) {
                students.set(i, student);
                return student;
            }
        }
        students.add(student);
        return student;
    }

    public void deleteById(String id) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId() != null && student.getId().equals(id)) {
                students.remove(i);
                return;
            }
        }
    }
}
