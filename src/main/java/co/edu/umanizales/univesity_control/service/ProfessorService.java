package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Professor;
import co.edu.umanizales.univesity_control.repository.impl.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository repository;

    public List<Professor> findAll() {
        return repository.findAll();
    }

    public Professor findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found with id: " + id));
    }

    public Professor save(Professor professor) {
        return repository.save(professor);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
