package org.wust.carshop.view.viewutil;

import org.wust.carshop.model.Part;
import org.wust.carshop.service.AdminService;
import org.wust.carshop.util.PartPair;
import org.wust.carshop.view.LoginUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddForm extends JFrame {
    private JPanel panel1;
    private JTextField nazwaField;
    private JButton addButton;
    private JComboBox czesciBox;
    private JButton addPartButton;
    private JLabel partSum;
    private JSpinner iloscSpin;
    private String partSumInit = "Parts summary: ";
    private String partsSummary = "";
    private List<PartPair> chosenParts = new ArrayList<>();


    public AddForm(AdminService service) {
        setTitle("CarShopDB - add form");
        setContentPane(panel1);
        setSize(800,800);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        czesciBox.setEnabled(true);
        czesciBox.setModel(new DefaultComboBoxModel(LoginUI.us.getAllParts().stream().map(e -> e.getSerialNumber() + " " + e.getCategory()).toArray()));
        addPartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Part part = LoginUI.us.getPartByID(czesciBox.getSelectedItem().toString().substring(0,15)).get(0);
                int count = (Integer)iloscSpin.getValue();
                chosenParts.add(new PartPair(part, count));
                partsSummary = partsSummary.concat(czesciBox.getSelectedItem().toString() + ", \n");
                partSum.setText(partSumInit + partsSummary);
                partSum.updateUI();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                service.addRepairTemplate(nazwaField.getText(), chosenParts);
            }
        });
    }


}
