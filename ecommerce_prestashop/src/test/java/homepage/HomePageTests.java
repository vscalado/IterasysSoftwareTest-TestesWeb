package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.PedidoPege;
import pages.ProdutoPage;
import util.Funcoes;

public class HomePageTests extends BaseTests {

	@Test
	public void testContarProdutos_oitoProdutosDiferentes() {

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

		//System.out.println(nomeProduto_HomePage);
		//System.out.println(precoProduto_HomePage);

		produtoPage = homePage.clicarProduto(indice);

		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();

		//System.out.println(nomeProduto_ProdutoPage);
		//System.out.println(precoProduto_ProdutoPage);

		assertThat(nomeProduto_HomePage.toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat(precoProduto_HomePage, is(precoProduto_ProdutoPage));

	}

	LoginPage loginPage;

	@Test
	public void testLoginComSucesso_UsuarioLogado() {

		loginPage = homePage.clicarBotaoSignIn();
		
		String email = "vitor.santos@outlook.com.br";
		String senha = "vitor123";

		loginPage.preencherEmail(email);
		loginPage.preencherPassword(senha);
		loginPage.clicarBotaoSignIn();

		assertThat(homePage.estaLogado("Vitor Calado"), is(true));

		carregarPaginaInicial();

	}
	
	

	ModalProdutoPage modalProdutoPage;

	@Test
	public void testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() {

		String tamanhoProduto = "L";
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
		//System.out.println(listaOpcoes.get(0));
		//System.out.println("Tamanho da Lista:" + listaOpcoes.size());

		produtoPage.selecionarOpçãoDropDown(tamanhoProduto);

		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		//System.out.println(listaOpcoes.get(0));
		//System.out.println("Tamanho da Lista:" + listaOpcoes.size());

		// Selecionar Cor
		produtoPage.selecionarCorPreta();

		// Selecionar Quantidade
		produtoPage.alterarQuantidade(quantidadeProduto);

		// Clicar em Add to Card
		modalProdutoPage = produtoPage.clicarBotaoAddToCart();

		// Validações
		//System.out.println(modalProdutoPage.obterMensagemProdutoAdicionado());
		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado()
				.endsWith("Product successfully added to your shopping cart"));

		// Converte o preço do produto de uma String para um numero Double
		Double precoProduto = Funcoes.removeCifranDevolveDouble(modalProdutoPage.obterPrecoProduto());
		//System.out.println(precoProduto);

		// Converte o subtotal de uma String para um numero Double
		Double subtotal = Funcoes.removeCifranDevolveDouble(modalProdutoPage.obterSubtotal());
		//System.out.println(subtotal);

		// Calcula o valor do Subtotal Multiplicando o preço do produto pela quantidade
		Double subtotalCalculado = quantidadeProduto * precoProduto;

		assertThat(modalProdutoPage.obterDescricaoProduto().toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
		assertThat(modalProdutoPage.obterCorProduto(), is(corProduto));
		assertThat(modalProdutoPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto)));
		assertThat(subtotal, is(subtotalCalculado));

	}

	// Valores Esperados

	String esperado_nomeProduto = "Hummingbird printed t-shirt";
	Double esperado_precoProduto = 19.12;
	String esperado_tamanhoProduto = "L";
	String esperado_corProduto = "Black";
	int esperado_input_quantidadeProduto = 2;
	Double esperado_subtotalProduto = esperado_precoProduto * esperado_input_quantidadeProduto;

	int esperado_numeroItensTotal = esperado_input_quantidadeProduto;
	Double esperado_subtotalTotal = esperado_subtotalProduto;
	Double esperado_shippingTotal = 7.00;
	Double esperado_totalTaxExclTotal = esperado_subtotalTotal + esperado_shippingTotal;
	Double esperado_taxesTotal = 0.0;
	Double esperado_totalTaxInclTotal = esperado_totalTaxExclTotal + esperado_taxesTotal;
	
	String esperado_nomeCliente = "Vitor Calado";
	
	CarrinhoPage carrinhoPage;
	
	@Test
	public void testIrParaCarrinho_InformaçõesPersistidas() {
		// Precondições
		// Produto Incluido no Carrinho
		testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();

		carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckout();

		// Teste

		// Validar todos os elementos da tela
		/*
		System.out.println("***Tela do Produto***");

		System.out.println(carrinhoPage.obter_nomeProduto());
		System.out.println(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_precoProduto()));
		System.out.println(carrinhoPage.obter_tamanhoProduto());
		System.out.println(carrinhoPage.obter_corProduto());
		System.out.println(carrinhoPage.obter_input_quantidadeProduto());
		System.out.println(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_subtotalProduto()));

		System.out.println("***Tela do Total Produto***");
		System.out.println(Funcoes.removeTextoDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		System.out.println(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		System.out.println(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_shippingTotal()));
		System.out.println(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		System.out.println(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_totalTaxInclTotal()));
		System.out.println(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_taxesTotal()));*/
		
		
		// Asserções Hamcrest
		assertThat(carrinhoPage.obter_nomeProduto(), is(esperado_nomeProduto));
		assertThat(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_precoProduto()), is(esperado_precoProduto));
		assertThat(carrinhoPage.obter_tamanhoProduto(), is(esperado_tamanhoProduto));
		assertThat(carrinhoPage.obter_corProduto(), is(esperado_corProduto));
		assertThat(Integer.parseInt(carrinhoPage.obter_input_quantidadeProduto()), is(esperado_input_quantidadeProduto));
		assertThat(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_subtotalProduto()),
				is(esperado_subtotalProduto));

		assertThat(Funcoes.removeTextoDevolveInt(carrinhoPage.obter_numeroItensTotal()), is(esperado_numeroItensTotal));
		assertThat(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_subtotalTotal()), is(esperado_subtotalTotal));
		assertThat(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_shippingTotal()), is(esperado_shippingTotal));
		assertThat(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()),
				is(esperado_totalTaxExclTotal));
		assertThat(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_totalTaxInclTotal()), is(esperado_totalTaxInclTotal));
		assertThat(Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_taxesTotal()), is(esperado_taxesTotal));

		// Asserções JUnit
		assertEquals(esperado_nomeProduto, carrinhoPage.obter_nomeProduto());
		assertEquals(esperado_precoProduto, Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_precoProduto()));
		assertEquals(esperado_tamanhoProduto, carrinhoPage.obter_tamanhoProduto());
		assertEquals(esperado_corProduto, carrinhoPage.obter_corProduto());
		assertEquals(esperado_input_quantidadeProduto, Integer.parseInt(carrinhoPage.obter_input_quantidadeProduto()));
		assertEquals(esperado_subtotalProduto, Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_subtotalProduto()));

		assertEquals(esperado_numeroItensTotal, Funcoes.removeTextoDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		assertEquals(esperado_subtotalTotal, Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		assertEquals(esperado_shippingTotal, Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_shippingTotal()));
		assertEquals(esperado_totalTaxExclTotal,
				Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		assertEquals(esperado_totalTaxInclTotal, Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_totalTaxInclTotal()));
		assertEquals(esperado_taxesTotal, Funcoes.removeCifranDevolveDouble(carrinhoPage.obter_taxesTotal()));

	}
	
	
	
	CheckoutPage checkoutPage;
	@Test
	public void testIrParaCheckout_FreteMeioPagamentoEnderecoListadoOK() {
		//Pré-condição
		
		//Produto disponivel no carrinho de compras
		testIrParaCarrinho_InformaçõesPersistidas();
		
		// Teste 
		
		// Clicar no Botão 
		checkoutPage = carrinhoPage.clicarBotaoProceedToCheckout();
		
		// Preencher informações
		
		//Validar informações da tela 
		assertThat(Funcoes.removeCifranDevolveDouble(checkoutPage.obter_totalTaxIncTotal()),is(esperado_totalTaxInclTotal));
		//assertThat(checkoutPage.obter_nomeCliente(), is(nomeCliente));
		assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomeCliente));
		
		checkoutPage.clicarBotaoContinueAddress();
		
		
		String encontrado_shippingValor_String = checkoutPage.obter_shippingValor();
		encontrado_shippingValor_String = Funcoes.removeTexto(encontrado_shippingValor_String, " tax excl.");
		Double encontrado_shippingValor_Double =  Funcoes.removeCifranDevolveDouble(encontrado_shippingValor_String);
		
		assertThat(encontrado_shippingValor_Double, is(esperado_shippingTotal));
		
		checkoutPage.clicar_botaoContinueShipping();
		
		//Selecionar a opção "pay by Check"
		checkoutPage.selecionar_radioPayByCheck();
		
		//Validar valor do cheque (amount)
		String encontrado_amountPyByCheck_String = checkoutPage.obter_amountPyByCheck();
		encontrado_amountPyByCheck_String = Funcoes.removeTexto(encontrado_amountPyByCheck_String, " (tax incl.)");
		Double encontrado_amountPyByCheck_Double = Funcoes.removeCifranDevolveDouble(encontrado_amountPyByCheck_String);
		
		assertThat(encontrado_amountPyByCheck_Double, is(esperado_totalTaxInclTotal));
		
		
		//Clicar na opção "I agree"
		checkoutPage.selecionar_checkboxIAgree();
		
		assertTrue(checkoutPage.estaSelecionadoCheckboxIAgree());
		
	}
	
	PedidoPege pedidoPage;
	@Test
	public void testFinalizarPedido_pedidoFinalizadoComSucesso() {
		//Pré condições
		//Checkout completamente concluido
		testIrParaCheckout_FreteMeioPagamentoEnderecoListadoOK();
		
		//Teste
		//Clicar no botão para confirmar pedido
		pedidoPage = checkoutPage.clicarbotaoConfirmaPedido();
		
		//Validar valores da tela
		
		assertTrue(pedidoPage.obter_textoPedidoConfirmado().endsWith("YOUR ORDER IS CONFIRMED"));
		//assertThat(pedidoPage.obter_textoPedidoConfirmado().toUpperCase(), is("YOUR ORDER IS CONFIRMED"));
		
		assertThat(pedidoPage.obter_email(), is("vitor.santos@outlook.com.br"));
		assertThat(pedidoPage.obter_totalProdutos(), is(esperado_subtotalProduto));
		assertThat(pedidoPage.obter_totalTaxIncl(), is(esperado_totalTaxInclTotal));
		assertThat(pedidoPage.obter_metodoPagamento(), is("check"));
	}
	
}
