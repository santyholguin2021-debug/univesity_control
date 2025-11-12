package co.edu.umanizales.univesity_control.repository.impl;

import co.edu.umanizales.univesity_control.model.Student;
import co.edu.umanizales.univesity_control.repository.CsvRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository implements CsvRepository<Student> {

    @Value("${csv.storage.path:./data}")
    private String storagePath;

    private static final String FILE_NAME = "students.csv";
    private static final String HEADER = "id,firstName,lastName,email,phone,address,enrollmentDate,major,gpa";

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
    public List<Student> findAll() {
        ensureFileExists();
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                students.add(parseStudent(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return students;
    }

    @Override
    public Optional<Student> findById(String id) {
        return findAll().stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }

    @Override
    public Student save(Student entity) {
        List<Student> students = findAll();
        students.removeIf(s -> s.getId().equals(entity.getId()));
        students.add(entity);
        saveAll(students);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        List<Student> students = findAll();
        students.removeIf(s -> s.getId().equals(id));
        saveAll(students);
    }

    @Override
    public void saveAll(List<Student> entities) {
        ensureFileExists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath()))) {
            writer.println(HEADER);
            for (Student student : entities) {
                writer.println(toCSV(student));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file", e);
        }
    }

    private Student parseStudent(String line) {
        String[] parts = line.split(",", -1);
        return new Student(
                parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                parts[6], parts[7], parts[8].isEmpty() ? 0.0 : Double.parseDouble(parts[8])
        );
    }

    private String toCSV(Student student) {
        return String.join(",",
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhone(),
                student.getAddress(),
                student.getEnrollmentDate(),
                student.getMajor(),
                String.valueOf(student.getGpa())
        );
    }
}
