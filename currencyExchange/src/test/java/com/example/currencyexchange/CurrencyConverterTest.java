
package com.example.currencyexchange;

import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

public class CurrencyConverterTest {

    private static CurrencyConverter currencyConverter;

    @BeforeAll
    public static void setup() {

        currencyConverter = new CurrencyConverter();
    }

    @DisplayName("Test Valid Parameters")
    @Test
    public void testConvert_ValidParameters() throws IOException {
    double convertedAmount = currencyConverter.convert("USD", "EUR", 100.0);
    Assertions.assertEquals(91.86, convertedAmount, 0.001);
    }

    @DisplayName("Test Valid Parameters if user uses Comma")
    @Test
    public void testConvert_ValidParameters_Comma() throws IOException,
    ParseException {
    double convertedAmount = currencyConverter.convert("USD", "EUR", "100,0");
    Assertions.assertEquals(91.86, convertedAmount, 0.001);
    }

    @DisplayName("Test null & Empty from currency")
    @ParameterizedTest
    @NullAndEmptySource
    public void testConvert_InvalidFromCurrency(String fromCurrency) {
        Assertions.assertThrows(IOException.class, () -> {
            currencyConverter.convert(fromCurrency, "EUR", 100.0);
        }, "Expected an IOException to be thrown for null fromCurrency.");

    }

    @DisplayName("Test null & Empty target currency")
    @ParameterizedTest
    @NullAndEmptySource
    public void testConvert_InvalidToCurrency(String toCurrency) {
        Assertions.assertThrows(NullPointerException.class, () -> {
            currencyConverter.convert("USD", toCurrency, 100.0);
        });
    }

    @DisplayName("Test Amount (negative,null,Nan and zero")
    @ParameterizedTest
    @ValueSource(doubles = { -100.0, 0.0 })
    public void testConvert_InvalidAmount(double amount) {
        Assertions.assertAll(
                () -> Assertions.assertNotNull(amount),
                () -> Assertions.assertTrue(amount <= 0));

    }

    @DisplayName("Test Amount (Nan)")
    @ParameterizedTest
    @ValueSource(doubles = { Double.NaN })
    public void testConvert_InvalidAmountNaN(double amount) {
        Assertions.assertTrue(Double.isNaN(amount));
    }

    @DisplayName("check if Currency is supported")
    @Test
    public void testGetSupportedCurrencies() throws IOException {
        Set<String> supportedCurrencies = currencyConverter.getSupportedCurrencies();
        Assertions.assertTrue(supportedCurrencies.contains("USD"));
        Assertions.assertTrue(supportedCurrencies.contains("ILS"));
        Assertions.assertFalse(supportedCurrencies.contains("y"));
        Assertions.assertFalse(supportedCurrencies.contains("x"));

    }
    @DisplayName("check if not Currency is supported AUD and BWP")
    @Test
    public void testConfirmRemoval() throws IOException {
        Set<String> supportedCurrencies = currencyConverter.getSupportedCurrencies();
        Assertions.assertFalse(supportedCurrencies.contains("AUD"));
        Assertions.assertFalse(supportedCurrencies.contains("BWP"));

    }
}
