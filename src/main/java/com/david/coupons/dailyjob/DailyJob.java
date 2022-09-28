package com.david.coupons.dailyjob;

import com.david.coupons.services.DailyJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyJob {

    private final DailyJobService dailyJobService;
   @Scheduled(fixedDelay = 1000*10 ,fixedRate = 1000*60)
    public void checkExpiredCoupons(){
        System.out.println("Starting expired coupons deletion");
        dailyJobService.check();
    }
}
