package com.example.currencyexchange;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Set;

public class CurrencyConverter {
    public static String API_URL = "https://v6.exchangerate-api.com/v6/dbac21ec89f4cb7eb3e5e440/latest/";

    public double convert(String fromCurrency, String toCurrency, String amount2) throws IOException, ParseException {

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        String amountStrModified = amount2.replace(",", ".");
        double amount = numberFormat.parse(amountStrModified).doubleValue();

        if (fromCurrency == null || fromCurrency.isEmpty()) {
            throw new IOException("Error: Invalid from currency.");
        } else if (toCurrency == null || toCurrency.isEmpty()) {
            throw new NullPointerException("Error: Invalid to currency.");
        }
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(API_URL + fromCurrency);
        HttpResponse httpResponse = httpClient.execute(httpGet);

        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        Gson gson = new Gson();
        try {
            JsonObject jsonObject = gson.fromJson(jsonBuilder.toString(), JsonObject.class);
            JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");
            if (rates == null) {
                throw new IOException("Error: Unable to fetch exchange rates.");
            }
            double exchangeRate = rates.get(toCurrency).getAsDouble();

            return amount * exchangeRate;
        } catch (Exception e) {
            return 0.0;
        }

    }

    public double convert(String fromCurrency, String toCurrency, double amount) throws IOException {
        if (fromCurrency == null || fromCurrency.isEmpty()) {
            throw new IOException("Error: Invalid from currency.");
        } else if (toCurrency == null || toCurrency.isEmpty()) {
            throw new NullPointerException("Error: Invalid to currency.");
        }
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(API_URL + fromCurrency);
        HttpResponse httpResponse = httpClient.execute(httpGet);

        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        Gson gson = new Gson();
        try {
            JsonObject jsonObject = gson.fromJson(jsonBuilder.toString(), JsonObject.class);
            JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");
            if (rates == null) {
                throw new IOException("Error: Unable to fetch exchange rates.");
            }
            double exchangeRate = rates.get(toCurrency).getAsDouble();

            return amount * exchangeRate;
        } catch (Exception e) {
            return 0.0;
        }

    }

    public static Set<String> getSupportedCurrencies() throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(API_URL + "USD");
        HttpResponse httpResponse = httpClient.execute(httpGet);

        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonBuilder.toString(), JsonObject.class);
        JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");
        if (rates == null) {
            throw new IOException("Error: Unable to fetch exchange rates.");
        }
        Set<String> supportedCurrencies = rates.keySet();
        supportedCurrencies.remove("AUD");
        supportedCurrencies.remove("BWP");
        return supportedCurrencies;
    }
}
