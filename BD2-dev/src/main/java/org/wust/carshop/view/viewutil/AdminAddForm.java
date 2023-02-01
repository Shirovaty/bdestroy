package org.wust.carshop.view.viewutil;

import org.wust.carshop.model.Employee;
import org.wust.carshop.model.Part;
import org.wust.carshop.service.AdminService;
import org.wust.carshop.util.PartPair;
import org.wust.carshop.view.LoginUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdminAddForm extends JFrame {
    private JPanel panel1;
    private JTextField nazwaField;
    private JButton addButton;
    private JComboBox<Part> czesciBox;
    private JButton addPartButton;
    private JSpinner iloscSpin;
    private JTextPane partSum;
    private JTextField stanowiskoField;
    private JButton dodajStanowiskoButton;
    private JTextField imieField;
    private JTextField nazwiskoField;
    private JComboBox stanowiskoBox;
    private JButton dodajPracownikaButton;
    private String partSumInit = "Parts summary: ";
    private String partsSummary = "";
    private List<PartPair> chosenParts = new ArrayList<>();

    private AdminService service;


    public AdminAddForm(AdminService service) {
        this.service = service;
        setTitle("CarShopDB - add form");
        setContentPane(panel1);
        setSize(1400,800);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        updateModels();
        czesciBox.setEnabled(true);
        addPartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((Integer)iloscSpin.getValue() > 0) {
                    Part part = LoginUI.us.getPartByID(((Part)czesciBox.getSelectedItem()).getId()).get(0);
                    int count = (Integer)iloscSpin.getValue();
                    chosenParts.add(new PartPair(part, count));
                    partsSummary = partsSummary.concat(czesciBox.getSelectedItem().toString() + " x " + count + ", \n");
                    partSum.setText(partSumInit + partsSummary);
                    partSum.updateUI();
                } else {
                    JOptionPane.showMessageDialog(null, "Value can't be less than 1");
                }

            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                service.addRepairTemplate(nazwaField.getText(), chosenParts);
            }
        });
        dodajStanowiskoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                service.addPosition(stanowiskoField.getText());
                updateModels();
            }
        });
        dodajPracownikaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee employee = Employee.builder()
                        .name(imieField.getText())
                        .surname(nazwiskoField.getText())
                        .position(stanowiskoBox.getSelectedItem().toString()).build();
                service.addEmployee(employee);
            }
        });
        czesciBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,czesciBox.getSelectedItem());
            }
        });

    }
    
    public void updateModels() {
        czesciBox.setModel(new DefaultComboBoxModel<Part>(LoginUI.us.getAllParts().toArray(Part[]::new)));
        stanowiskoBox.setModel(new DefaultComboBoxModel(service.getAllPositions().toArray()));
    }

}
