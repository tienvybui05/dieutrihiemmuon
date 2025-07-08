package uth.edu.dieutrihiemmuon.CustomerControllerUnitTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uth.edu.dieutrihiemmuon.controllers.Customer.BillingController;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BillingControllerTest {

    @InjectMocks
    private BillingController billingController;

    @Test
    void testHistory_ReturnsHistoryView() {
        String view = billingController.history();
        assertEquals("customer/history", view);
    }

    @Test
    void testPayment_ReturnsPaymentView() {
        String view = billingController.payment();
        assertEquals("customer/payment", view);
    }
}
