package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Classroom;
import co.edu.umanizales.univesity_control.repository.impl.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository repository;
    private final List<Classroom> classrooms = new ArrayList<>();

    public void loadData() {
        classrooms.clear();
        classrooms.addAll(repository.findAll());
    }

    public List<Classroom> findAll() {
        return new ArrayList<>(classrooms);
    }

    public Classroom findById(String id) {
        for (Classroom classroom : classrooms) {
            if (classroom.getId() != null && classroom.getId().equals(id)) {
                return classroom;
            }
        }
        throw new RuntimeException("Classroom not found with id: " + id);
    }

    public Classroom create(Classroom classroom) {
        // Verificar si el ID ya existe
        for (Classroom current : classrooms) {
            if (current.getId() != null && current.getId().equals(classroom.getId())) {
                throw new RuntimeException("Classroom with id: " + classroom.getId() + " already exists");
            }
        }
        classrooms.add(classroom);
        repository.saveAll(classrooms);
        return classroom;
    }

    public Classroom update(Classroom classroom) {
        for (int i = 0; i < classrooms.size(); i++) {
            Classroom current = classrooms.get(i);
            if (current.getId() != null && current.getId().equals(classroom.getId())) {
                classrooms.set(i, classroom);
                repository.saveAll(classrooms);
                return classroom;
            }
        }
        throw new RuntimeException("Classroom not found with id: " + classroom.getId());
    }

    public Classroom save(Classroom classroom) {
        for (int i = 0; i < classrooms.size(); i++) {
            Classroom current = classrooms.get(i);
            if (current.getId() != null && current.getId().equals(classroom.getId())) {
                classrooms.set(i, classroom);
                repository.save(classroom);
                return classroom;
            }
        }
        classrooms.add(classroom);
        repository.save(classroom);
        return classroom;
    }

    public void deleteById(String id) {
        for (int i = 0; i < classrooms.size(); i++) {
            Classroom classroom = classrooms.get(i);
            if (classroom.getId() != null && classroom.getId().equals(id)) {
                classrooms.remove(i);
                repository.deleteById(id);
                return;
            }
        }
    }
}
