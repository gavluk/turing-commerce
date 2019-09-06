package ua.com.gavluk.turing.ecommerce.core;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class MailingService {

    private final Configuration freemakerConfig;
    private final JavaMailSender mailSender;
    private final Properties subjects;


    @Autowired
    public MailingService(Configuration freemakerConfig, JavaMailSender mailSender) throws IOException {
        this.freemakerConfig = freemakerConfig;
        this.mailSender = mailSender;
        this.subjects = new Properties();
        this.subjects.load(this.getClass().getClassLoader().getResourceAsStream("mail-subjects.properties"));

    }

    public void send(Customer customer, String template, Map<String, Object> payloads) throws IOException, MessagingException, TemplateException {
        this.send(customer, customer.getEmail(), template, payloads);
    }

    public void send(Customer customer, String emailAddressToSend, String template, Map<String, Object> payloads)
            throws IOException, MessagingException, TemplateException
    {

        // creating new hashmap to do not touch input parameter
        Map<String, Object> content = new HashMap<>(payloads);
        content.put("customer", customer);

        MimeMessage msg = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(emailAddressToSend);
        helper.setSubject(this.subjects.getProperty(template));

        Template tpl = this.freemakerConfig.getTemplate(template);
        StringWriter body = new StringWriter();
        tpl.process(content, body);
        helper.setText(body.toString(), true);

        this.mailSender.send(msg);
    }

}
