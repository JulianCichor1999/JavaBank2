package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Main extends JFrame implements Runnable {
    private JMenuBar menuGlowne;
    private JMenu plik;
    private JMenuItem miWyloguj, miZamknij;


    private JLabel labelPrzywitanieKarta;
    private JLabel labelPodajNrKarty;
    private JLabel labelPodajPin;
    private JLabel labelPrzywitanieInfoNrKarty;
    private JLabel labelBledneDane;
    private JLabel labelPowitaniePoImieniu;

    private JTextField textNumerKartyPole;
    private JTextField textPinPole;

    private JButton buttonPotwierdzenie;
    private JButton buttonWyswietlSrodki;
    private JButton buttonWyplacPieniadze;
    private JButton buttonWplacPieniadze;

    private ImageIcon karta;


    private int numerAktywnegoPanelu;
    private JPanel panelAktywny;
    //    private JPanel panelPowitalnyPin;
//    private JPanel panelOpcje;

    ArrayList<KartaPlatnicza> klienci;
    KartaPlatnicza kartaPlatnicza;

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

//        wczytanie kart
        klienci = Reader.getKlienci();

//        pobranie danych uzytkownika
        String imie = "Jan", nazwisko = "Kowalski";
        double srodki = 50.25;

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

//        elemety do paneli poczatkowych
        textNumerKartyPole = new JTextField(10);
        textPinPole = new JTextField(4);
        buttonPotwierdzenie = new JButton("Potwierdź");
        labelPrzywitanieKarta = new JLabel();
        labelPodajNrKarty = new JLabel("Witamy w banku! Podaj swój numer karty płatniczej!");
        labelPodajPin = new JLabel("Podaj PIN:");
        karta = new ImageIcon("citi-simplicity-300x194.png");
        labelBledneDane = new JLabel("Podałeś błędne dane!");
        labelPrzywitanieInfoNrKarty = new JLabel();

//        elementy do panelu opcje
        labelPowitaniePoImieniu = new JLabel(String.format("Sz. P. %s %s", imie, nazwisko));
        buttonWyswietlSrodki = new JButton("Wyświetl środki");
        buttonWyplacPieniadze = new JButton("Wypłać pieniądze");
        buttonWplacPieniadze = new JButton("Wpłać pieniądze");

//        panel powitalny
        panelAktywny = new JPanel();
        BoxLayout layoutPowitalny = new BoxLayout(panelAktywny,BoxLayout.Y_AXIS);
        panelAktywny.setLayout(layoutPowitalny);
        labelPrzywitanieKarta.setIcon(karta);

        numerAktywnegoPanelu = 1;
        changePanel(1);

        add(panelAktywny);

        buttonPotwierdzenie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numerAktywnegoPanelu == 1) {
                    kartaPlatnicza = KartaPlatnicza.czyNumerKartyZgadzaSie(textNumerKartyPole.getText(), klienci);


//                  gdy uzytkownik podal błędny nr karty
                    if (kartaPlatnicza == null) {
                        changePanel(1, "Nie ma takiej karty w bazie!");
                        return;
                    }

//                  gdy uzytkownik podal poprawnie nr karty
                    changePanel(2);

                } else if (numerAktywnegoPanelu == 2) {
                    if (textPinPole.getText().length() == 0) return;

//                  gdy użytkownik podał poprawny kod PIN
                    if (kartaPlatnicza.getPIN() == Short.parseShort(textPinPole.getText())) {
                        changePanel(3);
                        return;
                    }

//                  gdy uzytkownik podal bledny PIN
                    changePanel(1, "Podałeś błędny PIN!");
                }
                else if (numerAktywnegoPanelu == 3) {
                    changePanel(1);
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

    private void changePanel(int stage) {
        changePanel(stage, null);
    }

    private void changePanel(int stage, String err) {
        panelAktywny.remove(labelBledneDane);
        /*
            stage:
            1 (default): panel do podania nr karty
            2: panel do podania nr pin
            3: panel z wyborem opcji
         */
        if (stage == 2) {
            numerAktywnegoPanelu = 2;
            labelPrzywitanieInfoNrKarty = new JLabel(String.format("Numer karty: %s", textNumerKartyPole.getText()));
//            panelAktywny.add(labelPrzywitanieKarta);
            panelAktywny.add(labelPrzywitanieInfoNrKarty);
            panelAktywny.remove(labelPodajNrKarty);
            panelAktywny.remove(textNumerKartyPole);
            panelAktywny.add(labelPodajPin);
            panelAktywny.add(textPinPole);
            panelAktywny.remove(labelBledneDane);

            panelAktywny.add(buttonPotwierdzenie);
        } else if (stage == 3) {
            numerAktywnegoPanelu = 3;
            panelAktywny.remove(labelPrzywitanieKarta);
            panelAktywny.remove(labelPrzywitanieInfoNrKarty);
            panelAktywny.remove(labelPodajPin);
            panelAktywny.remove(textPinPole);
            panelAktywny.remove(buttonPotwierdzenie);

            panelAktywny.add(labelPowitaniePoImieniu);
            panelAktywny.add(buttonWyswietlSrodki);
            panelAktywny.add(buttonWyplacPieniadze);
            panelAktywny.add(buttonWplacPieniadze);

        } else {
            numerAktywnegoPanelu = 1;
            panelAktywny.add(labelPrzywitanieKarta);
            if (err != null) {
                labelBledneDane = new JLabel("Nie ma takiej karty w bazie!");
                panelAktywny.add(labelBledneDane);
            }
            panelAktywny.add(labelPodajNrKarty);
            panelAktywny.add(textNumerKartyPole);

            panelAktywny.remove(labelPrzywitanieInfoNrKarty);
            panelAktywny.remove(labelPodajPin);
            panelAktywny.remove(textPinPole);

            panelAktywny.remove(labelPowitaniePoImieniu);
            panelAktywny.remove(buttonWyswietlSrodki);
            panelAktywny.remove(buttonWyplacPieniadze);
            panelAktywny.remove(buttonWplacPieniadze);

            panelAktywny.add(buttonPotwierdzenie);
        }
        SwingUtilities.updateComponentTreeUI(panelAktywny);
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
            changePanel(1);

            textNumerKartyPole.setText("");
            textPinPole.setText("");

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
