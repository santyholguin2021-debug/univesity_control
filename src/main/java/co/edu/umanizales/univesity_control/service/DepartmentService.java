package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Department;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private final List<Department> departments = new ArrayList<>();

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

    public Department save(Department department) {
        for (int i = 0; i < departments.size(); i++) {
            Department current = departments.get(i);
            if (current.getId() != null && current.getId().equals(department.getId())) {
                departments.set(i, department);
                return department;
            }
        }
        departments.add(department);
        return department;
    }

    public void deleteById(String id) {
        for (int i = 0; i < departments.size(); i++) {
            Department department = departments.get(i);
            if (department.getId() != null && department.getId().equals(id)) {
                departments.remove(i);
                return;
            }
        }
    }
}
