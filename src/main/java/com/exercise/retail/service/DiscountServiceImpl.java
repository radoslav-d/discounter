package com.exercise.retail.service;

import com.exercise.retail.model.DiscountInfo;
import com.exercise.retail.model.DiscountType;
import com.exercise.retail.model.PaymentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountMappingService mappingService;

    /**
     * Calculates a discount based on a PaymentInfo.
     *
     * Two types of discounts are calculated: percentage-based and voucher-based.
     * The percentage discount depends on the UserType and whether the paid items contains groceries.
     * The voucher discount reduce the total bill by $5 for every $100.
     */
    @Override
    public DiscountInfo calculateDiscount(PaymentInfo paymentInfo) {
        DiscountInfo discountInfo = new DiscountInfo();
        DiscountType discountType = checkDiscountType(paymentInfo);
        Double amount = paymentInfo.getAmount();

        Double percentageDiscount = calculatePercentageDiscount(discountType, amount);
        Double voucherDiscount = calculateAdditionalDiscount(amount);

        discountInfo.setDiscount(percentageDiscount + voucherDiscount)
                .setType(discountType)
                .setTotalBill(amount);
        return discountInfo;
    }

    private DiscountType checkDiscountType(PaymentInfo paymentInfo) {
        if (paymentInfo.isContainingGroceries()) {
            return DiscountType.NONE;
        }
        return mappingService.getDiscountByUserType(paymentInfo.getUserInfo().getType());
    }

    private double calculatePercentageDiscount(DiscountType discountType, Double amount) {
        return mappingService.getFunctionByDiscount(discountType).apply(amount);
    }

    private double calculateAdditionalDiscount(Double amount) {
        double vouchersCount = Math.floor(amount / 100);
        return vouchersCount * 5;
    }
}
