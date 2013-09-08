/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package richpolis.objetos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;


/**
 *
 * @author richpolis
 */
public class JRpFunSql {
    static public ComboBoxModel getComboBoxModelFromRs(ResultSet rs){
        String[] datos=JRpFunSql.getStringArrayOfResultSet(rs);
        if(datos==null){
            datos= new String[]{""};
        }
        return new DefaultComboBoxModel(datos);
    }
    static public int getResultSetCount(ResultSet rs){
        int cont=0;
        try{
            if(rs!=null){
                if(rs.last()){
                    cont= rs.getRow();
                 }
                 rs.first();
            }
        }catch(SQLException err){
            System.out.println(err.getMessage());
        }
        return cont;
    }
    static public ComboBoxModel getComboBoxModelFromRs(ResultSet rs, String[] adicional){
        String[] datos=JRpFunSql.getStringArrayOfResultSet(rs);
        if(datos==null){
            datos=new String[]{""};
        }
        if(adicional.length>0){
            for(int cont=0;cont<adicional.length;cont++){
                 if(adicional[cont].compareTo(JRpFunGen.TODAS)==0){
                     datos=JRpFunGen.getStringArrayJoinArrays(new String[]{JRpFunGen.TODAS},datos);
                 }else if(adicional[cont].compareTo(JRpFunGen.TODOS)==0){
                     datos=JRpFunGen.getStringArrayJoinArrays(new String[]{JRpFunGen.TODOS},datos);
                 }else if(adicional[cont].compareTo(JRpFunGen.NUEVA)==0){
                     datos=JRpFunGen.getStringArrayJoinArrays(datos,new String[]{JRpFunGen.NUEVA});
                 }
            }
        }
        return new DefaultComboBoxModel(datos);
    }
    static public String[] getStringArrayOfResultSet(ResultSet rs){
        int largo=JRpFunSql.getResultSetCount(rs);
        String[] datos=null;
        if(largo>0){
            datos= new String[largo];
            try{
                if(rs!=null){
                    if(rs.first()){
                        for(int cont=0;cont<largo;cont++){
                            datos[cont]=JRpFunSql.getJoinStringResultSetRow(rs, JRpFunGen.SEPARADOR_VALORES);
                            if(!rs.next()){
                                break;
                            }
                        }
                    }
                }
            }catch(SQLException err){
                System.out.println(err.getMessage());
            }
        }
        return datos;
    }
    static public String getJoinStringResultSetRow(ResultSet rs, String separador){
        String cadena="";
        if(separador==null) separador=",";
        try {
            if(rs!=null){
                int columnas = rs.getMetaData().getColumnCount();
                for(int cont=0;cont<columnas;cont++){
                    if(cadena.length()>0){
                        cadena+=separador+rs.getString(cont+1);
                    }else{
                        cadena=rs.getString(cont+1);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JRpFunSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cadena;
    }
}
