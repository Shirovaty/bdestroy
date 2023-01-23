package org.wust.carshop.view;

import org.wust.carshop.service.MechanicService;

import javax.swing.*;

public class MechanicUI extends JFrame {

    private JPanel panel1;

    public MechanicUI(MechanicService service) {
        setTitle("CarShopDB - mechanic");
        //setContentPane(panel1);
        setSize(400,200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
