package org.wust.carshop.view.viewutil;

import org.wust.carshop.model.Part;
import org.wust.carshop.service.AdminService;
import org.wust.carshop.service.MechanicService;
import org.wust.carshop.util.PartPair;
import org.wust.carshop.view.LoginUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddPartsToRepair extends JFrame {
    private JPanel panel1;
    private JComboBox czesciBox;
    private JSpinner iloscSpin;
    private JButton addPartButton;
    private JTextPane partSum;
    private JButton addButton;

    private String partSumInit = "Parts summary: ";
    private String partsSummary = "";
    private List<PartPair> chosenParts = new ArrayList<>();

    private MechanicService service;

    int updatedRepair;

    public AddPartsToRepair(MechanicService service, int updatedRepair) {
        this.service = service;
        setTitle("CarShopDB - add form");
        setContentPane(panel1);
        setSize(1400, 800);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        updateModels();
        czesciBox.setEnabled(true);
        addPartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((Integer) iloscSpin.getValue() > 0) {
                    Part part = LoginUI.us.getPartByID(((Part) czesciBox.getSelectedItem()).getId()).get(0);
                    int count = (Integer) iloscSpin.getValue();
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
                for (PartPair partPair : chosenParts) {
                    service.addPartToRepair(partPair.getPart(), partPair.getQuantity(), updatedRepair);
                }
                JOptionPane.showMessageDialog(null, "Parts successfully added");
                setVisible(false);
                dispose();
            }
        });
    }

    public void updateModels() {
        czesciBox.setModel(new DefaultComboBoxModel<Part>(LoginUI.us.getAllParts().toArray(Part[]::new)));
    }
}
