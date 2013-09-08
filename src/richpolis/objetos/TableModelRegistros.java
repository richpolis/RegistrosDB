/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package richpolis.objetos;

import java.io.File;
import richpolis.model.Registros;
import richpolis.model.RegistrosPeer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ralcanta
 */
public class TableModelRegistros extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TableModelRegistros() {
    }
    public final static int ID = 0;
    public final static int IMAGEN = 1;
    public final static int NOMBRE = 2;
    public final static int TELEFONO = 3;
    public final static int CELULAR = 4;
    public final static int RANGO = 5;
    public final static int OBSERVACIONES = 6;
    
    private String[] columnNames = {"ID", "Imagen", "Nombre", "Telefono", "Celular", "Rango","Observaciones"};
    private Object[] columnClass = {Integer.class, ImageIcon.class, String.class, String.class, String.class, String.class,String.class};
    private boolean[] columnEditable = {false, false, false, false, false, false, false};
    private LinkedList<Registros> registros = new LinkedList<Registros>();

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return registros.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        Registros registro = (Registros) registros.get(row);
        switch (col) {
            case TableModelRegistros.ID:
                return registro.getId();
            // break;
            case TableModelRegistros.IMAGEN:
                return createImage(registro.getImagen());
            //break;
            case TableModelRegistros.NOMBRE:
                return registro.getNombre();
            //break;
            case TableModelRegistros.TELEFONO:
                return registro.getTelefono();
            //break;
            case TableModelRegistros.CELULAR:
                return registro.getCelular();
            //break;
            case TableModelRegistros.RANGO:
                DecimalFormat format = new DecimalFormat("#,###,###");
                return format.format(registro.getRango());
                //break;
            case TableModelRegistros.OBSERVACIONES:
                return registro.getObservaciones();
            // break;
        }
        return this;
    }

    @Override
    public Class getColumnClass(int c) {
        return this.columnClass[c].getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return this.columnEditable[col];
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        Registros registro = (Registros) registros.get(row);
        switch (col) {
            case TableModelRegistros.ID:
                registro.setId((Integer) value);
                break;
            case TableModelRegistros.NOMBRE:
                registro.setNombre((String) value);
                break;
            case TableModelRegistros.TELEFONO:
                registro.setTelefono((String) value);
                break;
            case TableModelRegistros.CELULAR:
                registro.setCelular((String) value);
                break;
            case TableModelRegistros.IMAGEN:
                registro.setImagen((String)value);
                break;
            case TableModelRegistros.OBSERVACIONES:
                registro.setObservaciones((String) value);
                break;
            case TableModelRegistros.RANGO:
                registro.setRango((Double) value);
                break;
        }
        fireTableCellUpdated(row, col);
    }

    public void agregarRegistros(ResultSet rs) {
        int largo = JRpFunSql.getResultSetCount(rs);
        try {
            if (rs.first()) {
                for (int cont = 0; cont < largo; cont++) {
                    this.agregarRegistro(rs);
                    rs.next();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TableModelRegistros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarRegistro(ResultSet rs) {
        Registros registro = new Registros();
        String campo = "";
        try {
            for (int cont = 0; cont < rs.getMetaData().getColumnCount(); cont++) {
                campo = rs.getMetaData().getColumnName(cont + 1);
                if (campo.compareTo(RegistrosPeer.ID) == 0) {
                    registro.setId(new Integer(rs.getInt(RegistrosPeer.ID)));
                } else if (campo.compareTo(RegistrosPeer.NOMBRE) == 0) {
                    registro.setNombre(new String(rs.getString(RegistrosPeer.NOMBRE)));
                } else if (campo.compareTo(RegistrosPeer.TELEFONO) == 0) {
                    registro.setTelefono(new String(rs.getString(RegistrosPeer.TELEFONO)));
                } else if (campo.compareTo(RegistrosPeer.CELULAR) == 0) {
                    registro.setCelular(new String(rs.getString(RegistrosPeer.CELULAR)));
                } else if (campo.compareTo(RegistrosPeer.OBSERVACIONES) == 0) {
                    registro.setObservaciones(new String(rs.getString(RegistrosPeer.OBSERVACIONES)));
                } else if (campo.compareTo(RegistrosPeer.RANGO) == 0) {
                    registro.setRango(new Double(rs.getDouble(RegistrosPeer.RANGO)));
                } else if (campo.compareTo(RegistrosPeer.IMAGEN) == 0) {
                    registro.setImagen(rs.getString(RegistrosPeer.IMAGEN));
                }
            }
            this.agregarRegistro(registro);
        } catch (SQLException ex) {
            Logger.getLogger(TableModelRegistros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarRegistro(Registros registro) {
        registros.add(registro);
        this.fireTableRowsInserted(this.getRowCount() - 1, this.getRowCount() - 1);
    }

    public void removerRegistro(int indice) {
        registros.remove(indice);
        this.fireTableRowsDeleted(indice, indice);
    }

    public ImageIcon createImage(String path) {
        File imagen = new File(path);
        if(!imagen.exists()){
            path="default.png";
        }
        //URL imgURL = getClass().getResource(path);
        if (path != null) {
            return new ImageIcon(path);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    public Registros getRegistroRow(int fila){
        Registros registro = (Registros) registros.get(fila);
        return registro;
    }
    
}
