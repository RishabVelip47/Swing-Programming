package com.library.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private DashboardPanel dashboardPanel;
    private BooksPanel booksPanel;
    private MembersPanel membersPanel;
    private IssuePanel issuePanel;

    public MainFrame() {
        setTitle("Library Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 680));
        setLocationRelativeTo(null);
        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        getContentPane().setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout());

        // Sidebar navigation
        JPanel sidebar = buildSidebar();
        add(sidebar, BorderLayout.WEST);

        // Content area (CardLayout)
        JPanel content = new JPanel(new CardLayout());
        content.setBackground(Theme.BG_DARK);

        dashboardPanel = new DashboardPanel();
        booksPanel     = new BooksPanel();
        membersPanel   = new MembersPanel();
        issuePanel     = new IssuePanel();

        content.add(dashboardPanel, "dashboard");
        content.add(booksPanel,     "books");
        content.add(membersPanel,   "members");
        content.add(issuePanel,     "issue");
        add(content, BorderLayout.CENTER);

        // Sidebar buttons switch cards
        String[] names = {"dashboard", "books", "members", "issue"};
        String[] labels = {"🏠  Dashboard", "📖  Books", "👤  Members", "📋  Issue / Return"};
        CardLayout cl = (CardLayout) content.getLayout();

        JButton[] navBtns = new JButton[4];
        for (int i = 0; i < 4; i++) {
            final String name = names[i];
            JButton btn = navButton(labels[i]);
            navBtns[i] = btn;
            final int idx = i;
            btn.addActionListener(e -> {
                cl.show(content, name);
                // Refresh stats on dashboard when switching back
                if ("dashboard".equals(name)) dashboardPanel.refreshStats();
                for (JButton b : navBtns) b.setBackground(Theme.BG_PANEL);
                navBtns[idx].setBackground(Theme.BG_CARD);
            });
            sidebar.add(btn);
        }

        // Highlight first button by default
        navBtns[0].setBackground(Theme.BG_CARD);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Theme.BG_PANEL);
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JLabel logo = new JLabel("📚 LibraryMS");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setForeground(Theme.ACCENT);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        sidebar.add(logo);
        return sidebar;
    }

    private JButton navButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(Theme.TEXT_PRIMARY);
        btn.setBackground(Theme.BG_PANEL);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setForeground(Theme.ACCENT); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setForeground(Theme.TEXT_PRIMARY); }
        });
        return btn;
    }
}