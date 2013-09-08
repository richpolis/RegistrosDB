/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package richpolis.objetos;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author richpolis
 */
public class IconCellRenderer extends DefaultTableCellRenderer {

//lo puse en varias lineas para que lo veas completo
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {


//por default los objetos que renderizan texto en un JTable son
//JLabel asi que solo lo obtienes y le haces un cast

        JLabel label = (JLabel) super.
                getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

//si en esa columna y fila es un icon lo que quieres desplegar
// se lo agregas al JLabel con setIcon()
        if (value instanceof Icon) {
            label.setText(null);
            label.setIcon((Icon) value);
        }
        return label;
    }
}