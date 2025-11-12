package co.edu.umanizales.univesity_control.repository.impl;

import co.edu.umanizales.univesity_control.model.Course;
import co.edu.umanizales.univesity_control.repository.CsvRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository implements CsvRepository<Course> {

    @Value("${csv.storage.path:./data}")
    private String storagePath;

    private static final String FILE_NAME = "courses.csv";
    private static final String HEADER = "id,name,code,credits,description,departmentId";

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
    public List<Course> findAll() {
        ensureFileExists();
        List<Course> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                courses.add(parseCourse(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return courses;
    }

    @Override
    public Optional<Course> findById(String id) {
        return findAll().stream()
                .filter(course -> course.getId().equals(id))
                .findFirst();
    }

    @Override
    public Course save(Course entity) {
        List<Course> courses = findAll();
        courses.removeIf(c -> c.getId().equals(entity.getId()));
        courses.add(entity);
        saveAll(courses);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        List<Course> courses = findAll();
        courses.removeIf(c -> c.getId().equals(id));
        saveAll(courses);
    }

    @Override
    public void saveAll(List<Course> entities) {
        ensureFileExists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath()))) {
            writer.println(HEADER);
            for (Course course : entities) {
                writer.println(toCSV(course));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file", e);
        }
    }

    private Course parseCourse(String line) {
        String[] parts = line.split(",", -1);
        return new Course(
                parts[0], parts[1], parts[2],
                parts[3].isEmpty() ? 0 : Integer.parseInt(parts[3]),
                parts[4], parts[5]
        );
    }

    private String toCSV(Course course) {
        return String.join(",",
                course.getId(),
                course.getName(),
                course.getCode(),
                String.valueOf(course.getCredits()),
                course.getDescription(),
                course.getDepartmentId()
        );
    }
}
