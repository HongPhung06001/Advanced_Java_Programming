package com.admission.job;

import com.admission.controller.AdmissionController;
import org.quartz.*;

import java.time.LocalDate;

public class CongBoDiemChuanJob implements Job {
    
    private final AdmissionController admissionController = new AdmissionController();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        admissionController.handleAdmissonAndSendMail(LocalDate.now().getYear());
        // Lấy đối tượng Scheduler từ JobExecutionContext
        Scheduler scheduler = context.getScheduler();
        try {
            // Dừng scheduler

            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
