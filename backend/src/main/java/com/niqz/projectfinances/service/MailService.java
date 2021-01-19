package com.niqz.projectfinances.service;

import com.niqz.projectfinances.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public void sendPasswordRestoreLink(User targetUser, String link) throws MessagingException {
        sendEmail(
                targetUser.getEmail(),
                "Project Finances - Password Restore Link",
                "mail/password-restore-link",
                Map.of("firstName", targetUser.getFirstName(),
                        "lastName", targetUser.getLastName(),
                        "link", link));
    }

    public void sendUpdatedPasswordMessage(String email) throws MessagingException {
        sendEmail(
                email,
                "Project Finances - New Password",
                "mail/password-update",
                Collections.emptyMap()
        );
    }

    private void sendEmail(String to, String subject, String template, Map<String, String> params) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        params.forEach(context::setVariable);
        String text = templateEngine.process(template, context);

        helper.setFrom("ProjectFinancesAdmin");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        javaMailSender.send(message);
    }
}
