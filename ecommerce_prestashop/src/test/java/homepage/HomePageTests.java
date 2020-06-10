package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.ProdutoPage;

public class HomePageTests extends BaseTests {

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
	String nomeProduto_ProdutoPage;

	@Test
	public void testValidarDetalhesDoProduto_DescricaoEValorIguais() {
		int indice = 0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);

		System.out.println(nomeProduto_HomePage);
		System.out.println(precoProduto_HomePage);

		produtoPage = homePage.clicarProduto(indice);

		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
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

	ModalProdutoPage modalProdutoPage;
	@Test
	public void testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() {

		String tamanhoProduto = "M";
		String corProduto = "Black";
		int quantidadeProduto = 2;

		// --Pré Comdição - Usuario Logado
		if (!homePage.estaLogado("Vitor Calado")) {
			testLoginComSucesso_UsuarioLogado();
		}

		// -- Selecionando Produto
		testValidarDetalhesDoProduto_DescricaoEValorIguais();

		// Selecionar Tamanho
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da Lista:" + listaOpcoes.size());

		produtoPage.selecionarOpçãoDropDown(tamanhoProduto);

		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da Lista:" + listaOpcoes.size());

		// Selecionar Cor
		produtoPage.selecionarCorPreta();

		// Selecionar Quantidade
		produtoPage.alterarQuantidade(quantidadeProduto);

		// Clicar em Add to Card
		modalProdutoPage = produtoPage.clicarBotaoAddToCart();

		// Validações
		System.out.println(modalProdutoPage.obterMensagemProdutoAdicionado());
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado()
				.endsWith("Product successfully added to your shopping cart"));

		// Converte o preço do produto de uma String para um numero Double
		String precoProdutoString = modalProdutoPage.obterPrecoProduto();
		precoProdutoString = precoProdutoString.replace("$", "");
		Double precoProduto = Double.parseDouble(precoProdutoString);

		// Converte o subtotal de uma String para um numero Double
		String subtotalString = modalProdutoPage.obterSubtotal();
		subtotalString = subtotalString.replace("$", "");
		Double subtotal = Double.parseDouble(subtotalString);

		// Calcula o valor do Subtotal Multiplicando o preço do produto pela quantidade
		Double subtotalCalculado = quantidadeProduto * precoProduto;

		assertThat(modalProdutoPage.obterDescricaoProduto().toUpperCase(), 
				is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
		assertThat(modalProdutoPage.obterCorProduto(), is(corProduto));
		assertThat(modalProdutoPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto)));
		assertThat(subtotal, is(subtotalCalculado));

	}

	@Test
	public void IrParaCarrinho_InformaçõesPersistidas() {
		//Precondições
		//Produto Incluido no Carrinho
		testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();
		
		CarrinhoPage carrinhopage = modalProdutoPage.clicarBotaoProceedToCheckout();
		
		//Teste
		
		//Validar todos os elementos da tela 
		
	}
}
