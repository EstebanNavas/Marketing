package com.marketing.ServiceApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.HttpPut;

import java.io.BufferedReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.marketing.Model.dbaquamovil.CertificadoResponse;

@Service
public class ApiCertificado {
	
	@Autowired
    private Gson gson;

	 public String consumirApi(String xToken) {
	        String apiUrl = "http://78.46.19.158/api/ubl2.1/certificate-end-date";
	        String fechaPorDefecto = "01/01/2025"; // Fecha por defecto si ocurre un error

	        HttpClient httpClient = HttpClients.createDefault();
	        HttpPut httpPut = new HttpPut(apiUrl);

	        httpPut.addHeader("Authorization", "Bearer " + xToken);

	        try {
	            HttpResponse response = httpClient.execute(httpPut);
	            int statusCode = response.getStatusLine().getStatusCode();

	            System.out.println("Código de respuesta: " + statusCode);

	            if (statusCode != 200) {
	                System.err.println("Respuesta inesperada del servidor.");
	                return fechaPorDefecto;
	            }

	            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	            StringBuilder content = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                content.append(line);
	            }

	            String fecha = content.toString().replace("\"", "");
	            System.out.println("Fecha de vencimiento: " + fecha);

	            // Validación si retorna algo distinto a una fecha
	            if (fecha.isBlank() || !fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
	                System.err.println("Error al consultar certificado");
	                return fechaPorDefecto;
	            }

	            return fecha;

	        } catch (Exception e) {
	            e.printStackTrace();
	            return fechaPorDefecto;
	        } finally {
	            try {
	                ((Closeable) httpClient).close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }

}
