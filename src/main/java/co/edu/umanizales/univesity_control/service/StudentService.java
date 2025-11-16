package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Student;
import co.edu.umanizales.univesity_control.repository.impl.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;
    private final List<Student> students = new ArrayList<>();

    public void loadData() {
        students.clear();
        students.addAll(repository.findAll());
    }

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

    public Student create(Student student) {
        // Verificar si el ID ya existe
        for (Student current : students) {
            if (current.getId() != null && current.getId().equals(student.getId())) {
                throw new RuntimeException("Student with id: " + student.getId() + " already exists");
            }
        }
        students.add(student);
        // Guardar todos los estudiantes para evitar sobrescritura
        repository.saveAll(students);
        return student;
    }

    public Student update(Student student) {
        for (int i = 0; i < students.size(); i++) {
            Student current = students.get(i);
            if (current.getId() != null && current.getId().equals(student.getId())) {
                students.set(i, student);
                repository.saveAll(students);
                return student;
            }
        }
        throw new RuntimeException("Student not found with id: " + student.getId());
    }

    public Student save(Student student) {
        for (int i = 0; i < students.size(); i++) {
            Student current = students.get(i);
            if (current.getId() != null && current.getId().equals(student.getId())) {
                students.set(i, student);
                repository.save(student);
                return student;
            }
        }
        students.add(student);
        repository.save(student);
        return student;
    }

    public void deleteById(String id) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getId() != null && student.getId().equals(id)) {
                students.remove(i);
                repository.deleteById(id);
                return;
            }
        }
    }
}
