package com.shoes.ordering.system.domains.payment.domain.core;

import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditEntry;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditHistory;
import com.shoes.ordering.system.domains.payment.domain.core.entity.Payment;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentEvent;

import java.util.List;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages);

    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages);
}
