package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Department;
import co.edu.umanizales.univesity_control.repository.impl.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;
    private final List<Department> departments = new ArrayList<>();

    public void loadData() {
        departments.clear();
        departments.addAll(repository.findAll());
    }

    public List<Department> findAll() {
        return new ArrayList<>(departments);
    }

    public Department findById(String id) {
        for (Department department : departments) {
            if (department.getId() != null && department.getId().equals(id)) {
                return department;
            }
        }
        throw new RuntimeException("Department not found with id: " + id);
    }

    public Department create(Department department) {
        // Verificar si el ID ya existe
        for (Department current : departments) {
            if (current.getId() != null && current.getId().equals(department.getId())) {
                throw new RuntimeException("Department with id: " + department.getId() + " already exists");
            }
        }
        departments.add(department);
        repository.saveAll(departments);
        return department;
    }

    public Department update(Department department) {
        for (int i = 0; i < departments.size(); i++) {
            Department current = departments.get(i);
            if (current.getId() != null && current.getId().equals(department.getId())) {
                departments.set(i, department);
                repository.saveAll(departments);
                return department;
            }
        }
        throw new RuntimeException("Department not found with id: " + department.getId());
    }

    public Department save(Department department) {
        for (int i = 0; i < departments.size(); i++) {
            Department current = departments.get(i);
            if (current.getId() != null && current.getId().equals(department.getId())) {
                departments.set(i, department);
                repository.save(department);
                return department;
            }
        }
        departments.add(department);
        repository.save(department);
        return department;
    }

    public void deleteById(String id) {
        for (int i = 0; i < departments.size(); i++) {
            Department department = departments.get(i);
            if (department.getId() != null && department.getId().equals(id)) {
                departments.remove(i);
                repository.deleteById(id);
                return;
            }
        }
    }
}
