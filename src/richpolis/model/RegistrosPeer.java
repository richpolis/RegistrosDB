/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package richpolis.model;

/**
 *
 * @author ralcanta
 */
public class RegistrosPeer extends richpolis.dbs.Peer {

    public final static String ID               ="id";
    public final static String IMAGEN    		="imagen";
    public final static String NOMBRE       	="nombre";
    public final static String TELEFONO         ="telefono";
    public final static String CELULAR          ="celular";
    public final static String OBSERVACIONES    ="observaciones";
    public final static String RANGO            ="rango";

    public RegistrosPeer() {
        this.TABLA="registros";
        this.setMostrarTabla(true);
    }

    public RegistrosPeer(boolean bMostrarTabla) {
        this.TABLA="registros";
        this.setMostrarTabla(bMostrarTabla);

    }

   
}
