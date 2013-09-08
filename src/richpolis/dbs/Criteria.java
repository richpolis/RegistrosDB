/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package richpolis.dbs;

import richpolis.objetos.JRpFunGen;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.sql.*;

/**
 *
 * @author ralcanta
 */
public class Criteria {
    
    protected String sTipoBaseDatos;

    protected String sQuery;
    protected String sInstruccion;
    private int contInstruccion;
    protected String sFrom;
    protected String sWhere;
    protected String sGroupBy;
    protected String sOrderBy;
    protected int TipoQuery;
    
    public final static int eQuerySelect=0;
    public final static int eQueryInsert=1;
    public final static int eQueryUpdate=2;
    public final static int eQueryDelete=3;
        
    public final static int eDataString=0;
    public final static int eDataNumeric=1;
    public final static int eDataDate=2;
    public final static int eDataDouble=3;
    public final static int eDataMoneda=4;
    public final static int eDataBool=5;
    public final static int eDataEmpty=6;
    public final static int eDataEspecial=7;
    public final static int eDataPorcentaje=8;

    public Criteria() {
        //this.sTipoBaseDatos="ACCESS";
    	this.sTipoBaseDatos="MySQL";
        this.sQuery = "";
        this.sInstruccion = "";
        this.contInstruccion = 0;
        this.sFrom = "";
        this.sWhere = "";
        this.sGroupBy = "";
        this.sOrderBy = "";
        this.TipoQuery = Criteria.eQuerySelect;
    }

    public Criteria(String sTipoBaseDatos, String sInstruccion, String sFrom) {
        this.sTipoBaseDatos = sTipoBaseDatos;
        this.sInstruccion = sInstruccion;
        this.sFrom = sFrom;
        this.TipoQuery = Criteria.eQuerySelect;
    }


    public int getTipoQuery() {
        return TipoQuery;
    }

    public void setTipoQuery(int TipoQuery) {
        this.TipoQuery = TipoQuery;
    }

    public String getTipoBaseDatos() {
        return sTipoBaseDatos;
    }
    public void setTipoBaseDatos(String sTipoBaseDatos) {
       this.sTipoBaseDatos = sTipoBaseDatos;
    }
    ////////////////////// functions Set //////////////////////
    public void setParamters(String[] ArregloDatos){
        String ArregloParametro[]={"",""};
        int largo =ArregloDatos.length;
        if(largo>=1){
            this.sQuery="";
            this.getQuery();
            for(int cont=0;cont<=largo-1;cont++){
                ArregloParametro=ArregloDatos[cont].split(":=");
                this.sQuery=this.sQuery.replaceAll(ArregloParametro[0],ArregloParametro[1]);
            }
        }
    }
    public void setInstruccion(String cadena){
        String[] Arreglo=new String[1];
        Arreglo[0]=cadena;
        this.setInstruccion(Arreglo);
    }
    public void setInstruccion(String[] ArregloDatos){
        int largo =ArregloDatos.length;
        this.sQuery = "";
        if(largo>=1){
            for(int cont=0;cont<=largo-1;cont++){
                switch(this.TipoQuery){
                    case Criteria.eQuerySelect:
                        if(ArregloDatos[cont].length()>0){
                            if(this.contInstruccion==0){
                                this.sInstruccion="SELECT " + ArregloDatos[cont];
                                this.contInstruccion++;
                            }else{
                                this.sInstruccion+= ", " + ArregloDatos[cont];
                                this.contInstruccion++;
                            }
                        }
                        break;
                    case Criteria.eQueryDelete:
                        if(ArregloDatos[cont].length()>0){
                            if(this.contInstruccion==0){
                                this.sInstruccion="DELETE ";
                                this.contInstruccion++;
                            }
                        }
                        break;
                   case Criteria.eQueryInsert:
                       if(ArregloDatos[cont].length()>0){
                            if(this.contInstruccion==0){
                                this.sInstruccion= "INSERT INTO " + ArregloDatos[cont] + " (";
                                this.contInstruccion++;
                            }else if(this.contInstruccion==1){
                                this.sInstruccion+=ArregloDatos[cont] + ") VALUES (";
                                this.contInstruccion++;
                            }else if(this.contInstruccion==2){
                                this.sInstruccion+=ArregloDatos[cont] + ")";
                                this.contInstruccion++;
                            }
                        }
                        break;
                    case Criteria.eQueryUpdate:
                        if(ArregloDatos[cont].length()>0){
                            if(this.contInstruccion==0){
                                this.sInstruccion= "UPDATE " + ArregloDatos[cont] + " SET ";
                                this.contInstruccion++;
                            }else if(this.contInstruccion==1){
                                this.sInstruccion+=ArregloDatos[cont];
                                this.contInstruccion++;
                            }else{
                                this.sInstruccion+= ", " + ArregloDatos[cont];
                                this.contInstruccion++;
                            }
                        }
                        break;
                    default:
                        if(ArregloDatos[cont].length()>0){
                            if(this.contInstruccion==0)
                                this.sInstruccion="TRUNCATE " + ArregloDatos[cont];


                    }
                }
            }
        }
    }
    public void setFrom(String cadena){
        String[] Arreglo=new String[1];
        Arreglo[0]=cadena;
        this.setFrom(Arreglo);
    }
    public void setFrom(String[] ArregloDatos){
        int largo = ArregloDatos.length;
        this.sQuery="";
        if(largo>=1){
            for(int cont=0;cont<=largo-1;cont++){
                if(ArregloDatos[cont].length()>0){
                    if(this.sFrom.length()==0){
                        this.sFrom = " FROM " + ArregloDatos[cont];
                    }else{
                        this.sFrom+=", " + ArregloDatos[cont];
                    }
                }
            }
        }
    }
    public void setWhere(String cadena){
        String[] Arreglo=new String[1];
        Arreglo[0]=cadena;
        this.setWhere(Arreglo);
    }
    public void setWhere(String[] ArregloDatos)throws ArrayIndexOutOfBoundsException{
        //int largo = ArregloDatos.length;
        if(this.sWhere.length()>0){
            if(this.sWhere.charAt(this.sWhere.length()-1)==')'){
                this.sWhere=this.sWhere.substring(0, this.sWhere.length()-1);
            }
        }
        this.sQuery="";
        if(ArregloDatos.length>=1){
            for(int cont=0;cont<=ArregloDatos.length-1;cont++){
                if(ArregloDatos[cont].length()>0){
                    if(this.sWhere.length()!=0){
                        this.sWhere+=" AND " + ArregloDatos[cont];
                    }else{
                        this.sWhere=" WHERE (" + ArregloDatos[cont];
                    }
                }
            }
            if(this.sWhere.length()!=0){
                this.sWhere+=")";
            }
        }
    }
    public void setGroupBy(String cadena){
        String[] Arreglo=new String[1];
        Arreglo[0]=cadena;
        this.setGroupBy(Arreglo);
    }
    public void setGroupBy(String[] ArregloDatos){
        int largo = ArregloDatos.length;
        this.sQuery="";
        if(largo>=1){
            for(int cont=0;cont<=largo-1;cont++){
                if(ArregloDatos[cont].length()>0){
                    if(this.sGroupBy.length()!=0){
                        this.sGroupBy+=", " + ArregloDatos[cont];
                    }else{
                        this.sGroupBy=" GROUP BY " + ArregloDatos[cont];
                    }
                }
            }
        }
    }
    public void setOrderBy(String cadena){
        String[] Arreglo=new String[1];
        Arreglo[0]=cadena;
        this.setOrderBy(Arreglo);
    }
    public void setOrderBy(String[] ArregloDatos){
        int largo = ArregloDatos.length;
        this.sQuery="";
        if(largo>=1){
            for(int cont=0;cont<=largo-1;cont++){
                if(ArregloDatos[cont].length()>0){
                    if(this.sOrderBy.length()!=0){
                        this.sOrderBy+=", " + ArregloDatos[cont];
                    }else{
                        this.sOrderBy=" ORDER BY " + ArregloDatos[cont];
                    }
                }
            }
        }
    }
    //////////////////functions Add///////////////////////
    public String getWhereCampos(String sTablaCampo,String sValor){
        return this.getWhereCampos(sTablaCampo, sValor, "=", Criteria.eDataString, "");
    }
    public String getWhereCampos(String sTablaCampo,String sValor,String sOperador){
        return this.getWhereCampos(sTablaCampo, sValor, sOperador, Criteria.eDataString, "");
    }
    public String getWhereCampos(String sTablaCampo,String sValor,String sOperador,int TipoVar){
        return this.getWhereCampos(sTablaCampo, sValor, sOperador, TipoVar, "");
    }
    public String getWhereCampos(String sTablaCampo, String sValor, String sOperador, int TipoVar, String sValor2){
        String Resultado=new String();
        if(sOperador.length()==0){
            sOperador="=";
        }
        if(TipoVar==0){
            TipoVar=Criteria.eDataString;
        }
        if(sValor.compareTo(JRpFunGen.TODAS)!=0 || sValor.compareTo(JRpFunGen.TODOS)!=0){
            switch(TipoVar){
                case Criteria.eDataString:
                    if(sValor.length()>0){
                        if((sOperador.compareTo("LIKE")==0) || (sOperador.compareTo("NOT LIKE")==0)){
                            Resultado = sTablaCampo + " " + sOperador + " \'" + sValor + "\'";
                        }else{
                            Resultado = sTablaCampo + sOperador + "\'" + sValor + "\'";
                        }
                    }
                    break;
                case Criteria.eDataDouble:
                case Criteria.eDataNumeric:
                    if(sValor.compareTo("")!=0){
                        Resultado = sTablaCampo + sOperador + sValor;
                    }
                    break;
                case Criteria.eDataDate:
                    if(sValor.compareTo("")!=0){
                        if(sOperador.compareTo("BETWEEN")==0){
                            Resultado = sTablaCampo + "BETWEEN \'" + sValor + "\' TO \'" + sValor2 + "\'";
                        }else{
                            Resultado = sTablaCampo + sOperador + "\'" + sValor + "\'";
                        }
                    }
                case Criteria.eDataMoneda:
                    if(sValor.compareTo("")!=0){
                        Resultado = sTablaCampo + sOperador + JRpFunGen.removerFormatoMoneda(sValor);
                    }
                case Criteria.eDataBool:
                    if(sValor.compareTo("")!=0){
                        if(this.sTipoBaseDatos.compareTo("ACCESS")==0){
                            if(sValor.compareTo("1")==0){
                                Resultado = sTablaCampo + sOperador + "True";
                            }else{
                                Resultado = sTablaCampo + sOperador + "False";
                            }
                        }else{
                            Resultado = sTablaCampo + sOperador + sValor;
                        }
                    }
                default: //eDataQuerys.dataEmpty
                    if(sOperador.compareTo("IS NULL")==0 || sOperador.compareTo("IS NOT NULL")==0){
                        Resultado = sTablaCampo + " " + sOperador;
                    }else{
                        Resultado = "";
                    }
            }
        }else{
            Resultado = "";
        }
        return Resultado;
    }
    public void getWhereCamposMulti(String sTablaCampo,String CadenaValues){
        this.getWhereCamposMulti(sTablaCampo, CadenaValues, ",", "=", Criteria.eDataString, "");
    }
    public void getWhereCamposMulti(String sTablaCampo,String CadenaValues,String sSeparador){
        this.getWhereCamposMulti(sTablaCampo, CadenaValues, sSeparador, "=", Criteria.eDataString, "");
    }
    public void getWhereCamposMulti(String sTablaCampo,String CadenaValues,String sOperador,int TipoVar){
        this.getWhereCamposMulti(sTablaCampo, CadenaValues, ",", sOperador, TipoVar, "");
    }
    public void getWhereCamposMulti(String sTablaCampo, String CadenaValues, String sSeparador, String sOperador, int TipoVar,String sValor2){
        int cont;
        String CadenaOr =new String();
        String[] Arreglo=CadenaValues.split(sSeparador);
        int largo = Arreglo.length;
        if(largo>=1){
            for(cont=0;cont<=largo-1;cont++){
                if(CadenaOr.compareTo("")!=0){
                    CadenaOr+=" OR " + this.getWhereCampos(sTablaCampo, Arreglo[cont], sOperador, TipoVar, sValor2);
                }else{
                    CadenaOr = this.getWhereCampos(sTablaCampo, Arreglo[cont], sOperador, TipoVar, sValor2);
                }
            }
            if(CadenaOr.compareTo("")!=0){
                if(cont==1){
                    this.setWhere(CadenaOr);
                }else if(cont>1){
                    this.setWhere("(" + CadenaOr + ")");
                }
            }
        }
    }
    public void getWhereCamposArray(String cadena){
        String[] Arreglo=new String[1];
        Arreglo[0]=cadena;
        this.getWhereCamposArray(Arreglo);
    }
    public void getWhereCamposArray(String[] ArregloDatos){
        int largo=0, cont=0;
        String CadenaOr = new String();
        largo = ArregloDatos.length;
        if(largo>1){
            for(cont=0;cont<=largo-1;cont++){
                if(ArregloDatos[cont].compareTo("")!=0){
                    if(CadenaOr.compareTo("")!=0){
                        CadenaOr += " OR " + ArregloDatos[cont];
                    }else{
                        CadenaOr = ArregloDatos[cont];
                    }
               }
            }
            if(CadenaOr.compareTo("")!=0){
                if(cont==1){
                    this.setWhere(CadenaOr);
                }else if(cont>1){
                    this.setWhere("(" + CadenaOr + ")");
                }
            }
        }
    }
    public String getValores(String sValor){
        return this.getValores(sValor, Criteria.eDataString);
    }
    public String getValores(String sValor, int TipoDato){
        String Resultado=new String();
        switch(TipoDato){
            case Criteria.eDataString:
                if(sValor!=null && sValor.compareTo("")!=0){
                    Resultado = "\'" + sValor + "\'";
                }else{
                    Resultado = "\'\'";
                }
                break;
            case Criteria.eDataEspecial:
                if(sValor!=null && sValor.compareTo("")!=0){
                    Resultado= "\'" + JRpFunGen.removerCaracter(sValor,'\'') + "\'";
                }else{
                    Resultado = "\'\'";
                }
            case Criteria.eDataDate:
                if(sValor!=null){
                    if(this.sTipoBaseDatos.compareTo("MYSQL")==0){
                       SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                        try{
                            Resultado="\'" + sd.format(Date.parse(sValor)) + "\'";
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }else if(this.sTipoBaseDatos.compareTo("ACCESS")==0){
                       SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
                       try{
                            Resultado = "#" + sd.format(Date.parse(sValor)) + "#";
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                   }
                }else{
                    Resultado = "NULL";
                }
            case Criteria.eDataMoneda:
                if(sValor!=null){
                    Resultado = JRpFunGen.removerFormatoMoneda(sValor);
                }else{
                    Resultado = "0.00";
                }
            case Criteria.eDataPorcentaje:
                if(sValor!=null){
                    Resultado = JRpFunGen.removerFormatoPorcentaje(sValor);
                }else{
                    Resultado = "00";
                }
            default:
                if(sValor!=null){
                    Resultado = sValor;
                }else{
                    Resultado = "0";
                }
        }
        return Resultado;
    }
    public String getCampos(String cadena){
        String[] Arreglo=new String[1];
        Arreglo[0]=cadena;
        return this.getCampos(Arreglo);
    }
    public String getCampos(String[] ArregloDatos){
        int largo=0,cont=0;
        String Resultado=new String();
        largo = ArregloDatos.length;
        if(largo>=1){
            for(cont=0;cont<=largo-1;cont++){
                if(ArregloDatos[cont].compareTo("")!=0){
                    if(Resultado.compareTo("")!=0){
                        Resultado += "," + ArregloDatos[cont];
                    }else{
                        Resultado = ArregloDatos[cont];
                    }
                }
            }
        }
        return Resultado;
    }
    public String getDistinct(String TablaCampo){
        return "DISTINCT " + TablaCampo;
    }
    public String getFuncion(String Funcion, String TablaCampo,String Alias){
        if(Alias.compareTo("")==0)
            Alias = TablaCampo.split(".")[1];

        return Funcion + "(" + TablaCampo + ") as " + Alias;
    }
    public String getCount(String TablaCampo, String Alias){
        if(Alias.compareTo("")==0)
            Alias = TablaCampo.split(".")[1];

        return "COUNT(" + TablaCampo + ") as " + Alias;
    }
    public String getValorWithAlias(String valor, String Alias){
        return valor + " as " + Alias;
    }
    public String getSubConsulta(String TablaCampo, String Operador, String SubConsulta){
        return TablaCampo + Operador + "(" + SubConsulta + ")";
    }
    ////////////////////////// functions Get ////////////////////////
    /**
     * @return String con el Query formado.
     */
    public String getQuery(){
        if(this.sQuery.compareTo("")==0)
            this.sQuery = this.sInstruccion + this.sFrom + this.sWhere + this.sGroupBy + this.sOrderBy;

        return this.sQuery;
    }
    ////////////////////// funciones de la base de datos //////////////////
    /*
    public boolean ejecutar(){
        boolean Resultado=false;
        try{
            switch(this.getTipoQuery()){
                case Criteria.eQuerySelect:
                    this.conOdbc.ejecutarSelect(this.getQuery());
                    Resultado=true;
                    break;
                default:
                    this.conOdbc.ejecutarUpdate(this.getQuery());
                    Resultado=true;
                    break;

            }
            
        }catch(SQLException e){
            e.printStackTrace();
            Resultado=false;
        }
        return Resultado;
    }*/

    /*
    private void abrirConexcionQueryString(boolean bConectionPermamente) throws InstantiationException, IllegalAccessException{
        if(bConectionPermamente){
            //try{
                if(!this.ConectionOk){
                    if(this.getTipoBaseDatos().compareTo("ACCESS")==0){
                        this.conOdbc=new ConexcionOdbc("AccessMasterItem","","");
                    }else if(this.getTipoBaseDatos().compareTo("MYSQL")==0){
                        this.conOdbc=new ConexcionOdbc("myodbc","root","");
                    }
                }
                this.setConectionOk(this.conOdbc.abrirConexionPersistente());
                this.setConectionPermamente(true);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
                this.setConectionOk(false);
            }catch(SQLException e){
                e.printStackTrace();
                this.setConectionOk(false);
            }
        }else{
            //try{
                if(this.getTipoBaseDatos().compareTo("ACCESS")==0){
                    this.conOdbc=new ConexcionOdbc("AccessMasterItem","","");
                }else if(this.getTipoBaseDatos().compareTo("MYSQL")==0){
                    this.conOdbc=new ConexcionOdbc("myodbc","root","");
                }
                this.setConectionOk(this.conOdbc.abrirConexionPersistente());
                this.setConectionPermamente(false);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
                this.setConectionOk(false);
            }catch(SQLException e){
                e.printStackTrace();
                this.setConectionOk(false);
            }
        }
    }
    */
    /*
    public void cerrarConexcionDB(){
        this.conOdbc.cerrarConexionPersistente();
    }
    public boolean abrirConexcion() throws InstantiationException, IllegalAccessException{
        return this.abrirConexcion(false);
    }
    public boolean abrirConexcion(boolean bPermanente) throws InstantiationException, IllegalAccessException{
        this.abrirConexcionQueryString(bPermanente);
        return this.ConectionOk;
    }

    public ResultSet getRs(){
        return this.conOdbc.getRs();
    }
     *
     */
}
