package org.wust.carshop.view;

import org.wust.carshop.service.StoremanService;

import javax.swing.*;

public class StoremanUI extends JFrame {

    private JPanel panel1;

    public StoremanUI(StoremanService service) {
        setTitle("CarShopDB - storeman");
        //setContentPane(panel1);
        setSize(400,200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
