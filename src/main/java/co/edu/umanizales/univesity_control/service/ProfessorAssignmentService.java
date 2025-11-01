package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.ProfessorAssignment;
import co.edu.umanizales.univesity_control.repository.impl.ProfessorAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorAssignmentService {

    private final ProfessorAssignmentRepository repository;

    public List<ProfessorAssignment> findAll() {
        return repository.findAll();
    }

    public ProfessorAssignment findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor Assignment not found with id: " + id));
    }

    public ProfessorAssignment save(ProfessorAssignment assignment) {
        return repository.save(assignment);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
