# favor rodar a linha abaixo sempre que iniciar o R-studio
#setwd("~/Documents/trabalho/codigosR/matrizesMalCondicionadas")

source("~/Documents/trabalho/codigosR/matrizesMalCondicionadas/LU.r")

lerDeArquivo <- function(arquivoDeLeitura){
  dadosLidos <- as.matrix(read.table(arquivoDeLeitura, sep = " ", dec = ".", header = F))
  return(dadosLidos)
}

# funcao para escrever o resultado final em aquivo .txt
escreverEmArquivo <- function(arquivoDeEscrita, dados){
  write.table(x = dados, file = arquivoDeEscrita, sep = "\t", append = F, row.names = F, col.names = F)
}

criarIdentidade<- function(matriz){
  colunas <- ncol(matriz)
  linhas <- nrow(matriz)
  
  if(linhas != colunas){
    print("Não é uma matriz da forma NxN")
    return(F)
  }
  
  # Matriz de zeros
  matrizIdentidade <- matrix(rep(0, (linhas * colunas)), nrow = linhas, ncol = colunas)
  # Insere 1 na diagonal principal
  diag(matrizIdentidade) <- rep(1, linhas)
  
  return(matrizIdentidade)
}

main <- function(){
  arquivoLeitura <- "entradaDeDados.txt"
  arquivoEscrita <- "saidaDeDados.txt"
  
  dadosLidos <- lerDeArquivo(arquivoLeitura)
  matrizParaAnalizar <- dadosLidos                        # matriz NxN
  matrizIdentidade <- criarIdentidade(matrizParaAnalizar) # matriz NxN
  # lista de matrizes NxX
  listaLU <- decomposicaoLU(matrizParaAnalizar, matrizParaAnalizar[, 1])
  
  matrizResolucao <- matrix(rep(0, (ncol(matrizParaAnalizar) * nrow(matrizParaAnalizar))),
                            nrow = nrow(matrizParaAnalizar),
                            ncol = ncol(matrizParaAnalizar))
  
  for( i in 1:nrow(matrizParaAnalizar) ){
    matrizResolucao[, i] <- resolucaoLU(listaLU, matrizIdentidade[, i])
  }
  
  escreverEmArquivo(arquivoEscrita, matrizResolucao)
}

main()
