package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame implements Runnable {
    private JMenuBar menuGlowne;
    private JMenu plik;
    private JMenuItem miWyloguj, miZamknij;

    private JButton potwierdzenie;
    private JTextField numerKartyPole;
    private JLabel przywitanie;
    private ImageIcon karta;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Main("Bank"));
    }

    public Main(String title) {
//        wstepne ustawienia okna
        super(title);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        setSize(new Dimension(dim.width / 2, dim.height / 2));
        setLocation(dim.width / 4, dim.height / 4);
        setContentPane(new JPanel());

        //        zamykanie okna
        WindowClosingListener windowClosingListener = new WindowClosingListener();
        addWindowListener(windowClosingListener);

//        akcje menu oraz skroty klawiszowe
        CloseAction closeAction = new CloseAction();
        LogoutAction logoutAction = new LogoutAction();
        ComputeAction computeAction = new ComputeAction();

//        pasek menu na górze
        menuGlowne = new JMenuBar();
        plik = new JMenu("Ustawienia");
        miWyloguj = new JMenuItem(logoutAction);
        miZamknij = new JMenuItem(closeAction);

        setJMenuBar(menuGlowne);
        menuGlowne.add(plik);
        plik.add(miWyloguj);
        plik.add(miZamknij);

//        ustawianie elementow w oknie
        numerKartyPole = new JTextField(10);
        potwierdzenie = new JButton("Przejdź dalej");
        przywitanie = new JLabel("Witamy w banku! Podaj swój numer karty płatniczej!");
        karta = new ImageIcon("citi-simplicity-300x194.png");
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel,BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        przywitanie.setIcon(karta);
        panel.add(przywitanie);
        panel.add(numerKartyPole);
        panel.add(potwierdzenie);
        add(panel);
    }

    @Override
    public void run() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JFrame getMainWindow() {
        return this;
    }

    class WindowClosingListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            if (0 == JOptionPane.showOptionDialog(e.getWindow(),
                    "Czy chcesz zamknąć okno?",
                    "Potwierdzenie",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[] {"Tak", "Nie"},
                    1)
            ) System.exit(0);
        }
    }

    class CloseAction extends AbstractAction {
        public CloseAction() {
            putValue(Action.NAME, "Zamknij");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Z"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Main kf = (Main) getMainWindow();
            kf.dispatchEvent(new WindowEvent(kf, WindowEvent.WINDOW_CLOSING));
        }
    }

    class LogoutAction extends AbstractAction {
        public LogoutAction() {
            putValue(Action.NAME, "Wyloguj");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            do zrobienia
        }
    }

    class ComputeAction extends AbstractAction {
        public ComputeAction() {
            putValue(Action.NAME, "Potwierdz");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            do zrobienia
        }
    }
}
