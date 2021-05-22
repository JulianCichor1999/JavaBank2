package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements Runnable {

    private JButton potwierdzenie;
    private JTextField numerKartyPole;
    private JLabel przywitanie;
    private ImageIcon karta;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Main("Bank"));
    }

    public Main(String title) {
        super(title);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        setSize(800, 300);
        setLocation(dim.width / 4, dim.height / 4);
        setContentPane(new JPanel());
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
        potwierdzenie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(numerKartyPole.getText().isEmpty())
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
}
