package com.library.ui;

import com.library.db.BookDAO;
import com.library.model.Book;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BooksPanel extends JPanel {

    private BookDAO dao = new BookDAO();
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    private static final String[] COLS = {"ID", "Title", "Author", "ISBN", "Qty", "Available"};

    public BooksPanel() {
        setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        build();
        loadBooks(null);
    }

    private void build() {
        // Top bar
        JPanel topBar = new JPanel(new BorderLayout(10, 0));
        topBar.setBackground(Theme.BG_DARK);

        JLabel title = Theme.headerLabel("📖 Books");
        topBar.add(title, BorderLayout.WEST);

        JPanel rightBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightBar.setBackground(Theme.BG_DARK);
        searchField = Theme.styledField(20);
        searchField.setToolTipText("Search books...");
        JButton searchBtn = Theme.primaryButton("Search");
        JButton addBtn    = Theme.primaryButton("+ Add Book");
        JButton editBtn   = Theme.primaryButton("Edit");
        JButton deleteBtn = Theme.dangerButton("Delete");

        rightBar.add(new JLabel("🔍") {{ setForeground(Theme.TEXT_MUTED); }});
        rightBar.add(searchField);
        rightBar.add(searchBtn);
        rightBar.add(addBtn);
        rightBar.add(editBtn);
        rightBar.add(deleteBtn);
        topBar.add(rightBar, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(COLS, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        Theme.styleTable(table);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(4).setMaxWidth(50);
        table.getColumnModel().getColumn(5).setMaxWidth(80);
        add(Theme.styledScrollPane(table), BorderLayout.CENTER);

        // Actions
        searchBtn.addActionListener(e -> loadBooks(searchField.getText().trim()));
        searchField.addActionListener(e -> loadBooks(searchField.getText().trim()));

        addBtn.addActionListener(e -> {
            BookDialog dlg = new BookDialog(SwingUtilities.getWindowAncestor(this), null);
            dlg.setVisible(true);
            if (dlg.isConfirmed()) {
                if (dao.addBook(dlg.getBook()))
                    JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadBooks(null);
            }
        });

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a book to edit."); return; }
            Book selected = getSelectedBook(row);
            BookDialog dlg = new BookDialog(SwingUtilities.getWindowAncestor(this), selected);
            dlg.setVisible(true);
            if (dlg.isConfirmed()) {
                if (dao.updateBook(dlg.getBook()))
                    JOptionPane.showMessageDialog(this, "Book updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadBooks(null);
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a book to delete."); return; }
            int id = (int) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this book?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dao.deleteBook(id);
                loadBooks(null);
            }
        });
    }

    public void loadBooks(String keyword) {
        model.setRowCount(0);
        List<Book> books = (keyword == null || keyword.isEmpty()) ? dao.getAllBooks() : dao.searchBooks(keyword);
        for (Book b : books)
            model.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getQuantity(), b.getAvailable()});
    }

    private Book getSelectedBook(int row) {
        return new Book(
            (int) model.getValueAt(row, 0),
            (String) model.getValueAt(row, 1),
            (String) model.getValueAt(row, 2),
            (String) model.getValueAt(row, 3),
            (int) model.getValueAt(row, 4),
            (int) model.getValueAt(row, 5)
        );
    }
}