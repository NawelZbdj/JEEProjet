USE university;

INSERT INTO Account (username, password, role)
VALUES 
    ('nawzeb501', 'test', 'admin'),
    ('johmak142', 'test', 'admin'),
    ('martes520', 'securepass2', 'professor'),
    ('teslec497', 'password123', 'professor'),
    ('martes522', 'password123', 'professor'),
    ('johmak173', 'password123', 'professor'),
    ('glodel152', 'password123', 'professor'),
    ('manpri963', 'password123', 'professor'),
    ('phidun375', 'password123', 'professor'),
    ('camtuk945', 'password123', 'professor'),
    ('jakper019', 'password123', 'professor'),
    ('amebla663', 'test', 'professor'),
    ('sopder606', 'test', 'student'),
    ('sarpau761', 'password123', 'student'),
    ('markof013', 'password123', 'student'),
    ('rosdia419', 'password123', 'student'),
    ('chrplun740', 'password123', 'student'),
    ('petbae098', 'password123', 'student'),
    ('sansta387', 'password123', 'student'),
    ('jimlan746', 'password123', 'student'),
    ('edwyor201', 'password123', 'student'),
    ('terjef107', 'password123', 'student'),
    ('dantar974', 'password123', 'student'),
    ('nicmil159', 'password123', 'student');

INSERT INTO Administrator (last_name, first_name, birth_date, position, email, account_id)
VALUES 
    ('Zeboudj', 'Nawel', '2003-01-01', 'Manager', 'zeboudj.nawel@gmail.com', 
        (SELECT id FROM Account WHERE username = 'nawzeb501')),
    ('Markus', 'John', '1990-03-01', 'Manager', 'john@uni.com', 
        (SELECT id FROM Account WHERE username = 'johmak142'));

INSERT INTO Professor (last_name, first_name, birth_date, specialty, email, account_id)
VALUES 
    ('Martes', 'Sarah', '1985-06-01', 'Computer Science', 'sarah.martes@uni.com',
        (SELECT id FROM Account WHERE username = 'martes520')),
    ('Tesla', 'Eleanor', '1979-08-15', 'Physics', 'eleanor.tesla@uni.com',
        (SELECT id FROM Account WHERE username = 'teslec497')),
    ('Johannes', 'Mark', '1982-12-10', 'Mathematics', 'mark.johannes@uni.com',
        (SELECT id FROM Account WHERE username = 'johmak142')),
    ('Glover', 'Delilah', '1991-04-25', 'Art', 'delilah.glover@uni.com',
        (SELECT id FROM Account WHERE username = 'glodel152')),
    ('Manning', 'Priya', '1987-09-30', 'Chemistry', 'priya.manning@uni.com',
        (SELECT id FROM Account WHERE username = 'manpri963')),
    ('Phillips', 'Duncan', '1980-03-11', 'History', 'duncan.phillips@uni.com',
        (SELECT id FROM Account WHERE username = 'phidun375')),
    ('Cameron', 'Tucker', '1976-11-20', 'English', 'tucker.cameron@uni.com',
        (SELECT id FROM Account WHERE username = 'camtuk945')),
    ('Jackson', 'Perry', '1983-05-17', 'Computer Science', 'perry.jackson@uni.com',
        (SELECT id FROM Account WHERE username = 'jakper019')),
    ('Blabe', 'Amel', '1995-07-13', 'Physics', 'zeboudj.nawel@gmail.com',
        (SELECT id FROM Account WHERE username = 'amebla663'));


INSERT INTO Student (first_name,last_name, birth_date, email, account_id)
VALUES 
    ('Sophie', 'Deréan', '2001-09-15', 'zeboudj.nawel@gmail.com',
        (SELECT id FROM Account WHERE username = 'sopder606')),
    ('Sarah', 'Paul', '2002-04-21', 'sarah.paul@uni.com',
        (SELECT id FROM Account WHERE username = 'sarpau761')),
    ('Mark', 'Offerman', '1999-03-30', 'mark.offerman@uni.com',
        (SELECT id FROM Account WHERE username = 'markof013')),
    ('Rose', 'Diaz', '2000-12-17', 'rose.diaz@uni.com',
        (SELECT id FROM Account WHERE username = 'rosdia419')),
    ('Chris', 'Plunkett', '2002-07-09', 'chris.plunkett@uni.com',
        (SELECT id FROM Account WHERE username = 'chrplun740')),
    ('Peter', 'Bae', '2001-10-02', 'peter.bae@uni.com',
        (SELECT id FROM Account WHERE username = 'petbae098')),
    ('Sandra', 'Stark', '2003-01-15', 'sandra.stark@uni.com',
        (SELECT id FROM Account WHERE username = 'sansta387')),
    ('Jim', 'Lang', '2002-11-23', 'jim.lang@uni.com',
        (SELECT id FROM Account WHERE username = 'jimlan746')),
    ('Edward', 'Wyatt', '2000-06-18', 'edward.wyatt@uni.com',
        (SELECT id FROM Account WHERE username = 'edwyor201')),
    ('Terry', 'Jefferson', '2001-03-25', 'terry.jefferson@uni.com',
        (SELECT id FROM Account WHERE username = 'terjef107')),
    ('Daniel', 'Tarrant', '2002-08-05', 'daniel.tarrant@uni.com',
        (SELECT id FROM Account WHERE username = 'dantar974')),
    ('Nicole', 'Miller', '2001-02-13', 'nicole.miller@uni.com',
        (SELECT id FROM Account WHERE username = 'nicmil159'));


INSERT INTO Course (title, description, credit, speciality)
VALUES 
    ('Introduction to Computer Science', 'Basics of programming and computer systems.', 3.5, 'Computer Science'),
    ('Data Structures', 'Study of data organization and manipulation.', 4.0, 'Computer Science'),
    ('Algorithms', 'Techniques for designing and analyzing algorithms.', 3.5, 'Computer Science'),
    ('Operating Systems', 'Introduction to OS concepts and structures.', 4.0, 'Computer Science'),    
    ('History of Art', 'Study of significant art movements and figures.', 2.0, 'Art'),
    ('Modern Art Techniques', 'Exploration of 20th and 21st-century art methods.', 3.0, 'Art'),
    ('Renaissance Art', 'Focus on art from the Renaissance period.', 3.0, 'Art'),    
    ('Quantum Physics', 'Advanced topics in quantum mechanics.', 4.0, 'Physics'),
    ('Classical Mechanics', 'Newtonian mechanics and its applications.', 3.5, 'Physics'),
    ('Electromagnetism', 'Study of electric and magnetic fields.', 3.5, 'Physics'),    
    ('Linear Algebra', 'Matrix theory and linear transformations.', 3.0, 'Mathematics'),
    ('Calculus I', 'Differential calculus and its applications.', 4.0, 'Mathematics'),
    ('Statistics', 'Introduction to probability and statistical methods.', 3.5, 'Mathematics'),
    ('Organic Chemistry', 'Introduction to organic compounds and reactions.', 3.0, 'Chemistry'),
    ('Inorganic Chemistry', 'Study of inorganic compounds and their properties.', 3.5, 'Chemistry'),
    ('Physical Chemistry', 'Exploration of thermodynamics and kinetics.', 4.0, 'Chemistry'),
    ('World History', 'Overview of significant historical events.', 2.0, 'History'),
    ('Medieval History', 'Focus on the Middle Ages and its impact.', 3.0, 'History'),
    ('Modern History', 'Study of the modern era and its revolutions.', 3.5, 'History'),
    ('English Literature', 'Analysis of classic and modern English texts.', 2.0, 'English'),
    ('Creative Writing', 'Techniques for writing fiction and poetry.', 3.0, 'English'),
    ('Shakespearean Studies', 'Detailed study of Shakespeare’s works.', 3.0, 'English');

INSERT INTO Registration (registration_date, student_id, course_id, professor_id)
VALUES
    ('2024-01-01', (SELECT id FROM Student WHERE last_name = 'Deréan'), (SELECT id FROM Course WHERE title = 'Introduction to Computer Science'),NULL),
    ('2024-01-01', (SELECT id FROM Student WHERE last_name = 'Deréan'), (SELECT id FROM Course WHERE title = 'Data Structures'), NULL),
    ('2024-01-02', (SELECT id FROM Student WHERE last_name = 'Paul'), (SELECT id FROM Course WHERE title = 'Physical Chemistry'), NULL),
    ('2024-01-03', (SELECT id FROM Student WHERE last_name = 'Paul'), (SELECT id FROM Course WHERE title = 'Modern Art Techniques'), NULL),
    ('2024-01-03', (SELECT id FROM Student WHERE last_name = 'Offerman'), (SELECT id FROM Course WHERE title = 'Quantum Physics'), NULL),
    ('2024-01-05', (SELECT id FROM Student WHERE last_name = 'Offerman'), (SELECT id FROM Course WHERE title = 'Classical Mechanics'), NULL),
    ('2024-01-06', (SELECT id FROM Student WHERE last_name = 'Diaz'), (SELECT id FROM Course WHERE title = 'Linear Algebra'), NULL),
    ('2024-01-07', (SELECT id FROM Student WHERE last_name = 'Diaz'), (SELECT id FROM Course WHERE title = 'Calculus I'), NULL),
    ('2024-01-08', (SELECT id FROM Student WHERE last_name = 'Plunkett'), (SELECT id FROM Course WHERE title = 'Organic Chemistry'), NULL),
    ('2024-01-09', (SELECT id FROM Student WHERE last_name = 'Plunkett'), (SELECT id FROM Course WHERE title = 'Physical Chemistry'), NULL),
    ('2024-01-10', (SELECT id FROM Student WHERE last_name = 'Bae'), (SELECT id FROM Course WHERE title = 'World History'), NULL),
    ('2024-01-11', (SELECT id FROM Student WHERE last_name = 'Bae'), (SELECT id FROM Course WHERE title = 'Physical Chemistry'), NULL),
    ('2024-01-12', (SELECT id FROM Student WHERE last_name = 'Stark'), (SELECT id FROM Course WHERE title = 'English Literature'), NULL),
    ('2024-01-12', (SELECT id FROM Student WHERE last_name = 'Stark'), (SELECT id FROM Course WHERE title = 'Physical Chemistry'), NULL),
    ('2024-01-15', (SELECT id FROM Student WHERE last_name = 'Lang'), (SELECT id FROM Course WHERE title = 'Operating Systems'), NULL),
    ('2024-01-13', (SELECT id FROM Student WHERE last_name = 'Stark'), (SELECT id FROM Course WHERE title = 'Creative Writing'), NULL),
    ('2024-01-14', (SELECT id FROM Student WHERE last_name = 'Lang'), (SELECT id FROM Course WHERE title = 'Algorithms'), NULL),
    ('2024-01-16', (SELECT id FROM Student WHERE last_name = 'Wyatt'), (SELECT id FROM Course WHERE title = 'Modern History'), NULL),
    ('2024-01-17', (SELECT id FROM Student WHERE last_name = 'Wyatt'), (SELECT id FROM Course WHERE title = 'Shakespearean Studies'), NULL),
    ('2024-01-18', (SELECT id FROM Student WHERE last_name = 'Jefferson'), (SELECT id FROM Course WHERE title = 'Introduction to Computer Science'),NULL),
    ('2024-01-18', (SELECT id FROM Student WHERE last_name = 'Jefferson'), (SELECT id FROM Course WHERE title = 'Quantum Physics'), (SELECT id FROM Professor WHERE first_name = 'Amel')),
    ('2024-01-19', (SELECT id FROM Student WHERE last_name = 'Jefferson'), (SELECT id FROM Course WHERE title = 'Calculus I'), NULL);


INSERT INTO Result (grade, coefficient, registration_id)
VALUES 
    (85.0, 1.2, 
        (SELECT id FROM Registration WHERE student_id = (SELECT id FROM Student WHERE last_name = 'Jefferson') 
            AND course_id = (SELECT id FROM Course WHERE title = 'Quantum Physics')));
