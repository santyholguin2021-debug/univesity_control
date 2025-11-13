package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Professor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService {

    private final List<Professor> professors = new ArrayList<>();

    public List<Professor> findAll() {
        return new ArrayList<>(professors);
    }

    public Professor findById(String id) {
        for (Professor professor : professors) {
            if (professor.getId() != null && professor.getId().equals(id)) {
                return professor;
            }
        }
        throw new RuntimeException("Professor not found with id: " + id);
    }

    public Professor save(Professor professor) {
        for (int i = 0; i < professors.size(); i++) {
            Professor current = professors.get(i);
            if (current.getId() != null && current.getId().equals(professor.getId())) {
                professors.set(i, professor);
                return professor;
            }
        }
        professors.add(professor);
        return professor;
    }

    public void deleteById(String id) {
        for (int i = 0; i < professors.size(); i++) {
            Professor professor = professors.get(i);
            if (professor.getId() != null && professor.getId().equals(id)) {
                professors.remove(i);
                return;
            }
        }
    }
}
