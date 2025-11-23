package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Professor;
import co.edu.umanizales.univesity_control.repository.impl.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository repository;
    private final List<Professor> professors = new ArrayList<>();

    public void loadData() {
        professors.clear();
        professors.addAll(repository.findAll());
    }

    public List<Professor> findAll() {
        return new ArrayList<>(professors);
    }

    public Professor findById(String id) {
        for (Professor professor : professors) {
            if (professor.getId() != null && professor.getId().equals(id)) {
                return professor;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found with id: " + id);
    }

    public Professor create(Professor professor) {
        // Verificar si el ID ya existe
        for (Professor current : professors) {
            if (current.getId() != null && current.getId().equals(professor.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Professor with id: " + professor.getId() + " already exists");
            }
        }
        if (professor.getDepartment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "department is required for Professor");
        }
        professor.setDepartmentId(professor.getDepartment() != null ? professor.getDepartment().getId() : null);
        professors.add(professor);
        repository.saveAll(professors);
        return professor;
    }

    public Professor update(Professor professor) {
        if (professor.getDepartment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "department is required for Professor");
        }
        for (int i = 0; i < professors.size(); i++) {
            Professor current = professors.get(i);
            if (current.getId() != null && current.getId().equals(professor.getId())) {
                professor.setDepartmentId(professor.getDepartment() != null ? professor.getDepartment().getId() : null);
                professors.set(i, professor);
                repository.saveAll(professors);
                return professor;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor not found with id: " + professor.getId());
    }

    public Professor save(Professor professor) {
        for (int i = 0; i < professors.size(); i++) {
            Professor current = professors.get(i);
            if (current.getId() != null && current.getId().equals(professor.getId())) {
                professor.setDepartmentId(professor.getDepartment() != null ? professor.getDepartment().getId() : null);
                professors.set(i, professor);
                repository.save(professor);
                return professor;
            }
        }
        professor.setDepartmentId(professor.getDepartment() != null ? professor.getDepartment().getId() : null);
        professors.add(professor);
        repository.save(professor);
        return professor;
    }

    public void deleteById(String id) {
        for (int i = 0; i < professors.size(); i++) {
            Professor professor = professors.get(i);
            if (professor.getId() != null && professor.getId().equals(id)) {
                professors.remove(i);
                repository.deleteById(id);
                return;
            }
        }
    }
}
