package co.edu.umanizales.univesity_control.service;

import co.edu.umanizales.univesity_control.model.AdministrativeEmployee;
import co.edu.umanizales.univesity_control.repository.impl.AdministrativeEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministrativeEmployeeService {

    private final AdministrativeEmployeeRepository repository;

    public List<AdministrativeEmployee> findAll() {
        return repository.findAll();
    }

    public AdministrativeEmployee findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrative Employee not found with id: " + id));
    }

    public AdministrativeEmployee save(AdministrativeEmployee employee) {
        return repository.save(employee);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
