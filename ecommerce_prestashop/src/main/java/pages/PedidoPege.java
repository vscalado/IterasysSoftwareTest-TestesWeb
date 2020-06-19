package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import util.Funcoes;

public class PedidoPege {
	private WebDriver driver;
	
	private By textoPedidoConfirmado = By.cssSelector("#content-hook_order_confirmation h3");
	private By email = By.cssSelector("#content-hook_order_confirmation p");
	private By totalProdutos = By.cssSelector("div.order-confirmation-table div.order-line div.row div.bold");
	private By totalTaxIncl = By.cssSelector("div.order-confirmation-table table tr.total-value td:nth-child(2)");
	private By metodoPagamento = By.cssSelector("#order-details ul li:nth-child(2)");
	
	
	public PedidoPege(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obter_textoPedidoConfirmado() {
		return driver.findElement(textoPedidoConfirmado).getText();
	}
	
	public String obter_email() {
		//An email has been sent to the vitor.santos@outlook.com.br address.
		String texto = driver.findElement(email).getText();
		texto = Funcoes.removeTexto(texto, "An email has been sent to the ");
		texto = Funcoes.removeTexto(texto, " address.");
		return texto;
	}

	public Double obter_totalProdutos() {
		return Funcoes.removeCifranDevolveDouble(driver.findElement(totalProdutos).getText());
	}
	
	public Double obter_totalTaxIncl() {
		return Funcoes.removeCifranDevolveDouble(driver.findElement(totalTaxIncl).getText());
	}
	
	public String obter_metodoPagamento() {
		//Payment method: Payments by check
		String texto = driver.findElement(metodoPagamento).getText();
		texto = Funcoes.removeTexto(texto, "Payment method: Payments by ");
		return texto;
	}
	
}
