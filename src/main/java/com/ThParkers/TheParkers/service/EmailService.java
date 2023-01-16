package com.ThParkers.TheParkers.service;

import com.ThParkers.TheParkers.dummy.EmailMasivoBase;
import com.ThParkers.TheParkers.model.Cliente;
import com.ThParkers.TheParkers.model.Emails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired private JavaMailSender javaMailSender;
    private ClienteService clienteService;

    public EmailService (ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Value("${spring.mail.username}") private String sender;
    public boolean sendSimpleMail(Emails details)
    {
        try {

            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return true;
        }

        catch (Exception e) {
            return false;
        }
    }


    public String envioMasivo (EmailMasivoBase mailbase) {
        boolean correcto = false;
        int correctos =0;
        int correosTotales = 0;
        String mensaje = mailbase.getMensaje();
        String asunto = mailbase.getAsunto();
        List<Cliente> clienteList = clienteService.findAllClientes();
        if (!clienteList.isEmpty()){
            Emails emailstemporal = new Emails();
            correosTotales = clienteList.size();
            for (int i=0; i < correosTotales; i++) {
                emailstemporal.setRecipient(clienteList.get(i).getCorreo());
                emailstemporal.setMsgBody(clienteList.get(i).getNombreCliente() + ":\n\n" + mensaje);
                emailstemporal.setSubject(asunto);
                correcto = sendSimpleMail(emailstemporal);
                if (correcto){
                    correctos++;
                }
            }

            if (correctos == correosTotales){
                return "Todos los correos se han enviado correctamente";
            }

            if (correctos == 0){
                return "No se ha podido enviar ningún correo. Inténtelo más tarde";
            }

            return "Se han podido enviar " + correctos + " de los " + correosTotales + " correos en cola";
        }
        return "¡No hay clientes para enviar correos!";
    }
}
