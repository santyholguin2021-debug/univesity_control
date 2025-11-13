package co.edu.umanizales.univesity_control.repository.impl;

import co.edu.umanizales.univesity_control.model.Classroom;
import co.edu.umanizales.univesity_control.repository.CsvRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClassroomRepository implements CsvRepository<Classroom> {

    @Value("${csv.storage.path:./data}")
    private String storagePath;

    private static final String FILE_NAME = "classrooms.csv";
    private static final String HEADER = "id,name,building,capacity,type,equipment";

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
    public List<Classroom> findAll() {
        ensureFileExists();
        List<Classroom> classrooms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                classrooms.add(parseClassroom(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return classrooms;
    }

    @Override
    public Optional<Classroom> findById(String id) {
        return findAll().stream()
                .filter(classroom -> classroom.getId().equals(id))
                .findFirst();
    }

    @Override
    public Classroom save(Classroom entity) {
        List<Classroom> classrooms = findAll();
        classrooms.removeIf(c -> c.getId().equals(entity.getId()));
        classrooms.add(entity);
        saveAll(classrooms);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        List<Classroom> classrooms = findAll();
        classrooms.removeIf(c -> c.getId().equals(id));
        saveAll(classrooms);
    }

    @Override
    public void saveAll(List<Classroom> entities) {
        ensureFileExists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath()))) {
            writer.println(HEADER);
            for (Classroom classroom : entities) {
                writer.println(toCSV(classroom));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file", e);
        }
    }

    private Classroom parseClassroom(String line) {
        String[] parts = line.split(",", -1);
        return new Classroom(
                parts[0], parts[1], parts[2],
                parts[3].isEmpty() ? null : Integer.parseInt(parts[3]),
                parts[4], parts[5]
        );
    }

    private String toCSV(Classroom classroom) {
        return String.join(",",
                String.valueOf(classroom.getId()),
                String.valueOf(classroom.getName()),
                String.valueOf(classroom.getBuilding()),
                String.valueOf(classroom.getCapacity()),
                String.valueOf(classroom.getType()),
                String.valueOf(classroom.getEquipment())
        );
    }
}