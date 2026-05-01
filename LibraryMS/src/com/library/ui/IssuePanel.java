package com.library.ui;

import com.library.db.*;
import com.library.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class IssuePanel extends JPanel {

    private IssueDAO issueDAO   = new IssueDAO();
    private BookDAO bookDAO     = new BookDAO();
    private MemberDAO memberDAO = new MemberDAO();

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<Book> bookCombo;
    private JComboBox<Member> memberCombo;

    private static final String[] COLS = {"Issue ID", "Book", "Member", "Issue Date", "Return Date", "Status"};

    public IssuePanel() {
        setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        build();
        loadIssues();
    }

    private void build() {
        // Header
        add(Theme.headerLabel("📋 Issue & Return"), BorderLayout.NORTH);

        // Issue form card
        JPanel formCard = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        formCard.setBackground(Theme.BG_PANEL);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BG_CARD, 1),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));

        JLabel bookLbl   = new JLabel("Book:");
        JLabel memberLbl = new JLabel("Member:");
        bookLbl.setForeground(Theme.TEXT_MUTED);   bookLbl.setFont(Theme.FONT_BODY);
        memberLbl.setForeground(Theme.TEXT_MUTED); memberLbl.setFont(Theme.FONT_BODY);

        bookCombo   = new JComboBox<>();
        memberCombo = new JComboBox<>();
        styleCombo(bookCombo);
        styleCombo(memberCombo);

        JButton issueBtn  = Theme.primaryButton("Issue Book");
        JButton returnBtn = Theme.dangerButton("Return Book");
        JButton refreshBtn = Theme.primaryButton("↻ Refresh");

        formCard.add(bookLbl); formCard.add(bookCombo);
        formCard.add(memberLbl); formCard.add(memberCombo);
        formCard.add(issueBtn); formCard.add(returnBtn); formCard.add(refreshBtn);

        // Center: form + table
        JPanel center = new JPanel(new BorderLayout(0, 12));
        center.setBackground(Theme.BG_DARK);
        center.add(formCard, BorderLayout.NORTH);

        model = new DefaultTableModel(COLS, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        Theme.styleTable(table);
        table.getColumnModel().getColumn(0).setMaxWidth(70);

        // Color-code status column
        table.getColumnModel().getColumn(5).setCellRenderer((tbl, value, isSelected, hasFocus, row, col) -> {
            JLabel lbl = new JLabel(value == null ? "" : value.toString());
            lbl.setOpaque(true);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            if (isSelected) {
                lbl.setBackground(Theme.ACCENT);
                lbl.setForeground(Theme.BG_DARK);
            } else {
                lbl.setBackground(Theme.BG_PANEL);
                String status = value == null ? "" : value.toString();
                lbl.setForeground("ISSUED".equals(status) ? Theme.WARNING : Theme.SUCCESS);
            }
            return lbl;
        });

        center.add(Theme.styledScrollPane(table), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // Load combo data
        refreshCombos();

        // Actions
        issueBtn.addActionListener(e -> {
            Book book = (Book) bookCombo.getSelectedItem();
            Member member = (Member) memberCombo.getSelectedItem();
            if (book == null || member == null) {
                JOptionPane.showMessageDialog(this, "Select both a book and a member."); return;
            }
            if (issueDAO.issueBook(book.getId(), member.getId())) {
                JOptionPane.showMessageDialog(this, "Book issued successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadIssues(); refreshCombos();
            } else {
                JOptionPane.showMessageDialog(this, "Book not available or error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        returnBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select an issued record to return."); return; }
            String status = (String) model.getValueAt(row, 5);
            if (!"ISSUED".equals(status)) { JOptionPane.showMessageDialog(this, "This book is already returned."); return; }
            int issueId = (int) model.getValueAt(row, 0);
            if (issueDAO.returnBook(issueId)) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadIssues(); refreshCombos();
            }
        });

        refreshBtn.addActionListener(e -> { loadIssues(); refreshCombos(); });
    }

    private void refreshCombos() {
        bookCombo.removeAllItems();
        memberCombo.removeAllItems();
        for (Book b : bookDAO.getAllBooks())     bookCombo.addItem(b);
        for (Member m : memberDAO.getAllMembers()) memberCombo.addItem(m);
    }

    public void loadIssues() {
        model.setRowCount(0);
        for (Object[] row : issueDAO.getAllIssues())
            model.addRow(row);
    }

    private void styleCombo(JComboBox<?> combo) {
        combo.setBackground(Theme.BG_CARD);
        combo.setForeground(Theme.TEXT_PRIMARY);
        combo.setFont(Theme.FONT_BODY);
        combo.setPreferredSize(new Dimension(200, 34));
        ((JComponent) combo.getRenderer()).setOpaque(true);
    }
}