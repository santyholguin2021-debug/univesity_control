package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Faculty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacultyService {

    private final List<Faculty> faculties = new ArrayList<>();

    public List<Faculty> findAll() {
        return new ArrayList<>(faculties);
    }

    public Faculty findById(String id) {
        for (Faculty faculty : faculties) {
            if (faculty.getId() != null && faculty.getId().equals(id)) {
                return faculty;
            }
        }
        throw new RuntimeException("Faculty not found with id: " + id);
    }

    public Faculty save(Faculty faculty) {
        for (int i = 0; i < faculties.size(); i++) {
            Faculty current = faculties.get(i);
            if (current.getId() != null && current.getId().equals(faculty.getId())) {
                faculties.set(i, faculty);
                return faculty;
            }
        }
        faculties.add(faculty);
        return faculty;
    }

    public void deleteById(String id) {
        for (int i = 0; i < faculties.size(); i++) {
            Faculty faculty = faculties.get(i);
            if (faculty.getId() != null && faculty.getId().equals(id)) {
                faculties.remove(i);
                return;
            }
        }
    }
}
