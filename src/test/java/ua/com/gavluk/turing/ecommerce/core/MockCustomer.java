package ua.com.gavluk.turing.ecommerce.core;

public class MockCustomer extends Customer {
    public MockCustomer(long id, String name, String email) {
        super(name, email, "");
        this.id = id;
    }
}
