package org.wust.carshop.view;

import org.wust.carshop.exception.ServiceException;
import org.wust.carshop.service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class LoginUI extends JFrame {
    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton logInButton;
    private JTextField passwordField;
    private String[] roles = {"admin", "mechanic", "storeman"};
    public static UtilsService us;

    public LoginUI(){
        comboBox1.setModel(new DefaultComboBoxModel<>(roles));
        setTitle("CarShopDB - login");
        setContentPane(panel1);
        setSize(400,200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.setModalExclusionType(Dialog.ModalExclusionType.TOOLKIT_EXCLUDE);

        logInButton.addActionListener(e -> selectRole());
        passwordField.addActionListener(e -> selectRole());
    }


    private void selectRole(){
        var ls = new LoggingService();
        var handler = ls.validateRole("postgres", "docker");
        switch(comboBox1.getSelectedItem().toString()){
            case "admin":
                try {
                    handler = ls.validateRole("postgres", passwordField.getText());
                }catch (ServiceException e) {
                    JOptionPane.showMessageDialog(null, "Wrong password");
                    break;
                }
                us = new UtilsService(handler);
                new AdminUI(new AdminService(handler, us), new JFrame());
                setVisible(false);
                dispose();
                break;
            case "mechanic":
                try{
                    handler = ls.validateRole("mechanik", passwordField.getText());
                }catch (ServiceException e) {
                    JOptionPane.showMessageDialog(null, "Wrong password");
                    break;
                }
                //TODO uncomment to swap for mechanik role and password
                var id = ls.validateMechanic(JOptionPane.showInputDialog("Enter your login"), handler);
                us = new UtilsService(handler);
                new MechanicUI(new MechanicService(handler, us, id));
                setVisible(false);
                dispose();
                break;
            case "storeman":
                try{
                    handler = ls.validateRole("magazynier", passwordField.getText());
                }catch (ServiceException e) {
                    JOptionPane.showMessageDialog(null, "Wrong password");
                    break;
                }
                //TODO uncomment to swap for magazynier role and password
                new StoremanUI(new StoremanService(handler));
                setVisible(false);
                dispose();
                break;
        }
    }
}
