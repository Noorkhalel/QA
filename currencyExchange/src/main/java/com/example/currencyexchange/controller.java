package com.example.currencyexchange;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class controller {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/dbac21ec89f4cb7eb3e5e440/latest/";

    public double rateC=0.0;


    @FXML
    private TextField amount;

    @FXML
    private Button convert;

    @FXML
    private ComboBox<String> from;

    @FXML
    private TextField rate;

    @FXML
    private TextField result;

    @FXML
    private ComboBox<String> to;

    @FXML
    private Label status;

    @FXML
    private Button reset;

    public void initialize() throws IOException {
        ArrayList<String> currencies = getSupportedCurrencies();
        from.getItems().addAll(currencies);
        to.getItems().addAll(currencies);
    }


    @FXML
    void convert(ActionEvent event) {
        String fromCurrency = from.getValue();
        String toCurrency = to.getValue();
        String amountStr = amount.getText();

        if (fromCurrency != null && toCurrency != null && !amountStr.isEmpty()) {
            try{
                double amount = Double.parseDouble(amountStr);

                try {
                    if(Double.parseDouble(amountStr)<0){
                        status.setText("Error: Enter Valid Amount Please");
                        return;
                    }
                    double resultt = convert(fromCurrency, toCurrency, amount);
                    result.setText(String.valueOf(resultt));
                    rate.setText(String.valueOf(rateC));
                    status.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                    status.setText("Error: Unable to perform the conversion.");
                }
            }
            catch (Exception e1){
                status.setText("Error: Enter Valid Amount Please");
            }

        } else {
            status.setText("Error: Please select currencies\n     and enter an amount.");
        }

    }


    @FXML
    void reset(ActionEvent event) {
        from.setValue(null);
        to.setValue(null);
        amount.clear();
        rate.clear();
        result.clear();
        status.setText("");
    }


    public double convert(String fromCurrency, String toCurrency, double amount) throws IOException {
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
        JsonObject jsonObject = gson.fromJson(jsonBuilder.toString(), JsonObject.class);
        JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");
        if (rates == null) {
            throw new IOException("Error: Unable to fetch exchange rates.");
        }
        double exchangeRate = rates.get(toCurrency).getAsDouble();
        this.rateC=exchangeRate;

        return amount * exchangeRate;
    }

    public static ArrayList getSupportedCurrencies() throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(API_URL + "USD"); // You can choose any base currency
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

        ArrayList curr =new ArrayList();

        supportedCurrencies = CurrencyConverter.getSupportedCurrencies();
        for (String currency : supportedCurrencies) {
            curr.add(currency);
        }

        Collections.sort(curr);

        return curr;
    }

}
