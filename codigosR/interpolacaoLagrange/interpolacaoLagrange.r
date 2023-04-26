setwd("~/Documents/trabalho/codigosR/interpolacaoLagrange/")

lerDeArquivo <- function(nomeArquivo="entradaDeDados.txt"){
  
}

escreverEmArquivo <- function(nomeArquivo="saidaDeDados.txt", dados=NULL){
  
}

lagrange <- function(vetor_x, vetor_y, ponto_atual){
  somatorio <- 0
  tamanho <- length(vetor_x)
  
  for(i in 1:tamanho){
    produtorio <- 1
    
    for(j in 1:tamanho){
      if(i != j){
        produtorio <- produtorio * ((ponto_atual - vetor_x[j]) / (vetor_x[i] - vetor_x[j]))
      } 
    } # for interno
    
    somatorio <- somatorio + produtorio * vetor_y[i]
  } # for externo
  
  return(somatorio)
}

main <- function(){
  vetor1 <- c(5, 7, 11, 13, 17)
  vetor2 <- c(150, 392, 1452, 2366, 5202)

  resultado <- lagrange(vetor1, vetor2, 9)
  
  print(resultado)
}

main()
