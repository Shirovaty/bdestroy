package org.wust.carshop.view;

import org.wust.carshop.model.Part;
import org.wust.carshop.model.PartOrder;
import org.wust.carshop.service.StoremanService;
import org.wust.carshop.view.viewutil.AddPartToSystem;
import org.wust.carshop.view.viewutil.AddPartsToRepair;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class StoremanUI extends JFrame {

    private JPanel panel1;
    private JTable table1;
    private JButton dodajMagazynButton;
    private JButton dodajSystemButton;

    private DefaultTableModel model = new DefaultTableModel();

    ListSelectionModel cellSelection = table1.getSelectionModel();

    ListSelectionListener tableListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int a = table1.getSelectedRow();
            int b = table1.getSelectedColumn();
            if(model.getColumnName(b).equals("orderedPart")) {
                JOptionPane.showMessageDialog(null, table1.getValueAt(a,b).toString());
            }
        }
    };

    public StoremanUI(StoremanService service) {
        setTitle("CarShopDB - storeman");
        setContentPane(panel1);
        setSize(1000,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        table1.setDefaultEditor(Object.class, null);
        setModel(service.getAllOrders());
        table1.setModel(model);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelection.addListSelectionListener(tableListener);
        dodajMagazynButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                int id = (int)table1.getValueAt(row, 0);
                Part part = (Part) table1.getValueAt(row, 2);
                int quantity = (int)table1.getValueAt(row, 3);
                service.addPartStock(id, Integer.valueOf(JOptionPane.showInputDialog("Ile częsci dodać?")));
                service.deletePartOrder(PartOrder.builder().id(id).orderedPart(part).quantity(quantity).build());
                model.removeRow(row);
            }
        });
        dodajSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddPartToSystem(service);
            }
        });
    }

    <E> void setModel(List<E> list) {
        model = new DefaultTableModel();
        List<String> headers = new ArrayList<>();
        Field[] fields = new Field[1];
        try{
            fields = list.get(0).getClass().getDeclaredFields();
        } catch (IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "No record found");
        }
        table1.getTableHeader().setReorderingAllowed(false);
        table1.getTableHeader().setResizingAllowed(false);
        for (Field field : fields) {
            headers.add(field.getName());
        }
        model.setColumnIdentifiers(headers.toArray());
        for(E item : list) {
            Object temp = null;
            List<Object> dataArray = new ArrayList<>();
            for(Field field: fields) {
                field.setAccessible(true);
                try{
                    dataArray.add(field.get(item));
                } catch (IllegalAccessException e) {
                    System.out.println(e);
                }
            }
            model.addRow(dataArray.toArray());
        }
    }
}
