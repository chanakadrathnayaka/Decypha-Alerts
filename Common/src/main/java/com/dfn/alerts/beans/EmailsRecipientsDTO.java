package com.dfn.alerts.beans;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 6/5/14
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Bean class to transfer email recipient objects to upper layers
 */
public class EmailsRecipientsDTO {

    /**
     * Email address
     */
    private final String emailAddress;

    /**
     * Email type
     */
    private final String emailType;

    /**
     * which recipientType type
     */
    private final int recipientType;

    public EmailsRecipientsDTO(String emailAddress, String emailType , int recipientType){
          this.emailAddress      = emailAddress;
          this.emailType         = emailType;
          this.recipientType     = recipientType;
    }

    public String getEmailAddress() {
        return emailAddress;
    }


    public String getEmailType() {
        return emailType;
    }


    public int getRecipientType() {
        return recipientType;
    }
}
