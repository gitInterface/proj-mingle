package com.ispan.mingle.projmingle.Service;

import java.util.Properties;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.mingle.projmingle.domain.VolunteerBean;
import com.ispan.mingle.projmingle.domain.VolunteerDetailBean;
import com.ispan.mingle.projmingle.repository.VolunteerDetailRepository;
import com.ispan.mingle.projmingle.repository.VolunteerRepository;
import com.ispan.mingle.projmingle.util.DatetimeConverter;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VolunteerDetailService {

    @Autowired
    private VolunteerDetailRepository volunteerDetailRepository;
    @Autowired
    private VolunteerRepository volunteerRepository;

    public VolunteerDetailBean findById(String id) {
        return volunteerDetailRepository.findById(id).orElse(null);
    }

    public VolunteerDetailBean update(String userid, String json) {
        // 接收使用者資料
        JSONObject job = new JSONObject(json);
        String update = job.isNull("update") ? null : job.getString("update");
        // 呼叫企業邏輯程式
        VolunteerDetailBean vDBean = volunteerDetailRepository.findById(userid)
                // 如果找不到資料就回傳一個空的資料
                .orElse(new VolunteerDetailBean());
        // 驗證使用者資料
        if (vDBean.getUserid() == null || !vDBean.getUserid().equals(userid)) {
            vDBean.setUserid(userid);
            vDBean.setCreatedAt(DatetimeConverter.getCurrentDate());
        }
        // 轉換資料
        if (update.equals("introductions")) {
            String introduction = job.isNull("introduction") ? null : job.getString("introduction");
            String background = job.isNull("background") ? null : job.getString("background");
            String language = job.isNull("language") ? null : job.getString("language");
            String hobby = job.isNull("hobby") ? null : job.getString("hobby");
            // 輸入使用者所要更新的資料
            vDBean.setIntroduction(introduction);
            vDBean.setBackground(background);
            vDBean.setLanguage(language);
            vDBean.setHobby(hobby);
        } else if (update.equals("details")) {
            String name = job.isNull("name") ? null : job.getString("name");
            String gender = job.isNull("gender") ? null : job.getString("gender");
            String phone = job.isNull("phone") ? null : job.getString("phone");
            String email = job.isNull("email") ? null : job.getString("email");
            String birth = job.isNull("birth") ? null : job.getString("birth");
            String country = job.isNull("country") ? null : job.getString("country");
            // 輸入使用者所要更新的資料
            vDBean.setName(name);
            vDBean.setGender(gender);
            vDBean.setPhone(phone);
            vDBean.setEmail(email);
            vDBean.setBirth(DatetimeConverter.parse(birth, "yyyy-MM-dd"));
            vDBean.setCountry(country);
        } else {
            return null;
        }

        // 更新使用者資料
        VolunteerDetailBean newBean = volunteerDetailRepository.save(vDBean);
        return newBean;
    }

    public boolean exists(String id) {
        return volunteerDetailRepository.existsById(id);
    }

    public void sendPasswordResetEmail(VolunteerDetailBean volunteerDetail) {
        // Gmail SMTP configuration
        String host = "smtp.gmail.com";
        String port = "587";
        String senderUsername = "sheridan0021@gmail.com";
        String senderPassword = "wkhz yolo yuqk bhxl";

        // Email content
        String recipient = volunteerDetail.getEmail();
        String subject = "Password Reset Instructions";
        String body = "Dear " + volunteerDetail.getName() + ",\n\n"
                + "Please follow the link below to reset your password:\n"
                + "http://localhost:7890/resetpassword?email=" + volunteerDetail.getEmail() + "\n\n"
                + "If you did not request a password reset, please ignore this email.\n\n"
                + "Best regards,\n"
                + "Your Website Team";

        // Email properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Create a Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderUsername, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set From and To addresses
            message.setFrom(new InternetAddress(senderUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            // Set email subject and body
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            System.out.println("Password reset email sent successfully to " + recipient);
        } catch (MessagingException e) {
            System.out.println("Error sending password reset email: " + e.getMessage());
        }
    }

    public VolunteerDetailBean findByEmail(String email) {
        // Find VolunteerDetail by email
        return volunteerDetailRepository.findByEmail(email);
    }

    public VolunteerBean findVolunteerByEmail(String email) {
        VolunteerDetailBean volunteerDetail = volunteerDetailRepository.findByEmail(email);
        if (volunteerDetail != null) {
            String userId = volunteerDetail.getUserid();
            return volunteerRepository.findByUserid(userId);
        }
        return null;
    }
}
