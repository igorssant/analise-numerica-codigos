/**
 * @author igorsssantana
 */
package model.interfaces;

public abstract class ModeloMatematico {
    private double xAnterior;
    private double xAtual;
    private double fxAnterior;
    private double derivadaFxAnterior;
    private double tolerancia;
    
    public abstract void gerarNovoXk();
}