package com.library.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;

public class Theme {

    // Color Palette (Dark Navy Theme)
    public static final Color BG_DARK      = new Color(15, 23, 42);
    public static final Color BG_PANEL     = new Color(30, 41, 59);
    public static final Color BG_CARD      = new Color(51, 65, 85);
    public static final Color ACCENT       = new Color(99, 179, 237);
    public static final Color ACCENT_HOVER = new Color(147, 210, 255);
    public static final Color TEXT_PRIMARY = new Color(226, 232, 240);
    public static final Color TEXT_MUTED   = new Color(148, 163, 184);
    public static final Color SUCCESS      = new Color(74, 222, 128);
    public static final Color DANGER       = new Color(248, 113, 113);
    public static final Color WARNING      = new Color(251, 191, 36);

    public static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BODY   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);

    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(ACCENT);
        btn.setForeground(BG_DARK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 36));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(ACCENT_HOVER); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(ACCENT); }
        });
        return btn;
    }

    public static JButton dangerButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(DANGER);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 36));
        return btn;
    }

    public static JTextField styledField(int columns) {
        JTextField tf = new JTextField(columns);
        tf.setBackground(BG_CARD);
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(TEXT_PRIMARY);
        tf.setFont(FONT_BODY);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BG_CARD, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return tf;
    }

    public static JLabel headerLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }

    public static void styleTable(JTable table) {
        table.setBackground(BG_PANEL);
        table.setForeground(TEXT_PRIMARY);
        table.setFont(FONT_BODY);
        table.setRowHeight(32);
        table.setGridColor(BG_CARD);
        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(BG_DARK);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.getTableHeader().setBackground(BG_CARD);
        table.getTableHeader().setForeground(TEXT_PRIMARY);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.setIntercellSpacing(new Dimension(0, 0));
    }

    public static JScrollPane styledScrollPane(JTable table) {
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(BG_PANEL);
        return sp;
    }

    public static JPanel card(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BG_CARD, 1),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        if (title != null && !title.isEmpty()) {
            JLabel lbl = new JLabel(title);
            lbl.setFont(FONT_HEADER);
            lbl.setForeground(ACCENT);
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
            panel.add(lbl, BorderLayout.NORTH);
        }
        return panel;
    }
}