package com.david.coupons;
import com.david.coupons.dailyjob.DailyJob;
import com.david.coupons.mock.Insert;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CouponsProjectApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(CouponsProjectApplication.class, args);

        try {
            ctx.getBean(DailyJob.class).checkExpiredCoupons();
            //ctx.getBean(Insert.class).run();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
  }
}
