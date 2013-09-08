package richpolis.dbs;

import java.sql.*;
import com.mysql.jdbc.Driver;


/*
 * Debes de crear una base de datos en access con las siguientes:
 * datos=nombre de la tabla
 * id= campo autoincrementable
 * nombre=tipo string
 * sexo=tipo String
 * edad=tipo entero
 * La guardas y listo, copias la direcion donde la guardas en la varible dir
 * Ver archvi doc para la conexion manual..
 */
class ConexcionMysql{

    private static Connection conexion=null;
    private String sRutaBaseDatos="";
    private String sUsuario="";
    private String sPassword="";
    private boolean bConexcionOk=false;
    private boolean bDebug=false;
 
    public ConexcionMysql(){
        this.sRutaBaseDatos="jdbc:mysql://localhost/registrosdb";
        this.sUsuario="root";
        this.sPassword="root";
        this.bDebug=true;
    }

    public ConexcionMysql(String sRutaBaseDatos, String sUsuario, String sPassword) {
        this.sRutaBaseDatos=sRutaBaseDatos;
        this.sUsuario=sUsuario;
        this.sPassword=sPassword;
        this.bDebug=true;
    }

    public boolean isConexcionOk() {
        return bConexcionOk;
    }

    public boolean isDebug() {
        return bDebug;
    }

    public void setDebug(boolean bDebug) {
        this.bDebug = bDebug;
    }
     //Abre la conexion con la base de datos
     public boolean abrirConexion(){
        try{
           Class.forName("com.mysql.jdbc.Driver").newInstance();
           conexion=DriverManager.getConnection(this.sRutaBaseDatos,this.sUsuario,this.sPassword);
           this.bConexcionOk=true;
           //statment=conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);//para las consultas
           if(this.bDebug){
                System.out.println("Conexion exitosa");
           }
           return this.isConexcionOk();
          }catch(Exception e){
              System.out.println(e.getMessage());
              if(this.bDebug){
                System.out.println("No se ha podido cargar el Driver JDBC-ODBC");
              }
            return false;
          }
     }

     //Cierra la conexion con la base de datos
     public boolean cerrarConexion(){
         try{
            conexion.close();
            conexion=null;
            this.bConexcionOk=false;
            if(this.isDebug()){
                System.out.println("Cerrando la conexcion");
            }
            return this.isConexcionOk();
         }catch(SQLException e){
            if(this.isDebug()){
                System.out.println(e.getMessage());
            }
            this.bConexcionOk=false;
            return this.isConexcionOk();
         }
     }
     public static Connection getConexcion(){
         ConexcionMysql conRp=new ConexcionMysql();
         if(conRp.isHayConexcion()){
            return conRp.getConexcionActual();
         }else{
             if(conRp.isConexcionOk()){
                 return conRp.getConexcionActual();
             }else{
                if(conRp.abrirConexion()){
                     return conRp.getConexcionActual();
                 }else{
                     return null;
                 }
             }
         }
     }
     public boolean isHayConexcion(){
         if(conexion!=null){
             return true;
         }else{
             return false;
         }
     }
     public Connection getConexcionActual(){
         return conexion;
     }
}