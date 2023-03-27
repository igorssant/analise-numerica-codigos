/**
 * @author igorsssantana
 */
package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import model.Bissexao;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import utils.LidarComArquivo;

public class Main{
    /* Atributo com escopo de classe */
    public static Bissexao objetoBissexao;
    
    /**
     * Metodo para calcular f(entrada)
     * Redebe dois parametros: funcao e entrada para a funcao.
     * A funcao deve ser String.
     * A entrada deve ser Double.
     * Retorna f(entrada).
     * @param modeloFuncao
     * @param modeloArgumento
     * @return 
     */
    public static double funcao(String modeloFuncao, Double modeloArgumento){
        Function funcao = new Function(modeloFuncao);
        Argument argumento = new Argument("x = " + Double.toString(modeloArgumento));
        Expression instancia = new Expression("f(x)", funcao, argumento);
        return instancia.calculate();
    }
    
    /**
     * Esse metodo manipula o objeto com
     * escopo de classe objetoBissexao.
     * Com ele, sera gerado a melhor estimativa possivel.
     * Recebe o Limite de Interacoes.
     * Nao retorna nenhum resultado.
     * @param limiteDeInteracoes
    */
    public static void bissexao(int limiteDeInteracoes, String funcao){
        int contador = 0;
        double verificaMudancaDeSinal = 0.0;
        
        objetoBissexao.gerarEstimativa();
        objetoBissexao.gerarErro();
        objetoBissexao.setFAnterior(
                funcao(
                        funcao,
                        objetoBissexao.getIntervaloInferior()
                )
        );
        objetoBissexao.setFAtual(0.0);
        
        if(objetoBissexao.getErro() < 0.0){
            objetoBissexao.setErro( objetoBissexao.getErro() * (-1) );
        }
        
        while( objetoBissexao.getErro() >= objetoBissexao.getTolerancia() && contador < limiteDeInteracoes ){
            objetoBissexao.setFAtual(
                    funcao(
                            funcao,
                            objetoBissexao.getEstimativa()
                    )
            );
            verificaMudancaDeSinal = objetoBissexao.getFAnterior() * objetoBissexao.getFAtual();
            
            if(verificaMudancaDeSinal < 0.0){
                objetoBissexao.setIntervaloSuperior(objetoBissexao.getEstimativa());
            }
            
            else if(verificaMudancaDeSinal > 0.0){
                
            }
            
            else{
                break;
            }
            
            objetoBissexao.gerarErro();
            
            if(objetoBissexao.getErro() < 0.0){
                objetoBissexao.setErro( objetoBissexao.getErro() * (-1) );
            }
            
            objetoBissexao.gerarEstimativa();
            contador++;
        }
    } // Fim de metodo bissexao
    
    /* O ponto chave aqui é a estimativa!!! */
    public static void main(String[] args) throws IOException, FileNotFoundException, NumberFormatException{
        ArrayList<String> conteudoDoArquivo;
        String funcao = "",
                auxiliar = "";
        int limiteDeInteracoes = 10;
        double intervaloSuperior = 0.0,
                intervaloInferior = 0.0,
                tolerancia = 0.0;
        
        try{
            conteudoDoArquivo = new ArrayList<>(LidarComArquivo.lerDeArquivo());
            funcao = conteudoDoArquivo.get(0);
            intervaloSuperior = Double.parseDouble(conteudoDoArquivo.get(1));
            intervaloInferior = Double.parseDouble(conteudoDoArquivo.get(2));

            /* so executa abaixo se a lista retornada tiver tamanho */
            if(conteudoDoArquivo.size() > 3){
                limiteDeInteracoes = Integer.parseInt(conteudoDoArquivo.get(3));
            }
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
        
        objetoBissexao = new Bissexao(intervaloSuperior, intervaloInferior, tolerancia);
        bissexao(limiteDeInteracoes, funcao);
        
        try{
            LidarComArquivo.escreverEmArquivo(objetoBissexao.getEstimativa());
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
    } // Fim de metodo main
} // Fim de classe Main
