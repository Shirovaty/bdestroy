package org.wust.carshop.view.viewutil;

import org.wust.carshop.model.Part;
import org.wust.carshop.service.StoremanService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPartToSystem extends JFrame {
    private JTextField markaField;
    private JPanel panel1;
    private JTextField modelField;
    private JTextField numerField;
    private JTextField producentField;
    private JTextField typField;
    private JButton addButton;
    private JTextField kosztField;

    StoremanService service;

    public AddPartToSystem(StoremanService service) {
        this.service = service;
        setTitle("CarShopDB - add form");
        setContentPane(panel1);
        setSize(1400, 800);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!service.carBrandExists(markaField.getText())) {
                    service.addCarBrand(markaField.getText());
                }
                if (!service.carModelExists(modelField.getText())) {
                    service.addCarModel(modelField.getText(), markaField.getText());
                }
                if (!service.partProducerExists(producentField.getText())) {
                    service.addPartProducer(producentField.getText());
                }
                if (!service.partTypeExists(typField.getText())) {
                    service.addPartType(typField.getText());
                }
                service.addPart(Part.builder()
                        .carModel(modelField.getText())
                        .carBrand(markaField.getText())
                        .producer(producentField.getText())
                        .category(typField.getText())
                        .serialNumber(numerField.getText())
                        .price(Double.valueOf(kosztField.getText()))
                        .build());
                JOptionPane.showMessageDialog(null, "Dobra robota, dodałeś część");
                setVisible(false);
                dispose();
            }
        });
    }
}
