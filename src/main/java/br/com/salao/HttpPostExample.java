package br.com.salao;

/**
 * 
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;

import br.com.salao.model.Cliente;
import br.com.salao.model.Tipo;

public class HttpPostExample {

	public void sendPost(Cliente cliente) throws Exception {

		String telefone = cliente.getTelefone().replaceAll("[^a-zZ-Z0-9 ]", "");
		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");

		String tipo = cliente.getTipo().equals(Tipo.CORTE) ? "seu corte" : "sua progressiva";

		String conteudo = cliente.getNome() + " " + tipo + " foi agendado para o dia " + s.format(cliente.getDataAgendamento().getTime()) + " ás "
				+ cliente.getHorario() + "hrs, no salão do pingo coisa linda";
		String parameters = "sender=11973744577&content=" + conteudo + "&receivers=" + telefone.replace(" ", "");

		URI uri = new URI("https", "sms.comtele.com.br", "/Api/750afcea-5d4b-482d-9505-664d3c0542a9/SendMessage", parameters, null);

		URL obj = uri.toURL();

		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Length", Integer.toString(parameters.length()));
		con.setDoInput(true);
		con.setDoOutput(true);

		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(parameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();

		System.out.println("\nEnviando 'POST' para a URL : " + obj.toString());
		System.out.println("Parametros : " + parameters);
		System.out.println("Codigo de Resposta : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();
	}
}