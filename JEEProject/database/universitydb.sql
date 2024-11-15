CREATE DATABASE IF NOT EXISTS university;
USE university;


CREATE TABLE IF NOT EXISTS Account (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       username VARCHAR(100) NOT NULL UNIQUE,
                                       password VARCHAR(255) NOT NULL,
                                       role VARCHAR(50) NOT NULL
);


CREATE TABLE IF NOT EXISTS Administrator (
                                             id INT PRIMARY KEY,
                                             last_name VARCHAR(100),
                                             first_name VARCHAR(100),
                                             birth_date DATE,
                                             position VARCHAR(100),
                                             email VARCHAR(100),
                                             account_id INT,
                                             FOREIGN KEY (account_id) REFERENCES Account(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Professor (
                                         id INT PRIMARY KEY,
                                         last_name VARCHAR(100),
                                         first_name VARCHAR(100),
                                         birth_date DATE,
                                         specialty VARCHAR(100),
                                         email VARCHAR(100),
                                         account_id INT,
                                         FOREIGN KEY (account_id) REFERENCES Account(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Student (
                                       id INT PRIMARY KEY,
                                       last_name VARCHAR(100),
                                       first_name VARCHAR(100),
                                       birth_date DATE,
                                       email VARCHAR(100),
                                       account_id INT,
                                       FOREIGN KEY (account_id) REFERENCES Account(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Course (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      title VARCHAR(255) NOT NULL,
                                      description TEXT,
                                      credit DOUBLE
);

create table if not exists Registration (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            registration_date DATE,
                                            student_id INT,
                                            course_id INT,
                                            professor_id INT,
                                            FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE,
                                            FOREIGN KEY (course_id) REFERENCES Course(id) ON DELETE CASCADE,
                                            FOREIGN KEY (professor_id) REFERENCES Professor(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Result (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      grade DOUBLE NOT NULL,
                                      coefficient DOUBLE NOT NULL,
                                      registration_id INT,
                                      FOREIGN KEY (registration_id) REFERENCES Registration(id) ON DELETE CASCADE
);