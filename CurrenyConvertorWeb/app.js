const API_URL = 'https://v6.exchangerate-api.com/v6/dbac21ec89f4cb7eb3e5e440/latest/';

const amount = document.getElementById('amount');
const from = document.getElementById('from');
const to = document.getElementById('to');
const convertBtn = document.getElementById('convert');
const resetBtn = document.getElementById('reset');
const result = document.getElementById('result');
const rate = document.getElementById('rate');
const status = document.getElementById('status');

async function SaveToLocal() {
    const response = await fetch(API_URL + 'USD');
    const data = await response.json();
    const rates = data.conversion_rates;
    const currencies = Object.keys(rates).sort();
    localStorage.setItem('currencies', JSON.stringify(currencies));
}


async function getCurrencies() {
    // SaveToLocal();
    const storedCurrencies = localStorage.getItem('currencies');
    const currencies = JSON.parse(storedCurrencies);
    currencies.forEach(currency => {
        const option = document.createElement('option');
        option.value = currency;
        option.textContent = currency;
        from.appendChild(option);
    });
    currencies.forEach(currency => {
        const option1 = document.createElement('option');
        option1.value = currency;
        option1.textContent = currency;
        to.appendChild(option1);
    });
}



async function convert(fromCurrency, toCurrency, amount) {
    const response = await fetch(API_URL + fromCurrency);
    const data = await response.json();
    const rates = data.conversion_rates;
    const exchangeRate = rates[toCurrency];
    return amount * exchangeRate;
}

convertBtn.addEventListener('click', async () => {
    const fromCurrency = from.value;
    const toCurrency = to.value;
    const amountValue = amount.value.replace(',', ''); // Remove commas

    if (fromCurrency && toCurrency && !isNaN(amountValue)) {
        const parsedAmount = parseFloat(amountValue);

        if (parsedAmount < 0) {
            status.textContent = 'Error: Enter a valid amount, please.';
            return;
        }

        try {
            const convertedAmount = await convert(fromCurrency, toCurrency, parsedAmount);
            result.value = convertedAmount.toFixed(2);
            rate.value = (convertedAmount / parsedAmount).toFixed(6);
            status.textContent = '';
        } catch (error) {
            status.textContent = 'Error: Unable to perform the conversion.';
        }
    } else {
        status.textContent = 'Error: Please select currencies and enter a valid amount.';
    }
});


resetBtn.addEventListener('click', () => {
    from.value = '';
    to.value = '';
    amount.value = '';
    result.value = '';
    rate.value = '';
    status.textContent = '';
});

getCurrencies();

