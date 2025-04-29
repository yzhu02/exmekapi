package com.exmek.core.email;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.exmek.commons.net.ContentType;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailSenderService {

	@Autowired
    private JavaMailSender mailSender;

    public void sendMail(String[] to, String[] cc, String[] bcc, String subject, String content, ContentType contentType) throws MessagingException {
//        SimpleMailMessage message = new SimpleMailMessage();
        
    	MimeMessage message = createMimeMessage(to, cc, bcc, subject, content, contentType);
    	log.info("Sending email [{}] to {}", subject, cc);
		mailSender.send(message);
        
    }

	private MimeMessage createMimeMessage(String[] to, String[] cc, String[] bcc, String subject, String content, ContentType contentType) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
//		message.setFrom(new InternetAddress(from));
		message.setSubject(subject);
		message.setRecipients(RecipientType.TO, this.createAddrs(to));
		if (!ObjectUtils.isEmpty(cc)) {
			message.setRecipients(RecipientType.CC, this.createAddrs(cc));
		}
		if (!ObjectUtils.isEmpty(bcc)) {
			message.setRecipients(RecipientType.BCC, this.createAddrs(bcc));
		}
		if (contentType == ContentType.TEXT_HTML) {
			message.setContent(content, contentType.getCanonicalName());
		} else {
			message.setText(content);
		}
		message.setSentDate(new Date());
		return message;
	}

	private InternetAddress[] createAddrs(String[] emailAddrs) throws AddressException {
		InternetAddress[] addresses = new InternetAddress[emailAddrs.length];
		for (int i = 0; i < emailAddrs.length; ++i) {
			addresses[i] = new InternetAddress(emailAddrs[i]);
		}
		return addresses;
	}

}