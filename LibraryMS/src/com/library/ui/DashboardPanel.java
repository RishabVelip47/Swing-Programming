package com.library.ui;

import com.library.db.*;
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private BookDAO bookDAO     = new BookDAO();
    private MemberDAO memberDAO = new MemberDAO();
    private IssueDAO issueDAO   = new IssueDAO();

    private JLabel totalBooksVal, totalMembersVal, issuedBooksVal;

    public DashboardPanel() {
        setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        build();
    }

    private void build() {
        // Header
        JLabel header = new JLabel("📚  Library Management System");
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(Theme.ACCENT);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(header, BorderLayout.NORTH);

        // Stats row
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 20, 0));
        statsRow.setBackground(Theme.BG_DARK);

        totalBooksVal   = addStatCard(statsRow, "📖 Total Books",   "...", Theme.ACCENT);
        totalMembersVal = addStatCard(statsRow, "👤 Members",        "...", Theme.SUCCESS);
        issuedBooksVal  = addStatCard(statsRow, "📋 Books Issued",  "...", Theme.WARNING);

        add(statsRow, BorderLayout.CENTER);

        // Footer note
        JLabel note = new JLabel("Welcome! Use the tabs above to manage books, members, and transactions.");
        note.setFont(Theme.FONT_BODY);
        note.setForeground(Theme.TEXT_MUTED);
        note.setHorizontalAlignment(SwingConstants.CENTER);
        note.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        add(note, BorderLayout.SOUTH);

        refreshStats();
    }

    private JLabel addStatCard(JPanel parent, String title, String value, Color color) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Theme.BG_CARD, 1),
            BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(Theme.FONT_HEADER);
        titleLbl.setForeground(Theme.TEXT_MUTED);
        card.add(titleLbl, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("Segoe UI", Font.BOLD, 48));
        valueLbl.setForeground(color);
        card.add(valueLbl, gbc);

        parent.add(card);
        return valueLbl;
    }

    public void refreshStats() {
        totalBooksVal.setText(String.valueOf(bookDAO.getTotalBooks()));
        totalMembersVal.setText(String.valueOf(memberDAO.getTotalMembers()));
        issuedBooksVal.setText(String.valueOf(issueDAO.getTotalIssued()));
    }
}