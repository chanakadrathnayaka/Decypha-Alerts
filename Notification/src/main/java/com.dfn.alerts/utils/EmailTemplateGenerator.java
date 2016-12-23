package com.dfn.alerts.utils;


import com.dfn.alerts.beans.NotificationData;
import com.dfn.alerts.beans.financial.FinancialDataObject;
import com.dfn.alerts.constants.IConstants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 9/30/13
 * Time: 1:15 PM
 */
public class EmailTemplateGenerator {

    private Configuration configuration;

    private static final String ACCOUNT_ACTIVATION_PATH = "/usermgr/account/activation";

    private static final String PRODUCT_NAME = "Decypha";

    private static final String NOTIFY_CUSTOMER = "true";

    private static final String FROM = " :: From :: ";

    private String resetPasswordEmailTemplate;

    private String premiumWelcomeEmailTemplate;

    private String trialWelcomeEmailTemplate;

    private String premiumAccountExpEmailTemplate;

    private String trialAccountExpEmailTemplate;

    private String premiumAccountExpDateFormatter;

    private String trialAccountExpDateFormatter;

    private String fromEmailSupport;

    private String fromResetEmailSupport;

    private String fromNameSupport;

    public static final String NO_REPLY_EMAIL = "no-reply@decypha.com";

    private String demoAccountUsageReportTemplate;

    private String demoUserUsageSummaryReportTemplate;

    private String demoUserUsageSummaryReportAttachment;

    private String demoReportAttachmentPath;

    private String promoTrialUserExistTemplate;

    private String promoPremiumUserExistTemplate;

    private String promoWelcomeTemplate;

    private String eodJobTemplate;

    private String financialAnalystNotificationTemplate;

    private String serverUrl;

    private String releaseNoteEmailTemplate;

    private String designationUpdatesEmailTemplate;

    private String corporateActionEmailTemplate;

    private static final Logger LOG = LogManager.getLogger(EmailTemplateGenerator.class);

    private String exportDataEmailTemplate;

    private static final String LOG_PREFIX = " :: EmailTemplateGenerator :: ";

    private String requestTrialEmailTemplate;

    private String feedbackEmailTemplate;

    private String feedbackCaseEmailTemplate;

    private String feedbackCaseExternalEmailTemplate;

    private static final String DECYPHA_WELCOME_EMAIL_TITLE_PREMIUM = " Decypha Account Credentials";

    private static final String DECYPHA_WELCOME_EMAIL_TITLE_TRIAL = " Decypha Trial";

    private String solrHealthCheckJobTemplate;



    public String getEmailContentForAnalystNotification(String individualName, String companyName, String period,
                                                        String utmSource, String utmMedium, String utmCampaign,
                                                        Map<String, FinancialDataObject> companyFinancialData,
                                                        String financialYearEnd, Map<String, String[]> periods,
                                                        int periodLength, String currency, String language) {

        Map<String, Object> model = new HashMap<String, Object>();
        Map<String, String> financialTypeDescriptionMap = IConstants.FinancialTypeDescription.getFinancialTypeDescriptionMap();
        model.put("individualName", individualName);
        model.put("companyName", companyName);
        model.put("period", period);
        model.put("periods", periods);
        model.put("utmSource", utmSource);
        model.put("utmMedium", utmMedium);
        model.put("utmCampaign", utmCampaign);
        model.put("companyFinancialData", companyFinancialData);
        model.put("financialYearEnd", financialYearEnd);
        model.put("currency", currency);
        model.put("periodLength", periodLength);
        model.put("language", language);
        model.put("types", new String[]{IConstants.FinancialTypes.IS.toString(), IConstants.FinancialTypes.BS.toString(), IConstants.FinancialTypes.CF.toString()});
        model.put("description", financialTypeDescriptionMap);

        //Email body
        String emailText = null;
        try {
            emailText = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(financialAnalystNotificationTemplate), model);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (TemplateException e) {
            LOG.error(e.getMessage(), e);
        }
        return emailText;
    }

    public String getEmailContentForRequestTrial(Map<String, String[]> trialData) {
        String emailContent = null;
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("title", trialData.get("Designation")[0]);
        model.put("firstName", trialData.get("First Name")[0]);
        model.put("lastName", trialData.get("Last Name")[0]);
        model.put("email", trialData.get("Email")[0]);
        model.put("phone", trialData.get("Phone")[0]);
        model.put("country", trialData.get("Country")[0].split("\\|")[0]);
        model.put("company", trialData.get("Company")[0]);
        model.put("jobTitle", trialData.get("Industry")[0]);
        model.put("comment", trialData.get("Description")[0]);

        try {
            emailContent = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(requestTrialEmailTemplate), model);
        } catch (IOException e) {
            LOG.error(LOG_PREFIX, e);
        } catch (TemplateException e) {
            LOG.error(LOG_PREFIX, e);
        }
        return emailContent;
    }


    /**
     * There are lot of EOD process to syn central db data to Decypha db
     * Download images ( not included)
     * Sync in-memory database etc ( not included)
     * <p/>
     * So we are sending status email to dev team.
     * This is basically contains Central db to our own db DB sync jos details
     *
     * @param logDataList Job status  Data list
     * @param url         server url (production, uat etc)
     * @param toMails     To mail list
     * @param ccMails     CC mail list
     * @param bccMails    BCC mail list
     * @param subject     Email title
     * @return Email object
     */
    public NotificationData getEodLogStatusEmailContent(Map<String, List<Map<String, Object>>> logDataList,
                                                        String url,
                                                        String toMails,
                                                        String ccMails,
                                                        String bccMails,
                                                        String subject) {

        NotificationData notificationData = null;
        try {

            Map<String, String> notifyRequest = new HashMap<String, String>();
            notifyRequest.put(IConstants.CustomDataField.EMAIL_TITLE, subject + " :: From :: " + url);
            notifyRequest.put(IConstants.CustomDataField.EMAIL_FROM, NO_REPLY_EMAIL);
            notifyRequest.put(IConstants.CustomDataField.EMAIL_RECEPIENT_LIST, toMails);
            notifyRequest.put(IConstants.CustomDataField.EMAIL_CC_LIST, ccMails);
            notifyRequest.put(IConstants.CustomDataField.EMAIL_BCC_LIST, bccMails);

            notificationData = new NotificationData();

            Map<String, Object> model = new HashMap<String, Object>();
            Set keys = null;

            if (logDataList != null) {

                model.put("data", logDataList.get("SP"));
                model.put("job", logDataList.get("JOB_STATUS"));
                model.put("tz", logDataList.get("TZ"));

                if (logDataList.get("SP") != null && !logDataList.isEmpty() && logDataList.get("SP").get(0) != null && logDataList.get("SP").get(0).size() > 0) {
                    keys = logDataList.get("SP").get(0).keySet();
                }
            }

            model.put("dataKeys", keys);
            model.put("url", url);
            Template template = configuration.getTemplate(this.eodJobTemplate);
            String emailText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            //email content
            notifyRequest.put(IConstants.CustomDataField.EMAIL_CONTENT, emailText);


            notificationData.setNotificationData(notifyRequest);

        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (TemplateException e) {
            LOG.error(e.getMessage(), e);
        }
        return notificationData;
    }

    public String getExportDataEmailContent(IConstants.EmailType emailType) {
        String content;
        try {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("url", getServerUrl());
            model.put("emailType", emailType.toString());
            content = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(this.exportDataEmailTemplate), model);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            content = "";
        } catch (TemplateException e) {
            LOG.error(e.getMessage(), e);
            content = "";
        }
        return content;
    }

    public String getCorporateActionEmailContent(String symbol, String exchange, IConstants.EmailType emailType){
        String content;
        Map<String, Object> model = new HashMap<String, Object>();

        model.put("symbol", symbol);
        model.put("exchange", exchange);
        model.put("url", getServerUrl());
        model.put("releaseNoteType", emailType.toString());
        try {
            content = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(this.corporateActionEmailTemplate), model);
        } catch (IOException e) {
            e.printStackTrace();
            content = "";
        } catch (TemplateException e) {
            e.printStackTrace();
            content = "";
        }
        return content;
    }

    /**
     * get designation updates email content
     * @param emailType
     * @return content
     */
    public String getDesignationUpdatesEmailContent(IConstants.EmailType emailType) {
        String content;
        try {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("url", getServerUrl());
            model.put("releaseNoteType", emailType.toString());
            content = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(this.designationUpdatesEmailTemplate), model);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            content = "";
        } catch (TemplateException e) {
            LOG.error(e.getMessage(), e);
            content = "";
        }
        return content;
    }



    public void setFreemarkerMailConfiguration(Configuration freemarkerMailConfiguration) {
        this.configuration = freemarkerMailConfiguration;
    }

    public void setResetPasswordEmailTemplate(String resetPasswordEmailTemplate) {
        this.resetPasswordEmailTemplate = resetPasswordEmailTemplate;
    }

    public void setFromEmailSupport(String fromEmailSupport) {
        this.fromEmailSupport = fromEmailSupport;
    }

    public void setFromResetEmailSupport(String fromResetEmailSupport) {
        this.fromResetEmailSupport = fromResetEmailSupport;
    }

    public void setFromNameSupport(String fromNameSupport) {
        this.fromNameSupport = fromNameSupport;
    }

    public void setPremiumWelcomeEmailTemplate(String premiumWelcomeEmailTemplate) {
        this.premiumWelcomeEmailTemplate = premiumWelcomeEmailTemplate;
    }

    public void setTrialWelcomeEmailTemplate(String trialWelcomeEmailTemplate) {
        this.trialWelcomeEmailTemplate = trialWelcomeEmailTemplate;
    }

    public void setPremiumAccountExpEmailTemplate(String premiumAccountExpEmailTemplate) {
        this.premiumAccountExpEmailTemplate = premiumAccountExpEmailTemplate;
    }

    public void setTrialAccountExpEmailTemplate(String trialAccountExpEmailTemplate) {
        this.trialAccountExpEmailTemplate = trialAccountExpEmailTemplate;
    }

    public void setPremiumAccountExpDateFormatter(String premiumAccountExpDateFormatter) {
        this.premiumAccountExpDateFormatter = premiumAccountExpDateFormatter;
    }

    public void setTrialAccountExpDateFormatter(String trialAccountExpDateFormatter) {
        this.trialAccountExpDateFormatter = trialAccountExpDateFormatter;
    }

    public void setDemoAccountUsageReportTemplate(String demoAccountUsageReportTemplate) {
        this.demoAccountUsageReportTemplate = demoAccountUsageReportTemplate;
    }

    public void setDemoUserUsageSummaryReportTemplate(String demoUserUsageSummaryReportTemplate) {
        this.demoUserUsageSummaryReportTemplate = demoUserUsageSummaryReportTemplate;
    }

    public void setDemoUserUsageSummaryReportAttachment(String demoUserUsageSummaryReportAttachment) {
        this.demoUserUsageSummaryReportAttachment = demoUserUsageSummaryReportAttachment;
    }

    public void setDemoReportAttachmentPath(String demoReportAttachmentPath) {
        this.demoReportAttachmentPath = demoReportAttachmentPath;
    }

    public void setPromoTrialUserExistTemplate(String promoTrialUserExistTemplate) {
        this.promoTrialUserExistTemplate = promoTrialUserExistTemplate;
    }

    public void setPromoPremiumUserExistTemplate(String promoPremiumUserExistTemplate) {
        this.promoPremiumUserExistTemplate = promoPremiumUserExistTemplate;
    }

    public void setPromoWelcomeTemplate(String promoWelcomeTemplate) {
        this.promoWelcomeTemplate = promoWelcomeTemplate;
    }

    public void setRequestTrialEmailTemplate(String requestTrialEmailTemplate) {
        this.requestTrialEmailTemplate = requestTrialEmailTemplate;
    }

    public void setFeedbackEmailTemplate(String feedbackEmailTemplate) {
        this.feedbackEmailTemplate = feedbackEmailTemplate;
    }

    public void setFeedbackCaseEmailTemplate(String feedbackCaseEmailTemplate) {
        this.feedbackCaseEmailTemplate = feedbackCaseEmailTemplate;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void setEodJobTemplate(String eodJobTemplate) {
        this.eodJobTemplate = eodJobTemplate;
    }

    public void setReleaseNoteEmailTemplate(String releaseNoteEmailTemplate) {
        this.releaseNoteEmailTemplate = releaseNoteEmailTemplate;
    }

    public void setFinancialAnalystNotificationTemplate(String financialAnalystNotificationTemplate) {
        this.financialAnalystNotificationTemplate = financialAnalystNotificationTemplate;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getDesignationUpdatesEmailTemplate() {
        return designationUpdatesEmailTemplate;
    }

    public void setDesignationUpdatesEmailTemplate(String designationUpdatesEmailTemplate) {
        this.designationUpdatesEmailTemplate = designationUpdatesEmailTemplate;
    }

    public void setCorporateActionEmailTemplate(String corporateActionEmailTemplate) {
        this.corporateActionEmailTemplate = corporateActionEmailTemplate;
    }

    public String getCorporateActionEmailTemplate() {
        return corporateActionEmailTemplate;
    }

    public void setExportDataEmailTemplate(String exportDataEmailTemplate) {
        this.exportDataEmailTemplate = exportDataEmailTemplate;
    }

    public String getExportDataEmailTemplate() {
        return exportDataEmailTemplate;
    }

    public void setFeedbackCaseExternalEmailTemplate(String feedbackCaseExternalEmailTemplate) {
        this.feedbackCaseExternalEmailTemplate = feedbackCaseExternalEmailTemplate;
    }

    public void setSolrHealthCheckJobTemplate(String solrHealthCheckJobTemplate) {
        this.solrHealthCheckJobTemplate = solrHealthCheckJobTemplate;
    }
}
