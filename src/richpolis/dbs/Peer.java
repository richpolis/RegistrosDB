/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package richpolis.dbs;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author ralcanta
 */
public class Peer {

    private Connection con=null;
    private Statement cmd=null;
    private ResultSet rs=null;
    protected String TABLA="";
    protected boolean mostrarTabla=true;
    protected boolean bDebug=true;
    private String sBaseDatos="";

    public String getsBaseDatos() {
        return sBaseDatos;
    }

    public void setsBaseDatos(String sBaseDatos) {
        this.sBaseDatos = sBaseDatos;
    }

    public Peer() {
        this.setMostrarTabla(false);
        this.setsBaseDatos("MySQL");
        //this.setsBaseDatos("Access");
    }

    public Peer(boolean bMostrarTabla) {
        this.setMostrarTabla(bMostrarTabla);
    }
    
    /**
     * Get the value of tabla
     *
     * @return the value of tabla
     */
    public String getNameTabla() {
        return this.TABLA;
    }

    public boolean isDebug() {
        return bDebug;
    }

    public void setDebug(boolean bDebug) {
        this.bDebug = bDebug;
    }

    /**
     * Get the value of mostrarTabla
     *
     * @return the value of mostrarTabla
     */
    public boolean isMostrarTabla() {
        return mostrarTabla;
    }

    /**
     * Set the value of mostrarTabla
     *
     * @param mostrarTabla new value of mostrarTabla
     */
    public void setMostrarTabla(boolean mostrarTabla) {
        this.mostrarTabla = mostrarTabla;
    }

    public String getCampo(String sCampo){
        if(this.mostrarTabla){
            return this.TABLA + sCampo;
        }else{
            return sCampo;
        }
    }
    
    public ResultSet doSelect(){
        return this.doSelect(null);
    }
    public ResultSet doSelect(Criteria c){
        if(c==null){
            c=new Criteria();
            c.setInstruccion("*");
            c.setFrom(this.TABLA);
        }
        try{
            if(this.con==null){
                if(this.getsBaseDatos().compareTo("MySQL")==0)
                    this.con= ConexcionMysql.getConexcion();
                else
                    this.con=ConexcionOdbc.getConexcion();
            }
            this.crearComando();
            this.ejecutarQuery(c.getQuery());
            return this.rs;
        }catch(SQLException err){
            System.out.println(err.getMessage());
            return null;
        }
    }
    public long doCount(){
        return this.doCount(null);
    }
    public long doCount(Criteria c){
        if(c==null){
            c=new Criteria();
            c.setInstruccion(c.getCount("*", "registros"));
            c.setFrom(this.TABLA);
        }
        try{
            if(this.con==null){
                if(this.getsBaseDatos().compareTo("MySQL")==0)
                    this.con= ConexcionMysql.getConexcion();
                else
                    this.con=ConexcionOdbc.getConexcion();
            }
            this.crearComando();
            this.ejecutarQuery(c.getQuery());
            return this.rs.getLong("registros");
        }catch(SQLException err){
            System.out.println(err.getMessage());
            return 0;
        }
    }
    
    public boolean execute(Criteria c,boolean generarKeys){
        if(c==null){
            return false;
        }
        try{
            if(this.con==null){
                if(this.getsBaseDatos().compareTo("MySQL")==0)
                    this.con= ConexcionMysql.getConexcion();
                else
                    this.con=ConexcionOdbc.getConexcion();
            }
            this.crearComando();
            this.ejecutarUpdate(c.getQuery(),generarKeys);
            return true;
        }catch(SQLException err){
            System.out.println(err.getMessage());
            return false;
        }
    }
    
    private void crearComando() throws SQLException{
        if(this.cmd!=null){
            if(!this.cmd.isClosed()){
                this.cmd.close();
            }
        }
        this.cmd=this.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        if(this.isDebug()){
           System.out.println("Se creo el comando\n");
        }
    }
    
    public void ejecutarQuery(String sql) throws SQLException{
        if(this.rs!=null){
            if(!this.rs.isClosed()){
                this.rs.close();
            }
        }
        try{
        	this.rs=this.cmd.executeQuery(sql);
        }catch(SQLException e){
        	System.out.println(e.getSQLState());
        }
        if(this.isDebug()){
           System.out.println("Se realizo la consulta: "+sql+"\n");
        }
    }
    
    
    public void ejecutarUpdate(String sql,boolean generarKeys) throws SQLException{
        if(this.rs!=null){
            if(!this.rs.isClosed()){
                this.rs.close();
            }
        }
        if(generarKeys){
            this.cmd.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            this.rs=this.cmd.getGeneratedKeys();
        }else{
            this.cmd.executeUpdate(sql);
            this.rs=null;
        }
        if(this.isDebug()){
           System.out.println("Se realizo la consulta: "+sql+"\n");
        }
    }
    
    public ResultSet getRs(){
        return this.rs;
    }

}
