package util;

public class Funcoes {
	
	public static Double removeCifranDevolveDouble(String texto) {
		texto = texto.replace("$", "");
		return Double.parseDouble(texto);
	}
	
	public static int removeTextoDevolveInt(String texto) {
		texto = texto.replace(" items", "");
		return Integer.parseInt(texto);
	}
}
