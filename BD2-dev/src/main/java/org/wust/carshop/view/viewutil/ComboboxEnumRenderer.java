package org.wust.carshop.view.viewutil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ComboboxEnumRenderer extends JLabel implements ListCellRenderer<UtilEnum> {

    @Override
    public Component getListCellRendererComponent(JList<? extends UtilEnum> list, UtilEnum value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.label);
        return this;
    }
}
