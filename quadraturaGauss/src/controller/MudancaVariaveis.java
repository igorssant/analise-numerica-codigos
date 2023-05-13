package controller;
/**
 * @author igorsssantana
 */
public class MudancaVariaveis{
    public static String novoX(double limiteInferior, double limiteSuperior){
        double parte1 = (limiteSuperior + limiteInferior)/ 2,
                parte2 = (limiteSuperior - limiteInferior) / 2;
        return (new StringBuilder()).append(" * (").append(parte1 + " + ").append(parte2).append("x").append(")").toString();
    }
    
    public static String novoDx(String funcao, double limiteInferior, double limiteSuperior){
        double novoDx = (limiteSuperior - limiteInferior) / 2;
        String novaFuncao = funcao.substring(7);
        /*
         * Cortar fora esta parte abaixo
         * 0123456
         * f(x) = 
        */
        return (new StringBuilder()).append("f(x) = (").append(novaFuncao).append(") * " + novoDx).toString();
    }
    
    public static String contatenaFuncaoString(String funcao, String novaString){
        String auxiliar = funcao.substring(7);
        funcao = (new StringBuilder()).append("f(x) = ").append(auxiliar.replaceAll("x", novaString)).toString();
        return funcao;
    }
}