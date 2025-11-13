package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Classroom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassroomService {

    private final List<Classroom> classrooms = new ArrayList<>();

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

    public Classroom save(Classroom classroom) {
        for (int i = 0; i < classrooms.size(); i++) {
            Classroom current = classrooms.get(i);
            if (current.getId() != null && current.getId().equals(classroom.getId())) {
                classrooms.set(i, classroom);
                return classroom;
            }
        }
        classrooms.add(classroom);
        return classroom;
    }

    public void deleteById(String id) {
        for (int i = 0; i < classrooms.size(); i++) {
            Classroom classroom = classrooms.get(i);
            if (classroom.getId() != null && classroom.getId().equals(id)) {
                classrooms.remove(i);
                return;
            }
        }
    }
}
