package com.dfn.alerts.dataaccess.dao;

import com.dfn.alerts.beans.LastLoginDetails;
import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.user.*;
import com.dfn.alerts.dataaccess.orm.impl.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DAO layer to work with customer related database transactions.
 * This interface provide , create,delete update user details etc..
 * Hibernate implementation of this interface {@link com.dfn.alerts.dataaccess.dao.impl.UserDetailsDAOImpl}
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 7/18/13
 * Time: 10:16 AM
 * To change this template use File | Settings | File Templates.
 */
public interface UserDetailsDAO extends CommonDAO {

    /**
     * Check if given username already exist with the system or not
     *
     * @param username username
     * @return true if already exist
     */
    boolean isUserNameExists(final String username);


    /**
     * Check if given email already exist with the system or not
     *
     * @param email email
     * @return true if already exist
     */
    boolean isEmailExists(final String email);


    /**
     * Create user
     *
     * @param userDTO Hibernate user mapping object
     * @return status {@link  com.dfn.alerts.constants.UserDetailsConstants.UerDetails}
     */
    int createUser(UserDetails userDTO);    //TODO introduce new object type. Do not pass Hibernate mapping object to this class


    //Yet to implement

    boolean deleteUser(final String username);


    boolean updateUser(UserDetails userDTO);

    boolean archiveUser(ArchiveUserDetails archiveUser);


    /**
     * Load user by give username
     *
     * @param username username
     * @return user details object
     */
    CustomUserDetails loadUserByUsername(final String username);

    /**
     * get user BO for given
     *
     * @param username username
     * @return UserDetails
     */

    UserDetails getUser(String username);    //TODO remove this method and abstract BO form data access

    UserDetails getUserByEmail(String email);

    /**
     * Load user by give email
     *
     * @param email email
     * @return user details object
     */
    CustomUserDetails loadUserByEmail(final String email);

    /**
     * Load user by user id
     *
     * @param userID userID
     * @return user details object
     */
    CustomUserDetails loadUserByUserId(final long userID);

    /**
     * load users map my user ids
     * @param value Set<Long>
     * @return Map<Long,CustomUserDetails>
     */
    public Map<Long,CustomUserDetails>  loadUsersByUserIdMap(Set<Long> value);


    /**
     * Create premium account
     *
     * @param userPremiumAccount premium account
     * @return status
     */
    public int createPremiumAccount(UserPremiumAccount userPremiumAccount);


    /**
     * update premium account
     *
     * @param userPremiumAccount user Premium Account
     * @return status
     */
    public boolean updatePremiumAccount(UserPremiumAccount userPremiumAccount);

    /**
     * insert premium user to archive table
     * @param archiveUserPremiumAccount archive user details
     * @return status
     */
    public boolean archivePremiumAccount(ArchiveUserPremiumAccount archiveUserPremiumAccount);

    /**
     * Load premium account details
     *
     * @param userId userId
     * @return {@link PremiumAccountDetails}
     */
    PremiumAccountDetails loadPremiumAccount(final long userId);


    /**
     * Load user preferences by given user ID
     *
     * @param userId user ID
     * @return UserPreferenceDetails object
     */
    UserPreferenceDetails loadUserPreference(final long userId);


    /**
     * Change password
     *
     * @param username    username
     * @param newPassword new password
     * @return true if success
     */
    boolean changePassword(final String username, final String newPassword);

    /**
     * Update last login details
     *
     * @param username         username
     * @param lastLoginDetails last login details object
     * @return status
     */
    int updateLastLoginDetails(final String username, final LastLoginDetails lastLoginDetails);


    /**
     * Update no of failed attempts if user tru with wrong password
     *
     * @param username username
     * @param isReset  password
     * @return true if transaction/db update success
     */
    boolean updateFailedAttempts(final String username, boolean isReset);

    /**
     * create user activation status record
     *
     * @param userActivationData DA object
     * @return status
     */

    int createUserAccActivationStatus(final UserActivationData userActivationData);


    /**
     * updating user activation status
     *
     * @param userActivationData UserActivationData
     * @return status
     */

    int updateUserAccActivationStatus(final UserActivationData userActivationData);

    /**
     * Update active session details
     *
     *
     * @param sessionUser         user id
     * @return status
     */
    int updateActiveSessionDetails(final SessionUser sessionUser, int productId);

    /**
     * Remove session details entry on session expiry or logout
     *
     * @param userId user ID user id
     * @return status success status
     */
    public int removeSessionDetails(final String userId);

    /**
     * Remove all persisted session details
     *
     * @return status success status
     */
    public int removeAllSessionData(final int productId);

    /**
     * validating user email confirmation
     *
     * @param userId  user id
     * @param actCode activation code
     * @return status
     */
    int validateEmailConfirmation(int userId, String actCode);

    boolean doLogout(final String username);

    int updateUserPreference(UserPreference userPreferenceDTO);

    List<UserPremiumAccount> getAllPremiumUsers();

    List<UserDetails> getAccountExpireNotificationRequiredUsers(int numberOfDays);

    List<UserDetails> getAccountExpireNotificationRequiredUsers(Map<Integer, String> accountTypeRelExpDays);

    List<UserPremiumAccount> searchPremiumUsers(Map<String, String> searchParameters);

    Map<Long , MiniUserDetails> searchUsers(Map<String, String> searchParameters, int numberOfRecords);

    int updateUserPreferences(List<Long> userIDs, Map<String, String> updateParameters);

    List<Object> getAllActiveUserSessions();

    List<UserSessionDetailsDTO> getUserSessions(long userId, int productId);

    /**
     * Getting Non expired demo accounts
     * @return List of PremiumAccountDetails
     */
    List<CustomUserDetails> getNonExpiredDemoUsers();

    int updateLastLogoutTime(Long userId, String sessionId, Date lastLogoutDate, int productId);

    @Deprecated
    int insertEmailNotification(EmailNotification emailNotification);

    int updateEmailNotification(int emailId, int status);

    List<FailedEmailNotificationDTO> getFailedEmailNotifications(int retryCount);

    /**
     * Get failed email notification list
     * @return  List<FailedEmailNotificationDTO>
     */
    List<FailedEmailNotificationDTO> getAllFailedEmailNotifications();

    boolean archiveEmailNotifications();

    /**
     * get email summary data
     * @param requestDBDTO query embeded here
     * @return map with string, map
     */
    Map<String,Map<String,String>>  getEmailNotificationSummary(RequestDBDTO requestDBDTO);

    List<Map<String,Object>>  getUserSessionDetails(RequestDBDTO requestDBDTO);

    int saveAuthResponse(AuthenticationAPIResponse authenticationAPIResponse);

    /**
     * save feedback case to DB
     * @param contactCase
     * @return
     */
    int saveFeedbackCase(ContactCase contactCase);
}
