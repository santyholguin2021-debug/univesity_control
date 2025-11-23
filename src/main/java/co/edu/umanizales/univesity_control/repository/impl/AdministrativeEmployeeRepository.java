package co.edu.umanizales.univesity_control.repository.impl;

import co.edu.umanizales.univesity_control.model.AdministrativeEmployee;
import co.edu.umanizales.univesity_control.repository.CsvRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AdministrativeEmployeeRepository implements CsvRepository<AdministrativeEmployee> {

    @Value("${csv.storage.path:./data}")
    private String storagePath;

    private static final String FILE_NAME = "administrative_employees.csv";
    private static final String HEADER = "id,firstName,lastName,email,phone,address,position,hireDate,salary,departmentId";

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
    public List<AdministrativeEmployee> findAll() {
        ensureFileExists();
        List<AdministrativeEmployee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                employees.add(parseEmployee(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return employees;
    }

    @Override
    public Optional<AdministrativeEmployee> findById(String id) {
        return findAll().stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst();
    }

    @Override
    public AdministrativeEmployee save(AdministrativeEmployee entity) {
        List<AdministrativeEmployee> employees = findAll();
        // Check if ID already exists
        boolean exists = employees.stream().anyMatch(e -> e.getId().equals(entity.getId()));
        if (exists) {
            // Update existing
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getId().equals(entity.getId())) {
                    employees.set(i, entity);
                    break;
                }
            }
        } else {
            // Add new
            employees.add(entity);
        }
        saveAll(employees);
        return entity;
    }

    @Override
    public void deleteById(String id) {
        List<AdministrativeEmployee> employees = findAll();
        employees.removeIf(e -> e.getId().equals(id));
        saveAll(employees);
    }

    @Override
    public void saveAll(List<AdministrativeEmployee> entities) {
        ensureFileExists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath()))) {
            writer.println(HEADER);
            for (AdministrativeEmployee employee : entities) {
                writer.println(toCSV(employee));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV file", e);
        }
    }

    private AdministrativeEmployee parseEmployee(String line) {
        String[] parts = line.split(",", -1);
        AdministrativeEmployee emp = new AdministrativeEmployee(
                parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                parts[6], null, parts[7], parts[8].isEmpty() ? 0.0 : Double.parseDouble(parts[8])
        );
        if (parts.length > 9 && !parts[9].isEmpty()) {
            emp.setDepartmentId(parts[9]);
        }
        return emp;
    }

    private String toCSV(AdministrativeEmployee employee) {
        return String.join(",",
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getAddress(),
                employee.getPosition(),
                employee.getHireDate(),
                String.valueOf(employee.getSalary()),
                employee.getDepartmentId() == null ? "" : employee.getDepartmentId()
        );
    }
}