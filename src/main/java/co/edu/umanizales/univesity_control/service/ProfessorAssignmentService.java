package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.ProfessorAssignment;
import co.edu.umanizales.univesity_control.repository.impl.ProfessorAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorAssignmentService {

    private final ProfessorAssignmentRepository repository;
    private final List<ProfessorAssignment> assignments = new ArrayList<>();

    public void loadData() {
        assignments.clear();
        assignments.addAll(repository.findAll());
    }

    public List<ProfessorAssignment> findAll() {
        return new ArrayList<>(assignments);
    }

    public ProfessorAssignment findById(String id) {
        for (ProfessorAssignment assignment : assignments) {
            if (assignment.getId() != null && assignment.getId().equals(id)) {
                return assignment;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor Assignment not found with id: " + id);
    }

    public ProfessorAssignment create(ProfessorAssignment assignment) {
        // Verificar si el ID ya existe
        for (ProfessorAssignment current : assignments) {
            if (current.getId() != null && current.getId().equals(assignment.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Professor Assignment with id: " + assignment.getId() + " already exists");
            }
        }
        if (assignment.getProfessor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "professor is required for ProfessorAssignment");
        }
        if (assignment.getCourse() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "course is required for ProfessorAssignment");
        }
        assignments.add(assignment);
        if (assignment.getProfessor() != null && !assignment.getProfessor().getAssignments().contains(assignment)) {
            assignment.getProfessor().getAssignments().add(assignment);
        }
        if (assignment.getCourse() != null && !assignment.getCourse().getProfessorAssignments().contains(assignment)) {
            assignment.getCourse().getProfessorAssignments().add(assignment);
        }
        repository.saveAll(assignments);
        return assignment;
    }

    public ProfessorAssignment update(ProfessorAssignment assignment) {
        if (assignment.getProfessor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "professor is required for ProfessorAssignment");
        }
        if (assignment.getCourse() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "course is required for ProfessorAssignment");
        }
        for (int i = 0; i < assignments.size(); i++) {
            ProfessorAssignment current = assignments.get(i);
            if (current.getId() != null && current.getId().equals(assignment.getId())) {
                // detach from old relations
                if (current.getProfessor() != null) {
                    current.getProfessor().getAssignments().remove(current);
                }
                if (current.getCourse() != null) {
                    current.getCourse().getProfessorAssignments().remove(current);
                }
                assignments.set(i, assignment);
                // attach to new relations
                if (assignment.getProfessor() != null && !assignment.getProfessor().getAssignments().contains(assignment)) {
                    assignment.getProfessor().getAssignments().add(assignment);
                }
                if (assignment.getCourse() != null && !assignment.getCourse().getProfessorAssignments().contains(assignment)) {
                    assignment.getCourse().getProfessorAssignments().add(assignment);
                }
                repository.saveAll(assignments);
                return assignment;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor Assignment not found with id: " + assignment.getId());
    }

    public ProfessorAssignment save(ProfessorAssignment assignment) {
        for (int i = 0; i < assignments.size(); i++) {
            ProfessorAssignment current = assignments.get(i);
            if (current.getId() != null && current.getId().equals(assignment.getId())) {
                // detach from old relations
                if (current.getProfessor() != null) {
                    current.getProfessor().getAssignments().remove(current);
                }
                if (current.getCourse() != null) {
                    current.getCourse().getProfessorAssignments().remove(current);
                }
                assignments.set(i, assignment);
                // attach to new relations
                if (assignment.getProfessor() != null && !assignment.getProfessor().getAssignments().contains(assignment)) {
                    assignment.getProfessor().getAssignments().add(assignment);
                }
                if (assignment.getCourse() != null && !assignment.getCourse().getProfessorAssignments().contains(assignment)) {
                    assignment.getCourse().getProfessorAssignments().add(assignment);
                }
                repository.save(assignment);
                return assignment;
            }
        }
        assignments.add(assignment);
        if (assignment.getProfessor() != null && !assignment.getProfessor().getAssignments().contains(assignment)) {
            assignment.getProfessor().getAssignments().add(assignment);
        }
        if (assignment.getCourse() != null && !assignment.getCourse().getProfessorAssignments().contains(assignment)) {
            assignment.getCourse().getProfessorAssignments().add(assignment);
        }
        repository.save(assignment);
        return assignment;
    }

    public void deleteById(String id) {
        for (int i = 0; i < assignments.size(); i++) {
            ProfessorAssignment assignment = assignments.get(i);
            if (assignment.getId() != null && assignment.getId().equals(id)) {
                if (assignment.getProfessor() != null) {
                    assignment.getProfessor().getAssignments().remove(assignment);
                }
                if (assignment.getCourse() != null) {
                    assignment.getCourse().getProfessorAssignments().remove(assignment);
                }
                assignments.remove(i);
                repository.deleteById(id);
                return;
            }
        }
    }
}
