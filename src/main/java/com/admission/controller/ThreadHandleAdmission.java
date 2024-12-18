package com.admission.controller;

import com.admission.dto.StudentDTO;
import com.admission.model.Admission;
import com.admission.model.MajorDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ThreadHandleAdmission extends Thread {

    private final Logger logger = LogManager.getLogger(ThreadHandleAdmission.class);

    private Thread thread;

    private final String threadName;

    private final int year;

    private final StudentDTO student;

    private final AdmissionController admissionController;

    private final MajorDetailController majorDetailController;

    public ThreadHandleAdmission(String name, int year, StudentDTO student) {
        this.threadName = name;
        this.year = year;
        this.student = student;
        this.admissionController = new AdmissionController();
        this.majorDetailController = new MajorDetailController();
        logger.info("Creating " + threadName);
    }

    @Override
    public void run() {
        logger.info("Running " + threadName);
        try {
            List<Admission> admissions = admissionController.getAdmissionsByStudentId(student.getId());
            for (Admission admission : admissions) {
                if (checkStatusAdmission(admissions)) {
                    admission.setStatus(4);
                } else {
                    MajorDetail majorDetail = majorDetailController.getMajorDetailByMajorIdAndYear(admission.getMajor().getId(), year);
                    if (admission.getTotalScore() < majorDetail.getBenchMark()) {
                        admission.setStatus(3);
                    } else {
                        admission.setStatus(2);
                        StringBuilder threadNameSendMail = new StringBuilder("Thread-SendMail-");
                        threadNameSendMail.append(student.getLastName());
                        threadNameSendMail.append("-");
                        threadNameSendMail.append(student.getFirstName());
                        threadNameSendMail.append("-");
                        threadNameSendMail.append(student.getOrderNumber());
                        String majorName = admission.getMajor().getName();
                        String toEmail = student.getEmail();
                        ThreadSendMailPassAdmission threadSendMail
                                = new ThreadSendMailPassAdmission(threadNameSendMail.toString(), toEmail, majorName);
                        threadSendMail.start();
                    }
                }
                Thread.sleep(50);
            }
            admissions.forEach(admission -> {
                admissionController.adminUpdateAdmission(admission);
            });
        } catch (InterruptedException e) {
            logger.error("Thread " + threadName + " bị gián đoạn.");
        } catch (Exception e) {
            logger.error("Thread " + threadName + " bị lỗi: " + e.getMessage());
        }
        logger.info("Thread " + threadName + " hoàn thành.");
    }

    @Override
    public void start() {
        logger.info("Starting " + threadName);
        if (thread == null) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }

    private boolean checkStatusAdmission(List<Admission> admissions) {
        return admissions.stream().anyMatch(admission -> (admission.getStatus().equals(2)));
    }
    
}
