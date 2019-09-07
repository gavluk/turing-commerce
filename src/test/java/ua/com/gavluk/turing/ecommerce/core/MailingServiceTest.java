package ua.com.gavluk.turing.ecommerce.core;

import freemarker.template.Configuration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MailingServiceTest {

    @Autowired
    Configuration tplConfig;

    @Test
    void test_templates_are_there() throws IOException {

        //String resourcePath =  OrderService.MAIL_TEMPLATE_ORDER_IS_PAYED + ".ftl";
        //InputStream is = this.getClass().getClassLoader().getResourceAsStream(resourcePath);

//        tplConfig.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "/mail-templates/");
        tplConfig.getTemplate(OrderService.MAIL_TEMPLATE_ORDER_IS_PAYED + ".ftl");
        tplConfig.getTemplate(OrderService.MAIL_TEMPLATE_ORDER_IS_NOT_PAYED + ".ftl");
    }

}