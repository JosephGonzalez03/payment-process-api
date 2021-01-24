package com.nerd.PaymentProcessApi.controller;

import com.nerd.PaymentProcessApi.configuration.RestTemplateConfiguration;
import com.nerd.PaymentProcessApi.configuration.HttpProperties;
import com.nerd.PaymentProcessApi.model.contract.OrderBy;
import com.nerd.PaymentProcessApi.model.contract.PaymentSummariesOperation;
import com.nerd.PaymentProcessApi.model.contract.PaymentSummary;
import com.nerd.PaymentProcessApi.model.provider.Loan;
import com.nerd.PaymentProcessApi.service.PaymentSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("users/{userId}/paymentSummaries")
public class PaymentSummaryController {

    @Autowired
    RestTemplateConfiguration restTemplateConfiguration;

    @Autowired
    PaymentSummaryService paymentService;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<PaymentSummary>> getAllPaymentSummaries(
            @PathVariable Integer userId,
            @RequestParam PaymentSummariesOperation operation,
            @RequestParam(defaultValue = "NAME") OrderBy orderBy)
    {
        HttpProperties properties = restTemplateConfiguration.loanSystemApiProperties();
        RestTemplate loanSystemApi = restTemplateConfiguration.restTemplate(properties);
        List<PaymentSummary> paymentSummaryResponseBodies = new ArrayList<>();

        switch (operation) {
            case FORECAST:
                ResponseEntity<Loan[]> orderedLoans = loanSystemApi.getForEntity("/users/{userId}/loans?orderBy={orderBy}", Loan[].class, userId, orderBy);
                paymentSummaryResponseBodies = paymentService.getPaymentSummaries(Arrays.asList(orderedLoans.getBody()));
                break;
            default:
                break;
        }

        return new ResponseEntity<>(paymentSummaryResponseBodies, HttpStatus.OK);
    }
}
