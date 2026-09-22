package edu.uees.refactor.domain;

public record Monto(double valor) {

    public Monto {
        if (valor < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }
    }

    public Monto aplicarDescuento(double porcentaje) {
        double nuevoValor = this.valor * (1.0 - porcentaje);
        return new Monto(nuevoValor);
    }
}