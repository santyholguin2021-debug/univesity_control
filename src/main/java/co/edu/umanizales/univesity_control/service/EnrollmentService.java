package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Enrollment;
import co.edu.umanizales.univesity_control.repository.impl.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

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
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found with id: " + id);
    }

    public Enrollment create(Enrollment enrollment) {
        // Verificar si el ID ya existe
        for (Enrollment current : enrollments) {
            if (current.getId() != null && current.getId().equals(enrollment.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enrollment with id: " + enrollment.getId() + " already exists");
            }
        }
        if (enrollment.getStudent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "student is required for Enrollment");
        }
        if (enrollment.getCourse() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "course is required for Enrollment");
        }
        // Sync duplicated/derived fields from related objects
        enrollment.setStudentId(enrollment.getStudent() != null ? enrollment.getStudent().getId() : null);
        enrollment.setCourseId(enrollment.getCourse() != null ? enrollment.getCourse().getId() : null);
        // Ensure enrollmentDate matches the Student's enrollmentDate
        if (enrollment.getStudent() != null) {
            enrollment.setEnrollmentDate(enrollment.getStudent().getEnrollmentDate());
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
        if (enrollment.getStudent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "student is required for Enrollment");
        }
        if (enrollment.getCourse() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "course is required for Enrollment");
        }
        // Sync duplicated/derived fields from related objects
        enrollment.setStudentId(enrollment.getStudent() != null ? enrollment.getStudent().getId() : null);
        enrollment.setCourseId(enrollment.getCourse() != null ? enrollment.getCourse().getId() : null);
        if (enrollment.getStudent() != null) {
            enrollment.setEnrollmentDate(enrollment.getStudent().getEnrollmentDate());
        }
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
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found with id: " + enrollment.getId());
    }

    public Enrollment save(Enrollment enrollment) {
        // Sync duplicated/derived fields from related objects
        enrollment.setStudentId(enrollment.getStudent() != null ? enrollment.getStudent().getId() : null);
        enrollment.setCourseId(enrollment.getCourse() != null ? enrollment.getCourse().getId() : null);
        if (enrollment.getStudent() != null) {
            enrollment.setEnrollmentDate(enrollment.getStudent().getEnrollmentDate());
        }
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
    
    public List<Enrollment> findEnrollmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Both startDate and endDate are required");
        }
        if (startDate.isAfter(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "startDate must be before or equal to endDate");
        }
        
        return enrollments.stream()
            .filter(enrollment -> {
                String dateStr = enrollment.getEnrollmentDate();
                if (dateStr == null || dateStr.isEmpty()) {
                    return false;
                }
                try {
                    LocalDate enrollmentDate = LocalDate.parse(dateStr);
                    return !enrollmentDate.isBefore(startDate) &&
                           !enrollmentDate.isAfter(endDate);
                } catch (Exception e) {
                    return false;
                }
            })
            .collect(Collectors.toList());
    }
}
