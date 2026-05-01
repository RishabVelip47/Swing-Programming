package com.library.ui;

import com.library.model.Member;
import javax.swing.*;
import java.awt.*;

public class MemberDialog extends JDialog {

    private JTextField nameField, emailField, phoneField;
    private boolean confirmed = false;
    private Member member;

    public MemberDialog(Window parent, Member existing) {
        super(parent, existing == null ? "Add Member" : "Edit Member", ModalityType.APPLICATION_MODAL);
        this.member = existing != null ? existing : new Member();
        buildUI(existing);
        pack();
        setLocationRelativeTo(parent);
    }

    private void buildUI(Member existing) {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(Theme.BG_PANEL);
        main.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel header = new JLabel(existing == null ? "Add New Member" : "Edit Member");
        header.setFont(Theme.FONT_TITLE);
        header.setForeground(Theme.SUCCESS);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        main.add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 12));
        form.setBackground(Theme.BG_PANEL);

        nameField  = Theme.styledField(20);
        emailField = Theme.styledField(20);
        phoneField = Theme.styledField(15);

        if (existing != null) {
            nameField.setText(existing.getName());
            emailField.setText(existing.getEmail());
            phoneField.setText(existing.getPhone());
        }

        form.add(label("Name:")); form.add(nameField);
        form.add(label("Email:")); form.add(emailField);
        form.add(label("Phone:")); form.add(phoneField);
        main.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(Theme.BG_PANEL);
        JButton save   = Theme.primaryButton("Save");
        JButton cancel = Theme.dangerButton("Cancel");
        btnPanel.add(cancel); btnPanel.add(save);
        main.add(btnPanel, BorderLayout.SOUTH);

        save.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            member.setName(nameField.getText().trim());
            member.setEmail(emailField.getText().trim());
            member.setPhone(phoneField.getText().trim());
            confirmed = true;
            dispose();
        });

        cancel.addActionListener(e -> dispose());
        setContentPane(main);
    }

    private JLabel label(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(Theme.FONT_BODY);
        lbl.setForeground(Theme.TEXT_MUTED);
        return lbl;
    }

    public boolean isConfirmed() { return confirmed; }
    public Member getMember()    { return member; }
}