package com.nerd.PaymentProcessApi.controller;

import com.nerd.PaymentProcessApi.configuration.RestTemplateConfiguration;
import com.nerd.PaymentProcessApi.configuration.HttpProperties;
import com.nerd.PaymentProcessApi.model.contract.OrderBy;
import com.nerd.PaymentProcessApi.model.contract.PaymentSummariesOperation;
import com.nerd.PaymentProcessApi.model.contract.PaymentSummary;
import com.nerd.PaymentProcessApi.model.provider.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users/{userId}")
public class PaymentController {

    @Autowired
    RestTemplateConfiguration restTemplateConfiguration;

    @GetMapping(value = "/paymentSummaries", produces = "application/json")
    public ResponseEntity<List<PaymentSummary>> getAllPaymentSummaries(
            @PathVariable Integer userId,
            @RequestParam PaymentSummariesOperation operation,
            @RequestParam(defaultValue = "NAME") OrderBy orderBy)
    {
        HttpProperties properties = restTemplateConfiguration.loanSystemApiProperties();
        RestTemplate loanSystemApi = restTemplateConfiguration.restTemplate(properties);

        ResponseEntity<Loan[]> orderedLoans = loanSystemApi.getForEntity("/users/1/loans?orderBy={orderBy}", Loan[].class, orderBy);

        List<PaymentSummary> paymentSummaryResponseBodies = new ArrayList<>();
        return new ResponseEntity<>(paymentSummaryResponseBodies, HttpStatus.OK);
    }
}
