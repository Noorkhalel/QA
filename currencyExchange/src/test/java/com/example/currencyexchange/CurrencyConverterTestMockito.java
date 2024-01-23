package com.example.currencyexchange;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import static org.mockito.AdditionalMatchers.lt;
import static org.mockito.ArgumentMatchers.*;
import java.io.IOException;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencyConverterTestMockito {
    @Mock
    private CurrencyConverter currencyConverter;

    @BeforeEach
    public void setUp() {
        currencyConverter = spy(CurrencyConverter.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testConvertWithNullOrEmptyFromCurrency(String fromCurrency) throws IOException {
        doThrow(new IOException("Error: From currency is empty or null.")).when(currencyConverter).convert(eq(fromCurrency), anyString(), anyDouble());
        String finalFromCurrency = fromCurrency;
        IOException exception = assertThrows(IOException.class, () -> currencyConverter.convert(finalFromCurrency, "USD", 100));
        assertEquals("Error: From currency is empty or null.", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testConvertWithNullOrEmptyToCurrency(String toCurrency) throws IOException {
        doThrow(new IOException("Error: From currency is empty or null.")).when(currencyConverter).convert( anyString(),eq(toCurrency), anyDouble());
        String finalFromCurrency = toCurrency;
        IOException exception = assertThrows(IOException.class, () -> currencyConverter.convert("USD",finalFromCurrency,  100));
        assertEquals("Error: From currency is empty or null.", exception.getMessage());
    }


    @Test
    public void testConvertWithEmptyOrNullAmount() throws IOException {
        doThrow(new IOException("Error: Amount is empty or null.")).when(currencyConverter).convert(anyString(), anyString(), eq(0));
        double value=currencyConverter.convert("USD", "EUR", 0);
        assertEquals(0,value);
    }

    @Test
    public void testConvertWithNegativeAmount() throws IOException {
        doThrow(new IOException("Error: Amount is negative.")).when(currencyConverter).convert(anyString(), anyString(), lt(0));
        Double value =currencyConverter.convert("USD", "EUR", -100);
        assertTrue(value<=0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"X", "Y", "Z"})
    public void testConvertWithUnsupportedCurrency(String currency) throws IOException {
        int amount = 100;
        doThrow(new IOException("Error: Currency " + currency + " is not supported."))
                .when(currencyConverter).convert("USD", currency, amount);

        IOException exception = assertThrows(IOException.class,
                () -> currencyConverter.convert("USD", currency, amount));


        assertEquals("Error: Currency " + currency + " is not supported.", exception.getMessage());

    }

//////////////////


    @ParameterizedTest
    @ValueSource(strings = {"USD","ILS", "EUR"})
    public void testGetSupportedCurrenciesThrowsIOException(String curr) throws IOException {
        Set<String> currencies = currencyConverter.getSupportedCurrencies();
        assertNotNull(currencies);
        assertTrue(currencies.contains(curr));
//        verify(currencyConverter, times(1)).getSupportedCurrencies();
    }

}



