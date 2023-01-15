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
    public String sendSimpleMail(Emails details)
    {
        try {

            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return "Correo enviado con éxito...";
        }

        catch (Exception e) {
            return "Algo ha fallado al enviar el correo";
        }
    }


    public String envioMasivo (EmailMasivoBase mailbase) {
        String mensaje = mailbase.getMensaje();
        String asunto = mailbase.getAsunto();
        List<Cliente> clienteList = clienteService.findAllClientes();
        if (!clienteList.isEmpty()){
            Emails emailstemporal = new Emails();
            for (int i=0; i < clienteList.size(); i++) {
                emailstemporal.setRecipient(clienteList.get(i).getCorreo());
                System.out.println(clienteList.get(i).getCorreo());
                emailstemporal.setMsgBody(clienteList.get(i).getNombreCliente() + ":\n\n" + mensaje);
                emailstemporal.setSubject(asunto);
                sendSimpleMail(emailstemporal);
            }

            return "Los correos se han enviado con éxito";
        }
        return "¡No hay clientes para enviar correos!";
    }
}
