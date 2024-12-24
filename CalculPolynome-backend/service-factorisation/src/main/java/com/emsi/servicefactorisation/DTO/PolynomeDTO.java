package com.emsi.servicefactorisation.DTO;

public class PolynomeDTO {
    private String expression; // L'expression d'origine du polynôme
    private String factorizedValue; // La valeur factorisée

    // Constructeurs
    public PolynomeDTO() {
    }

    public PolynomeDTO(String expression, String factorizedValue) {
        this.expression = expression;
        this.factorizedValue = factorizedValue;
    }

    // Getters et setters
    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getFactorizedValue() {
        return factorizedValue;
    }

    public void setFactorizedValue(String factorizedValue) {
        this.factorizedValue = factorizedValue;
    }
}
