package co.edu.umanizales.univesity_control.repository.impl;

import co.edu.umanizales.univesity_control.model.Department;
import co.edu.umanizales.univesity_control.repository.CsvRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepository implements CsvRepository<Department> {

    @Value("${csv.storage.path:./data}")
    private String storagePath;

    private static final String FILE_NAME = "departments.csv";
    private static final String HEADER = "id,name,code,facultyId,headProfessorId";

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
    public List<Department> findAll() {
        ensureFileExists();
        List<Department> departments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                departments.add(parseDepartment(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return departments;
    }

    @Override
    public Optional<Department> findById(String id) {
        return findAll().stream()
                .filter(department -> department.getId().equals(id))
                .findFirst();
    }

    @Override
    public Department save(Department entity) {
        List<Department> departments = findAll();
        // Check if ID already exists
        boolean exists = departments.stream().anyMatch(d -> d.getId().equals(entity.getId()));
        if (exists) {
            // Update existing
            for (int i = 0; i < departments.size(); i++) {
                if (departments.get(i).getId().equals(entity.getId())) {
                    departments.set(i, entity);
                    break;
                }
            }
        } else {
            // Add new
            departments.add(entity);
        }
        saveAll(departments);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        List<Department> departments = findAll();
        departments.removeIf(d -> d.getId().equals(id));
        saveAll(departments);
    }

    @Override
    public void saveAll(List<Department> entities) {
        ensureFileExists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath()))) {
            writer.println(HEADER);
            for (Department department : entities) {
                writer.println(toCSV(department));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file", e);
        }
    }

    private Department parseDepartment(String line) {
        String[] parts = line.split(",", -1);
        Department dep = new Department(parts[0], parts[1], parts[2], null, null);
        if (parts.length > 3 && !parts[3].isEmpty()) {
            dep.setFacultyId(parts[3]);
        }
        if (parts.length > 4 && !parts[4].isEmpty()) {
            dep.setHeadProfessorId(parts[4]);
        }
        return dep;
    }

    private String toCSV(Department department) {
        return String.join(",",
                department.getId(),
                department.getName(),
                department.getCode(),
                department.getFacultyId() == null ? "" : department.getFacultyId(),
                department.getHeadProfessorId() == null ? "" : department.getHeadProfessorId()
        );
    }
}
