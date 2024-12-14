package com.admission.controller;

import com.sun.mail.smtp.SMTPSendFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ThreadSendMailPassAdmission extends Thread {

    private final Logger logger = LogManager.getLogger(ThreadSendMailPassAdmission.class);

    private Thread thread;

    private final String threadName;

    private final String toEmail;

    private final String majorName;

    public ThreadSendMailPassAdmission(String name, String toEmail, String majorName) {
        this.threadName = name;
        this.toEmail = toEmail;
        this.majorName = majorName;
        logger.info("Creating " + threadName);
    }

    @Override
    public void run() {
        logger.info("Running " + threadName);
        try {
            send(toEmail, majorName);
        } catch (Exception e) {
            logger.error("Thread " + threadName + " lỗi: " + e.getMessage());
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

    public void send(String toEmail, String tenNganh) throws Exception {
        final String fromEmail = "bloomi.hit@gmail.com";
        final String password = "mmxibmamsyfrbood";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "false");
        properties.put("mail.smtp.starttls.enable", "true"); //TLS

        // Tạo đối tượng Authenticator để xác thực tài khoản email
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(properties, authenticator);
        try {
            // Tạo đối tượng MimeMessage
            MimeMessage message = new MimeMessage(session);
            // Thiết lập thông tin người gửi và người nhận
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Thông báo chúng tuyển", "UTF-8");
            // Thiết lập nội dung email dạng HTML
            String htmlContent = htmlThongBaoChungTuyen(tenNganh);
            message.setContent(htmlContent, "text/html; charset=UTF-8");
            // Gửi email
            Transport.send(message);
        } catch (SMTPSendFailedException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String htmlThongBaoChungTuyen(String tenNganh) {
        String html = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "    <title>Thông báo chúng tuyển</title>\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            background-color: #f2f2f2;\n"
                + "            padding: 20px;\n"
                + "        }\n"
                + "\n"
                + "        h1 {\n"
                + "            color: #333333;\n"
                + "            text-align: center;\n"
                + "        }\n"
                + "\n"
                + "        .notification {\n"
                + "            background-color: #ffffff;\n"
                + "            border-radius: 5px;\n"
                + "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n"
                + "            padding: 20px;\n"
                + "            margin: 0 auto;\n"
                + "            max-width: 600px;\n"
                + "        }\n"
                + "\n"
                + "        p {\n"
                + "            color: #666666;\n"
                + "            line-height: 1.6;\n"
                + "        }\n"
                + "\n"
                + "        .cta-button {\n"
                + "            display: inline-block;\n"
                + "            background-color: #4caf50;\n"
                + "            color: #ffffff;\n"
                + "            text-decoration: none;\n"
                + "            padding: 10px 20px;\n"
                + "            border-radius: 4px;\n"
                + "            margin-top: 20px;\n"
                + "        }\n"
                + "\n"
                + "        .cta-button:hover {\n"
                + "            background-color: #45a049;\n"
                + "        }\n"
                + "    </style>\n"
                + "</head>"
                + "<body>\n"
                + "    <div class=\"notification\">\n"
                + "        <h1>Chúc mừng bạn đã được tuyển vào trường đại học</h1>\n"
                + "        <p>\n"
                + "            Chúng tôi xin chân thành chúc mừng bạn đã vượt qua quá trình tuyển sinh và trở thành một thành viên của trường đại học chúng tôi.\n"
                + "            Chúng tôi rất vui mừng được chào đón bạn và hy vọng rằng bạn sẽ có những trải nghiệm học tập thú vị và thành công tại trường.\n"
                + "        </p>\n"
                + "        <p>\n"
                + "            Ngành chúng tuyển: "
                + tenNganh + "\n</br>"
                + "            Thời gian nhập học: 05/09/2023\n"
                + "        </p>\n"
                + "        <p>\n"
                + "            Hãy chuẩn bị tinh thần để bắt đầu cuộc hành trình học tập và khám phá tại trường đại học của chúng tôi.\n"
                + "            Chúng tôi tin rằng bạn sẽ tận dụng tốt cơ hội này và đạt được những thành tựu xuất sắc trong tương lai.\n"
                + "        </p>\n"
                + "        <a href=\"https://tuyensinh.haui.edu.vn/\" class=\"cta-button\">Truy cập để biết thêm thông tin chi tiết nhập học</a>\n"
                + "    </div>\n"
                + "</body>\n"
                + "</html>";
        return html;
    }

}
