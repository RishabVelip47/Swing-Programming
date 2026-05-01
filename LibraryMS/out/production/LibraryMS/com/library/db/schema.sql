-- Run this in MySQL to set up the database

CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    quantity INT DEFAULT 1,
    available INT DEFAULT 1,
    added_date DATE DEFAULT (CURRENT_DATE)
);

CREATE TABLE IF NOT EXISTS members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    joined_date DATE DEFAULT (CURRENT_DATE)
);

CREATE TABLE IF NOT EXISTS issued_books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    issue_date DATE DEFAULT (CURRENT_DATE),
    return_date DATE,
    status ENUM('ISSUED', 'RETURNED') DEFAULT 'ISSUED',
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (member_id) REFERENCES members(id)
);

-- Sample data
INSERT INTO books (title, author, isbn, quantity, available) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', 3, 3),
('To Kill a Mockingbird', 'Harper Lee', '978-0061935466', 2, 2),
('1984', 'George Orwell', '978-0451524935', 4, 4);

INSERT INTO members (name, email, phone) VALUES
('Alice Johnson', 'alice@email.com', '9876543210'),
('Bob Smith', 'bob@email.com', '9123456780');