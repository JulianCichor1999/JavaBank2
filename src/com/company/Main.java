package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame implements Runnable {
    private JMenuBar menuGlowne;
    private JMenu plik;
    private JMenuItem miWyloguj, miZamknij;

    private JButton buttonPotwierdzenie;
    private JTextField textNumerKartyPole;
    private JTextField textPinPole;
    private JLabel labelPrzywitanieKarta;
    private JLabel labelPodajPin;
    private ImageIcon karta;

    private JPanel panelPowitalny;
    private JPanel panelPowitalnyPin;
    private JPanel panelOpcje;

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
        textNumerKartyPole = new JTextField(10);
        textPinPole = new JTextField(4);
        buttonPotwierdzenie = new JButton("Potwierdź");
        labelPrzywitanieKarta = new JLabel("Witamy w banku! Podaj swój numer karty płatniczej!");
        labelPodajPin = new JLabel("Podaj PIN:");
        karta = new ImageIcon("citi-simplicity-300x194.png");

//        panel powitalny
        panelPowitalny = new JPanel();
        BoxLayout layoutPowitalny = new BoxLayout(panelPowitalny,BoxLayout.Y_AXIS);
        panelPowitalny.setLayout(layoutPowitalny);
        labelPrzywitanieKarta.setIcon(karta);
        panelPowitalny.add(labelPrzywitanieKarta);
        panelPowitalny.add(textNumerKartyPole);
        panelPowitalny.add(buttonPotwierdzenie);

//        panel powitalny z pinem
        panelPowitalnyPin = new JPanel();
        BoxLayout layoutPowitalnyPin = new BoxLayout(panelPowitalnyPin,BoxLayout.Y_AXIS);
        panelPowitalnyPin.setLayout(layoutPowitalnyPin);
        labelPrzywitanieKarta.setIcon(karta);
        panelPowitalnyPin.add(labelPrzywitanieKarta);
        panelPowitalnyPin.add(textNumerKartyPole);
        panelPowitalnyPin.add(labelPodajPin);
        panelPowitalnyPin.add(textPinPole);
        panelPowitalnyPin.add(buttonPotwierdzenie);

//        panel z opcjami - po zalogowaniu
        panelOpcje = new JPanel();
        BoxLayout layoutOpcje = new BoxLayout(panelOpcje,BoxLayout.Y_AXIS);
        panelOpcje.setLayout(layoutOpcje);


        add(panelOpcje);

        buttonPotwierdzenie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textNumerKartyPole.getText().isEmpty())
                {
                    JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(),
                            "Pusto!");
                }
                else
                {
                    JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(),
                            "Niepusto!");
                }
            }
        });
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
