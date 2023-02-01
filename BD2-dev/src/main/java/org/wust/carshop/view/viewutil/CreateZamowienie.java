package org.wust.carshop.view.viewutil;

import org.wust.carshop.model.Part;
import org.wust.carshop.service.MechanicService;
import org.wust.carshop.util.PartPair;
import org.wust.carshop.view.LoginUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CreateZamowienie extends JFrame {
    private JComboBox<Part> czesciBox;
    private JSpinner iloscSpin;
    private JButton addPartButton;
    private JTextPane partSum;
    private JPanel panel1;
    private JButton orderButton;
    private String partSumInit = "Parts summary: ";
    private String partsSummary = "";
    private List<PartPair> chosenParts = new ArrayList<>();

    public CreateZamowienie(MechanicService service){
        setTitle("CarShopDB - create order");
        setContentPane(panel1);
        setSize(800,500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        czesciBox.setModel(new DefaultComboBoxModel<Part>(LoginUI.us.getAllParts().toArray(Part[]::new)));
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
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(PartPair pair : chosenParts) {
                    service.createPartOrder(pair.getPart(), pair.getQuantity());
                }
                JOptionPane.showMessageDialog(null, "Order done");
                setVisible(false);
                dispose();
            }
        });
        czesciBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,czesciBox.getSelectedItem());
            }
        });
    }
}
