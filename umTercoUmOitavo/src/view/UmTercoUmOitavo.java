package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import model.TresOitavos;
import model.UmTerco;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import utils.LidarComArquivo;

/**
 * @author igorsssantana
 */
public class UmTercoUmOitavo{
    /* ESCOPO DE CLASSE */
    private static UmTerco umTerco;
    private static TresOitavos tresOitavos;
    
    /**
     * Recebe uma funcao em String
     * e um ponto x
     * retorna f(x)
     * @param stringFuncao
     * @param x
     * @return fx = funcao(funcao, derivadanumerica.getPontosX((int) (posicaoDoPonto + derivadanumerica.getH())));
     */
    public static double funcao(String stringFuncao, double x){
        Function funcao = new Function(stringFuncao);
        Argument argumento = new Argument("x = " + Double.toString(x));
        Expression instancia = new Expression("f(x)", funcao, argumento);
        return instancia.calculate();
    }
    
    public static void calculaIntegralUmTerco(String funcao){
        if(umTerco.getH() == 0f){
            umTerco.gerarNovoH();
        }
        
        umTerco.setIntegralCalculada(
            funcao(funcao, umTerco.getPontos(0))
            + funcao(funcao, umTerco.getPontos(1))
            + funcao(funcao, umTerco.getPontos(2))
        );
        umTerco.setIntegralCalculada(
            umTerco.getIntegralCalculada() * (umTerco.getH() / 3)
        );
    }
    
    /**
     * @param funcao 
     */
    public static void calculaIntegralTresOitavos(String funcao){
        if(tresOitavos.getH() == 0f){
            tresOitavos.gerarNovoH();
        }
        
        tresOitavos.setIntegralCalculada(
            (3f / 8f) * tresOitavos.getH()
            * (
                funcao(funcao, tresOitavos.getPontos(0))
                + 3 * (funcao(funcao, tresOitavos.getPontos(1)))
                + 3 * (funcao(funcao, tresOitavos.getPontos(2)))
                + funcao(funcao, tresOitavos.getPontos(3))
            )
        );
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, NumberFormatException{
        ArrayList<String> conteudoDoArquivo = null;
        String funcao = "";
        double integralResultante = 0f;
        double [] pontosUmTerco = new double[3],
                pontosTresOitavos = new double[4];
        
        try{
            conteudoDoArquivo = new ArrayList<>(LidarComArquivo.lerDeArquivo());
            funcao = conteudoDoArquivo.get(0);
            
            for(int i = 0; i < 3; i++){
                pontosUmTerco[i] = Double.parseDouble(conteudoDoArquivo.get(i+1));
                pontosTresOitavos[i] = Double.parseDouble(conteudoDoArquivo.get(i+4));
            }
            
            pontosTresOitavos[3] = Double.parseDouble(conteudoDoArquivo.get(7));
            conteudoDoArquivo = null;
        }
        
        catch(FileNotFoundException e){
            System.out.println("Não foi possível encontrar o arquivo no caminho passado.\nPor favor digite o caminho correto.");
        }
        
        catch(IOException e){
            System.out.println("Não foi possível ler o conteúdo do arquivo.\nPor favor coloque as permissões corretas, ou o caminho correto.");
        }
        
        catch(NumberFormatException e){
            System.out.println("Ocorreu um erro durante a leitura do arquivo.\nErro de conversão de tipos.");
        }
        
        catch(Exception e){
            System.out.println("1- Ocorreu um erro inesperado!\nPor favor tente novamente.");
        }
        
        umTerco = new UmTerco(pontosUmTerco);
        umTerco.gerarNovoH();
        tresOitavos = new TresOitavos(pontosTresOitavos, umTerco.getH());
        calculaIntegralUmTerco(funcao);
        calculaIntegralTresOitavos(funcao);
        integralResultante = umTerco.getIntegralCalculada() + tresOitavos.getIntegralCalculada();
        
        try{
            LidarComArquivo.escreverEmArquivo(integralResultante);
        }
        
        catch(FileNotFoundException e){
            System.out.println("Não foi possível encontrar o arquivo no caminho passado.\nPor favor digite o caminho correto.");
        }
        
        catch(IOException e){
            System.out.println("Não foi possível escrever o conteúdo do arquivo.\nPor favor coloque as permissões corretas, ou o caminho correto.");
        }
        
        catch(NumberFormatException e){
            System.out.println("Ocorreu um erro durante a escrita do arquivo.\nErro de conversão de tipos.");
        }

        catch(Exception e){
            System.out.println("2- Ocorreu um erro inesperado!\nPor favor tente novamente.");
        }
        
        System.out.println("Finalizado com sucesso!!!");
    }
}