# Diagrama de Clases - Sistema de Control Universitario

## Visualización Mermaid

```mermaid
classDiagram
    %% Clase abstracta Person
    class Person {
        <<abstract>>
        -String id
        -String firstName
        -String lastName
        -String email
        -String phone
        -String address
    }

    %% Clases que heredan de Person
    class Student {
        -String enrollmentDate
        -String major
        -double gpa
    }

    class Professor {
        -String departmentId
        -String specialization
        -String hireDate
        -double salary
    }

    class AdministrativeEmployee {
        -String position
        -String departmentId
        -String hireDate
        -double salary
    }

    %% Estructura universitaria
    class Faculty {
        -String id
        -String name
        -String code
        -String deanId
        -String description
    }

    class Department {
        -String id
        -String name
        -String code
        -String facultyId
        -String headProfessorId
    }

    class Course {
        -String id
        -String name
        -String code
        -int credits
        -String description
        -String departmentId
    }

    class Classroom {
        -String id
        -String name
        -String building
        -int capacity
        -String type
        -String equipment
    }

    %% Clases de relación
    class Enrollment {
        -String id
        -String studentId
        -String courseId
        -String enrollmentDate
        -String semester
        -String status
        -double grade
    }

    class ProfessorAssignment {
        -String id
        -String professorId
        -String courseId
        -String semester
        -String assignmentDate
        -String schedule
    }

    %% Relaciones de herencia
    Person <|-- Student
    Person <|-- Professor
    Person <|-- AdministrativeEmployee

    %% Relaciones de asociación
    Faculty "1" -- "*" Department : contains
    Department "1" -- "*" Course : offers
    Department "0..1" -- "1" Professor : headProfessor
    
    Professor "1" -- "*" ProfessorAssignment : teaches
    Course "1" -- "*" ProfessorAssignment : assignedTo
    
    Student "1" -- "*" Enrollment : enrolls
    Course "1" -- "*" Enrollment : enrolledIn
    
    Professor "*" -- "1" Department : worksIn
    AdministrativeEmployee "*" -- "1" Department : worksIn
    
    Faculty "0..1" -- "1" AdministrativeEmployee : dean
```

## Descripción de las Clases

### Jerarquía de Person
- **Person** (Abstracta): Clase base para todas las personas del sistema
  - **Student**: Estudiantes matriculados en la universidad
  - **Professor**: Profesores que imparten cursos
  - **AdministrativeEmployee**: Empleados administrativos

### Estructura Organizacional
- **Faculty**: Facultades de la universidad
- **Department**: Departamentos que pertenecen a facultades
- **Course**: Cursos ofrecidos por departamentos
- **Classroom**: Aulas disponibles en la universidad

### Relaciones
- **Enrollment**: Matrícula de estudiantes en cursos
- **ProfessorAssignment**: Asignación de profesores a cursos

## Relaciones Principales

1. **Herencia**: Student, Professor y AdministrativeEmployee heredan de Person
2. **Composición**: 
   - Faculty contiene Departments
   - Department ofrece Courses
3. **Asociaciones**:
   - Enrollment relaciona Student con Course
   - ProfessorAssignment relaciona Professor con Course
   - Department tiene un headProfessor
   - Faculty tiene un dean (AdministrativeEmployee)

## Cómo visualizar el diagrama

### Opción 1: PlantUML
1. Instalar la extensión PlantUML en tu IDE
2. Abrir el archivo `class-diagram.puml`
3. Usar la vista previa de PlantUML

### Opción 2: Mermaid
- Este archivo `.md` se puede visualizar en:
  - GitHub
  - GitLab
  - VS Code con extensión Mermaid
  - Cualquier visor de Markdown compatible con Mermaid

### Opción 3: Online
- PlantUML: https://www.plantuml.com/plantuml/uml/
- Mermaid: https://mermaid.live/
