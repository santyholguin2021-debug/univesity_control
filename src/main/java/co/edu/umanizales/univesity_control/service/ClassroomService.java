package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Classroom;
import co.edu.umanizales.univesity_control.repository.impl.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository repository;

    public List<Classroom> findAll() {
        return repository.findAll();
    }

    public Classroom findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found with id: " + id));
    }

    public Classroom save(Classroom classroom) {
        return repository.save(classroom);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
