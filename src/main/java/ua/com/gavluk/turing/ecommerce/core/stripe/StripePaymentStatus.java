package ua.com.gavluk.turing.ecommerce.core.stripe;

import com.stripe.model.Charge;
import ua.com.gavluk.turing.ecommerce.core.PaymentStatus;

public class StripePaymentStatus extends PaymentStatus {

    private static final String STATUS_SUCCEED = "succeeded";
    private final Charge charge;

    public StripePaymentStatus(Charge charge) {
        super(charge.getId(), STATUS_SUCCEED.equals(charge.getStatus()));
        if (!this.isSucceed()) {
            this.errorKey = charge.getFailureCode();
            this.errorMessage = charge.getFailureMessage();
        }
        this.charge = charge;
    }

    public Charge getCharge() {
        return charge;
    }
}
