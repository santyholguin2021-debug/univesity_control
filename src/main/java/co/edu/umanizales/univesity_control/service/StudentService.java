package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Student;
import co.edu.umanizales.univesity_control.repository.impl.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public Student save(Student student) {
        return repository.save(student);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
