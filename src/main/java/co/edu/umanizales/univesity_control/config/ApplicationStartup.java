package co.edu.umanizales.univesity_control.config;

import co.edu.umanizales.univesity_control.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationStartup {

    private final StudentService studentService;
    private final CourseService courseService;
    private final ProfessorService professorService;
    private final FacultyService facultyService;
    private final DepartmentService departmentService;
    private final AdministrativeEmployeeService administrativeEmployeeService;
    private final ClassroomService classroomService;
    private final EnrollmentService enrollmentService;
    private final ProfessorAssignmentService professorAssignmentService;

    @EventListener(ApplicationReadyEvent.class)
    public void loadDataOnStartup() {
        // Load data from CSV files in order of dependencies
        facultyService.loadData();
        departmentService.loadData();
        courseService.loadData();
        studentService.loadData();
        professorService.loadData();
        administrativeEmployeeService.loadData();
        classroomService.loadData();
        enrollmentService.loadData();
        professorAssignmentService.loadData();
    }
}
