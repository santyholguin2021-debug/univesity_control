package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Enrollment;
import co.edu.umanizales.univesity_control.repository.impl.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository repository;

    public List<Enrollment> findAll() {
        return repository.findAll();
    }

    public Enrollment findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id: " + id));
    }

    public Enrollment save(Enrollment enrollment) {
        return repository.save(enrollment);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
