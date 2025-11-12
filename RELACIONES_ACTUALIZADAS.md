# Relaciones Entre Clases - Actualización Completada

## Resumen de Cambios

Se han actualizado todas las clases del modelo para implementar correctamente las relaciones bidireccionales según el diagrama de clases, manteniendo la compatibilidad con la persistencia CSV.

## Estrategia Implementada

### Dual-Field Pattern (IDs + Referencias a Objetos)

Para cada relación, se mantienen **dos campos**:
1. **Campo ID (String)**: Para persistencia en CSV
2. **Campo de Objeto**: Para navegación de relaciones en memoria (marcado con `@JsonIgnore`)

Esto permite:
- ✅ Persistir datos en CSV usando solo IDs
- ✅ Navegar relaciones entre objetos en memoria
- ✅ Evitar referencias circulares en serialización JSON

## Clases Actualizadas

### 1. **Faculty** (Facultad)
- **Campo ID**: `deanId` (String)
- **Relaciones**:
  - `List<Department> departments` - Lista de departamentos que pertenecen a la facultad

### 2. **Department** (Departamento)
- **Campos ID**: 
  - `facultyId` (String)
  - `headProfessorId` (String)
- **Relaciones**:
  - `Faculty faculty` - Facultad a la que pertenece
  - `List<Course> courses` - Cursos ofrecidos por el departamento
  - `List<Professor> professors` - Profesores del departamento
  - `List<AdministrativeEmployee> employees` - Empleados administrativos del departamento

### 3. **Course** (Curso)
- **Campo ID**: `departmentId` (String)
- **Relaciones**:
  - `Department department` - Departamento que ofrece el curso
  - `List<Enrollment> enrollments` - Matrículas de estudiantes
  - `List<ProfessorAssignment> professorAssignments` - Asignaciones de profesores

### 4. **Professor** (Profesor)
- **Campo ID**: `departmentId` (String)
- **Relaciones**:
  - `Department department` - Departamento donde trabaja
  - `List<ProfessorAssignment> assignments` - Cursos asignados

### 5. **AdministrativeEmployee** (Empleado Administrativo)
- **Campo ID**: `departmentId` (String)
- **Relaciones**:
  - `Department department` - Departamento donde trabaja

### 6. **Student** (Estudiante)
- **Relaciones**:
  - `List<Enrollment> enrollments` - Matrículas del estudiante

### 7. **Enrollment** (Matrícula)
- **Campos ID**:
  - `studentId` (String)
  - `courseId` (String)
- **Relaciones**:
  - `Student student` - Estudiante matriculado
  - `Course course` - Curso matriculado

### 8. **ProfessorAssignment** (Asignación de Profesor)
- **Campos ID**:
  - `professorId` (String)
  - `courseId` (String)
- **Relaciones**:
  - `Professor professor` - Profesor asignado
  - `Course course` - Curso asignado

## Anotaciones Utilizadas

### `@JsonBackReference`
- Marca el lado "hijo" de una relación bidireccional
- Previene ciclos infinitos en serialización JSON
- **Ejemplo**: `Department.faculty` apunta a `Faculty`

### `@JsonManagedReference`
- Marca el lado "padre" de una relación bidireccional
- Se serializa normalmente en JSON
- **Ejemplo**: `Faculty.departments` lista de `Department`

### `@JsonIgnore`
- Evita que el campo se serialice/deserialice en JSON/CSV
- Usado en todos los campos de relaciones de objetos
- Los campos ID no tienen esta anotación y se serializan normalmente

## Tipos de Datos

### Tipos Primitivos (no Wrappers)
- `int credits` en Course
- `double gpa` en Student
- `double salary` en Professor y AdministrativeEmployee
- `double grade` en Enrollment

**Ventaja**: No admite valores null, siempre tiene valor por defecto (0.0 o 0)

## Compatibilidad con CSV

Los repositorios CSV funcionan correctamente porque:
1. Los campos ID son String y se persisten/cargan normalmente
2. Los campos de objetos (`@JsonIgnore`) no se incluyen en serialización CSV
3. Los constructores usan solo campos ID, no objetos

## Diagrama de Relaciones

```
Faculty
  ├─> departments: List<Department>
  └─> deanId: String

Department
  ├─> faculty: Faculty
  ├─> courses: List<Course>
  ├─> professors: List<Professor>
  ├─> employees: List<AdministrativeEmployee>
  ├─> facultyId: String
  └─> headProfessorId: String

Course
  ├─> department: Department
  ├─> enrollments: List<Enrollment>
  ├─> professorAssignments: List<ProfessorAssignment>
  └─> departmentId: String

Professor
  ├─> department: Department
  ├─> assignments: List<ProfessorAssignment>
  └─> departmentId: String

Student
  └─> enrollments: List<Enrollment>

Enrollment
  ├─> student: Student
  ├─> course: Course
  ├─> studentId: String
  └─> courseId: String

ProfessorAssignment
  ├─> professor: Professor
  ├─> course: Course
  ├─> professorId: String
  └─> courseId: String
```

## Ejemplo de Uso

```java
// Cargar desde CSV (usa IDs)
Department dept = departmentRepository.findById("DEPT001").get();
String facultyId = dept.getFacultyId(); // "FAC001"

// Establecer relaciones en memoria
Faculty faculty = facultyRepository.findById(facultyId).get();
dept.setFaculty(faculty);
faculty.getDepartments().add(dept);

// Navegar relaciones
List<Course> courses = dept.getCourses();
List<Professor> professors = dept.getProfessors();
```

## Beneficios de esta Implementación

1. ✅ **Compatibilidad CSV**: Los repositorios existentes funcionan sin cambios
2. ✅ **Navegación de relaciones**: Se puede navegar entre objetos relacionados
3. ✅ **Sin ciclos JSON**: Las anotaciones previenen referencias circulares
4. ✅ **Flexible**: Se puede usar IDs o objetos según la necesidad
5. ✅ **Tipos primitivos**: Mayor eficiencia y claridad de datos no nulos

## Próximos Pasos Sugeridos

1. Crear métodos helper en los servicios para establecer relaciones automáticamente
2. Implementar DTOs para respuestas API que incluyan información relacionada
3. Agregar validaciones de integridad referencial
4. Considerar migrar a una base de datos relacional (JPA) para aprovechar al máximo las relaciones
