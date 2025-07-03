package com.conversormoneda.ConversorMoneda;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConversorApp {

    private static final String API_KEY = "e4267433fe40fbb90f42e079";

    public static double obtenerTasa(String base, String destino) throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/" + base + "/" + destino;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(java.time.Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error en la solicitud HTTP. Código: " + response.statusCode());
        }

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(response.body(), JsonObject.class);

        if (!json.get("result").getAsString().equals("success")) {
            throw new RuntimeException("Error en la API: " + json);
        }

        return json.get("conversion_rate").getAsDouble();
    }
}