package com.dfn.alerts.dataaccess.dao.api;

import com.dfn.alerts.beans.EmailsRecipientsDTO;
import com.dfn.alerts.dataaccess.orm.impl.SecurityFilterMetadata;
import com.dfn.alerts.dataaccess.orm.impl.SecurityRoles;
import com.dfn.alerts.dataaccess.orm.impl.URLMetadata;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 9/2/13
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IAppConfigData extends CommonDAO{
    boolean insertSecurityFilterMetadata(SecurityFilterMetadata securityFilterMetadata);
    boolean insertURLMetadata(URLMetadata urlMetadata);
    boolean updateSecurityFilterMetadata(SecurityFilterMetadata securityFilterMetadata);
    boolean deleteSecurityFilterMetadata(Long securityFilterMetadataId);
    SecurityFilterMetadata getSecurityFilterMetadata(Long securityFilterMetadataId);
    List<SecurityFilterMetadata> getAllSecurityFilterMetadata();
    List<URLMetadata> getAllUrls();

    boolean insertSecurityRole(SecurityRoles securityRoles);
    boolean updateSecurityRole(SecurityRoles securityRoles);
    boolean deleteSecurityRole(Long securityRolesId);
    SecurityFilterMetadata getSecurityRole(Long securityRolesId);
    List<SecurityRoles> getAllSecurityRoles();

    /*boolean insertWidgetUsrPermission(SecurityWidgetUsrPermission widgetUsrPermission);
    boolean updateWidgetUsrPermission(SecurityWidgetUsrPermission widgetUsrPermission);
    boolean deleteWidgetUsrPermission(Long widgetUsrPermissionID);
    SecurityWidgetUsrPermission getWidgetUsrPermission(Long widgetUsrPermissionID);
    List<SecurityWidgetUsrPermission> getAllWidgetUsrPermission();*/

    List<EmailsRecipientsDTO> getAllEmailsRecipients();
    List<EmailsRecipientsDTO> getEmailsRecipients(int emailType, int receiptType);
    boolean insertEmailsRecipients(String email, int emailType, int receiptType);
    boolean updateEmailsRecipients(String email, int emailType, int receiptType, long id);


}
