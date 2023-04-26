setwd("~/Documents/trabalho/codigosR/interpolacaoNewton/")

lerDeArquivo <- function(nomeArquivo = "entradaDeDados.txt"){
  
}

escreverEmArquivo <- function(nomeArquivo = "saidaDeDados.txt", dados=NULL){
  
}

diferencasDivididas <- function(vetor_x, vetor_y){
  tabela <- matrix(0, nrow = length(vetor_x), ncol = length(vetor_x))
  tamanho <- length(vetor_x)
  tabela[1:(tamanho), 1] <- vetor_y
  
  for(j in 2:tamanho){
    for(i in 1:(tamanho - j + 1)){
      tabela[i, j] <- ( tabela[(i + 1), (j - 1)] - tabela[i, (j - 1)] ) / ( vetor_x[(i + j - 1)] - vetor_x[i] )
    } # for interno
  } # for externo
  
  return(tabela)
}

interpolacaoNewton <- function(vetor_x, vetor_y, ponto_atual, tabelaDiferencas){
  tamanho <- length(vetor_x)
  somatorio <- vetor_y[1]
  
  for(i in 2:tamanho){
    produtorio <- 1
    
    for(j in 1:(i-1)){
      produtorio <- produtorio * (ponto_atual - vetor_x[j])
    } # for interno
    
    somatorio <- somatorio + produtorio * tabelaDiferencas[1, i]
  } # for externo
  
  return(somatorio)
}

main <- function(){
  # x
  vetor1 <- c(1, 4, 6, 5)
  # f(x)
  vetor2 <- c(0, 1.386294, 1.791760, 1.609438)
  
  tabela <- diferencasDivididas(vetor1, vetor2)

  print(interpolacaoNewton(vetor1, vetor2, 2, tabela))
  
}

main()
