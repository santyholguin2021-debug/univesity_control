package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Enrollment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService {

    private final List<Enrollment> enrollments = new ArrayList<>();

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }

    public Enrollment findById(String id) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() != null && enrollment.getId().equals(id)) {
                return enrollment;
            }
        }
        throw new RuntimeException("Enrollment not found with id: " + id);
    }

    public Enrollment save(Enrollment enrollment) {
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment current = enrollments.get(i);
            if (current.getId() != null && current.getId().equals(enrollment.getId())) {
                enrollments.set(i, enrollment);
                return enrollment;
            }
        }
        enrollments.add(enrollment);
        return enrollment;
    }

    public void deleteById(String id) {
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment enrollment = enrollments.get(i);
            if (enrollment.getId() != null && enrollment.getId().equals(id)) {
                enrollments.remove(i);
                return;
            }
        }
    }
}
