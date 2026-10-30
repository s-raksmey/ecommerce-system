package com.example.payment.domain;

import com.example.payment.domain.entity.CreditEntry;
import com.example.payment.domain.entity.CreditHistory;
import com.example.payment.domain.entity.Payment;
import com.example.payment.domain.event.PaymentEvent;

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
