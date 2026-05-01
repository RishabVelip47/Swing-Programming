package com.library.db;

import java.sql.*;
import java.util.*;

public class IssueDAO {

    // Returns list of rows: {issue_id, book_title, member_name, issue_date, return_date, status}
    public List<Object[]> getAllIssues() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT i.id, b.title, m.name, i.issue_date, i.return_date, i.status " +
                     "FROM issued_books i " +
                     "JOIN books b ON i.book_id = b.id " +
                     "JOIN members m ON i.member_id = m.id " +
                     "ORDER BY i.issue_date DESC";
        try (Statement st = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("id"), rs.getString("title"), rs.getString("name"),
                    rs.getString("issue_date"), rs.getString("return_date"), rs.getString("status")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean issueBook(int bookId, int memberId) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            // Check availability
            PreparedStatement check = conn.prepareStatement("SELECT available FROM books WHERE id=?");
            check.setInt(1, bookId);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt("available") < 1) {
                conn.setAutoCommit(true);
                return false; // Not available
            }

            // Insert issue record
            PreparedStatement ins = conn.prepareStatement(
                "INSERT INTO issued_books (book_id, member_id, issue_date, status) VALUES (?, ?, CURDATE(), 'ISSUED')");
            ins.setInt(1, bookId); ins.setInt(2, memberId);
            ins.executeUpdate();

            // Decrease available count
            PreparedStatement upd = conn.prepareStatement(
                "UPDATE books SET available = available - 1 WHERE id=?");
            upd.setInt(1, bookId);
            upd.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); conn.setAutoCommit(true); } catch (SQLException ignored) {}
            e.printStackTrace(); return false;
        }
    }

    public boolean returnBook(int issueId) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            // Get book_id
            PreparedStatement get = conn.prepareStatement(
                "SELECT book_id FROM issued_books WHERE id=? AND status='ISSUED'");
            get.setInt(1, issueId);
            ResultSet rs = get.executeQuery();
            if (!rs.next()) { conn.setAutoCommit(true); return false; }
            int bookId = rs.getInt("book_id");

            // Update issue record
            PreparedStatement upd1 = conn.prepareStatement(
                "UPDATE issued_books SET status='RETURNED', return_date=CURDATE() WHERE id=?");
            upd1.setInt(1, issueId);
            upd1.executeUpdate();

            // Increase available count
            PreparedStatement upd2 = conn.prepareStatement(
                "UPDATE books SET available = available + 1 WHERE id=?");
            upd2.setInt(1, bookId);
            upd2.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); conn.setAutoCommit(true); } catch (SQLException ignored) {}
            e.printStackTrace(); return false;
        }
    }

    public int getTotalIssued() {
        try (Statement st = DatabaseConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM issued_books WHERE status='ISSUED'")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}