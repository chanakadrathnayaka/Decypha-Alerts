package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.beans.LastLoginDetails;
import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.user.*;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.constants.UserDetailsConstants;
import com.dfn.alerts.constants.UserManagementConstants;
import com.dfn.alerts.dataaccess.orm.impl.*;
import com.dfn.alerts.dataaccess.dao.UserDetailsDAO;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import com.dfn.alerts.utils.FormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 7/18/13
 * Time: 11:25 AM
 */
@SuppressWarnings("unchecked, unused")
public class UserDetailsDAOImpl implements UserDetailsDAO {

    private static final Logger LOG = LogManager.getLogger(UserDetailsDAOImpl.class);

    private static final String USER_DETAILS_LOG_PRE_FIX   = " :: User Details :: ";

    public static final String USER_ACTIVATION_PRE_FIX     = ":: User Activation Status : : ";

    private static final String QUERY_GET_USER_SESSIONS    = "from UserSessionDetails where userID = :userID and productId = :productId " +
            "and lastLoginDate > to_date(sysdate() - 1) order by lastLoginDate desc";

    private static final String QUERY_GET_ACTIVE_SESSIONS  = "from UserSessionDetails where loginStatus = 1";
    /**
     * Success status constants
     */
    public static final int TRANS_PENDING = 0;
    public static final int TRANS_SUCCESS = 1;
    public static final int TRANS_FAILED = -1;

    private SessionFactory sessionFactory = null;

    /**
     * DFN plus supported authorities
     */
    private static Map<Long, GrantedAuthority> grantedAuthorityMap = null;

    /**
     * DFN plus supported roles
     */
    private static Map<Long, SecurityRoles> stringSecurityRolesMap = null;


    private static final String COLUMN_VALUE_EXISTS_SQL = "from UserDetails where {0}=:a";

    private static final String UPDATE_USER_STATUS = "update UserDetails set accountStatus = :accountStatus where userID = :userID";

    private static final String DELETE_USER_SESSIONS = "delete from UserSessionDetails where productId=:productId and loginStatus=0";

    private static final String UPDATE_SYS_LOGOUT_USER_SESSIONS = "update UserSessionDetails set systemLogout = 1," +
            " loginStatus = 0, lastLogoutDate = :lastLogoutDate  where loginStatus = 1 and productId = :productId";

    private static final String UPDATE_USER_PRIOR_SESSIONS = "update UserSessionDetails set systemLogout = 1," +
            " loginStatus = 0, lastLogoutDate = :lastLogoutDate  where loginStatus = 1 and productId = :productId and userID = :userID";

    private static final String UPDATE_USER_SESSIONS = "update UserSessionDetails set " +
            "lastLogoutDate = :lastLogoutDate, loginStatus = :loginStatus  where webSessionId = :webSessionId and " +
            "userID = :userID and productId = :productId ";

    private static final String COLUMN_USERNAME = "username";

    private static final String COLUMN_EMAIL = "email";

    public static final String COLUMN_USER_ID = "userID";

    public static final int STATUS_ACTIVE = 1;

    private static final int LOAD_USER_BY_EMAIL = 0;

    private static final int LOAD_USER_BY_USERNAME = 1;

    private static final int LOAD_USER_BY_USER_ID = 2;

    private static Map<String, Boolean> SERVICES = null;

    public static final String COLUMN_ID = "id";


    private boolean approvalRequired = true;

    private static final String LOG_PREFIX = " :: UserDetailsDAOImpl :: ";

    /**
     * Create user
     *
     * @param user user details object
     * @return status
     */
    public int createUser(UserDetails user) {
        int returnVal = UserDetailsConstants.UerDetails.STATUS_FAILED;


        if (isUserNameExists(user.getUsername())) {
            returnVal = UserDetailsConstants.UerDetails.STATUS_USER_EXIST;
            LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Create user ");
        } else if (isEmailExists(user.getEmail())) {
            returnVal = UserDetailsConstants.UerDetails.STATUS_EMAIL_EXIST;
            LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Create user ");
        } else {
            Session session = null;
            Transaction transaction = null;
            try {
                session = this.sessionFactory.openSession();
                returnVal = UserDetailsConstants.UerDetails.STATUS_FAILED;
                LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Create user ");
                transaction = session.beginTransaction();
                session.save(user);
                session.save(getDefaultUserPreference(user.getUserID()));
                session.save(getCustomerPublicInfo(1, user.getUserID()));
                transaction.commit();
                returnVal = UserDetailsConstants.UerDetails.STATUS_SUCCESS;
            } catch (Exception e) {
                LOG.error(USER_DETAILS_LOG_PRE_FIX + "  Create plus user  ", e);
                if (transaction != null) {
                    transaction.rollback();
                }
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
        return returnVal;

    }

    private UserPreference getDefaultUserPreference(long userId) {
        UserPreference userPreference = new UserPreference();
        userPreference.setUserID(userId);
        userPreference.setDefaultTimeZone("");
        userPreference.setDefaultCurrency("");
        userPreference.setDefaultMarket("");
        userPreference.setDefaultCompany("");
        userPreference.setDefaultCountry("");
        userPreference.setDefaultRegion("");
        userPreference.setEditionControlType("");
        userPreference.setPreferredNewsReadingLanguages("");
        userPreference.setPreferredNewsHeadLanguages("");
        userPreference.setSaveNewsStreamingSettings(false);
        userPreference.setPreferredFilesLanguage("");
        userPreference.setMarketsInTimeLine("");
        userPreference.setMarketsInActivePanel("");
        userPreference.setCommodities("");
        userPreference.setCurrencies("");
        userPreference.setScreenerSettings("");
        userPreference.setWatchListSettings("");
        userPreference.setNewsStreamSettings("");
        userPreference.setShowReleaseNotes(1);
        userPreference.setEmailReleaseNotes(1);
        userPreference.setShowLatestReleaseNote(1);
        userPreference.setIsEmailSent(0);
        userPreference.setStockChartType("R");
        return userPreference;
    }

    private CustomerPublicInfo getCustomerPublicInfo(int type, long id) {
        CustomerPublicInfo customerPublicInfo = new CustomerPublicInfo();
        customerPublicInfo.setType(type);
        customerPublicInfo.setId(id);
        return customerPublicInfo;
    }

    /**
     * Load user by give username
     *
     * @param username username
     * @return Custom user details object  {@link CustomUserDetails}
     */

    public CustomUserDetails loadUserByUsername(String username) {
        CustomUserDetails userDetails = null;
        UserDetails user = loadUser(username, LOAD_USER_BY_USERNAME);

        if (user != null) {
            userDetails = convertUser(user);
            UserPreferenceDetails userPreferenceDetails = loadUserPreference(userDetails.getUserId());
            userDetails.setUserPreferenceDetails(userPreferenceDetails);
        }

        return userDetails;
    }


    public UserDetails getUser(String username) {
        return loadUser(username, LOAD_USER_BY_USERNAME);
    }

    public UserDetails getUserByEmail(String email){
        return loadUser(email, LOAD_USER_BY_EMAIL);
    }

    @Override
    public Map<Long, MiniUserDetails> searchUsers(Map<String, String> searchParameters, int numberOfRecords) {
        return null;
    }

    @Override
    public int updateUserPreferences(List<Long> userIDs, Map<String, String> updateParameters) {
        return 0;
    }

    /**
     * Load user by give email
     *
     * @param email email
     * @return Custom user details object  {@link CustomUserDetails}
     */

    public CustomUserDetails loadUserByEmail(String email) {
        CustomUserDetails userDetails = null;
        UserDetails user = loadUser(email, LOAD_USER_BY_EMAIL);

        if (user != null) {
            userDetails = convertUser(user);
            UserPreferenceDetails userPreferenceDetails = loadUserPreference(userDetails.getUserId());
            userDetails.setUserPreferenceDetails(userPreferenceDetails);
        }

        return userDetails;
    }

    /**
     * Load user by given userId
     *
     * @param userID userID
     * @return Custom user details object  {@link CustomUserDetails}
     */

    public CustomUserDetails loadUserByUserId(long userID) {
        CustomUserDetails userDetails = null;
        UserDetails user = loadUser(Long.toString(userID), LOAD_USER_BY_USER_ID);

        if (user != null) {
            userDetails = convertUser(user);
            UserPreferenceDetails userPreferenceDetails = loadUserPreference(userDetails.getUserId());
            userDetails.setUserPreferenceDetails(userPreferenceDetails);
        }

        return userDetails;
    }

    public int createPremiumAccount(UserPremiumAccount userPremiumAccount) {
        int returnVal = UserDetailsConstants.UerDetails.STATUS_FAILED;

        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            returnVal = UserDetailsConstants.UerDetails.STATUS_FAILED;
            LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Create Premium Account ");
            transaction = session.beginTransaction();
            session.saveOrUpdate(userPremiumAccount);
            transaction.commit();
            returnVal = UserDetailsConstants.UerDetails.STATUS_SUCCESS;
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "  Create Premium Account  ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;

    }


    public boolean updatePremiumAccount(UserPremiumAccount userPremiumAccount) {
        boolean returnVal = false;
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(userPremiumAccount);
            transaction.commit();
            returnVal = true;
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "  update premium account   ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;

    }


    /**
     * Load premium account details
     *
     * @param userID user id
     * @return PremiumAccountDetails object
     */
    public PremiumAccountDetails loadPremiumAccount(long userID) {
        PremiumAccountDetails premiumAccountDetails = null;
        UserPremiumAccount userPremiumAccount;
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Loading user  ");
        Session session = null;

        UserDetails userDetails = new UserDetails();
        userDetails.setUserID(userID);
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(UserPremiumAccount.class);
            criteria.add(Restrictions.eq("userDetails", userDetails));
            Object o = criteria.uniqueResult();
            if (o != null) {
                userPremiumAccount = (UserPremiumAccount) o;
                premiumAccountDetails = covertPreUser(userPremiumAccount);
            }
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "Loading user   ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return premiumAccountDetails;
    }

    /**
     * archive premium user before update
     * @param archiveUserPremiumAccount archive user details
     * @return  status
     */
    public boolean archivePremiumAccount(ArchiveUserPremiumAccount archiveUserPremiumAccount) {
        boolean returnVal = false;
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(archiveUserPremiumAccount);
            transaction.commit();
            returnVal = true;
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "  insert archive premium account   ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    /**
     * Load user settings
     *
     * @param userID user ID
     * @return UserPreferenceDetails Object
     */
    public UserPreferenceDetails loadUserPreference(long userID) {
        UserPreferenceDetails userPreferenceDetails = null;
        UserPreference userPreference;
        LOG.debug(" load User Preference");
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(UserPreference.class);
            criteria.add(Restrictions.eq(COLUMN_USER_ID, userID));
            Object o = criteria.uniqueResult();
            if (o != null) {
                userPreference = (UserPreference) o;
                userPreferenceDetails = convertUserPreferences(userPreference);
            }
        } catch (Exception e) {
            LOG.error("  load User Preference  ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userPreferenceDetails;
    }

    /**
     * Change password
     *
     * @param username    username
     * @param newPassword new password
     * @return true if password change success
     */
    public boolean changePassword(final String username, final String newPassword) {
        boolean isPasswordChanged;
        UserDetails user = loadUser(username, LOAD_USER_BY_USERNAME);
        if (user != null) {
            user.setPassword(newPassword);
            updateUser(user);
            isPasswordChanged = true;
        } else {
            LOG.error(" Password changed failed. User not found in database");
            isPasswordChanged = false;
        }

        return isPasswordChanged;
    }

    private UserDetails loadUser(String value, int loadBy) {
        UserDetails user = null;
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Loading user  ");
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(UserDetails.class);
            if (loadBy == LOAD_USER_BY_USERNAME) {
                criteria.add(Restrictions.eq("username", value));
            } else if (loadBy == LOAD_USER_BY_EMAIL) {
                criteria.add(Restrictions.eq("email", value).ignoreCase());
            } else if (loadBy == LOAD_USER_BY_USER_ID) {
                criteria.add(Restrictions.eq(COLUMN_USER_ID, Long.parseLong(value)));
            } else {
                return null;
            }


            Object o = criteria.uniqueResult();
            if (o != null) {
                user = (UserDetails) o;
            }
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "Loading user   ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    public Map<Long,CustomUserDetails> loadUsersByUserIdMap(Set<Long> value) {
        Map<Long,CustomUserDetails>  customUserDetailsMap = null;
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Loading users by userIds  ");
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(UserDetails.class);
            criteria.add(Restrictions.in(COLUMN_USER_ID, value));
            List<UserDetails> userDetailsList = (List<UserDetails>)criteria.list();
            customUserDetailsMap = convertUserMap(userDetailsList);
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "Loading user   ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customUserDetailsMap;
    }

    /**
     * Load user session by user id
     *
     * @param userId  userId
     * @return user session business object
     */
    private UserSessionDetails loadSession(final String userId) {
        UserSessionDetails user = null;
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Loading user  ");
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(UserSessionDetails.class);
            criteria.add(Restrictions.eq(COLUMN_USER_ID, Long.parseLong(userId)));
            Object o = criteria.uniqueResult();
            if (o != null) {
                user = (UserSessionDetails) o;
            }
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "Loading user   ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    public boolean deleteUser(String username) {
        //DON'T delete set deleted flag

        return false;
    }

    /**
     * Update user object
     *
     * @param user user object
     * @return status
     */
    public boolean updateUser(UserDetails user) {
        boolean returnVal = false;
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            returnVal = true;
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "  update plus user  ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;

    }

    public boolean archiveUser(ArchiveUserDetails archiveUser) {
        boolean returnVal = false;
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(archiveUser);
            transaction.commit();
            returnVal = true;
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "  archive plus user  ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    public boolean isEmailExists(String email) {
        return isColumnValueExists(email, COLUMN_EMAIL);
    }

    public boolean isUserNameExists(String username) {
        return isColumnValueExists(username, COLUMN_USERNAME);
    }

    /**
     * Check if username is exist with the system or not
     *
     * @param columnName name
     * @param columnVal  value
     * @return status
     */
    private boolean isColumnValueExists(final String columnVal, final String columnName) {
        boolean returnVal = false;
        String sql = MessageFormat.format(COLUMN_VALUE_EXISTS_SQL, columnName);
        if (LOG.isDebugEnabled()) {
            LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Check if username is exist with the system or not ");
        }
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query query = session.createQuery(sql);
            query.setString("a", columnVal);
            List list = query.list();
            returnVal = (!list.isEmpty());
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "  Check if given column value already exist with the system or not ", e);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    /**
     * Update last login ip and
     *
     * @param lastLoginDetails last login details object
     * @return status
     */
    public int updateLastLoginDetails(final String username, LastLoginDetails lastLoginDetails) {
        int returnVal = UserDetailsConstants.UpdateData.UPDATE_DATA_FAILED;
        LOG.debug(" Update user ");
        Session session = null;
        Transaction transaction;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            UserDetails user = loadUser(username, LOAD_USER_BY_USERNAME);
            if (user != null) {
                user.setLastLoginIp(lastLoginDetails.getLastLoginIp());
                user.setLastLoginDate(lastLoginDetails.getLastLoginDate());
                user.setLastLoginCountry(lastLoginDetails.getLastLoginCountry());
                user.setLastWebSession(lastLoginDetails.getLastWebSession());
                user.setNoOfFailedAttempts(0);
            }
            session.update(user);
            transaction.commit();
            returnVal = UserDetailsConstants.UpdateData.UPDATE_DATA_SUCCESS;
        } catch (Exception e) {
            LOG.error("  Update user  ", e);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    /**
     * Update active session details
     *
     *
     * @param sessionUser session user details
     * @return status
     */
    public int updateActiveSessionDetails(final SessionUser sessionUser, int productId) {
        int returnVal = UserDetailsConstants.UpdateData.UPDATE_DATA_FAILED;
        LOG.debug(" Update active session details... ");
        Session session = null;
        Transaction transaction;
        UserSessionDetails sessionDetails;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            sessionDetails = new UserSessionDetails();
            setUserSessionDetails(sessionDetails,sessionUser, productId);

            Query q = session.createQuery(UPDATE_USER_PRIOR_SESSIONS);
            q.setParameter("lastLogoutDate", FormatUtils.GetUTCDateTimeAsDate());
            q.setParameter("productId",productId);
            q.setParameter("userID", sessionDetails.getUserID());

            q.executeUpdate();
            session.save(sessionDetails);

            transaction.commit();
            returnVal = UserDetailsConstants.UpdateData.UPDATE_DATA_SUCCESS;
            LOG.debug("  User Session details update Success.. ");
        } catch (Exception e) {
            LOG.error("  User Session details update failed..  ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    public int updateLastLogoutTime(Long userId, final String webSessionId, Date lastLogoutDate,int productId) {

        Session session = null;
        Transaction transaction;
        int transactionStatus;
        int loginStatus = 0;


        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query query = session.createQuery(UPDATE_USER_SESSIONS);
            query.setParameter("lastLogoutDate", lastLogoutDate);
            query.setParameter("webSessionId", webSessionId);
            query.setParameter("loginStatus", loginStatus);
            query.setParameter(COLUMN_USER_ID, userId);
            query.setParameter("productId", productId);

            query.executeUpdate();
            transaction.commit();
            transactionStatus = TRANS_SUCCESS;
        } catch (Exception e) {

            transactionStatus = TRANS_FAILED;
        }
        return transactionStatus;
    }



    /**
     * Remove session details entry on session expiry or logout
     *
     * @param userId userId
     * @return status success status
     */
    public int removeSessionDetails(final String userId) {
        int returnVal = UserDetailsConstants.UpdateData.UPDATE_DATA_FAILED;
        LOG.debug(" Update active session details... ");
        Session session = null;
        Transaction transaction;
        UserSessionDetails sessionDetails;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            sessionDetails = loadSession(userId);

            if (sessionDetails != null) {
                session.delete(sessionDetails);
            } else {
                LOG.debug("  Session data not available for USER_ID : " + userId);
            }
            transaction.commit();
            returnVal = UserDetailsConstants.UpdateData.UPDATE_DATA_SUCCESS;
        } catch (Exception e) {
            returnVal = UserDetailsConstants.UpdateData.UPDATE_DATA_FAILED;
            LOG.error("  User Session remove process failed..  ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    @Override
    public int removeAllSessionData(final int productId) {
        int status = -1;
        Transaction transaction;
        Session session = null;
        Date lastLogoutDate = FormatUtils.GetUTCDateTimeAsDate();


        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query q = session.createQuery(DELETE_USER_SESSIONS);
            q.setParameter("productId",productId);
            q.executeUpdate();
            transaction.commit();
            status = UserDetailsConstants.UpdateData.UPDATE_DATA_SUCCESS;
        } catch (Exception e) {
            status = UserDetailsConstants.UpdateData.UPDATE_DATA_FAILED;
            LOG.error("  System Logout update failed..  ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return status;
    }

    /**
     * Set user session details to  business object
     *
     * @param sessionDetails  session details business object
     * @param sessionUser sessionUser details
     */
    private void setUserSessionDetails(UserSessionDetails sessionDetails, SessionUser sessionUser, int productId) {
        sessionDetails.setUserID(sessionUser.getUserId());
        sessionDetails.setUsername(sessionUser.getUserName());
        sessionDetails.setLastLoginIp(sessionUser.getLoginIp());
        sessionDetails.setLastLoginDate(sessionUser.getLastLoginDate());
        sessionDetails.setLastLoginCountry(sessionUser.getLoginCountry());
        sessionDetails.setWebSessionId(sessionUser.getLastWebSession());
        sessionDetails.setLoginStatus(STATUS_ACTIVE);
        sessionDetails.setLastUpdatedTime(sessionUser.getLastLoginDate());
        sessionDetails.setProductId(productId);
    }

    /**
     * Update last login ip and
     *
     * @param username username
     * @param isReset  to reset to 0 or
     * @return status
     */
    public boolean updateFailedAttempts(final String username, boolean isReset) {
        boolean returnVal = false;
        LOG.debug(" update Failed Attempts user " + username);
        Session session = null;
        Transaction transaction;
        try {
            session = this.sessionFactory.openSession();
            UserDetails user = loadUser(username, LOAD_USER_BY_USERNAME);
            if (user != null) {
                transaction = session.beginTransaction();
                if (isReset) {
                    user.setNoOfFailedAttempts(0);
                } else {
                    Integer failedAttempts = user.getNoOfFailedAttempts();
                    if (failedAttempts == null) {
                        user.setNoOfFailedAttempts(1);
                    } else {
                        user.setNoOfFailedAttempts(++failedAttempts);
                    }
                }
                session.update(user);
                transaction.commit();
            }
            returnVal = true;
        } catch (Exception e) {
            LOG.error("  Update user  ", e);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    /**
     * Update user account activation status
     *
     * @param userActivationData activation data object
     * @return success status
     */
    public int updateUserAccActivationStatus(UserActivationData userActivationData) {
        int activationStatus = TRANS_PENDING;
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            //TODO have same status
            userActivationData.setStatus(UserDetailsConstants.UserActivationStatus.USER_ACTIVATION_ACTIVATED);
            //updating activation table
            session.saveOrUpdate(userActivationData);
            //updating user table
            int userStatus = UserDetailsConstants.UserStatus.USER_STATUS_ACTIVE;
            if (approvalRequired) {
                userStatus = UserDetailsConstants.UserStatus.USER_STATUS_PENDING_APPROVAL;
            }
            int transactionStatus = updateUserAccActivationStatus(session, userActivationData.getUserID(), userStatus);
            if (transactionStatus == TRANS_SUCCESS) {
                transaction.commit();
                activationStatus = TRANS_SUCCESS;
            } else {
                transaction.rollback();
                activationStatus = TRANS_FAILED;
            }
        } catch (Exception e) {
            activationStatus = TRANS_FAILED;
            LOG.error(USER_ACTIVATION_PRE_FIX + " FAILED " + e.getMessage(), e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return activationStatus;
    }

    public int createUserAccActivationStatus(UserActivationData userActivationData) {
        int returnVal = UserDetailsConstants.UerDetails.STATUS_FAILED;
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " creating user activation ");
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(userActivationData);
            transaction.commit();
            returnVal = UserDetailsConstants.UerDetails.STATUS_SUCCESS;
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "  creating user activation   ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    private int updateUserAccActivationStatus(Session session, Long userId, int status)  {
        int transactionStatus;
        Query query = session.createQuery(UPDATE_USER_STATUS);
        query.setParameter("accountStatus", status);
        query.setParameter(COLUMN_USER_ID, userId);
        int result = query.executeUpdate();
        if (result > 0) {
            transactionStatus = TRANS_SUCCESS;
        } else {
            transactionStatus = TRANS_FAILED;
        }
        return transactionStatus;
    }

    /**
     * Validate email confirmation link and activate user account
     *
     * @param userId  user id
     * @param actCode activation code
     * @return activation status {@link com.dfn.alerts.constants.UserDetailsConstants.UserActivationStatus}
     */
    public int validateEmailConfirmation(int userId, String actCode) {
        Session session;
        Criteria criteria;
        int activationStatus;

        try {
            session = this.sessionFactory.openSession();
            criteria = session.createCriteria(UserActivationData.class);
            //match user id
            criteria.add(Restrictions.eq(IConstants.UserPreferences.USER_ID_BO, (long) userId));
            //match activation code
            criteria.add(Restrictions.eq(IConstants.UserPreferences.ACT_CODE_BO, actCode));
            criteria.addOrder(Order.asc("actCodeSentTime"));
            List<UserActivationData> activationDataList = criteria.list();
            activationStatus = getActivationStatus(activationDataList);
        } catch (HibernateException e) {
            activationStatus = UserDetailsConstants.UserActivationStatus.USER_ACTIVATION_RETRY;
            LOG.debug(USER_ACTIVATION_PRE_FIX + "Failed.." + e.getMessage(), e);
        }
        return activationStatus;
    }

    /**
     * private method to get activation status
     *
     * @param activationDataList list of activation records for give user
     * @return status     {@link UserDetailsConstants.UserActivationStatus}
     */
    private int getActivationStatus(List<UserActivationData> activationDataList) {

        UserActivationData userActivationData;
        int activationStatus = UserDetailsConstants.UserActivationStatus.USER_ACTIVATION_UNSUCCESSFUL;

        if (activationDataList != null && !activationDataList.isEmpty()) {
            userActivationData = activationDataList.get(0);
            //This is to check, activation record hasn't expired
            int status = userActivationData.getStatus();

            if (status == UserDetailsConstants.UserActivationStatus.USER_ACTIVATION_PENDING) {
                if (isActivationValid(userActivationData)) {
                    LOG.debug(USER_ACTIVATION_PRE_FIX + "Matching user activation code found. Updating record...");
                    activationStatus = updateUserAccActivationStatus(userActivationData);
                    LOG.debug(USER_ACTIVATION_PRE_FIX + "User account activated successfully..");
                } else {
                    LOG.debug(USER_ACTIVATION_PRE_FIX + "Matching user activation code found.  but expired...");
                    activationStatus = UserDetailsConstants.UserActivationStatus.USER_ACTIVATION_EXPIRED;
                }
            } else if (status == UserDetailsConstants.UserActivationStatus.USER_ACTIVATION_ACTIVATED) {
                LOG.debug(USER_ACTIVATION_PRE_FIX + "User account already activated..");
                activationStatus = UserDetailsConstants.UserActivationStatus.USER_ACTIVATION_ALREADY_ACTIVATED;
            } else {
                activationStatus = UserDetailsConstants.UserActivationStatus.USER_ACTIVATION_UNSUCCESSFUL;
                LOG.debug(USER_ACTIVATION_PRE_FIX + "User account Activation unsuccessful");

            }
        } else {
            LOG.debug(USER_ACTIVATION_PRE_FIX + "No such activation code or user found..");
        }

        return activationStatus;
    }

    /**
     * Check if existing record is valid (Expired time is within the limits allowed)
     * [Expired_time - activation_code < 24 ]
     *
     * @param userActivationData UserActivationData
     * @return true if valid request
     */
    private boolean isActivationValid(UserActivationData userActivationData) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        return userActivationData.getActCodeExpiryTime().getTime() > currentDate.getTime();
    }

    public boolean doLogout(String username) {
        return false;
    }

    /**
     * updating user preferences
     *
     * @param userPreference UserPreference  object
     * @return status
     */
    public int updateUserPreference(UserPreference userPreference) {
        int returnVal = UserDetailsConstants.UerDetails.STATUS_FAILED;
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Update user ");
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from UserPreference where userID=:userID");
            query.setLong(COLUMN_USER_ID, userPreference.getUserID());
            UserPreference currentObj = (UserPreference) query.uniqueResult();
            if (currentObj == null) {
                session.save(userPreference);
            } else {
                userPreference.setId(currentObj.getId());
                session.merge(userPreference);
            }
            transaction.commit();
            returnVal = UserDetailsConstants.UerDetails.STATUS_SUCCESS;
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "  Update user  ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }


    private CustomUserDetails convertUser(UserDetails user) {
        CustomUserDetails customUserDetails = new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getUserID(),
                getAuthorities(user.getUserRoles()),
                user.getNoOfFailedAttempts(),
                user.getPasswordExpiryDate(),
                user.getAccountStatus(),
                getMaxUserLevel(user.getUserRoles()));

        customUserDetails.setAccountCreationDate(user.getAccountCreationDate());
        customUserDetails.setAccountExpiryDate(user.getAccountExpiryDate());
        customUserDetails.setAccountType(user.getAccountType() != null ? user.getAccountType() : UserManagementConstants.UserAccountType.TRIAL.getId());
        customUserDetails.setCompany(user.getCompany());
        customUserDetails.setCountry(user.getCountry());
        customUserDetails.setEmail(user.getEmail());
        customUserDetails.setFirstName(user.getFirstName());
        customUserDetails.setLastLoginCountry(user.getLastLoginCountry());
        customUserDetails.setLastLoginDate(user.getLastLoginDate());
        customUserDetails.setLastLoginIp(user.getLastLoginIp());
        customUserDetails.setLastName(user.getLastName());
        customUserDetails.setLastWebSession(user.getLastWebSession());
        customUserDetails.setLoginStatus(user.getLoginStatus());
        customUserDetails.setNoOfFailedAttempts(user.getNoOfFailedAttempts());
        customUserDetails.setPasswordChangedDate(user.getPasswordChangedDate());
        customUserDetails.setPasswordExpiryDate(user.getPasswordExpiryDate());
        customUserDetails.setPhone(user.getPhone());
        customUserDetails.setWorkTel(user.getWorkTel());
        customUserDetails.setTitle(user.getTitle());
        customUserDetails.setUserVersion(user.getUserVersion());
        customUserDetails.setDesignation(user.getDesignation());
        customUserDetails.setCity(user.getCity());
        customUserDetails.setAddress1(user.getAddress1());
        customUserDetails.setAddress2(user.getAddress2());
        customUserDetails.setLastUpdatedBy(user.getLastUpdatedBy());
        customUserDetails.setUserRoles(user.getUserRoles());

        if(user.getUserPremiumAccount() != null){
            UserPremiumAccount userPremiumAccount = user.getUserPremiumAccount();
            Map<String, Boolean> services = new HashMap<String, Boolean>(getServices());
            if (userPremiumAccount.getServices() != null) {
                for (String service : userPremiumAccount.getServices().split(",")) {
                    services.put(service, true);
                }
            }
            PremiumAccountDetails premiumAccountDetails;
            if (userPremiumAccount.getPriceUserId() == null || userPremiumAccount.getPriceProfileId() == null) {
                premiumAccountDetails = new PremiumAccountDetails(userPremiumAccount.getPriceUsername(), userPremiumAccount.getStatus(), services);
            } else {
                premiumAccountDetails = new PremiumAccountDetails(userPremiumAccount.getPriceUserId(), userPremiumAccount.getAccountDetailsID(),
                        userPremiumAccount.getPriceProfileId(), userPremiumAccount.getPriceUsername(), userPremiumAccount.getStatus(),
                        services, userPremiumAccount.getExpiryDate(),  userPremiumAccount.getVersion());
                SalesRepresentative representative = userPremiumAccount.getSalesRep();
                if(representative != null) {
                    SalesRepDetails salesRepDetails = new SalesRepDetails(representative.getSalesRepId() , representative.getEmail());
                    salesRepDetails.setDesignation(representative.getDesignation());
                    salesRepDetails.setFirstName(representative.getFirstName());
                    salesRepDetails.setLastName(representative.getLastName());
                    salesRepDetails.setMobileNumber(representative.getMobileNumber());
                    salesRepDetails.setPlusAccountId(representative.getPlusAccountId());
                    premiumAccountDetails.setSalesRepDetails(salesRepDetails);
                }

            }
            customUserDetails.setPremiumAccountDetails(premiumAccountDetails);
        }

        return customUserDetails;
    }

   //TO-DO Delete
    private PremiumAccountDetails covertPreUser(UserPremiumAccount userPremiumAccount) {
        Map<String, Boolean> services = new HashMap<String, Boolean>(getServices());
        if (userPremiumAccount.getServices() != null) {
            for (String service : userPremiumAccount.getServices().split(",")) {
                services.put(service, true);
            }
        }

        PremiumAccountDetails premiumAccountDetails;
        if (userPremiumAccount.getPriceUserId() == null || userPremiumAccount.getPriceProfileId() == null) {
            premiumAccountDetails = new PremiumAccountDetails(userPremiumAccount.getPriceUsername(), userPremiumAccount.getStatus(), services);
        } else {
            premiumAccountDetails = new PremiumAccountDetails(userPremiumAccount.getPriceUserId(), userPremiumAccount.getAccountDetailsID(),
                    userPremiumAccount.getPriceProfileId(), userPremiumAccount.getPriceUsername(), userPremiumAccount.getStatus(),
                    services, userPremiumAccount.getExpiryDate(), userPremiumAccount.getVersion());
            SalesRepresentative representative = userPremiumAccount.getSalesRep();
            if(representative != null) {
                SalesRepDetails salesRepDetails = new SalesRepDetails(representative.getSalesRepId() , representative.getEmail());
                salesRepDetails.setDesignation(representative.getDesignation());
                salesRepDetails.setFirstName(representative.getFirstName());
                salesRepDetails.setLastName(representative.getLastName());
                salesRepDetails.setMobileNumber(representative.getMobileNumber());
                salesRepDetails.setPlusAccountId(representative.getPlusAccountId());
                premiumAccountDetails.setSalesRepDetails(salesRepDetails);
            }
        }

        premiumAccountDetails.setUserDetails(convertUser(userPremiumAccount.getUserDetails()));

        return premiumAccountDetails;
    }

    /**
     * Create subscription services map with all false
     *
     * @return services map
     */
    private Map<String, Boolean> getServices() {

        if (SERVICES == null) {
            synchronized (this) {
                SERVICES = new HashMap<String, Boolean>(UserManagementConstants.SubscriptionServices.values().length);
                for (UserManagementConstants.SubscriptionServices ser : UserManagementConstants.SubscriptionServices.values()) {
                    SERVICES.put(ser.toString(), false);
                }
            }


        }

        return SERVICES;

    }

    private UserPreferenceDetails convertUserPreferences(UserPreference userPreference) {
        UserPreferenceDetails userPreferenceDetails = new UserPreferenceDetails(userPreference.getUserID(), false);

        userPreferenceDetails.setDefaultCompany(userPreference.getDefaultCompany());
        userPreferenceDetails.setDefaultCountry(userPreference.getDefaultCountry());
        userPreferenceDetails.setDefaultCurrency(userPreference.getDefaultCurrency());
        userPreferenceDetails.setDefaultMarket(userPreference.getDefaultMarket());
        userPreferenceDetails.setDefaultTimeZone(userPreference.getDefaultTimeZone());
        userPreferenceDetails.setDefaultRegion(userPreference.getDefaultRegion());
        userPreferenceDetails.setEditionControlType(userPreference.getEditionControlType());

        userPreferenceDetails.setPreferredFilesLanguage(userPreference.getPreferredFilesLanguage());
        userPreferenceDetails.setPreferredNewsHeadLanguages(userPreference.getPreferredNewsReadingLanguages());
        userPreferenceDetails.setNewsReadingLanguagePreference(userPreference.getNewsReadingLanguagePreference());
        userPreferenceDetails.setPreferredNewsReadingLanguages(userPreference.getPreferredNewsReadingLanguages());

        userPreferenceDetails.setMarketsInActivePanel(userPreference.getMarketsInActivePanel());
        userPreferenceDetails.setMarketsInTimeLine(userPreference.getMarketsInTimeLine());
        userPreferenceDetails.setCommodities(userPreference.getCommodities());
        userPreferenceDetails.setCurrencies(userPreference.getCurrencies());
        userPreferenceDetails.setScreenerSettings(userPreference.getScreenerSettings());
        userPreferenceDetails.setWatchListSettings(userPreference.getWatchListSettings());
        userPreferenceDetails.setNewsStreamSettings(userPreference.getNewsStreamSettings());

        userPreferenceDetails.setEmailReleaseNotes(userPreference.getEmailReleaseNotes() == null ? 0 : userPreference.getEmailReleaseNotes());
        userPreferenceDetails.setShowLatestReleaseNote(userPreference.getShowLatestReleaseNote() == null ? 0 : userPreference.getShowLatestReleaseNote());
        userPreferenceDetails.setShowReleaseNotes(userPreference.getShowReleaseNotes() == null ? 0 : userPreference.getShowReleaseNotes());

        userPreferenceDetails.setPeriodBasis(userPreference.getPeriodBasis());
        userPreferenceDetails.setDefaultFinancialTab(userPreference.getDefaultFinancialTab());
        userPreferenceDetails.setDefaultFinancialView(userPreference.getDefaultFinancialView());
        userPreferenceDetails.setBreakdown(userPreference.getBreakdown());
        userPreferenceDetails.setFutureEstimates(userPreference.getFutureEstimates() == null ? 0 : userPreference.getFutureEstimates());

        userPreferenceDetails.setStockChartType(userPreference.getStockChartType());

        return userPreferenceDetails;
    }

    /**
     * Get user authorities for  given user roles list
     *
     * @param userRoles comma separated user roles
     * @return set of user authorities
     */
    private Set<GrantedAuthority> getAuthorities(String userRoles) {
        if (userRoles == null) {
            return null;
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        String[] userRolesA = userRoles.split(",");
        for (String role : userRolesA) {
            grantedAuthorities.add(getGrantedAuthorityMap().get(Long.parseLong(role)));
        }

        return grantedAuthorities;
    }

    private int getMaxUserLevel(String userRoles) {
        Map<Long, SecurityRoles> rolesMap = getSecurityRolesMap();
        List<SecurityRoles> rolesCollection = new ArrayList<SecurityRoles>();

        String[] userRolesA = userRoles.split(",");
        for (String role : userRolesA) {
            rolesCollection.add(rolesMap.get(Long.parseLong(role)));
        }
        Collections.sort(rolesCollection);

        return rolesCollection.get(0).getRoleWeight();
    }


    private Map<Long, GrantedAuthority> getGrantedAuthorityMap() {
        if (grantedAuthorityMap == null) {
            synchronized (this) {
                initGrantedAuthorityMap();
            }
        }

        return grantedAuthorityMap;
    }

    private Map<Long, SecurityRoles> getSecurityRolesMap() {
        if (stringSecurityRolesMap == null) {
            synchronized (this) {
                initSecurityRolesMap();
            }
        }

        return stringSecurityRolesMap;
    }

    private void initSecurityRolesMap() {
        List<SecurityRoles> securityRoles = getAllSecurityRoles();
        if (securityRoles != null) {
            stringSecurityRolesMap = new HashMap<Long, SecurityRoles>(securityRoles.size());
            for (SecurityRoles securityRole : securityRoles) {
                stringSecurityRolesMap.put(securityRole.getId(), securityRole);

            }
        }
    }

    private void initGrantedAuthorityMap() {
        List<SecurityRoles> securityRoles = getAllSecurityRoles();
        if (securityRoles != null) {
            grantedAuthorityMap = new HashMap<Long, GrantedAuthority>(securityRoles.size());
            for (SecurityRoles securityRole : securityRoles) {
                grantedAuthorityMap.put(securityRole.getId(), new SimpleGrantedAuthority(securityRole.getRoleId()));
            }
        }
    }

    private List<SecurityRoles> getAllSecurityRoles() {
        Session session = null;
        List<SecurityRoles> securityRoles = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery("from SecurityRoles");
            securityRoles = q.list();
        } catch (Exception e) {
            LOG.error(" Get all Security roles ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return securityRoles;
    }

    public void setApprovalRequired(boolean approvalRequired) {
        this.approvalRequired = approvalRequired;
    }

    public List<UserPremiumAccount> getAllPremiumUsers() {
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Loading premium users  ");
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(UserPremiumAccount.class);
            return (List<UserPremiumAccount>) criteria.list();
        } catch (Exception e) {
            LOG.error("Loading all premium users   ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public List<UserDetails> getAccountExpireNotificationRequiredUsers(int numberOfDays) {
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Loading users  ");
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(UserDetails.class);
            criteria.add(Restrictions.sqlRestriction("(ACCOUNT_EXPIRE_DATE - (select sysdate from dual)) > 0 and (ACCOUNT_EXPIRE_DATE - (select sysdate from dual)) <= " + numberOfDays));
            return (List<UserDetails>) criteria.list();
        } catch (Exception e) {
            LOG.error("Loading all users   ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public List<UserDetails> getAccountExpireNotificationRequiredUsers(Map<Integer, String> accountTypeRelExpDays) {
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Loading users  ");
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(UserDetails.class);
            Disjunction disjunction = Restrictions.disjunction();
            for(int accountType : accountTypeRelExpDays.keySet()){
                String numberOfDays = accountTypeRelExpDays.get(accountType);
                Conjunction con = Restrictions.conjunction();
                if(numberOfDays.indexOf(IConstants.Delimiter.COMMA) > 0){
                    String[] days = numberOfDays.split(IConstants.Delimiter.COMMA + "");
                    Disjunction dis = Restrictions.disjunction();
                    for(String dayCount : days){
                        dis.add(Restrictions.sqlRestriction("trunc(ACCOUNT_EXPIRE_DATE - (select sysdate from dual)) = " + dayCount));
                    }
                    con.add(dis);
                }else{
                    con.add(Restrictions.sqlRestriction("trunc(ACCOUNT_EXPIRE_DATE - (select sysdate from dual)) = " + numberOfDays));
                }
                con.add(Restrictions.eq("accountType", accountType));
                disjunction.add(con);
            }
            criteria.add(disjunction);
            return (List<UserDetails>) criteria.list();
        } catch (Exception e) {
            LOG.error("Loading all users   ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public List<UserPremiumAccount> searchPremiumUsers(Map<String, String> searchParameters) {
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Searching premium users  ");
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(UserPremiumAccount.class).createCriteria("userDetails");
            for (String param : searchParameters.keySet()) {
                String val = searchParameters.get(param);
                if(param.equalsIgnoreCase(IConstants.UserPreferences.PRE_ACCOUNT_TYPE_BO)){
                    if(!"-1".equals(val)){
                        criteria.add(Restrictions.eq(param, Integer.parseInt(val)));
                    }
                }else{
                    if (val != null && !"".equals(val)) {
                        criteria.add(Restrictions.like(param, "%" + val + "%").ignoreCase());
                    }
                }
            }
            return (List<UserPremiumAccount>) criteria.list();
        } catch (Exception e) {
            LOG.error("Searching premium users   ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }


    public List<UserSessionDetailsDTO> getUserSessions(long userId , int productId) {
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Searching Active users  ");
        List<UserSessionDetails> list = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(QUERY_GET_USER_SESSIONS);
            q.setLong(COLUMN_USER_ID, userId);
            q.setInteger("productId", productId);
            list = (List<UserSessionDetails>)q.list();
        } catch (UncategorizedSQLException ignored) {
            //
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return convertUserSessionsDetails(list);
    }

   private List<UserSessionDetailsDTO> convertUserSessionsDetails(List<UserSessionDetails> userSessionDetailsList){

       List<UserSessionDetailsDTO>  userSessionDetailsDTOList = null;

       if(userSessionDetailsList != null && !userSessionDetailsList.isEmpty()) {
           userSessionDetailsDTOList = new ArrayList<UserSessionDetailsDTO>(userSessionDetailsList.size());
           for(UserSessionDetails userSessionDetails : userSessionDetailsList){
               userSessionDetailsDTOList.add(convertUserSessionsDetails(userSessionDetails)) ;
           }
       }

       return userSessionDetailsDTOList;
   }

    private UserSessionDetailsDTO convertUserSessionsDetails(UserSessionDetails userSessionDetails){
        UserSessionDetailsDTO userSessionDetailsDTO = null;

        if(userSessionDetails != null){
            userSessionDetailsDTO = new UserSessionDetailsDTO();

            userSessionDetailsDTO.setLastLoginCountry(userSessionDetails.getLastLoginCountry());
            userSessionDetailsDTO.setLastLoginDate(userSessionDetails.getLastLoginDate());
            userSessionDetailsDTO.setLastLoginIp(userSessionDetails.getLastLoginIp());
            userSessionDetailsDTO.setLastLogoutDate(userSessionDetails.getLastLogoutDate());
            userSessionDetailsDTO.setLastUpdatedTime(userSessionDetails.getLastUpdatedTime());
            userSessionDetailsDTO.setLoginStatus(userSessionDetails.getLoginStatus());
            userSessionDetailsDTO.setProductId(userSessionDetails.getProductId());
            userSessionDetailsDTO.setSystemLogout(userSessionDetails.isSystemLogout());
            userSessionDetailsDTO.setUserID(userSessionDetails.getUserID());
            userSessionDetailsDTO.setUserSessionDetails(userSessionDetails.generateUserSessionDetails());
            userSessionDetailsDTO.setWebSessionId(userSessionDetails.getWebSessionId());

        }

        return userSessionDetailsDTO;
   }

    public List<Object> getAllActiveUserSessions() {
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Searching user sessions  ");
        List<Object> list = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(QUERY_GET_ACTIVE_SESSIONS);
            list = q.list();
        } catch (UncategorizedSQLException ignored) {
            //
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }


    /**
     * Getting Non expired demo accounts
     * @return List of PremiumAccountDetails
     */
    public List<CustomUserDetails> getNonExpiredDemoUsers(){
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Loading users  ");
        Session session = null;
        List<CustomUserDetails> customUserDetailsList = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(UserDetails.class);
            criteria.add(Restrictions.sqlRestriction("(ACCOUNT_EXPIRE_DATE  > sysdate) and (ACCOUNT_TYPE = " + UserManagementConstants.UserAccountType.TRIAL.getId() + ")"));
            List<UserDetails> userDetailsList = (List<UserDetails>) criteria.list();
            customUserDetailsList =  convertUserList(userDetailsList);
        } catch (Exception e) {
            LOG.error("Loading all users   ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customUserDetailsList;
    }

    private Map<Long,CustomUserDetails> convertUserMap(List<UserDetails> userDetails){

        if(userDetails == null || userDetails.isEmpty()){
            return null;
        }

        Map<Long,CustomUserDetails>  customUserDetailsMap = new HashMap<Long, CustomUserDetails>(userDetails.size());

        for(UserDetails user : userDetails){
            customUserDetailsMap.put(user.getUserID(),convertUser(user));
        }

        return customUserDetailsMap;

    }

    private List<CustomUserDetails> convertUserList(List<UserDetails> userDetails){
        
        if(userDetails == null || userDetails.isEmpty()){
            return null;
        }
        
        List<CustomUserDetails> customUserDetailsList = new ArrayList<CustomUserDetails>(userDetails.size());
        
        for(UserDetails user : userDetails){
            customUserDetailsList.add(convertUser(user));
        }

        return customUserDetailsList;
    
    }

    public void setSessionFactory(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
        //
    }

    //region EMAIL-AUDIT

    private static final String SQL_ARCHIVE_EMAILS = "INSERT INTO EMAIL_AUDIT_HISTORY(" +
                "EMAIL_ID, EMAIL_FROM, EMAIL_FROM_NAME, EMAIL_TO, EMAIL_BCC, EMAIL_CC, EMAIL_TITLE, EMAIL_BODY, NOTIFICATION_TIME, " +
                    "STATUS, LAST_UPDATED_TIME, RETRY_COUNT, USER_ID, EMAIL_TYPE, ARCHIVED_TIME" +
            ") " +
            "SELECT EMAIL_ID, EMAIL_FROM, EMAIL_FROM_NAME, EMAIL_TO, EMAIL_BCC, EMAIL_CC, EMAIL_TITLE, EMAIL_BODY, NOTIFICATION_TIME, " +
                    "STATUS, LAST_UPDATED_TIME, RETRY_COUNT, USER_ID, EMAIL_TYPE, SYSTIMESTAMP " +
            "FROM EMAIL_AUDIT WHERE STATUS = 1";

    private static final String SQL_REMOVE_ARCHIVED_EMAILS = "DELETE FROM EMAIL_AUDIT WHERE STATUS = 1";

    private static final String UPDATE_FAILED_EMAILS = "UPDATE EmailNotification SET status = :status, lastUpdatedTime = :lastUpdatedTime, " +
            "retryCount = retryCount+1 WHERE emailId = :emailId";

    /**
     * insert email notification obj to DB
     * @param emailNotification email notification obj
     * @return UserDetailsConstants.DBUpdateStatus.STATUS_FAILED/UserDetailsConstants.DBUpdateStatus.STATUS_SUCCESS
     */
    @Deprecated
    public int insertEmailNotification(EmailNotification emailNotification){
        int returnVal = UserDetailsConstants.DBUpdateStatus.STATUS_FAILED;
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " creating email notification ");
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            emailNotification.setBody(DBUtils.setClob(emailNotification.getContent(), session));
            session.save(emailNotification);
            transaction.commit();
            returnVal = UserDetailsConstants.DBUpdateStatus.STATUS_SUCCESS;
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "  creating email notification ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    /**
     * update email notification obj to DB
     * @param emailId email notification obj id
     * @param status status
     * @return UserDetailsConstants.DBUpdateStatus.STATUS_FAILED/UserDetailsConstants.DBUpdateStatus.STATUS_SUCCESS
     */
    public int updateEmailNotification(int emailId, int status){
        int returnVal = UserDetailsConstants.DBUpdateStatus.STATUS_FAILED;
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " updating email notification ");
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query q = session.createQuery(UPDATE_FAILED_EMAILS);
            q.setInteger("emailId", emailId);
            q.setInteger("status", status);
            q.setTimestamp("lastUpdatedTime", new Timestamp(System.currentTimeMillis()));
            int updateCount = q.executeUpdate();
            transaction.commit();
            if(updateCount == 1){
                returnVal = UserDetailsConstants.DBUpdateStatus.STATUS_SUCCESS;
            }
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + "  updating email notification ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return returnVal;
    }

    /**
     * load failed email notification objects from DB
     * @return List<FailedEmailNotificationDTO> failed notifications
     */
    public List<FailedEmailNotificationDTO> getFailedEmailNotifications(int retryCount){
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Loading failed email notifications ");
        Session session = null;
        List<FailedEmailNotificationDTO> failedEmailNotifications = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(EmailNotification.class);
            criteria.add(Restrictions.eq("status", UserDetailsConstants.DBUpdateStatus.STATUS_FAILED));
            criteria.add(Restrictions.le("retryCount", retryCount));
            List<EmailNotification> emailNotificationList = (List<EmailNotification>) criteria.list();
            failedEmailNotifications = convertFailedEmailNotification(emailNotificationList);
        } catch (Exception e) {
            LOG.error("Loading all users   ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return failedEmailNotifications;
    }

    public List<FailedEmailNotificationDTO> getAllFailedEmailNotifications(){
        LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Loading failed email notifications ");
        Session session = null;
        List<FailedEmailNotificationDTO> failedEmailNotifications = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(EmailNotification.class);
            criteria.add(Restrictions.eq("status", UserDetailsConstants.DBUpdateStatus.STATUS_FAILED));
            List<EmailNotification> emailNotificationList = (List<EmailNotification>) criteria.list();
            failedEmailNotifications = convertFailedEmailNotification(emailNotificationList);
        } catch (Exception e) {
            LOG.error("Loading all users   ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return failedEmailNotifications;
    }

    /**
     * convert to DTO object
     * @param emailNotifications failed email notifications
     * @return list of DTOs failed email notifications
     */
    private List<FailedEmailNotificationDTO> convertFailedEmailNotification(List<EmailNotification> emailNotifications){
        List<FailedEmailNotificationDTO> failedEmailNotificationDTOs = new ArrayList<FailedEmailNotificationDTO>(emailNotifications.size());
        for(EmailNotification emailNotification : emailNotifications){
            FailedEmailNotificationDTO failedEmailNotificationDTO = new FailedEmailNotificationDTO(
                    emailNotification.getEmailId(), emailNotification.getFromEmail(), emailNotification.getFromName(),
                    emailNotification.getTo(), emailNotification.getBcc(), emailNotification.getCc(),
                    emailNotification.getTitle(), DBUtils.readClob(emailNotification.getBody()),emailNotification.getLastUpdatedTime(),
                    emailNotification.getNotificationTime(), emailNotification.getRetryCount(), emailNotification.getUserId(), emailNotification.getEmailType());
            failedEmailNotificationDTOs.add(failedEmailNotificationDTO);
        }
        return failedEmailNotificationDTOs;
    }

    /**
     * archive successful email
     * @return inserts to email audit history == deletes from email audit
     */
    public boolean archiveEmailNotifications(){
        int insertCount = -1;
        int deleteCount = 0;
        Transaction transaction = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query q = session.createSQLQuery(SQL_ARCHIVE_EMAILS);
            insertCount = q.executeUpdate();

            q = session.createSQLQuery(SQL_REMOVE_ARCHIVED_EMAILS);
            deleteCount = q.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            LOG.error("ERROR in archiveEmailNotifications()", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return insertCount == deleteCount;
    }

    @Override
    public Map<String,Map<String,String>> getEmailNotificationSummary(RequestDBDTO requestDBDTO) {
        return null;
    }

     public List<Map<String, Object>> getUserSessionDetails(RequestDBDTO requestDBDTO) {
         return null;
     }
    //endregion


    public int saveAuthResponse(AuthenticationAPIResponse authenticationAPIResponse) {
        int returnVal = UserDetailsConstants.UerDetails.STATUS_FAILED;

        Session session = null;
        Transaction transaction = null;
        try {
            session = this.sessionFactory.openSession();
            returnVal = UserDetailsConstants.UerDetails.STATUS_FAILED;
            LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Save auth response ");
            transaction = session.beginTransaction();
            session.save(authenticationAPIResponse);
            transaction.commit();
            returnVal = UserDetailsConstants.UerDetails.STATUS_SUCCESS;
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + " Save auth error ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return returnVal;

    }

    /**
     * save feedback case to DB
     * @param contactCase
     * @return
     */
    @Override
    public int saveFeedbackCase(ContactCase contactCase) {
        int returnVal = UserDetailsConstants.UerDetails.STATUS_FAILED;

        Session session = null;
        Transaction transaction = null;
        try {
            LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Save contactCase start ");
            session = this.sessionFactory.openSession();
            returnVal = UserDetailsConstants.UerDetails.STATUS_FAILED;
            transaction = session.beginTransaction();
            session.save(contactCase);
            transaction.commit();
            returnVal = UserDetailsConstants.UerDetails.STATUS_SUCCESS;
            LOG.debug(USER_DETAILS_LOG_PRE_FIX + " Save contactCase success ");
        } catch (Exception e) {
            LOG.error(USER_DETAILS_LOG_PRE_FIX + " Save contactCase error ", e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return returnVal;

    }

}
