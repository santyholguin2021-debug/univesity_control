# University Control System API

## Overview
REST API built with Spring Boot for managing university entities including Students, Professors, Administrative Employees, Courses, Departments, Faculties, Classrooms, Enrollments, and Professor Assignments.

## Architecture
- **Model Layer**: Entities with inheritance (Person → Student, Professor, AdministrativeEmployee)
- **Repository Layer**: CSV-based persistence
- **Service Layer**: Business logic
- **Controller Layer**: REST endpoints

## Principles Applied
- **SOLID**: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion
- **KISS**: Keep It Simple, Stupid
- **DRY**: Don't Repeat Yourself
- **MVC**: Model-View-Controller pattern

## Configuration
- **Port**: 8080 (configurable in `application.properties`)
- **CSV Storage**: `./data` directory (configurable via `csv.storage.path`)

## API Endpoints

### Students
- `GET /api/students` - Get all students
- `GET /api/students/{id}` - Get student by ID
- `POST /api/students` - Create new student
- `PUT /api/students/{id}` - Update student
- `DELETE /api/students/{id}` - Delete student

**Student Model:**
```json
{
  "id": "string",
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "phone": "string",
  "address": "string",
  "enrollmentDate": "string",
  "major": "string",
  "gpa": 0.0
}
```

### Professors
- `GET /api/professors` - Get all professors
- `GET /api/professors/{id}` - Get professor by ID
- `POST /api/professors` - Create new professor
- `PUT /api/professors/{id}` - Update professor
- `DELETE /api/professors/{id}` - Delete professor

**Professor Model:**
```json
{
  "id": "string",
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "phone": "string",
  "address": "string",
  "departmentId": "string",
  "specialization": "string",
  "hireDate": "string",
  "salary": 0.0
}
```

### Administrative Employees
- `GET /api/administrative-employees` - Get all administrative employees
- `GET /api/administrative-employees/{id}` - Get employee by ID
- `POST /api/administrative-employees` - Create new employee
- `PUT /api/administrative-employees/{id}` - Update employee
- `DELETE /api/administrative-employees/{id}` - Delete employee

**Administrative Employee Model:**
```json
{
  "id": "string",
  "firstName": "string",
  "lastName": "string",
  "email": "string",
  "phone": "string",
  "address": "string",
  "position": "string",
  "departmentId": "string",
  "hireDate": "string",
  "salary": 0.0
}
```

### Courses
- `GET /api/courses` - Get all courses
- `GET /api/courses/{id}` - Get course by ID
- `POST /api/courses` - Create new course
- `PUT /api/courses/{id}` - Update course
- `DELETE /api/courses/{id}` - Delete course

**Course Model:**
```json
{
  "id": "string",
  "name": "string",
  "code": "string",
  "credits": 0,
  "description": "string",
  "departmentId": "string"
}
```

### Departments
- `GET /api/departments` - Get all departments
- `GET /api/departments/{id}` - Get department by ID
- `POST /api/departments` - Create new department
- `PUT /api/departments/{id}` - Update department
- `DELETE /api/departments/{id}` - Delete department

**Department Model:**
```json
{
  "id": "string",
  "name": "string",
  "code": "string",
  "facultyId": "string",
  "headProfessorId": "string"
}
```

### Faculties
- `GET /api/faculties` - Get all faculties
- `GET /api/faculties/{id}` - Get faculty by ID
- `POST /api/faculties` - Create new faculty
- `PUT /api/faculties/{id}` - Update faculty
- `DELETE /api/faculties/{id}` - Delete faculty

**Faculty Model:**
```json
{
  "id": "string",
  "name": "string",
  "code": "string",
  "deanId": "string",
  "description": "string"
}
```

### Classrooms
- `GET /api/classrooms` - Get all classrooms
- `GET /api/classrooms/{id}` - Get classroom by ID
- `POST /api/classrooms` - Create new classroom
- `PUT /api/classrooms/{id}` - Update classroom
- `DELETE /api/classrooms/{id}` - Delete classroom

**Classroom Model:**
```json
{
  "id": "string",
  "name": "string",
  "building": "string",
  "capacity": 0,
  "type": "string",
  "equipment": "string"
}
```

### Enrollments
- `GET /api/enrollments` - Get all enrollments
- `GET /api/enrollments/{id}` - Get enrollment by ID
- `POST /api/enrollments` - Create new enrollment
- `PUT /api/enrollments/{id}` - Update enrollment
- `DELETE /api/enrollments/{id}` - Delete enrollment

**Enrollment Model:**
```json
{
  "id": "string",
  "studentId": "string",
  "courseId": "string",
  "enrollmentDate": "string",
  "semester": "string",
  "status": "string",
  "grade": 0.0
}
```

### Professor Assignments
- `GET /api/professor-assignments` - Get all assignments
- `GET /api/professor-assignments/{id}` - Get assignment by ID
- `POST /api/professor-assignments` - Create new assignment
- `PUT /api/professor-assignments/{id}` - Update assignment
- `DELETE /api/professor-assignments/{id}` - Delete assignment

**Professor Assignment Model:**
```json
{
  "id": "string",
  "professorId": "string",
  "courseId": "string",
  "semester": "string",
  "assignmentDate": "string",
  "schedule": "string"
}
```

## Running the Application

1. **Build the project:**
   ```bash
   mvn clean install
   ```

2. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the API:**
   - Base URL: `http://localhost:8080/api`

## Data Storage
All data is persisted in CSV files located in the `./data` directory:
- `students.csv`
- `professors.csv`
- `administrative_employees.csv`
- `courses.csv`
- `departments.csv`
- `faculties.csv`
- `classrooms.csv`
- `enrollments.csv`
- `professor_assignments.csv`

The CSV files are automatically created on first run with appropriate headers.

## Testing with cURL

### Create a Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "id": "S001",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@university.edu",
    "phone": "555-0100",
    "address": "123 Campus St",
    "enrollmentDate": "2024-01-15",
    "major": "Computer Science",
    "gpa": 3.8
  }'
```

### Get All Students
```bash
curl http://localhost:8080/api/students
```

### Get Student by ID
```bash
curl http://localhost:8080/api/students/S001
```

### Update Student
```bash
curl -X PUT http://localhost:8080/api/students/S001 \
  -H "Content-Type: application/json" \
  -d '{
    "id": "S001",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@university.edu",
    "phone": "555-0100",
    "address": "123 Campus St",
    "enrollmentDate": "2024-01-15",
    "major": "Computer Science",
    "gpa": 3.9
  }'
```

### Delete Student
```bash
curl -X DELETE http://localhost:8080/api/students/S001
```
