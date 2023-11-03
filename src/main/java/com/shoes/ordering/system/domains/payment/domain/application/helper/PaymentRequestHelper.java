package com.shoes.ordering.system.domains.payment.domain.application.helper;

import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.payment.domain.application.dto.PaymentRequest;
import com.shoes.ordering.system.domains.payment.domain.application.exception.PaymentApplicationServiceException;
import com.shoes.ordering.system.domains.payment.domain.application.mapper.PaymentDataMapper;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository.CreditEntryRepository;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository.CreditHistoryRepository;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository.PaymentRepository;
import com.shoes.ordering.system.domains.payment.domain.core.PaymentDomainService;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditEntry;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditHistory;
import com.shoes.ordering.system.domains.payment.domain.core.entity.Payment;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class PaymentRequestHelper {

    private final PaymentDomainService paymentDomainService;
    private final PaymentDataMapper paymentDataMapper;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditHistoryRepository creditHistoryRepository;

    public PaymentRequestHelper(PaymentDomainService paymentDomainService,
                                PaymentDataMapper paymentDataMapper,
                                PaymentRepository paymentRepository,
                                CreditEntryRepository creditEntryRepository,
                                CreditHistoryRepository creditHistoryRepository) {
        this.paymentDomainService = paymentDomainService;
        this.paymentDataMapper = paymentDataMapper;
        this.paymentRepository = paymentRepository;
        this.creditEntryRepository = creditEntryRepository;
        this.creditHistoryRepository = creditHistoryRepository;
    }

    @Transactional
    public PaymentEvent persistPayment(PaymentRequest paymentRequest) {
        log.info("Received payment complete event for order id: {}", paymentRequest.getOrderId());
        Payment payment = paymentDataMapper.paymentRequestToPayment(paymentRequest);
        CreditEntry creditEntry = getCreditEntry(payment.getMemberId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getMemberId());
        List<String> failureMessages = new ArrayList<>();

        PaymentEvent paymentEvent
                = paymentDomainService.validateAndInitiatePayment(payment, creditEntry, creditHistories,failureMessages);
        persisDBObjects(payment, creditEntry, creditHistories, failureMessages);
        return paymentEvent;
    }

    private CreditEntry getCreditEntry(MemberId memberId) {
        Optional<CreditEntry> creditEntry = creditEntryRepository.findByMemberId(memberId);
        if (creditEntry.isEmpty()) {
            log.error("Could not find credit for memberId: {}", memberId.getValue());
            throw  new PaymentApplicationServiceException("Could not find credit for memberId: "
            + memberId.getValue());
        }
        return creditEntry.get();
    }

    private List<CreditHistory> getCreditHistory(MemberId memberId) {
        Optional<List<CreditHistory>> creditHistories = creditHistoryRepository.findByMemberId(memberId);
        if (creditHistories.isEmpty()) {
            log.error("Could not find credit history for memberId: {}", memberId.getValue());
            throw new PaymentApplicationServiceException("Could not find credit history for memberId: "
            + memberId.getValue());
        }
        return creditHistories.get();
    }


    private void persisDBObjects(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        paymentRepository.save(payment);

        if (failureMessages.isEmpty()) {
            creditEntryRepository.save(creditEntry);
            creditHistoryRepository.save(creditHistories.get(creditHistories.size() - 1));
        }
    }
}
