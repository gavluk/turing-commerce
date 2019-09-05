package ua.com.gavluk.turing.ecommerce.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.gavluk.turing.ecommerce.core.Order;
import ua.com.gavluk.turing.ecommerce.core.OrderService;
import ua.com.gavluk.turing.ecommerce.core.PaymentStatus;
import ua.com.gavluk.turing.ecommerce.core.stripe.StripePaymentCredentials;
import ua.com.gavluk.turing.ecommerce.core.stripe.StripePaymentStatus;
import ua.com.gavluk.turing.ecommerce.exceptions.AuthException;
import ua.com.gavluk.turing.ecommerce.exceptions.InternalErrorException;
import ua.com.gavluk.turing.ecommerce.exceptions.NotFoundException;
import ua.com.gavluk.turing.ecommerce.exceptions.ValidationException;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/stripe")
public class StripeController {

    private final OrderService orderService;

    @Autowired
    public StripeController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/charge")
    public StripeChargeResponseDto payByStripe(
            CustomerAuthentication auth,
            @RequestBody @Valid StripeChargeRequestDto payReq
    ) throws AuthException, NotFoundException, InternalErrorException, IOException, ValidationException {

        Order order = this.orderService.findById(auth.getCustomer(), payReq.getOrderId());

        PaymentStatus paymentStatus = this.orderService.payOrder(
                order,
                new StripePaymentCredentials(payReq.getStripeToken()),
                auth.getCustomer(),
                payReq.getEmail()
        );

        return new StripeChargeResponseDto(
                (StripePaymentStatus) paymentStatus,
                String.format("Payment is %s", paymentStatus.isSucceed() ? "succeed" : "fail")
        );
    }

}
