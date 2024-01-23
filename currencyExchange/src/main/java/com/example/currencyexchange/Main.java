//package com.example.currencyexchange;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Scanner;
//import java.util.Set;
//
//public class Main {
//    public static void main(String[] args) throws IOException {
//        ArrayList curr =new ArrayList();
//        try {
//            Set<String> supportedCurrencies = CurrencyConverter.getSupportedCurrencies();
//            for (String currency : supportedCurrencies) {
//                curr.add(currency);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Collections.sort(curr);
//
//        for (int i=0; i<curr.size();i++){
//            System.out.println(curr.get(i));
//        }
//
//        ////
//        System.out.println("--------------\nEXCHANGE:\n----------------\n");
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Enter the amount: ");
//        double amount = scanner.nextDouble();
//
//        System.out.print("Enter the source currency: ");
//        String fromCurrency = scanner.next().toUpperCase();
//
//        System.out.print("Enter the target currency: ");
//        String toCurrency = scanner.next().toUpperCase();
//
//        try {
//            double convertedAmount = CurrencyConverter.convert(fromCurrency, toCurrency, amount);
//            System.out.printf("%.2f %s is equal to %.2f %s%n", amount, fromCurrency, convertedAmount, toCurrency);
//        } catch (IOException e) {
//            System.out.println("Error: Unable to fetch exchange rates.");
//        }
//    }
//}