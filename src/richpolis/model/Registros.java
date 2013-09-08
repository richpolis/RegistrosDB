/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package richpolis.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import richpolis.dbs.Criteria;

/**
 *
 * @author richpolis
 */
public class Registros {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String imagen;
    private String nombre;
    private String telefono;
    private String celular;
    private String observaciones;
    private Double rango;

    public Registros() {
        this.id = 0;
        this.imagen = null;
        this.nombre = "";
        this.telefono = "";
        this.celular = "";
        this.observaciones = "";
        this.rango = new Double(0);
    }

    public Registros(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public String getImagen() {
        return this.imagen;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public String getCelular() {
        return this.celular;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public Double getRango() {
        return this.rango;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImagen(String sImagen) {
        this.imagen = sImagen;
    }

    public void setNombre(String sNombre) {
        this.nombre = sNombre;
    }

    public void setTelefono(String sTelefono) {
        this.telefono = sTelefono;
    }

    public void setCelular(String sCelular) {
        this.celular = sCelular;
    }

    public void setObservaciones(String sOb) {
        this.observaciones = sOb;
    }

    public void setRango(Double dRango) {
        this.rango = dRango;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registros)) {
            return false;
        }
        Registros other = (Registros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "richpolis.model[id=" + id + "]";
    }

    public boolean save() throws SQLException {
        Criteria c = new Criteria();
        String[] instrucciones;
        ResultSet rs=null;
        if (this.getId() == 0) {
            instrucciones = new String[3];
            c.setTipoQuery(Criteria.eQueryInsert);
            instrucciones[0] = "registros";
            instrucciones[1] = this.getStringCampos();
            instrucciones[2] = this.getStringValues();
        } else {
            instrucciones = new String[7];
            c.setTipoQuery(Criteria.eQueryUpdate);
            instrucciones[0] = "registros";
            instrucciones[1] = "nombre='" + this.getNombre() + "'";
            instrucciones[2] = "telefono='" + this.getTelefono() + "'";
            instrucciones[3] = "celular='" + this.getCelular() + "'";
            instrucciones[4] = "observaciones='" + this.getObservaciones() + "'";
            instrucciones[5] = "rango=" + this.getRango().toString();
            instrucciones[6] = "imagen='" + this.getImagen() + "'";
            c.setWhere("id="+this.getId());

        }
        c.setInstruccion(instrucciones);
        RegistrosPeer cmd = new RegistrosPeer();
        if(c.getTipoQuery()==Criteria.eQueryInsert){
            cmd.execute(c,true);
            rs = cmd.getRs();
            rs.first();
            this.setId(rs.getInt(1));
        }else{
            cmd.execute(c,false);
        }
        
        return true;


    }

    private String getStringValues() {
        return this.id + ",'"
                + this.getNombre() + "','"
                + this.getTelefono() + "','"
                + this.getCelular() + "','"
                + this.getObservaciones() + "','"
                + this.getRango().toString() + "','"
                + this.getImagen() + "'";
    }

    private String getStringCampos() {
        return "id,nombre,telefono,celular,observaciones,rango,imagen";
    }
}
