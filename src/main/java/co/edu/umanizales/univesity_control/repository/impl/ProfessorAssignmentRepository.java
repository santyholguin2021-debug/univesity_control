package co.edu.umanizales.univesity_control.repository.impl;

import co.edu.umanizales.univesity_control.model.ProfessorAssignment;
import co.edu.umanizales.univesity_control.repository.CsvRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProfessorAssignmentRepository implements CsvRepository<ProfessorAssignment> {

    @Value("${csv.storage.path:./data}")
    private String storagePath;

    private static final String FILE_NAME = "professor_assignments.csv";
    private static final String HEADER = "id,professorId,courseId,semester,assignmentDate,schedule";

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
    public List<ProfessorAssignment> findAll() {
        ensureFileExists();
        List<ProfessorAssignment> assignments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                assignments.add(parseAssignment(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return assignments;
    }

    @Override
    public Optional<ProfessorAssignment> findById(String id) {
        return findAll().stream()
                .filter(assignment -> assignment.getId().equals(id))
                .findFirst();
    }

    @Override
    public ProfessorAssignment save(ProfessorAssignment entity) {
        List<ProfessorAssignment> assignments = findAll();
        // Check if ID already exists
        boolean exists = assignments.stream().anyMatch(a -> a.getId().equals(entity.getId()));
        if (exists) {
            // Update existing
            for (int i = 0; i < assignments.size(); i++) {
                if (assignments.get(i).getId().equals(entity.getId())) {
                    assignments.set(i, entity);
                    break;
                }
            }
        } else {
            // Add new
            assignments.add(entity);
        }
        saveAll(assignments);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        List<ProfessorAssignment> assignments = findAll();
        assignments.removeIf(a -> a.getId().equals(id));
        saveAll(assignments);
    }

    @Override
    public void saveAll(List<ProfessorAssignment> entities) {
        ensureFileExists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath()))) {
            writer.println(HEADER);
            for (ProfessorAssignment assignment : entities) {
                writer.println(toCSV(assignment));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file", e);
        }
    }

    private ProfessorAssignment parseAssignment(String line) {
        String[] parts = line.split(",", -1);
        return new ProfessorAssignment(parts[0], null, null, parts[3], parts[4], parts[5]);
    }

    private String toCSV(ProfessorAssignment assignment) {
        return String.join(",",
                assignment.getId(),
                "",
                "",
                assignment.getSemester(),
                assignment.getAssignmentDate(),
                assignment.getSchedule()
        );
    }
}
