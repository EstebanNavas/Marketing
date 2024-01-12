package com.marketing.ServiceApi;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.marketing.Model.dbaquamovil.ResolucionResponse;


@Service
public class ApiResolucion {

    @Autowired
    private Gson gson;

    public ResolucionResponse consumirApi(String xToken, String xIdResolucion) {
        String apiUrl = "https://mobile-tic.apifacturacionelectronica.xyz/api/ubl2.1/config/resolutions";

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(apiUrl);

        // Agrega el token de autorización en la cabecera
        httpGet.addHeader("Authorization", "Bearer " + xToken);

        try {
            HttpResponse response = httpClient.execute(httpGet);

            System.out.println("Código de respuesta: " + response.getStatusLine().getStatusCode());

            // Lee y muestra el cuerpo de la respuesta
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            System.out.println("Cuerpo de la respuesta: " + content.toString());

            // Parseamos el JSON a un array de objetos JSON
            JsonArray jsonArray = gson.fromJson(content.toString(), JsonArray.class);

            // Busca el objeto con "prefix" igual a "xPrefijo"
            ResolucionResponse resolucionResponse = buscarResolucionPorId(jsonArray, xIdResolucion);
            
            System.out.println("resolucionResponse: " + resolucionResponse);

            // Imprime los resultados
            if (resolucionResponse != null) {
            	System.out.println("id: " + resolucionResponse.getId());
                System.out.println("Prefix: " + resolucionResponse.getPrefix());
                System.out.println("Resolution: " + resolucionResponse.getResolution());
                System.out.println("From: " + resolucionResponse.getFrom());
                System.out.println("To: " + resolucionResponse.getTo());
                System.out.println("Date_to: " + resolucionResponse.getDate_to());
            }

            return resolucionResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Cerramos el HttpClient
            try {
                ((Closeable) httpClient).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ResolucionResponse buscarResolucionPorId(JsonArray jsonArray, String xIdResolucion) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            JsonElement IdElement = jsonObject.get("id");

            // Verifica si el campo "prefix" no es nulo
            if (IdElement != null && !IdElement.isJsonNull() && xIdResolucion.equals(IdElement.getAsString())) {
                // Encuentra el objeto con "ID" igual a "xIdResolucion"
            	JsonElement idElement = jsonObject.get("id");
            	JsonElement prefixElement = jsonObject.get("prefix");
            	JsonElement resolutionElement = jsonObject.get("resolution");
                JsonElement dateFromElement = jsonObject.get("date_from");
                JsonElement dateToElement = jsonObject.get("date_to");
                JsonElement FromElement = jsonObject.get("from");
                JsonElement ToElement = jsonObject.get("to");

                // Verifica si los campos "date_from" y "date_to" no son nulos
                if (dateFromElement != null && !dateFromElement.isJsonNull() && dateToElement != null
                        && !dateToElement.isJsonNull()) {
                    ResolucionResponse resolucionResponse = new ResolucionResponse();
                    resolucionResponse.setId(idElement.getAsInt());
                    resolucionResponse.setPrefix(prefixElement.getAsString());
                    resolucionResponse.setResolution(resolutionElement.getAsString());
                    resolucionResponse.setFrom(FromElement.getAsInt());
                    resolucionResponse.setTo(ToElement.getAsInt());
                    resolucionResponse.setDate_from(dateFromElement.getAsString());
                    resolucionResponse.setDate_to(dateToElement.getAsString());
                    return resolucionResponse;
                }
            }
        }
        System.out.println("No se encontró un objeto con 'ID' igual a: " + xIdResolucion);
        return null; // Devuelve null si no se encuentra el objeto o si los campos son nulos
    }
}
