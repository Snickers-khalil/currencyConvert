package com.isetr.currencyconverter.Data;

public class ExchangeRate {
    private String currency;

    private String amount;
    private double rate;

    public ExchangeRate(String currency,String amount, double rate) {
        this.currency = currency;
        this.amount=amount;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }
    public String getAmount() {
        return amount;
    }

    public double getRate() {
        return rate;
    }
}
