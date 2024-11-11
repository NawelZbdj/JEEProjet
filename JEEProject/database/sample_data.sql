USE university;
-- Insert Users
INSERT INTO Users (username, password, role) VALUES ('admin', 'adminpassword', 'admin');
INSERT INTO Users (username, password, role) VALUES ('professor1', 'professorpassword', 'professor');
INSERT INTO Users (username, password, role) VALUES ('student1', 'studentpassword', 'student');

-- Insert Accounts
INSERT INTO Account (username, password, role) VALUES ('admin', 'adminpassword', 'admin');
INSERT INTO Account (username, password, role) VALUES ('professor1', 'professorpassword', 'professor');
INSERT INTO Account (username, password, role) VALUES ('student1', 'studentpassword', 'student');

-- Insert Administrators (assuming they are in the Account table)
INSERT INTO Administrator (id, last_name, first_name, birth_date, position, email) VALUES (1, 'Smith', 'John', '1980-01-01', 'Administrator', 'john.smith@example.com');

-- Insert Professors
INSERT INTO Professor (id, last_name, first_name, birth_date, specialty, email) VALUES (2, 'Brown', 'Alice', '1975-05-12', 'Computer Science', 'alice.brown@example.com');

-- Insert Students
INSERT INTO Student (id, last_name, first_name, birth_date, email) VALUES (3, 'Taylor', 'Chris', '1999-09-25', 'chris.taylor@example.com');

-- Insert Courses
INSERT INTO Course (title, description, credit) VALUES ('Introduction to Java', 'Basic Java programming course', 3.0);
INSERT INTO Course (title, description, credit) VALUES ('Data Structures', 'Learn about data structures', 3.0);

-- Insert Results
INSERT INTO Result (grade, coefficient, student_id, course_id) VALUES (85.5, 1.0, 3, 1);
