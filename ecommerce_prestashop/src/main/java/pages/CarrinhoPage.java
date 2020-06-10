package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarrinhoPage {
	
	private WebDriver driver;
	
	//
	public By nomeProduto = By.cssSelector("div.product-line-info a");
	public By precoProduto = By.cssSelector("span.price");
	public By tamanhoProduto = By.xpath("//div[contains(@class,'product-line-grid-body')]//div[3]/span[contains(@class,'value')]");
	public By corProduto = By.xpath("//div[contains(@class,'product-line-grid-body')]//div[4]/span[contains(@class,'value')]");
	public By quantidadeProduto = By.cssSelector("input.js-cart-line-product-quantity");
	public By subtotalProduto = By.cssSelector("span.product-price strong");
	public By numeroItensTotal = By.cssSelector("span.js-subtotal");
	public By subtotalTotal = By.cssSelector("#cart-subtotal-products span.value");
	public By shippingTotal = By.cssSelector("#cart-subtotal-shipping span.value");
	public By totalTaxExclTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(1) span.value");
	public By totalTaxInclTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(2) span.value");
	public By taxesTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(3) span.value");
	
	public CarrinhoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obter_nomeProduto() {
		return driver.findElement(nomeProduto).getText();
	}
	
	public String obter_precoProduto() {
		return driver.findElement(precoProduto).getText();
	}
	
	public String obter_tamanhoProduto() {
		return driver.findElement(tamanhoProduto).getText();
	}
 
	public String obter_corProduto() {
		return driver.findElement(corProduto).getText();
	}
 
	public String obter_quantidadeProduto() {
		return driver.findElement(quantidadeProduto).getText();
	}
 
	public String obter_subtotalProduto() {
		return driver.findElement(subtotalProduto).getText();
	}
 
	public String obter_numeroItensTotal() {
		return driver.findElement(numeroItensTotal).getText();
	}
 
	public String obter_subtotalTotal() {
		return driver.findElement(subtotalTotal).getText();
	}
 
	public String obter_shippingTotal() {
		return driver.findElement(shippingTotal).getText();
	}
 
	public String obter_totalTaxExclTotal() {
		return driver.findElement(totalTaxExclTotal).getText();
	}
 
	public String obter_totalTaxInclTotal() {
		return driver.findElement(totalTaxInclTotal).getText();
	}
 
	public String obter_taxesTotal() {
		return driver.findElement(taxesTotal).getText();
	}
 
	
	

}
