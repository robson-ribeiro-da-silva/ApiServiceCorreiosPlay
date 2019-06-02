package controllers;


import play.libs.WS;
import play.mvc.Controller;
import play.mvc.Http;

import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.libs.XML;
import play.mvc.Controller;

public class ApiCorreio extends Controller{
	/*
	public static void consumeFrete(String cepDestino, String peso, String comprimento, String altura, String largura, double valorDeclarado) {
		ApiCorreio.response.accessControl("*");
		play.libs.WS.HttpResponse response = WS.url("http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?nCdEmpresa=08082650&sDsSenha=564321"
				+ "&sCepOrigem=74420180&sCepDestino="+cepDestino+"&nVlPeso="+peso+"&nCdFormato=1&nVlComprimento="+comprimento+
				"&nVlAltura="+altura+"&nVlLargura="+largura+"&sCdMaoPropria=n&nVlValorDeclarado="+valorDeclarado
				+ "&sCdAvisoRecebimento=n&nCdServico=04510&nVlDiametro=0&StrRetorno=xml&nIndicaCalculo=3").get();
		renderXml(response.getString());
	}*/
	
	//http://localhost:9000/59900000/20,0/50,0/50,0/50,0/500.0
	public static String consumeFrete(String cepDestino, String peso, String comprimento, String altura, String largura, double valorDeclarado) {
		ApiCorreio.response.accessControl("*");
		HttpResponse res = 
                WS.url("http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?nCdEmpresa=08082650&sDsSenha=564321"
				+ "&sCepOrigem=59910000&sCepDestino="+cepDestino+"&nVlPeso="+peso+"&nCdFormato=1&nVlComprimento="+comprimento+
				"&nVlAltura="+altura+"&nVlLargura="+largura+"&sCdMaoPropria=n&nVlValorDeclarado="+valorDeclarado
				+ "&sCdAvisoRecebimento=n&nCdServico=04510&nVlDiametro=0&StrRetorno=xml&nIndicaCalculo=3").get();
		
		String xml = res.getString();
		String tagV = "<Valor>";
		String valueValor = getTagValue(xml, tagV);
		
		String tagP = "<PrazoEntrega>";
		String valuePrazo = getTagValue(xml, tagP);
		
		//System.out.println(valueValor+" : "+valuePrazo);
		
		String response =  "{\"prazo\":\""+valuePrazo+"\",\"preco\":\""+valueValor+"\"}";
		
		return response;
		}
	

	public static String getTagValue(String xml, String tag) {
	    String closeTag = new StringBuffer(tag).insert(1, "/").toString(); // fecha a tag adicionando "/"
	    int from = xml.indexOf(tag) + tag.length();
	    int to = xml.indexOf(closeTag);
	
	    return xml.substring(from, to);
	}
}
