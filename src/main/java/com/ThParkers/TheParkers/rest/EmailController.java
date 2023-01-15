package com.ThParkers.TheParkers.rest;

// Java Program to Create Rest Controller that
// Defines various API for Sending Mail

// Importing required classes
import com.ThParkers.TheParkers.dummy.EmailDetails;
import com.ThParkers.TheParkers.service.EmailServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// Annotation
@RestController
// Class
public class EmailController {

    EmailServiceImpl emailService;

    public EmailController (EmailServiceImpl emailService){
        this.emailService = emailService;
    }

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }
}
