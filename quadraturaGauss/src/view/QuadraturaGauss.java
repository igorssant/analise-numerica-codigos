package view;

import controller.Funcao;
import controller.MudancaVariaveis;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Double.NaN;
import java.util.ArrayList;
import model.Integral;
import model.TabelaLegendre;
import utils.LidarComArquivo;

/**
 * @author igorsssantana
 */
public class QuadraturaGauss{
    /* ESCOPO DE CLASSE */
    private static Integral integral;
    private static TabelaLegendre tabelaLegendre;
    
    /**
     * Nao possui retorno
     * recebe como parametro
     * a funcao original
     * @param funcao 
     */
    public static void QuadraturaDeGauss(String funcao){
        String novoX = MudancaVariaveis.novoX(integral.getLimiteInferior(), integral.getLimiteSuperior());
        
        funcao = MudancaVariaveis.contatenaFuncaoString(funcao, novoX);
        System.out.println(funcao);
        funcao = MudancaVariaveis.novoDx(funcao, integral.getLimiteInferior(), integral.getLimiteSuperior());
        integral.setIntegralCalculada(0f);
        
        System.out.println(funcao);
        
        for(int i = 0; i < tabelaLegendre.getQuantidadePontos(); i++){
            integral.setIntegralCalculada(
                    integral.getIntegralCalculada() +
                    tabelaLegendre.getPeso(i) * Funcao.funcao(
                            funcao,
                            tabelaLegendre.getArgumento(i)
                    )
            );
        }
    }
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, NumberFormatException{
        ArrayList<String> conteudoDoArquivo;
        String funcao = "";
        int qtdPontos = 0;
        double limiteInferior = 0f,
                limiteSuperior = 0f;
        double[] pontos = null;
        
        try{
            conteudoDoArquivo = new ArrayList<>(LidarComArquivo.lerDeArquivo());
            funcao = conteudoDoArquivo.get(0);
            qtdPontos = Integer.parseInt(conteudoDoArquivo.get(1));
            limiteInferior = Double.parseDouble(conteudoDoArquivo.get(2));
            limiteSuperior = Double.parseDouble(conteudoDoArquivo.get(3));
            pontos = new double[qtdPontos];
            
            for(int i = 0; i < qtdPontos; i++){
                pontos[i] = Double.parseDouble(conteudoDoArquivo.get(i+4));
            }
            
            conteudoDoArquivo.clear();
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
        
        integral = new Integral(limiteInferior, limiteSuperior, pontos);
        tabelaLegendre = new TabelaLegendre(qtdPontos);
        QuadraturaDeGauss(funcao);
        
        try{
            LidarComArquivo.escreverEmArquivo(integral.getIntegralCalculada());
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