package src.com.practico2.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaResultado {
    private JButton ingresarResultadoBtn;
    private JPanel ventana;
    private JTextArea cedulaArea;
    private JTextArea resultadoArea;
    private JLabel cedulaLabel;
    private JLabel resultadoLabel;
    private JTable table1;

    public VentanaResultado() {
        ingresarResultadoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Hello World!");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VentanaResultado");
        frame.setContentPane(new VentanaResultado().ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
