package richpolis.dbs;

import java.io.IOException;
import java.sql.*;

/*
 * Debes debez de crear una base de datos en access con las siguientes:
 * datos=sDSN de la tabla
 * id= campo autoincrementable
 * sDSN=tipo string
 * sexo=tipo String
 * edad=tipo entero
 * La guardas y listo, copias la direcion donde la guardas en la varible dir
 * Ver archvi doc para la con manual..
 */
class ConexcionOdbc{
    private static Connection conexcion=null;
    private static int contConections=0;
    private String sDSN="";//sDSN de la con

    private boolean bDebug=true;
    private boolean bConexcionOk=false;
    

    public ConexcionOdbc(String sDNS){
        this.sDSN=sDNS;

    }

    private ConexcionOdbc() {
       this.sDSN="Driver={Microsoft Access Driver (*.mdb, *.accdb)};Dbq=D:\\Comparte\\sf\\RegistrosDB\\db1.mdb;";
       //this.sDSN="FormMasterItemDBAccess";
    }

   
    
    //Abre la con con la base de datos
    public boolean abrirConexionPersistente(){
        try{
            if(ConexcionOdbc.contConections>0){
                if(!ConexcionOdbc.conexcion.isClosed()){
                    ConexcionOdbc.contConections++;
                    if(this.bDebug){
                        System.out.println("Abrio conexcion permamente :" + String.valueOf(ConexcionOdbc.contConections));
                    }
                    return true;
                }
            }
           Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");//carga el driver
           ConexcionOdbc.conexcion=DriverManager.getConnection("jdbc:odbc:"+ this.sDSN,"","");
           ConexcionOdbc.contConections++;
           if(this.bDebug){
              System.out.println("Abrio conexcion permamente :" + String.valueOf(ConexcionOdbc.contConections));
           }
           return true;
        }catch(Exception e){
           return false;
        }
    }
   

 //Cierra la con con la base de datos
    public boolean cerrarConexionPersistente(){
        try{
            if(!ConexcionOdbc.conexcion.isClosed()){
              ConexcionOdbc.contConections--;
            if(ConexcionOdbc.contConections==0){
                ConexcionOdbc.conexcion.close();
            }
          }
          if(this.bDebug){
            System.out.println("Cerro todos los objetos\n");
          }
          return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean isOpen() throws SQLException{
        return !ConexcionOdbc.conexcion.isClosed();
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

    public boolean cerrarConexcion(){
        return !this.cerrarConexionPersistente();
    }
     
     public static Connection getConexcion(){
         ConexcionOdbc conRp=new ConexcionOdbc();
         if(conRp.isHayConexcion()){
            return conRp.getConexcionActual();
         }else{
             if(conRp.isConexcionOk()){
                 return conRp.getConexcionActual();
             }else{
                if(conRp.abrirConexionPersistente()){
                     return conRp.getConexcionActual();
                 }else{
                     return null;
                 }
             }
         }
     }
     public boolean isHayConexcion(){
         if(ConexcionOdbc.conexcion!=null){
             return true;
         }else{
             return false;
         }
     }
     public Connection getConexcionActual(){
         return ConexcionOdbc.conexcion;
     }
}