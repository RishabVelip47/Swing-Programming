package com.library;

import com.library.ui.MainFrame;
import com.library.ui.Theme;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Override UI defaults for dark theme globally
        UIManager.put("OptionPane.background",        Theme.BG_PANEL);
        UIManager.put("Panel.background",             Theme.BG_PANEL);
        UIManager.put("OptionPane.messageForeground", Theme.TEXT_PRIMARY);
        UIManager.put("Button.background",            Theme.ACCENT);
        UIManager.put("Button.foreground",            Theme.BG_DARK);

        SwingUtilities.invokeLater(MainFrame::new);
    }
}