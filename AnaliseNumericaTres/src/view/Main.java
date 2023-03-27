/**
 * @author igorsssantana
 */
package view;

import java.util.ArrayList;
import model.MetodoNewtonRaphson;

public class Main {
    /* Objeto com escopo de classe */
    public static MetodoNewtonRaphson objetoNewton;
    
    /**
     * 
     * @param xAnterior
     * @return 
     */
    public static double funcao(/*String funcao, */ double xAnterior){
        double resultado = Math.pow(xAnterior, 2) + (2 * xAnterior) - 4;
        return resultado;
    }
    
    /**
     * 
     * @param xAnterior
     * @return 
     */
    public static double derivada(/*String derivada, */ double xAnterior){
        double resultado = (2 * xAnterior) + 2;
        return resultado;
    }
    
    public static void metodoNewtonRaphson(/*String funcao, String derivada, */int maximoDeIteracoes){
        int contador = 0;
        
        do{
            objetoNewton.setFxAnterior(
                funcao(
                        objetoNewton.getxAnterior()
                )
            );
            objetoNewton.setDerivadaFxAnterior(
                    derivada(
                            objetoNewton.getxAnterior()
                    )
            );
            objetoNewton.gerarNovoXk();

            contador++;
        } while( objetoNewton.getxAtual() >= objetoNewton.getTolerancia() && contador < maximoDeIteracoes );
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String> conteudoLido;
        String funcao = "",
                derivada = "";
        int maximoDeIteracoes = 10;
        double  xAnterior = 0.0,
                xAtual = 0.0,
                tolerancia = 0.0;
        
        objetoNewton = new MetodoNewtonRaphson(xAnterior, tolerancia);
        metodoNewtonRaphson(maximoDeIteracoes);
        
        System.out.println("Resultado = " + objetoNewton.getxAtual());
    } // Fim de metodo main
} // Fim de classe Main