package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Faculty;
import co.edu.umanizales.univesity_control.repository.impl.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository repository;
    private final List<Faculty> faculties = new ArrayList<>();

    public void loadData() {
        faculties.clear();
        faculties.addAll(repository.findAll());
    }

    public List<Faculty> findAll() {
        return new ArrayList<>(faculties);
    }

    public Faculty findById(String id) {
        for (Faculty faculty : faculties) {
            if (faculty.getId() != null && faculty.getId().equals(id)) {
                return faculty;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found with id: " + id);
    }

    public Faculty create(Faculty faculty) {
        // Verificar si el ID ya existe
        for (Faculty current : faculties) {
            if (current.getId() != null && current.getId().equals(faculty.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Faculty with id: " + faculty.getId() + " already exists");
            }
        }
        if (faculty.getDean() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dean is required for Faculty");
        }
        faculty.setDeanId(faculty.getDean() != null ? faculty.getDean().getId() : null);
        faculties.add(faculty);
        repository.saveAll(faculties);
        return faculty;
    }

    public Faculty update(Faculty faculty) {
        if (faculty.getDean() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "dean is required for Faculty");
        }
        for (int i = 0; i < faculties.size(); i++) {
            Faculty current = faculties.get(i);
            if (current.getId() != null && current.getId().equals(faculty.getId())) {
                faculty.setDeanId(faculty.getDean() != null ? faculty.getDean().getId() : null);
                faculties.set(i, faculty);
                repository.saveAll(faculties);
                return faculty;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found with id: " + faculty.getId());
    }

    public Faculty save(Faculty faculty) {
        for (int i = 0; i < faculties.size(); i++) {
            Faculty current = faculties.get(i);
            if (current.getId() != null && current.getId().equals(faculty.getId())) {
                faculty.setDeanId(faculty.getDean() != null ? faculty.getDean().getId() : null);
                faculties.set(i, faculty);
                repository.save(faculty);
                return faculty;
            }
        }
        faculty.setDeanId(faculty.getDean() != null ? faculty.getDean().getId() : null);
        faculties.add(faculty);
        repository.save(faculty);
        return faculty;
    }

    public void deleteById(String id) {
        for (int i = 0; i < faculties.size(); i++) {
            Faculty faculty = faculties.get(i);
            if (faculty.getId() != null && faculty.getId().equals(id)) {
                faculties.remove(i);
                repository.deleteById(id);
                return;
            }
        }
    }
}
