package com.tuan.ebankservice.configuration;


import com.tuan.ebankservice.service.LoanService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleTasks {
    LoanService loanService;

    @Scheduled(fixedRate = 86400000)
    public void checkLoanLate(){
        loanService.findLoanLate();
    }

}
