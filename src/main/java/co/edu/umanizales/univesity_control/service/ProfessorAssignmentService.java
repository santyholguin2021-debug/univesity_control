package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.ProfessorAssignment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorAssignmentService {

    private final List<ProfessorAssignment> assignments = new ArrayList<>();

    public List<ProfessorAssignment> findAll() {
        return new ArrayList<>(assignments);
    }

    public ProfessorAssignment findById(String id) {
        for (ProfessorAssignment assignment : assignments) {
            if (assignment.getId() != null && assignment.getId().equals(id)) {
                return assignment;
            }
        }
        throw new RuntimeException("Professor Assignment not found with id: " + id);
    }

    public ProfessorAssignment save(ProfessorAssignment assignment) {
        for (int i = 0; i < assignments.size(); i++) {
            ProfessorAssignment current = assignments.get(i);
            if (current.getId() != null && current.getId().equals(assignment.getId())) {
                assignments.set(i, assignment);
                return assignment;
            }
        }
        assignments.add(assignment);
        return assignment;
    }

    public void deleteById(String id) {
        for (int i = 0; i < assignments.size(); i++) {
            ProfessorAssignment assignment = assignments.get(i);
            if (assignment.getId() != null && assignment.getId().equals(id)) {
                assignments.remove(i);
                return;
            }
        }
    }
}
