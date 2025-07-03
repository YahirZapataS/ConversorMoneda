package com.conversormoneda.ConversorMoneda;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConversorApp {

    private static final String API_KEY = "e4267433fe40fbb90f42e079";

    public static double getRate(String coin, String converter) throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/" + coin + "/" + converter;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(java.time.Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        System.out.println("HTTP Code: " + statusCode);

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error: HTTP Status Code: " + statusCode);
        }

        String body = response.body();
        System.out.println("JSON Response: " + body);

        Gson gson = new Gson();
        JsonObject json = gson.fromJson(response.body(), JsonObject.class);

        if (!json.get("result").getAsString().equals("success")) {
            throw new RuntimeException("API Error: " + json);
        }

        return json.get("converter_rate").getAsDouble();
    }

    public static Set<String> getValidCodes() throws IOException, InterruptedException {
        String url = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(java.time.Duration.ofSeconds(10))
            .header("Accept", "application/json")
            .GET()
            .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200)
                throw new RuntimeException("Error loading currencies: " + response.statusCode());

            JsonObject json = new Gson().fromJson(response.body(), JsonObject.class);
            JsonObject converterRates = json.getAsJsonObject("converter_rates");

            return converterRates.keySet();
    }
}