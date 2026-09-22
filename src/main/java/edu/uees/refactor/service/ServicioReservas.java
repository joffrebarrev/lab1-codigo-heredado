package edu.uees.refactor.service;

import edu.uees.refactor.domain.Monto;
import edu.uees.refactor.domain.Reserva;

public class ServicioReservas {

    private final NotificadorEmail notificador;

    public ServicioReservas() {
        this.notificador = new NotificadorEmail();
    }

    public ServicioReservas(NotificadorEmail notificador) {
        this.notificador = notificador;
    }

    public double procesar(Reserva r, int horasAnticipacion) {

        if (r == null) {
            return 0;
        }

        if (r.getCorreo() == null || !r.getCorreo().contains("@")) {
            return 0;
        }

        if (r.getInicio() == null || r.getFin() == null || !r.getFin().isAfter(r.getInicio())) {
            return 0;
        }

        if (horasAnticipacion < 2) {
            return 0;
        }

        r.confirmar(); 
        System.out.println("Guardando reserva " + r.getId());

        notificador.enviarConfirmacion(r);

        return calcularTotal(r);
    }

    private double calcularTotal(Reserva r) {
        Monto montoBase = new Monto(40.0);
        if ("VIP".equals(r.getTipo())) {
            return montoBase.aplicarDescuento(0.15).valor();
        }
        return montoBase.valor();
    }
}