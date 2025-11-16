package co.edu.umanizales.univesity_control.repository.impl;

import co.edu.umanizales.univesity_control.model.Faculty;
import co.edu.umanizales.univesity_control.repository.CsvRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FacultyRepository implements CsvRepository<Faculty> {

    @Value("${csv.storage.path:./data}")
    private String storagePath;

    private static final String FILE_NAME = "faculties.csv";
    private static final String HEADER = "id,name,code,deanId,description";

    private String getFilePath() {
        return storagePath + File.separator + FILE_NAME;
    }

    private void ensureFileExists() {
        File directory = new File(storagePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(getFilePath());
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.println(HEADER);
            } catch (IOException e) {
                throw new RuntimeException("Error creating CSV file", e);
            }
        }
    }

    @Override
    public List<Faculty> findAll() {
        ensureFileExists();
        List<Faculty> faculties = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                faculties.add(parseFaculty(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return faculties;
    }

    @Override
    public Optional<Faculty> findById(String id) {
        return findAll().stream()
                .filter(faculty -> faculty.getId().equals(id))
                .findFirst();
    }

    @Override
    public Faculty save(Faculty entity) {
        List<Faculty> faculties = findAll();
        // Check if ID already exists
        boolean exists = faculties.stream().anyMatch(f -> f.getId().equals(entity.getId()));
        if (exists) {
            // Update existing
            for (int i = 0; i < faculties.size(); i++) {
                if (faculties.get(i).getId().equals(entity.getId())) {
                    faculties.set(i, entity);
                    break;
                }
            }
        } else {
            // Add new
            faculties.add(entity);
        }
        saveAll(faculties);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        List<Faculty> faculties = findAll();
        faculties.removeIf(f -> f.getId().equals(id));
        saveAll(faculties);
    }

    @Override
    public void saveAll(List<Faculty> entities) {
        ensureFileExists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath()))) {
            writer.println(HEADER);
            for (Faculty faculty : entities) {
                writer.println(toCSV(faculty));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file", e);
        }
    }

    private Faculty parseFaculty(String line) {
        String[] parts = line.split(",", -1);
        return new Faculty(parts[0], parts[1], parts[2], null, parts[4]);
    }

    private String toCSV(Faculty faculty) {
        return String.join(",",
                faculty.getId(),
                faculty.getName(),
                faculty.getCode(),
                "",
                faculty.getDescription()
        );
    }
}
