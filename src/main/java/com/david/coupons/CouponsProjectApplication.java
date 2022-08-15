package com.david.coupons;
import com.david.coupons.dailyjob.DailyJob;
//import com.david.coupons.main.CouponSystem;
//import com.JB.couponsproject.rest.RestApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CouponsProjectApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(CouponsProjectApplication.class, args);

        //ctx.getBean(RestApi.class).run();
        try {
            //ctx.getBean(Insert.class).run();
            ctx.getBean(DailyJob.class).checkExpiredCoupons();
            //ctx.getBean(CouponSystem.class).login();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
  }
}
