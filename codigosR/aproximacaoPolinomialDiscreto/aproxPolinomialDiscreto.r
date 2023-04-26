setwd("~/Documents/trabalho/codigosR/aproximacaoPolinomialDiscreto")

source("~/Documents/trabalho/codigosR/aproximacaoPolinomialDiscreto/regressao.r")
source("~/Documents/trabalho/codigosR/aproximacaoPolinomialDiscreto/LU.r")
source("~/Documents/trabalho/codigosR/aproximacaoPolinomialDiscreto/normalizaMatriz.r")
source("~/Documents/trabalho/codigosR/aproximacaoPolinomialDiscreto/permutacaoLinha.r")

lerDeArquivo <- function(nomeArquivo="entradaDeDados.txt"){
  
}

escreverEmArquivo <- function(nomeArquivo="saidaDeDados.txt", dados=NaN){
  
}

erroPadrao <- function(soma_r, tamanho, nro_equacoes){
  erro <- sqrt( soma_r / (tamanho - (nro_equacoes + 1)) )
  
  return(erro)
}

regressaoPolinomial <- function(pontos, ordem){
  coeficientes <- matrix(1, nrow = (ordem+1), ncol = (ordem+1))
  matriz_expandida <- matrix(1, nrow = ncol(pontos), ncol = (ordem+1))
  y <- c( rep(1, (ordem+1) ) )
  
  for(linha in 1:ncol(matriz_expandida)){
    for(coluna in 1:nrow(matriz_expandida)){
      matriz_expandida[coluna, linha] <- pontos[1, coluna] ^ (linha - 1)
    }
  }
  
  for(i in 1:(ordem+1)){
    y <- pontos[2, ] %*% matriz_expandida#[, i]
  
    for(j in 1:(ordem+1)){
      coeficientes[i, j] <- matriz_expandida[, i] %*% matriz_expandida[, j]
    }
  }
  
  return(list(X = coeficientes, Y = y))
}

main <- function(){
  ordem_polinomio <- 2
  pontos <- matrix(c(-1, 0, 1, 2,
                      0, -1, 0, 7), ncol = 4, nrow = 2, byrow = T)
  
  if(ncol(pontos) < (ordem_polinomio + 1)){
    print("Não é possível realizar a regressão")
    # SALVAR NaN no arquivo de saida
    stop()
  }
  
  listaDeMatrizes <- regressaoPolinomial(pontos, ordem_polinomio)
  #listaDeMatrizes$X <- normalizar(listaDeMatrizes$X)
  print(listaDeMatrizes)
  print(permutaLinhas(listaDeMatrizes$X, listaDeMatrizes$Y))
  Y <- listaDeMatrizes$Y
  
  listaDeMatrizes <- decomposicaoLU(listaDeMatrizes$X, listaDeMatrizes$Y)
  aproximacao <- resolucaoLU(listaDeMatrizes, Y)
  
  print(aproximacao)
}

main()

#http://www.inf.ufes.br/~avalli/calc_num/aulas/interpolacaoSemPausa.pdf