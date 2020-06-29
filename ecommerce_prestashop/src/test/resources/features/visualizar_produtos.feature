# language: pt
Funcionalidade: Visualizar Produtos
  Como um usuario não logado 
  Quero visualizar produtos disponiveis
  Para poder escolher qual eu vou comprar

  Cenario: Deve mostrar uma lista de oito produtos na pagina inicial
    Dado que estou na pagina inicial
    Quando não estou logado
    Entao visualizo 8 produtos disponiveis
    E carrinho esta zerado
