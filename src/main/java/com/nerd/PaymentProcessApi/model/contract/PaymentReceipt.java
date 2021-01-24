package com.nerd.PaymentProcessApi.model.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class PaymentReceipt implements Serializable {
    String loanName;

    @Digits(integer=10, fraction=2)
    BigDecimal outstandingBalance;

    @Digits(integer=10, fraction=2)
    BigDecimal contribution;
}
