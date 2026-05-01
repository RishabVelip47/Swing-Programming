package com.library.ui;

import com.library.model.Book;
import javax.swing.*;
import java.awt.*;

public class BookDialog extends JDialog {

    private JTextField titleField, authorField, isbnField, qtyField;
    private boolean confirmed = false;
    private Book book;

    public BookDialog(Window parent, Book existing) {
        super(parent, existing == null ? "Add Book" : "Edit Book", ModalityType.APPLICATION_MODAL);
        this.book = existing != null ? existing : new Book();
        buildUI(existing);
        pack();
        setLocationRelativeTo(parent);
    }

    private void buildUI(Book existing) {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(Theme.BG_PANEL);
        main.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel header = new JLabel(existing == null ? "Add New Book" : "Edit Book");
        header.setFont(Theme.FONT_TITLE);
        header.setForeground(Theme.ACCENT);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        main.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 12));
        form.setBackground(Theme.BG_PANEL);

        titleField  = Theme.styledField(20);
        authorField = Theme.styledField(20);
        isbnField   = Theme.styledField(20);
        qtyField    = Theme.styledField(5);

        if (existing != null) {
            titleField.setText(existing.getTitle());
            authorField.setText(existing.getAuthor());
            isbnField.setText(existing.getIsbn());
            qtyField.setText(String.valueOf(existing.getQuantity()));
        }

        form.add(styledLabel("Title:")); form.add(titleField);
        form.add(styledLabel("Author:")); form.add(authorField);
        form.add(styledLabel("ISBN:")); form.add(isbnField);
        form.add(styledLabel("Quantity:")); form.add(qtyField);
        main.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(Theme.BG_PANEL);
        JButton saveBtn   = Theme.primaryButton("Save");
        JButton cancelBtn = Theme.dangerButton("Cancel");
        btnPanel.add(cancelBtn);
        btnPanel.add(saveBtn);
        main.add(btnPanel, BorderLayout.SOUTH);

        saveBtn.addActionListener(e -> {
            if (titleField.getText().trim().isEmpty() || authorField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and Author are required.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int qty = Integer.parseInt(qtyField.getText().trim());
                book.setTitle(titleField.getText().trim());
                book.setAuthor(authorField.getText().trim());
                book.setIsbn(isbnField.getText().trim());
                book.setQuantity(qty);
                book.setAvailable(existing == null ? qty : existing.getAvailable());
                confirmed = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be a number.", "Validation", JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dispose());
        setContentPane(main);
    }

    private JLabel styledLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(Theme.FONT_BODY);
        lbl.setForeground(Theme.TEXT_MUTED);
        return lbl;
    }

    public boolean isConfirmed() { return confirmed; }
    public Book getBook()        { return book; }
}