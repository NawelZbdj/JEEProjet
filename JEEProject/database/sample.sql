use university;

INSERT INTO Account (username, password, role) VALUES
('admin1', 'password123', 'administrator'),
('prof1', 'password123', 'professor'),
('prof2', 'password123', 'professor'),
('student1', 'password123', 'student'),
('student2', 'password123', 'student');

-- Insérer un administrateur
INSERT INTO Administrator (id, last_name, first_name, birth_date, position, email, account_id) VALUES
(1, 'Smith', 'John', '1980-05-15', 'Director', 'john.smith@university.edu', 1);

-- Insérer des professeurs
INSERT INTO Professor (id, last_name, first_name, birth_date, specialty, email, account_id) VALUES
(1, 'Doe', 'Jane', '1975-04-22', 'Mathematics', 'jane.doe@university.edu', 2),
(2, 'Brown', 'James', '1982-03-12', 'Physics', 'james.brown@university.edu', 3);

-- Insérer des étudiants
INSERT INTO Student (id, last_name, first_name, birth_date, email, account_id) VALUES
(1, 'Taylor', 'Emily', '2003-09-08', 'emily.taylor@student.university.edu', 4),
(2, 'Johnson', 'Michael', '2002-07-19', 'michael.johnson@student.university.edu', 5);

-- Insérer des cours
INSERT INTO Course (title, description, credit, speciality) VALUES
('Mathematics 101', 'Introduction to calculus and linear algebra.', 3.0, 'Mathematics'),
('Physics 101', 'Basics of classical mechanics and thermodynamics.', 4.0, 'Physics'),
('Data Structures', 'Study of algorithms and data organization.', 3.5, 'Computer Science');

-- Insérer des inscriptions
INSERT INTO Registration (registration_date, student_id, course_id, professor_id) VALUES
('2024-11-01', 1, 1, NULL),
('2024-11-01', 2, 2, NULL);

-- Insérer des résultats
INSERT INTO Result (grade, coefficient, registration_id) VALUES
(85.0, 1.0, 1),
(90.0, 1.0, 2);