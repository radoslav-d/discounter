package com.exercise.retail.rest;

import com.exercise.retail.DiscountBase;
import com.exercise.retail.DiscounterApplication;
import com.exercise.retail.model.*;
import com.exercise.retail.service.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        RestService.class,
        DiscountService.class,
        DiscountServiceImpl.class,
        DiscountMappingService.class,
        DiscountMappingServiceImpl.class,
        DiscounterApplication.class
})
public class RestServiceTest extends DiscountBase {

    @Autowired
    private RestService restService;

    @Test
    public void Should_Calculate_Discount_For_Employee_Without_Groceries() {
        withGroceries(false);
        withAmount(150.45);
        withUserType(UserType.EMPLOYEE);
        discountInfo = restService.calculateDiscount(paymentInfo);
        assertCalculated(150.45, 50.135, DiscountType.EMPLOYEE_DISCOUNT);
    }

    @Test
    public void Should_Calculate_Discount_For_Affiliate_Without_Groceries() {
        withGroceries(false);
        withAmount(280.0);
        withUserType(UserType.AFFILIATE);
        discountInfo = restService.calculateDiscount(paymentInfo);
        assertCalculated(280.0, 38.0, DiscountType.AFFILIATE_DISCOUNT);
    }

    @Test
    public void Should_Calculate_Discount_For_LoyalUser_Without_Groceries() {
        withGroceries(false);
        withAmount(380.0);
        withUserType(UserType.LOYAL);
        discountInfo = restService.calculateDiscount(paymentInfo);
        assertCalculated(380.0, 34.0, DiscountType.LOYALTY_DISCOUNT);
    }

    @Test
    public void Should_NotCalculate_Discount_For_RegularUser_With_Groceries() {
        withGroceries(true);
        withAmount(380.0);
        withUserType(UserType.REGULAR);
        discountInfo = restService.calculateDiscount(paymentInfo);
        assertCalculated(380.0, 15.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_Employee_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.EMPLOYEE);
        discountInfo = restService.calculateDiscount(paymentInfo);
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_Affiliate_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.AFFILIATE);
        discountInfo = restService.calculateDiscount(paymentInfo);
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }

    @Test
    public void Should_NotCalculate_Discount_For_LoyalUser_With_Groceries() {
        withGroceries(true);
        withAmount(150.45);
        withUserType(UserType.LOYAL);
        discountInfo = restService.calculateDiscount(paymentInfo);
        assertCalculated(150.45, 5.0, DiscountType.NONE);
    }
}
