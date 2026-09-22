package edu.uees.refactor.service;

import edu.uees.refactor.domain.Reserva;

public class NotificadorEmail {

    public void enviarConfirmacion(Reserva r) {
        if (r != null && r.getCorreo() != null) {
            System.out.println("Correo enviado a " + r.getCorreo());
        }
    }
}