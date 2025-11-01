package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Faculty;
import co.edu.umanizales.univesity_control.repository.impl.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository repository;

    public List<Faculty> findAll() {
        return repository.findAll();
    }

    public Faculty findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found with id: " + id));
    }

    public Faculty save(Faculty faculty) {
        return repository.save(faculty);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
