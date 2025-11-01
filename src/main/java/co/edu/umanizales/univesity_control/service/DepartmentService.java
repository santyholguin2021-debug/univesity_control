package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.Department;
import co.edu.umanizales.univesity_control.repository.impl.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;

    public List<Department> findAll() {
        return repository.findAll();
    }

    public Department findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    public Department save(Department department) {
        return repository.save(department);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
