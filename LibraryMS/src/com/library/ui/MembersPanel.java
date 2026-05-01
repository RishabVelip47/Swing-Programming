package com.library.ui;

import com.library.db.MemberDAO;
import com.library.model.Member;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MembersPanel extends JPanel {

    private MemberDAO dao = new MemberDAO();
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;

    private static final String[] COLS = {"ID", "Name", "Email", "Phone", "Joined"};

    public MembersPanel() {
        setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        build();
        loadMembers(null);
    }

    private void build() {
        JPanel topBar = new JPanel(new BorderLayout(10, 0));
        topBar.setBackground(Theme.BG_DARK);
        topBar.add(Theme.headerLabel("👤 Members"), BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setBackground(Theme.BG_DARK);
        searchField = Theme.styledField(18);
        JButton searchBtn = Theme.primaryButton("Search");
        JButton addBtn    = Theme.primaryButton("+ Add");
        JButton editBtn   = Theme.primaryButton("Edit");
        JButton deleteBtn = Theme.dangerButton("Delete");

        right.add(searchField); right.add(searchBtn);
        right.add(addBtn); right.add(editBtn); right.add(deleteBtn);
        topBar.add(right, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        model = new DefaultTableModel(COLS, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        Theme.styleTable(table);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        add(Theme.styledScrollPane(table), BorderLayout.CENTER);

        searchBtn.addActionListener(e -> loadMembers(searchField.getText().trim()));
        searchField.addActionListener(e -> loadMembers(searchField.getText().trim()));

        addBtn.addActionListener(e -> {
            MemberDialog dlg = new MemberDialog(SwingUtilities.getWindowAncestor(this), null);
            dlg.setVisible(true);
            if (dlg.isConfirmed()) { dao.addMember(dlg.getMember()); loadMembers(null); }
        });

        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a member to edit."); return; }
            MemberDialog dlg = new MemberDialog(SwingUtilities.getWindowAncestor(this), getSelectedMember(row));
            dlg.setVisible(true);
            if (dlg.isConfirmed()) { dao.updateMember(dlg.getMember()); loadMembers(null); }
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a member to delete."); return; }
            int id = (int) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this member?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) { dao.deleteMember(id); loadMembers(null); }
        });
    }

    public void loadMembers(String keyword) {
        model.setRowCount(0);
        List<Member> members = (keyword == null || keyword.isEmpty()) ? dao.getAllMembers() : dao.searchMembers(keyword);
        for (Member m : members)
            model.addRow(new Object[]{m.getId(), m.getName(), m.getEmail(), m.getPhone(), "—"});
    }

    private Member getSelectedMember(int row) {
        return new Member(
            (int) model.getValueAt(row, 0),
            (String) model.getValueAt(row, 1),
            (String) model.getValueAt(row, 2),
            (String) model.getValueAt(row, 3)
        );
    }
}