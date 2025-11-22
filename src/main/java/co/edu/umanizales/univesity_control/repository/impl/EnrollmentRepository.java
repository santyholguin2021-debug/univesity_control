package co.edu.umanizales.univesity_control.repository.impl;

import co.edu.umanizales.univesity_control.model.Enrollment;
import co.edu.umanizales.univesity_control.repository.CsvRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EnrollmentRepository implements CsvRepository<Enrollment> {

    @Value("${csv.storage.path:./data}")
    private String storagePath;

    private static final String FILE_NAME = "enrollments.csv";
    private static final String HEADER = "id,enrollmentDate,semester,status,grade";

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
    public List<Enrollment> findAll() {
        ensureFileExists();
        List<Enrollment> enrollments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                enrollments.add(parseEnrollment(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return enrollments;
    }

    @Override
    public Optional<Enrollment> findById(String id) {
        return findAll().stream()
                .filter(enrollment -> enrollment.getId().equals(id))
                .findFirst();
    }

    @Override
    public Enrollment save(Enrollment entity) {
        List<Enrollment> enrollments = findAll();
        // Check if ID already exists
        boolean exists = enrollments.stream().anyMatch(e -> e.getId().equals(entity.getId()));
        if (exists) {
            // Update existing
            for (int i = 0; i < enrollments.size(); i++) {
                if (enrollments.get(i).getId().equals(entity.getId())) {
                    enrollments.set(i, entity);
                    break;
                }
            }
        } else {
            // Add new
            enrollments.add(entity);
        }
        saveAll(enrollments);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        List<Enrollment> enrollments = findAll();
        enrollments.removeIf(e -> e.getId().equals(id));
        saveAll(enrollments);
    }

    @Override
    public void saveAll(List<Enrollment> entities) {
        ensureFileExists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath()))) {
            writer.println(HEADER);
            for (Enrollment enrollment : entities) {
                writer.println(toCSV(enrollment));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file", e);
        }
    }

    private Enrollment parseEnrollment(String line) {
        String[] parts = line.split(",", -1);
        return new Enrollment(
                parts[0], null, null, parts[1], parts[2], parts[3],
                parts[4].isEmpty() ? 0.0 : Double.parseDouble(parts[4])
        );
    }

    private String toCSV(Enrollment enrollment) {
        return String.join(",",
                enrollment.getId(),
                enrollment.getEnrollmentDate(),
                enrollment.getSemester(),
                enrollment.getStatus(),
                String.valueOf(enrollment.getGrade())
        );
    }
}
