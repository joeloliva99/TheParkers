package com.ThParkers.TheParkers.rest;

import com.ThParkers.TheParkers.dummy.EmailMasivoBase;
import com.ThParkers.TheParkers.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailRestController {
   private EmailService emailService;
   public EmailRestController (EmailService emailService) {
       this.emailService = emailService;
   }

    @PostMapping(value = "")
    public String enviarCorreo(@RequestBody EmailMasivoBase emailMasivoBase) {
        return emailService.envioMasivo(emailMasivoBase);
    }
}
