/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package richpolis.objetos;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ralcanta
 */
public class JRpFunGen {
	public final static String TODAS    ="[TODAS]";
    public final static String TODOS    ="[TODOS]";
    public final static String NUEVA    ="[NUEVA]";
    public final static String GLOBAL   ="GLOBAL";
    public final static String LOCAL    ="LOCAL";
    public final static String SEPARADOR_VALORES=".- ";
    public final static String SUCURSAL_EN_CATALOGO="En Catalogo";
    public final static String SUCURSAL_NUEVA="Nueva";

    static public String removerFormatoMoneda(String sValor){
        char[] cadena=sValor.toCharArray();
        String Resultado=new String();
        for(int cont=0;cont<=cadena.length-1;cont++){
            if(cadena[cont]!='$' && cadena[cont]!=','){
                Resultado+=String.valueOf(cadena[cont]);
            }
        }
        return Resultado;
    }
    static public String removerFormatoPorcentaje(String sValor){
        return JRpFunGen.removerCaracter(sValor, '%');
    }
    static public String agregarFormatoPorcentaje(String sValor){
        return sValor+"%";
    }
    static public String removerCaracter(String sValor,char caracter){
        char[] cadena=sValor.toCharArray();
        String Resultado=new String();
        for(int cont=0;cont<=cadena.length-1;cont++){
            if(cadena[cont]!=caracter){
                Resultado+=String.valueOf(cadena[cont]);
            }
        }
        return Resultado;
    }

    static public String[] getStringArrayJoinArrays(String[] datos1, String[] datos2) {
        String[] resultado=new String[datos1.length+datos2.length];
        int cont=0,cont2=0;
        for(;cont<datos1.length;cont++){
            resultado[cont]=datos1[cont];
        }
        for(;cont2<datos2.length;cont2++){
            resultado[cont++]=datos2[cont2];
        }
        return resultado;
    }
    
    static public String getValueForString(String cadena,String separador){
        if(cadena.compareTo("")!=0){
            if(separador.compareTo("")!=0)
                return cadena.split(separador)[0];
            else
                return cadena.split(",")[0];
        }else{
            return "";
        }
    }
    static public int getIndexCbForString(DefaultComboBoxModel modelo,String cadena){
        return JRpFunGen.getIndexCbForString((ComboBoxModel)modelo, cadena);
    }

    public static int getIndexCbForString(ComboBoxModel modelo, String cadena) {
        int cont=0,resp=-1;
        String elemento;
        for(;cont<modelo.getSize();cont++){
            elemento=(String)modelo.getElementAt(cont);
            if(elemento.compareTo(cadena)==0){
                resp=cont;
                break;
            }
        }
        return resp;
    }
}
