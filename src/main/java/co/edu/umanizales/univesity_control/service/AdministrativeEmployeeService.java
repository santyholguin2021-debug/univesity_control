package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.AdministrativeEmployee;
import co.edu.umanizales.univesity_control.repository.impl.AdministrativeEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministrativeEmployeeService {

    private final AdministrativeEmployeeRepository repository;
    private final List<AdministrativeEmployee> employees = new ArrayList<>();

    public void loadData() {
        employees.clear();
        employees.addAll(repository.findAll());
    }

    public List<AdministrativeEmployee> findAll() {
        return new ArrayList<>(employees);
    }

    public AdministrativeEmployee findById(String id) {
        for (AdministrativeEmployee employee : employees) {
            if (employee.getId() != null && employee.getId().equals(id)) {
                return employee;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrative Employee not found with id: " + id);
    }

    public AdministrativeEmployee create(AdministrativeEmployee employee) {
        // Verificar si el ID ya existe
        for (AdministrativeEmployee current : employees) {
            if (current.getId() != null && current.getId().equals(employee.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Administrative Employee with id: " + employee.getId() + " already exists");
            }
        }
        if (employee.getDepartment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "department is required for AdministrativeEmployee");
        }
        employee.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null);
        employees.add(employee);
        repository.saveAll(employees);
        return employee;
    }

    public AdministrativeEmployee update(AdministrativeEmployee employee) {
        if (employee.getDepartment() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "department is required for AdministrativeEmployee");
        }
        for (int i = 0; i < employees.size(); i++) {
            AdministrativeEmployee current = employees.get(i);
            if (current.getId() != null && current.getId().equals(employee.getId())) {
                employee.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null);
                employees.set(i, employee);
                repository.saveAll(employees);
                return employee;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrative Employee not found with id: " + employee.getId());
    }

    public AdministrativeEmployee save(AdministrativeEmployee employee) {
        for (int i = 0; i < employees.size(); i++) {
            AdministrativeEmployee current = employees.get(i);
            if (current.getId() != null && current.getId().equals(employee.getId())) {
                employee.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null);
                employees.set(i, employee);
                repository.save(employee);
                return employee;
            }
        }
        employee.setDepartmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null);
        employees.add(employee);
        repository.save(employee);
        return employee;
    }

    public void deleteById(String id) {
        for (int i = 0; i < employees.size(); i++) {
            AdministrativeEmployee employee = employees.get(i);
            if (employee.getId() != null && employee.getId().equals(id)) {
                employees.remove(i);
                repository.deleteById(id);
                return;
            }
        }
    }
}
