package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.AdministrativeEmployee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdministrativeEmployeeService {

    private final List<AdministrativeEmployee> employees = new ArrayList<>();

    public List<AdministrativeEmployee> findAll() {
        return new ArrayList<>(employees);
    }

    public AdministrativeEmployee findById(String id) {
        for (AdministrativeEmployee employee : employees) {
            if (employee.getId() != null && employee.getId().equals(id)) {
                return employee;
            }
        }
        throw new RuntimeException("Administrative Employee not found with id: " + id);
    }

    public AdministrativeEmployee save(AdministrativeEmployee employee) {
        for (int i = 0; i < employees.size(); i++) {
            AdministrativeEmployee current = employees.get(i);
            if (current.getId() != null && current.getId().equals(employee.getId())) {
                employees.set(i, employee);
                return employee;
            }
        }
        employees.add(employee);
        return employee;
    }

    public void deleteById(String id) {
        for (int i = 0; i < employees.size(); i++) {
            AdministrativeEmployee employee = employees.get(i);
            if (employee.getId() != null && employee.getId().equals(id)) {
                employees.remove(i);
                return;
            }
        }
    }
}