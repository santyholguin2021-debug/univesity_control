package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Enrollment;
import co.edu.umanizales.univesity_control.repository.impl.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository repository;
    private final List<Enrollment> enrollments = new ArrayList<>();

    public void loadData() {
        enrollments.clear();
        enrollments.addAll(repository.findAll());
    }

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

    public Enrollment create(Enrollment enrollment) {
        // Verificar si el ID ya existe
        for (Enrollment current : enrollments) {
            if (current.getId() != null && current.getId().equals(enrollment.getId())) {
                throw new RuntimeException("Enrollment with id: " + enrollment.getId() + " already exists");
            }
        }
        enrollments.add(enrollment);
        if (enrollment.getStudent() != null && !enrollment.getStudent().getEnrollments().contains(enrollment)) {
            enrollment.getStudent().getEnrollments().add(enrollment);
        }
        if (enrollment.getCourse() != null && !enrollment.getCourse().getEnrollments().contains(enrollment)) {
            enrollment.getCourse().getEnrollments().add(enrollment);
        }
        repository.saveAll(enrollments);
        return enrollment;
    }

    public Enrollment update(Enrollment enrollment) {
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment current = enrollments.get(i);
            if (current.getId() != null && current.getId().equals(enrollment.getId())) {
                // detach from old relations
                if (current.getStudent() != null) {
                    current.getStudent().getEnrollments().remove(current);
                }
                if (current.getCourse() != null) {
                    current.getCourse().getEnrollments().remove(current);
                }
                enrollments.set(i, enrollment);
                // attach to new relations
                if (enrollment.getStudent() != null && !enrollment.getStudent().getEnrollments().contains(enrollment)) {
                    enrollment.getStudent().getEnrollments().add(enrollment);
                }
                if (enrollment.getCourse() != null && !enrollment.getCourse().getEnrollments().contains(enrollment)) {
                    enrollment.getCourse().getEnrollments().add(enrollment);
                }
                repository.saveAll(enrollments);
                return enrollment;
            }
        }
        throw new RuntimeException("Enrollment not found with id: " + enrollment.getId());
    }

    public Enrollment save(Enrollment enrollment) {
        // replace existing by id if present
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment current = enrollments.get(i);
            if (current.getId() != null && current.getId().equals(enrollment.getId())) {
                // detach from old relations
                if (current.getStudent() != null) {
                    current.getStudent().getEnrollments().remove(current);
                }
                if (current.getCourse() != null) {
                    current.getCourse().getEnrollments().remove(current);
                }
                enrollments.set(i, enrollment);
                // attach to new relations
                if (enrollment.getStudent() != null && !enrollment.getStudent().getEnrollments().contains(enrollment)) {
                    enrollment.getStudent().getEnrollments().add(enrollment);
                }
                if (enrollment.getCourse() != null && !enrollment.getCourse().getEnrollments().contains(enrollment)) {
                    enrollment.getCourse().getEnrollments().add(enrollment);
                }
                repository.save(enrollment);
                return enrollment;
            }
        }
        enrollments.add(enrollment);
        if (enrollment.getStudent() != null && !enrollment.getStudent().getEnrollments().contains(enrollment)) {
            enrollment.getStudent().getEnrollments().add(enrollment);
        }
        if (enrollment.getCourse() != null && !enrollment.getCourse().getEnrollments().contains(enrollment)) {
            enrollment.getCourse().getEnrollments().add(enrollment);
        }
        repository.save(enrollment);
        return enrollment;
    }

    public void deleteById(String id) {
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment enrollment = enrollments.get(i);
            if (enrollment.getId() != null && enrollment.getId().equals(id)) {
                if (enrollment.getStudent() != null) {
                    enrollment.getStudent().getEnrollments().remove(enrollment);
                }
                if (enrollment.getCourse() != null) {
                    enrollment.getCourse().getEnrollments().remove(enrollment);
                }
                enrollments.remove(i);
                repository.deleteById(id);
                return;
            }
        }
    }
}
