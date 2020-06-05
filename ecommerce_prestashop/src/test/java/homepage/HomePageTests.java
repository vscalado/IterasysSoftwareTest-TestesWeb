package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.LoginPage;
import pages.ProdutoPage;

public class HomePageTests extends BaseTests{
	
	@Test
	public void testcontarProdutos_oitoProdutosDiferentes() {
		
		carregarPaginaInicial();
		assertThat(homePage.contarProdutos(), is(8));
	}
	
	@Test
	public void testValidarCarrinhoZerado_ZeroItensNoCarrinho() {
		
		int produtosNoCarrinho = homePage.obterQuantidadeProdutosNoCarrinho();
		assertThat(produtosNoCarrinho, is(0));
	}
	
	ProdutoPage produtoPage;
	@Test
	public void testValidarDetalhesDoProduto_DescricaoEValorIguais() {
		int indice = 0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);
		
		System.out.println(nomeProduto_HomePage);
		System.out.println(precoProduto_HomePage);
		
		produtoPage = homePage.clicarProduto(indice);
		
		String nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();
		
		System.out.println(nomeProduto_ProdutoPage);
		System.out.println(precoProduto_ProdutoPage);
		
		assertThat(nomeProduto_HomePage.toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat(precoProduto_HomePage, is(precoProduto_ProdutoPage));
		
	}
	
	LoginPage loginPage;
	@Test
	public void testLoginComSucesso_UsuarioLogado() {
		String email = "vitor.santos@outlook.com.br";
		String senha = "vitor123";
		
		loginPage = homePage.clicarBotaoSignIn();
		
		loginPage.preencherEmail(email);
		loginPage.preencherPassword(senha);
		loginPage.clicarBotaoSignIn();
		
		assertThat(homePage.estaLogado("Vitor Calado"), is(true));
		
		carregarPaginaInicial();
		
	}
	
	@Test
	public void testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() {
		//--Pr� Comdi��o - Usuario Logado
		if (!homePage.estaLogado("Vitor Calado")) {
			testLoginComSucesso_UsuarioLogado();
		}
		
		//-- Selecionando Produto
		testValidarDetalhesDoProduto_DescricaoEValorIguais();
		
		//Selecionar Tamanho 
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da Lista:" + listaOpcoes.size());
		
		produtoPage.selecionarOp��oDropDown("M");
		
		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da Lista:" + listaOpcoes.size());
		
		//Selecionar Cor 
		produtoPage.selecionarCorPreta();
		
		//Selecionar Quantidade 
		produtoPage.alterarQuantidade(2);
		
		//Clicar em Add to Card
		produtoPage.clicarBotaoAddToCart();
	}

}
