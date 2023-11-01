package com.shoes.ordering.system.domains.payment.domain;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.common.valueobject.PaymentStatus;
import com.shoes.ordering.system.domains.payment.domain.core.PaymentDomainService;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditEntry;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditHistory;
import com.shoes.ordering.system.domains.payment.domain.core.entity.Payment;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentCompletedEvent;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentEvent;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentFailedEvent;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.CreditHistoryId;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.TransactionType;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static com.shoes.ordering.system.domains.common.config.DomainConstants.UTC;

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService {
    @Override
    public PaymentEvent validateAndInitiatePayment(Payment payment,
                                                   CreditEntry creditEntry,
                                                   List<CreditHistory> creditHistories,
                                                   List<String> failureMessages) {
        payment.validatePayment(failureMessages);
        payment.initializePayment();
        validateCreditEntry(payment, creditEntry, failureMessages);
        subtractCreditEntry(payment,creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.DEBIT);
        validateCreditHistory(creditEntry, creditHistories, failureMessages);

        if (failureMessages.isEmpty()) {
            log.info("Payment is initiating for orderId: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.COMPLETED);
            return new PaymentCompletedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)));
        } else {
            log.info("Payment initiating is failed for orderId: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), failureMessages);
        }
    }

    @Override
    public PaymentEvent validateAndCancelPayment(Payment payment,
                                                 CreditEntry creditEntry,
                                                 List<CreditHistory> creditHistories,
                                                 List<String> failureMessages) {
        return null;
    }

    private void validateCreditEntry(Payment payment, CreditEntry creditEntry, List<String> failureMessages) {
        if (payment.getPrice().isGreaterThan(creditEntry.getTotalCreditAmount())) {
            log.error("Member with id: {} doesn't have enough credit for payment", payment.getMemberId().getValue());
            failureMessages.add("Member with id= " + payment.getMemberId().getValue()
                    + " doesn't have enough credit for payment.");
        }
    }

    private void subtractCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.subtractCreditAmount(payment.getPrice());
    }

    private void updateCreditHistory(Payment payment, List<CreditHistory> creditHistories, TransactionType transactionType) {
        creditHistories.add(CreditHistory.builder()
                .creditHistoryId(new CreditHistoryId(UUID.randomUUID()))
                .memberId(payment.getMemberId())
                .amount(payment.getPrice())
                .transactionType(transactionType)
                .build());
    }

    private void validateCreditHistory(CreditEntry creditEntry,
                                       List<CreditHistory> creditHistories,
                                       List<String> failureMessages) {
        Money totalCreditHistory = getTotalHistoryAmount(creditHistories, TransactionType.CREDIT);
        Money totalDebitHistory = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT);

        if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
            log.error("Member with id: {} doesn't have enough credit for payment", creditEntry.getMemberId().getValue());
            failureMessages.add("Member with id= " + creditEntry.getMemberId().getValue()
                    + " doesn't have enough credit for payment.");
        }

        if (!creditEntry.getTotalCreditAmount().equals(totalCreditHistory.subtract(totalDebitHistory))) {
            log.error("Credit history total is not equal to current credit for customer id: {}",
                    creditEntry.getMemberId().getValue());
            failureMessages.add("Credit history total is not equal to current credit for customer id: "
                    + creditEntry.getMemberId().getValue());
        }
    }

    private Money getTotalHistoryAmount(List<CreditHistory> creditHistories, TransactionType transactionType) {
        return creditHistories.stream()
                .filter(creditHistory -> transactionType == creditHistory.getTransactionType())
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::add);
    }
}
