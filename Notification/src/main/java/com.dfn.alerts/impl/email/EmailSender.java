package com.dfn.alerts.impl.email;

import com.dfn.alerts.api.NotificationSender;
import com.dfn.alerts.beans.NotificationData;
import com.dfn.alerts.beans.SchedulerJobResult;
import com.dfn.alerts.beans.notification.EmailDTO;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.constants.JobConstants;
import com.dfn.alerts.dao.impl.email.EmailDataAccess;
import org.apache.commons.lang3.CharUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Danuran on 4/2/2015.
 */
public class EmailSender implements NotificationSender {
    private static final String LOG_PREFIX = "EmailSender :: ";
    private static final Logger LOG = LogManager.getLogger(EmailSender.class);

    private static Properties PROPERTIES = null;
    private static final String PROPERTY_PROTOCOL = "mail.transport.protocol";
    private static final String PROPERTY_HOST = "mail.smtp.host";
    private static final String PROPERTY_PORT = "mail.smtp.port";
    private static final String PROPERTY_AUTH = "mail.smtp.auth";

    private String SMTPHost;
    private String SMTPPort;
    private String SMTPPort2;
    private String SMTPUser;
    private String SMTPPassword;
    private String charset;
    private String contentType;
    private String protocol;
    private String auth;

    private EmailDataAccess emailNotificationDataAccess;
    private static final int SMS_SEND_DELAY = 5000;
    private static final int NUMBER_OF_EMAIL_MESSAGES = 100;
    private static final String STATUS_TRUE = "1";
    private static final String STATUS_FALSE = "0";
    private static final int EMAIL_ARCHIVE_PERIOD = 30;

    public EmailSender(String SMTPHost, String SMTPPort, String SMTPPort2, String SMTPUser, String SMTPPassword, String charset, String contentType, String protocol, String auth) {
        this.SMTPHost = SMTPHost;
        this.SMTPPort = SMTPPort;
        this.SMTPPort2 = SMTPPort2;
        this.SMTPUser = SMTPUser;
        this.SMTPPassword = SMTPPassword;
        this.charset = charset;
        this.contentType = contentType;
        this.protocol = protocol;
        this.auth = auth;
    }

    private Properties getConfigProps() {
        if (PROPERTIES == null) {
            synchronized (this) {
                PROPERTIES = new Properties();
                PROPERTIES.put(PROPERTY_PROTOCOL, protocol);
                PROPERTIES.put(PROPERTY_HOST, SMTPHost);
                PROPERTIES.put(PROPERTY_PORT, SMTPPort);
                PROPERTIES.put(PROPERTY_AUTH, auth);
            }
        }

        return PROPERTIES;
    }

    //scheduler job to send emails
    @Override
    public SchedulerJobResult sendNotification() {
        SchedulerJobResult schedulerJobResult = new SchedulerJobResult();
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(LOG_PREFIX + "sendNotification()");
            }
            getEmailFromDB();
            schedulerJobResult.setJobStatus(JobConstants.JOB_STATUS_COMPLETED);
        } catch (Exception e) {
            schedulerJobResult.setJobStatus(JobConstants.JOB_STATUS_COMPLETED_WITH_ERRORS);
        }
        return schedulerJobResult;
    }

    //load pending emails with retry count less than 10
    public void getEmailFromDB() {
        int status = 0;
        int retryCount = 10;
        int emailCount = 0;
        List<EmailDTO> emailDTOList = emailNotificationDataAccess.getPendingEmails(status, retryCount);

        if (emailDTOList != null && !emailDTOList.isEmpty()) {
            for (EmailDTO emailDTO : emailDTOList) {
                sendEmailToUser(emailDTO);
                emailCount++;

                if (emailCount == NUMBER_OF_EMAIL_MESSAGES) {
                    emailCount = 0;
                    try {
                        Thread.sleep(SMS_SEND_DELAY);
                    } catch (InterruptedException e) {
                        LOG.error(LOG_PREFIX + "exception in thread sleep()", e);
                    }
                }
            }
        } else {
            LOG.debug(LOG_PREFIX + "no emails to send");
        }
    }

    //create notification object with Email Data
    private void sendEmailToUser(EmailDTO emailDTO) {
        NotificationData notificationData = new NotificationData();
        Map<String, String> notifyRequest = new HashMap<String, String>();
        notifyRequest.put(IConstants.CustomDataField.EMAIL_TITLE, emailDTO.getSubject());
        notifyRequest.put(IConstants.CustomDataField.EMAIL_RECEPIENT_LIST, emailDTO.getTo());
        notifyRequest.put(IConstants.CustomDataField.EMAIL_CC_LIST, emailDTO.getCc());
        notifyRequest.put(IConstants.CustomDataField.EMAIL_BCC_LIST, emailDTO.getBcc());
        notifyRequest.put(IConstants.CustomDataField.EMAIL_FROM, emailDTO.getFrom());
        notifyRequest.put(IConstants.CustomDataField.EMAIL_FROM_NAME, emailDTO.getFromName());
        if (emailDTO.isAttachment()) {
            notifyRequest.put(IConstants.CustomDataField.EMAIL_ATTACHMENT_PATH, emailDTO.getAttachmentLocation());
            notifyRequest.put(IConstants.CustomDataField.EMAIL_ATTACHMENT_NAME, emailDTO.getAttachmentName());
            notifyRequest.put(IConstants.CustomDataField.EMAIL_ATTACHMENT, STATUS_TRUE);
        } else {
            notifyRequest.put(IConstants.CustomDataField.EMAIL_ATTACHMENT, STATUS_FALSE);
        }
        notifyRequest.put(IConstants.CustomDataField.EMAIL_CONTENT, emailDTO.getBody());
        notificationData.setNotificationData(notifyRequest);

        //send emails
        int status = sendNotification(notificationData);
        emailNotificationDataAccess.update(emailDTO.getId(), status);
    }

    public void setEmailNotificationDataAccess(EmailDataAccess emailNotificationDataAccess) {
        this.emailNotificationDataAccess = emailNotificationDataAccess;
    }

    public int sendNotification(Object notificationData) {
        return sendMail(notificationData, getConfigProps(), true);
    }

    @Override
    public void archiveNotification() {
        this.emailNotificationDataAccess.archiveEmails(EMAIL_ARCHIVE_PERIOD);
    }

    private int sendMail(Object notificationData, Properties configProps, boolean sendUsingSecondary) {

        Map<String, String> msgData = ((NotificationData) notificationData).getNotificationData();

        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getInstance(configProps, auth);
        String attachmentContent;
        String attachmentPath;
        String attachmentName;
        Multipart multipart;

        try {
            Transport transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);

            message.setFrom(new InternetAddress(msgData.get(IConstants.CustomDataField.EMAIL_FROM), msgData.get(IConstants.CustomDataField.EMAIL_FROM_NAME)));
            message.setSubject(msgData.get(IConstants.CustomDataField.EMAIL_TITLE), charset);
            message.setContent(msgData.get(IConstants.CustomDataField.EMAIL_CONTENT), contentType);

            String[] toList = msgData.get(IConstants.CustomDataField.EMAIL_RECEPIENT_LIST).split(CharUtils.toString(IConstants.Delimiter.COMMA));

            for (String recps : toList) {
                if (!recps.isEmpty()) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(recps));
                }
            }

            if (msgData.get(IConstants.CustomDataField.EMAIL_CC_LIST) != null) {
                String[] ccList = msgData.get(IConstants.CustomDataField.EMAIL_CC_LIST).split(CharUtils.toString(IConstants.Delimiter.COMMA));
                for (String cc : ccList) {
                    if (!cc.isEmpty()) {
                        message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
                    }
                }
            }

            if (msgData.get(IConstants.CustomDataField.EMAIL_BCC_LIST) != null) {
                String[] ccList = msgData.get(IConstants.CustomDataField.EMAIL_BCC_LIST).split(CharUtils.toString(IConstants.Delimiter.COMMA));
                for (String cc : ccList) {
                    if (!cc.isEmpty()) {
                        message.addRecipient(Message.RecipientType.BCC, new InternetAddress(cc));
                    }
                }
            }

            attachmentContent = msgData.get(IConstants.CustomDataField.EMAIL_ATTACHMENT);
            attachmentPath = msgData.get(IConstants.CustomDataField.EMAIL_ATTACHMENT_PATH);
            attachmentName = msgData.get(IConstants.CustomDataField.EMAIL_ATTACHMENT_NAME);

            if (attachmentContent != null && attachmentPath != null) {
                multipart = new MimeMultipart();
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(msgData.get(IConstants.CustomDataField.EMAIL_CONTENT), contentType);
                multipart.addBodyPart(messageBodyPart);

                try {
                    messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(attachmentPath)));
                    messageBodyPart.setFileName(attachmentName);
                    multipart.addBodyPart(messageBodyPart);
                } catch (Exception ex) {
                    LOG.error(LOG_PREFIX + ex.getMessage(), ex.getCause());
                }

                // Put parts in message
                message.setContent(multipart);
            }


            if (LOG.isDebugEnabled()) {
                LOG.debug(LOG_PREFIX + "<!--SMTP Email Service--> Opening Connection to send email via sendgrid Email Service || Host : " + SMTPHost + "Port : " + configProps.get(PROPERTY_PORT));
            }
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            if (LOG.isDebugEnabled()) {
                LOG.debug(LOG_PREFIX + "<!--SMTP Email Service--> Sending mail via sendgrid Email Service || Host : " + SMTPHost + "Port : " + configProps.get(PROPERTY_PORT) + " || SUCCESS");
            }
            return NOTIFICATION_SEND_SUCCESS;
        } catch (MessagingException me) {
            LOG.error(LOG_PREFIX + "<!--SMTP Email Service--> Sending mail via sendgrid Email Service || Host : " + SMTPHost + "Port : " + configProps.get(PROPERTY_PORT) + "|| FAILED");
            LOG.error(me.getMessage(), me);
            //try sending via secondary if primary fails
            if (sendUsingSecondary) {
                return sendViaSecondary(notificationData, configProps);
            }
        } catch (Exception me) {
            LOG.error(LOG_PREFIX + me.getMessage(), me);
        }
        return NOTIFICATION_SEND_ERROR;
    }

    //Send via secondary port if unable to send via primary port

    private int sendViaSecondary(Object notificationData, Properties configProps) {
        configProps.put(PROPERTY_PORT, SMTPPort2);
        //setting sendViaSecondary to false.
        return sendMail(notificationData, configProps, false);
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTPUser;
            String password = SMTPPassword;
            return new PasswordAuthentication(username, password);
        }
    }
}
