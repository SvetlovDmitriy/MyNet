package com.my.utilit;

import com.my.db.entity.Product;
import com.my.db.entity.Service;
import com.my.db.entity.TimeT;
import com.my.db.entity.User;
import java.util.Date;

public class CalculationOfPayments {
    private static final int COEFFICIENT = 5;
    public static void getPayment(User user, Product product, Service service, TimeT timeT){
        if (service.getStatusId() == 2) {
            user.setCash(user.getCash() - product.getPrice() / COEFFICIENT * timeT.getTotal());
        } else {
            java.util.Date date = new Date();
            long time = date.getTime();
            long total = (time - timeT.getTime().getTime()) / 1000 + timeT.getTotal();
            user.setCash(user.getCash() - product.getPrice() / COEFFICIENT * total);
        }
    }
}
