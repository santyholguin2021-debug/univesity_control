package co.edu.umanizales.univesity_control.repository.impl;

import co.edu.umanizales.univesity_control.model.Professor;
import co.edu.umanizales.univesity_control.repository.CsvRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProfessorRepository implements CsvRepository<Professor> {

    @Value("${csv.storage.path:./data}")
    private String storagePath;

    private static final String FILE_NAME = "professors.csv";
    private static final String HEADER = "id,firstName,lastName,email,phone,address,departmentId,specialization,hireDate,salary";

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
    public List<Professor> findAll() {
        ensureFileExists();
        List<Professor> professors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                professors.add(parseProfessor(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return professors;
    }

    @Override
    public Optional<Professor> findById(String id) {
        return findAll().stream()
                .filter(professor -> professor.getId().equals(id))
                .findFirst();
    }

    @Override
    public Professor save(Professor entity) {
        List<Professor> professors = findAll();
        professors.removeIf(p -> p.getId().equals(entity.getId()));
        professors.add(entity);
        saveAll(professors);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        List<Professor> professors = findAll();
        professors.removeIf(p -> p.getId().equals(id));
        saveAll(professors);
    }

    @Override
    public void saveAll(List<Professor> entities) {
        ensureFileExists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath()))) {
            writer.println(HEADER);
            for (Professor professor : entities) {
                writer.println(toCSV(professor));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file", e);
        }
    }

    private Professor parseProfessor(String line) {
        String[] parts = line.split(",", -1);
        return new Professor(
                parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                parts[6], parts[7], parts[8], parts[9].isEmpty() ? 0.0 : Double.parseDouble(parts[9])
        );
    }

    private String toCSV(Professor professor) {
        return String.join(",",
                professor.getId(),
                professor.getFirstName(),
                professor.getLastName(),
                professor.getEmail(),
                professor.getPhone(),
                professor.getAddress(),
                professor.getDepartmentId(),
                professor.getSpecialization(),
                professor.getHireDate(),
                String.valueOf(professor.getSalary())
        );
    }
}
