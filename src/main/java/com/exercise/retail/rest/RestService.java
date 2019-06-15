package com.exercise.retail.rest;

import com.exercise.retail.model.DiscountInfo;
import com.exercise.retail.model.PaymentInfo;
import com.exercise.retail.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest service responsible for calculating the discount for a payment depending on the user and payment information
 */
@RestController
public class RestService {

    @Autowired
    private DiscountService discountService;

    @PostMapping(path = "/discount")
    @ResponseBody
    public DiscountInfo calculateDiscount(@RequestBody PaymentInfo paymentInfo) {
        return discountService.calculateDiscount(paymentInfo);
    }
}
