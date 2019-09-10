package ua.com.gavluk.turing.ecommerce.core;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultPasswordEncoder extends Pbkdf2PasswordEncoder {
}
