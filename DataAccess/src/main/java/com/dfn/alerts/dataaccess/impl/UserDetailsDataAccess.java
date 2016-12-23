package com.dfn.alerts.dataaccess.impl;

import com.dfn.alerts.beans.DataAccessRequestDTO;
import com.dfn.alerts.beans.LastLoginDetails;
import com.dfn.alerts.beans.NotificationData;
import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.notification.EmailDTO;
import com.dfn.alerts.beans.user.*;
import com.dfn.alerts.constants.CacheKeyConstant;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.constants.UserDetailsConstants;
import com.dfn.alerts.dataaccess.api.ICacheManager;
import com.dfn.alerts.dataaccess.api.IRequestGenerator;
import com.dfn.alerts.dataaccess.beans.ResponseObj;
import com.dfn.alerts.dataaccess.orm.impl.*;
import com.dfn.alerts.dataaccess.dao.UserDetailsDAO;
import com.dfn.alerts.dataaccess.dao.impl.DAOFactory;
import com.dfn.alerts.dataaccess.utils.DataAccessUtils;
import com.dfn.alerts.exception.SocketAccessException;
import com.dfn.alerts.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: aravindal  , lasanthak
 * Date: 6/26/13
 * Time: 4:44 PM
 */
public class UserDetailsDataAccess extends DataAccess {

    /**
     * DAO factory
     */
    private DAOFactory hibernateDaoFactory;

    private DAOFactory jdbcDaoFactory;

    /**
     * Mix request generator
     */
    private IRequestGenerator requestGenerator;

    private int productId;

    private ICacheManager redisCacheManager;

    /**
     * LOG 4 J instance
     */
    private static final Logger LOG = LogManager.getLogger(UserDetailsDataAccess.class);

    public static final String SESSION_DURATION = "SESSION_DURATION";
    public static final int IN_MINUTES = 1000 * 60;
    private static final int PRICE_USER_REQUEST_TTL = 604800; //60*60*24*7

    private static final String EMAIL_AUDIT_SUMMARY_QUERY = "SELECT EMAIL_TYPE,STATUS,COUNT(EMAIL_ID) AS EMAIL_COUNT FROM (SELECT EMAIL_ID,EMAIL_TYPE,STATUS FROM EMAIL_AUDIT WHERE NOTIFICATION_TIME > ADD_MONTHS( systimestamp, -1 ) UNION SELECT EMAIL_ID,EMAIL_TYPE,STATUS FROM EMAIL_AUDIT_HISTORY WHERE NOTIFICATION_TIME > ADD_MONTHS( systimestamp, -1 ))GROUP BY EMAIL_TYPE,STATUS";
    private static final String USER_SESSION_DETAILS_QUERY_1 = "SELECT U.USERNAME,U.FIRST_NAME,U.LAST_NAME,U.COMPANY,U.COUNTRY,U.PHONE,U.WORK_TEL,U.EMAIL,SR.FIRST_NAME AS SALES_REP_NAME, U.ACCOUNT_CREATION_DATE AS START_DATE,PA.EXPIRY_DATE, US.LOGIN_DATE, US.LOGOUT_DATE FROM USR_ACCOUNT_DETAILS U " +
            "LEFT OUTER JOIN (SELECT * FROM USR_SESSIONS WHERE ";
    private static final String USER_SESSION_DETAILS_QUERY_2 = " ) US ON (US.USER_ID = U.USER_ID) ";
    private static final String USER_SESSION_DETAILS_QUERY_3 = " LEFT OUTER JOIN USR_PREMIUM_ACCOUNT_DETAILS PA ON (PA.USER_ID = U.USER_ID) " +
            "LEFT OUTER JOIN SALES_REPRESENTATIVE SR ON PA.SALES_REP_ID = SR.REP_ID  " +
            "WHERE PA.EXPIRY_DATE >= sysdate AND U.ACCOUNT_STATUS = " + UserDetailsConstants.UserStatus.USER_STATUS_ACTIVE;
    private static final String QUERY_AND = " AND ";
    private static final String QUERY_ORDER_BY = " ORDER BY ";
    private static final String QUERY_ASC = " ASC ";
    private static final String QUERY_GREATER_THAN_EQL = " >= ";
    private static final String QUERY_LESS_THAN_EQL = " <= ";
    private static final String QUERY_FIELD_LOGIN_DATE = " LOGIN_DATE ";
    private static final String QUERY_FIELD_LOGIN_DATE_TRUNC = " TRUNC(LOGIN_DATE) ";
    private static final String QUERY_FIELD_PRODUCT_ID_EQL = " PRODUCT_ID = ";
    private static final String QUERY_FIELD_ACCOUNT_TYPE_EQL = " U.ACCOUNT_TYPE = ";
    private static final String QUERY_TO_DATE = " TO_DATE('_DATE_', 'DD/MM/YYYY') ";
    private static final String DATE_CONSTANT = "_DATE_";
    private static final String CHECKBOX_SELECTED = "1";
    private static final int NUM_1 = 1;
    private static final int NUM_0 = 0;
    public static final int STATUS_ACTIVE = 1;

    public void setRedisCacheManager(ICacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    public boolean isUserNameExists(final String username) {
        LOG.debug(" isUserNameExists");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.isUserNameExists(username);
    }

    public boolean isEmailExists(final String email) {
        LOG.debug(" isEmailExists");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.isEmailExists(email);
    }

    public int createUser(Map<String, String> requestData) {
        LOG.debug(" Create user ");
        UserDetails user = getCreateUserObject(requestData);
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        return userDAO.createUser(user);
    }

    public int updateUser(Map<String, String> requestData) {
        LOG.debug(" Updating user details ");
        int status = UserDetailsConstants.UerDetails.STATUS_FAILED;
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        CustomUserDetails customUserDetails = userDAO.loadUserByUsername(requestData.get(IConstants.UserPreferences.USER_NAME));
        UserDetails userDetails = getUpdateUser(requestData, userDAO, customUserDetails);
        if (userDAO.updateUser(userDetails)) {
            ArchiveUserDetails archiveUserDetails = getArchiveUser(customUserDetails);
            if (!userDAO.archiveUser(archiveUserDetails)) {
                LOG.debug(" User Details archive failed! : Username - " + archiveUserDetails.getUsername());
            }
            status = UserDetailsConstants.UerDetails.STATUS_SUCCESS;
        }
        return status;
    }

    public int createPremiumAccount(Map<String, String> requestData) {
        LOG.debug(" Create Premium account ");
        UserPremiumAccount userPremiumAccount = gePremiumAccountObject(requestData);
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        return userDAO.createPremiumAccount(userPremiumAccount);
    }

    public boolean updatePremiumAccount(Map<String, String> requestData) {
        boolean status = false;

        LOG.debug(" update Premium account ");
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();

        long userId = Long.parseLong(requestData.get(IConstants.UserPreferences.USER_ID));
        PremiumAccountDetails currentPremiumAccount = userDAO.loadPremiumAccount(userId);

        UserPremiumAccount userPremiumAccount = getUpdatePremiumAccountObject(requestData, currentPremiumAccount);

        status = userDAO.updatePremiumAccount(userPremiumAccount);

        if (status) {
            ArchiveUserPremiumAccount archiveUserPremiumAccount = getArchivePremiumAccount(currentPremiumAccount);

            if (!userDAO.archivePremiumAccount(archiveUserPremiumAccount)) {
                LOG.debug(" Archive premium user failed! : Username - " + userPremiumAccount.getUserDetails().getUsername());
            }
        }

        return status;
    }

    public CustomUserDetails loadUserByUsername(final String username) {
        LOG.debug(" Load User By Username user");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.loadUserByUsername(username);
    }

    /**
     * load users map my user ids
     *
     * @param value Set<Long>
     * @return Map<Long,CustomUserDetails>
     */
    public Map<Long, CustomUserDetails> loadUsersByUserIdMap(Set<Long> value) {
        LOG.debug(" Load User By user Id list");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.loadUsersByUserIdMap(value);
    }

    public List<UserPremiumAccount> getAllPremiumUsers() {
        LOG.debug(" Load all premium Users");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.getAllPremiumUsers();
    }

    public List<UserPremiumAccount> searchPremiumUsers(Map<String, String> searchParameters) {
        LOG.debug(" search premium Users");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.searchPremiumUsers(searchParameters);
    }

    public CustomUserDetails loadUserByEmail(final String email) {
        LOG.debug(" Load User By email");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.loadUserByEmail(email);
    }


    public PremiumAccountDetails loadPremiumAccount(final long userId) {
        LOG.debug(" Load Premium Account");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.loadPremiumAccount(userId);
    }

    /**
     * Get User preferences from the db
     *
     * @param userId userId
     * @return User preferences object
     * @see com.dfn.infoplus.dataaccess.bo.impl.UserPreference
     */
    public UserPreferenceDetails loadUserPreference(final long userId) {
        LOG.debug(" load User Preference");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.loadUserPreference(userId);
    }

    /**
     * Change password
     *
     * @param username    username
     * @param newPassword new password
     * @return status
     */
    public boolean changePassword(final String username, final String newPassword) {
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        return userDAO.changePassword(username, newPassword);
    }

    /**
     * Update last login details
     *
     * @param username         username
     * @param lastLoginDetails last login details
     * @return status
     */
    public int updateLastLoginDetails(final String username, LastLoginDetails lastLoginDetails) {
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        return userDAO.updateLastLoginDetails(username, lastLoginDetails);
    }

    /**
     * Persist active session details
     *
     * @param sessionUser active session user details
     * @return status
     */
    public int updateActiveSessionDetails(final SessionUser sessionUser) {
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        updateSessionCacheForLogin(sessionUser);
        return userDAO.updateActiveSessionDetails(sessionUser, productId);
    }

    public int updateLastLogoutTime(Long userId, final SessionUser sessionUser, final String sessionId, Date lastLogoutDate) {
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        updateSessionCacheForLogout(sessionUser);
        return userDAO.updateLastLogoutTime(userId, sessionId, lastLogoutDate, productId);
    }

    /**
     * remove session from active sessions cache :: cache key: ACTIVE_SESSIONS
     * @param sessionUser SessionUser
     */
    private void updateSessionCacheForLogout(final SessionUser sessionUser) {
        String cacheKey = generateActiveUserSessionCacheKey();
        ActiveSessionDetails activeSessionDetails = (ActiveSessionDetails) redisCacheManager.get(cacheKey, ActiveSessionDetails.class);
        Map<String, List<UserSessionDetails>> userSessionDetailsMap = activeSessionDetails.getUserSessionDetailsMap();
        if (userSessionDetailsMap != null && !userSessionDetailsMap.isEmpty()) {
            if (userSessionDetailsMap.containsKey(sessionUser.getUserName())) {
                UserSessionDetails userSessionDetailsToRemove = null;
                List<UserSessionDetails> userSessionDetailsList = userSessionDetailsMap.get(sessionUser.getUserName());

                for (UserSessionDetails userSessionDetails : userSessionDetailsList) {
                    if (userSessionDetails.getWebSessionId().equals(sessionUser.getLastWebSession())) {
                        userSessionDetailsToRemove = userSessionDetails;
                    }
                }

                if (userSessionDetailsToRemove != null) {
                    userSessionDetailsList.remove(userSessionDetailsToRemove);
                    if(userSessionDetailsList.size() == 0){
                        userSessionDetailsMap.remove(sessionUser.getUserName());
                    }
                    redisCacheManager.put(cacheKey, activeSessionDetails);
                }
            }
        }

    }

    /**
     * add session to active sessions cache :: cache key: ACTIVE_SESSIONS
     * @param sessionUser SessionUser
     */
    private void updateSessionCacheForLogin(final SessionUser sessionUser) {
        UserSessionDetails userSessionDetails = new UserSessionDetails();
        setUserSessionDetails(userSessionDetails, sessionUser, productId);

        String cacheKey = generateActiveUserSessionCacheKey();
        ActiveSessionDetails activeSessionDetails = (ActiveSessionDetails) redisCacheManager.get(cacheKey, ActiveSessionDetails.class);
        Map<String, List<UserSessionDetails>> userSessionDetailsMap = activeSessionDetails.getUserSessionDetailsMap();
        List<UserSessionDetails> userSessionDetailsList = new ArrayList<>(1);
        userSessionDetailsList.add(userSessionDetails);
        userSessionDetailsMap.put(sessionUser.getUserName(), userSessionDetailsList);
        redisCacheManager.put(cacheKey, activeSessionDetails);
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
     * remove persist item for a particular userID~ProductID on session expiry or logout
     *
     * @param userId userId
     * @return status success/failure status
     */
    public int removeSessionDetails(final String userId) {
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        return userDAO.removeSessionDetails(userId);
    }

    /**
     * Update number of failed attempts
     *
     * @param username username
     * @param isReset  true if reset needed
     * @return true if success
     */
    public boolean updateFailedAttempts(final String username, boolean isReset) {
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        return userDAO.updateFailedAttempts(username, isReset);
    }

    /**
     * Authenticate price user from MIX system
     *
     * @param requestData map contains username and password
     * @return response map from mix
     */
    public Object authenticateMixUser(Map<String, String> requestData) {
        return getSocketResponse(this.requestGenerator.generateRequest(requestData, IConstants.RequestDataType.AUTHENTICATION), false);
    }

    public void setHibernateDaoFactory(DAOFactory hibernateDaoFactory) {
        this.hibernateDaoFactory = hibernateDaoFactory;
    }

    public void setJdbcDaoFactory(DAOFactory jdbcDaoFactory) {
        this.jdbcDaoFactory = jdbcDaoFactory;
    }

    /**
     * Get MIx user data
     *
     * @param requestData request data object
     * @return mix response
     */
    public Object getMixUserData(Map<String, String> requestData) {

        String cacheKey = this.generateCacheKey(requestData);
        Object mixUserData = null;

        if (mixUserData == null) {
            mixUserData = getSocketResponse(this.requestGenerator.generateRequest(requestData, IConstants.RequestDataType.USER_DATA), false);

            if(mixUserData != null) {
//                this.cacheManager.put(cacheKey, mixUserData, PRICE_USER_REQUEST_TTL);
            }
        }

        return mixUserData;
    }

    /**
     * Send Mix User[PRICE USER] LogOut request
     *
     * @param requestData request data object
     * @return mix response
     */
    public Object logOutPriceUser(Map<String, String> requestData) {
        return getSocketResponse(this.requestGenerator.generateRequest(requestData, IConstants.RequestDataType.PRICE_USER_LOGOUT), false);
    }

    /**
     * Get failed email notification list
     *
     * @return List<FailedEmailNotificationDTO>
     */
    public List<FailedEmailNotificationDTO> getFailedEmailNotificationDTOList() {
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.getAllFailedEmailNotifications();
    }

    public int createUserAccActivationStatus(long userId, String activationCode) {
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        return userDAO.createUserAccActivationStatus(getUserActObj(userId, activationCode));
    }

    public Object updateUserActivationStatus(long userId, String activationCode) {
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        return userDAO.updateUserAccActivationStatus(getUserActObj(userId, activationCode));
    }

    /**
     * Validate email confirmation link and activate user account
     *
     * @param userId  userId
     * @param actCode activation Code
     * @return account activation status
     * @see UserDetailsConstants.UserActivationStatus
     */
    public int validateEmailConfirmation(int userId, String actCode) {
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        return userDAO.validateEmailConfirmation(userId, actCode);
    }

    public int updateUserDefaultSettings(Map<String, String> requestData) {
        LOG.debug(" Updating user details ");
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        String userId = requestData.get(IConstants.UserPreferences.USER_ID);
        UserPreferenceDetails userPreference = userDAO.loadUserPreference(Long.parseLong(userId));
        UserPreference userPreferenceDTO = getUserPreferencesObj(requestData, userPreference);
        return userDAO.updateUserPreference(userPreferenceDTO);
    }

    /**
     * Get all the users than need to notify the user expiration date starting from provided days range
     *
     * @param accountTypeRelExpDays - notification start days by account type
     * @return notification required users which has expiration within provided days range
     */
    public List<UserAccountExpireNotificationDTO> getAccountExpireNotificationRequiredUsers(Map<Integer, String> accountTypeRelExpDays) {
        LOG.debug(" Load account expire notification Users");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        List<UserAccountExpireNotificationDTO> userAccountExpireNotificationDTOs = null;
        List<UserDetails> users = userDetailsDAO.getAccountExpireNotificationRequiredUsers(accountTypeRelExpDays);

        if (users != null && !users.isEmpty()) {
            userAccountExpireNotificationDTOs = new ArrayList<UserAccountExpireNotificationDTO>();
            String salesRepEmail = null;
            for (UserDetails user : users) {
                UserPremiumAccount premiumAccountDetails = user.getUserPremiumAccount();
                if (premiumAccountDetails != null) {
                    SalesRepresentative representative = premiumAccountDetails.getSalesRep();
                    if (representative != null) {
                        salesRepEmail = representative.getEmail();
                    }
                }
                userAccountExpireNotificationDTOs.add(new UserAccountExpireNotificationDTO(user.getUserID(), user.getUsername(), user.getFirstName()
                        , user.getLastName(), user.getAccountExpiryDate(), user.getEmail(), user.getTitle(), salesRepEmail, user.getAccountType()));
            }
        }

        return userAccountExpireNotificationDTOs;
    }

    /**
     * Retrieving sales reps and non expired Demo users
     *
     * @return list of Demo user accounts
     */
    public Map<SalesRepDetails, List<CustomUserDetails>> getNonExpiredDemoUsersWithSalesRep() {
        LOG.debug(" Load account expire notification Users");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        List<CustomUserDetails> users = userDetailsDAO.getNonExpiredDemoUsers();

        Map<SalesRepDetails, List<CustomUserDetails>> salesRepCustomersMap = new HashMap<SalesRepDetails, List<CustomUserDetails>>();

        if (users != null && !users.isEmpty()) {
            SalesRepDetails salesRepDetails = null;
            for (CustomUserDetails user : users) {
                PremiumAccountDetails premiumAccountDetails = user.getPremiumAccountDetails();
                if (premiumAccountDetails != null) {
                    salesRepDetails = premiumAccountDetails.getSalesRepDetails();

                    List<CustomUserDetails> salesRepCustomUserDetailsList = salesRepCustomersMap.get(salesRepDetails);
                    //first time, create new list and add to map
                    if (salesRepCustomUserDetailsList == null) {
                        salesRepCustomUserDetailsList = new ArrayList<CustomUserDetails>();
                    }

                    salesRepCustomUserDetailsList.add(user);
                    salesRepCustomersMap.put(salesRepDetails, salesRepCustomUserDetailsList);

                }
            }
        }

        return salesRepCustomersMap;
    }

    /**
     * Retrieving sales reps and non expired Demo users
     *
     * @return list of Demo user accounts
     */
    public List<CustomUserDetails> getNonExpiredDemoUsers() {
        LOG.debug(" Load account expire notification Users");
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.getNonExpiredDemoUsers();
    }

    public List<UserSessionDetailsDTO> getUserSessions(long userId, int productId) {
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        return userDetailsDAO.getUserSessions(userId, productId);
    }

    /**
     * get user session details with user data
     *
     * @param productId productId
     * @param fromDate  greater than
     * @param toDate    less than
     * @return
     */
    public List<Map<String, Object>> getUserSessionDetails(int productId, int accountType, String fromDate, String toDate) {
        UserDetailsDAO userDAO = jdbcDaoFactory.getUerDAO();
        StringBuilder query = new StringBuilder();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        query.append(USER_SESSION_DETAILS_QUERY_1);
        query.append(QUERY_FIELD_PRODUCT_ID_EQL).append(productId);

        if (fromDate != null) {
            query.append(QUERY_AND);
            query.append(QUERY_FIELD_LOGIN_DATE_TRUNC).append(QUERY_GREATER_THAN_EQL);
            query.append(QUERY_TO_DATE.replaceAll(DATE_CONSTANT, fromDate));
        }

        if (toDate != null) {
            query.append(QUERY_AND);
            query.append(QUERY_FIELD_LOGIN_DATE_TRUNC).append(QUERY_LESS_THAN_EQL);
            query.append(QUERY_TO_DATE.replaceAll(DATE_CONSTANT, toDate));
        }
        query.append(USER_SESSION_DETAILS_QUERY_2);
        query.append(USER_SESSION_DETAILS_QUERY_3);
        query.append(QUERY_AND);
        query.append(QUERY_FIELD_ACCOUNT_TYPE_EQL).append(accountType);
        query.append(QUERY_ORDER_BY).append(QUERY_FIELD_LOGIN_DATE).append(QUERY_ASC);

        requestDBDTO.setQuery(query.toString());
        return userDAO.getUserSessionDetails(requestDBDTO);
    }

    @Override
    protected Object getSocketResponse(String request, boolean isJsonResponse) {
        Object object = null;

        try {
            ResponseObj response = (ResponseObj) socketManager.getData(request);
            object = processResponse(response);
        } catch (SocketAccessException e) {
            //
        }

        return object;
    }

    @Override
    protected Object getSocketResponse(String request, int timeout, boolean isJsonResponse) {
        return null;
    }

    @Override
    protected Object processResponse(ResponseObj response) {
        Object returnObject = null;
        if (response != null) {

            returnObject = response.getDAT();
        }

        return returnObject;

    }


    private UserDetails getCreateUserObject(Map<String, String> requestData) {
        UserDetails userDTO = new UserDetails();
        userDTO.setUsername(requestData.get(IConstants.UserPreferences.USER_NAME));
        userDTO.setPassword(requestData.get(IConstants.UserPreferences.PASSWORD));
        userDTO.setUserRoles(requestData.get(IConstants.UserPreferences.USER_ROLES));
        userDTO.setTitle(requestData.get(IConstants.UserPreferences.TITLE));
        userDTO.setFirstName(requestData.get(IConstants.UserPreferences.FIRST_NAME));
        userDTO.setLastName(requestData.get(IConstants.UserPreferences.LAST_NAME));
        userDTO.setEmail(requestData.get(IConstants.UserPreferences.EMAIL));
        userDTO.setPhone(requestData.get(IConstants.UserPreferences.PHONE));
        userDTO.setWorkTel(requestData.get(IConstants.UserPreferences.WORK_TEL));
        userDTO.setCountry(requestData.get(IConstants.UserPreferences.COUNTRY));
        userDTO.setCompany(requestData.get(IConstants.UserPreferences.COMPANY));
        userDTO.setAccountStatus(Integer.parseInt(requestData.get(IConstants.UserPreferences.STATUS)));
        userDTO.setAccountExpiryDate(java.sql.Date.valueOf(requestData.get(IConstants.UserPreferences.EXP_DATE)));

        Timestamp createDateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
        userDTO.setAccountCreationDate(createDateTime);

        userDTO.setDesignation(requestData.get(IConstants.UserPreferences.DESIGNATION));
        userDTO.setCity(requestData.get(IConstants.UserPreferences.CITY));
        userDTO.setAddress1(requestData.get(IConstants.UserPreferences.ADDRESS_1));
        userDTO.setAddress2(requestData.get(IConstants.UserPreferences.ADDRESS_2));
        userDTO.setAccountType(Integer.parseInt(requestData.get(IConstants.UserPreferences.ACCOUNT_TYPE)));
        userDTO.setLastUpdatedBy(Integer.parseInt(requestData.get(IConstants.UserPreferences.UPDATED_BY)));

        return userDTO;
    }

    private UserPremiumAccount gePremiumAccountObject(Map<String, String> requestData) {
        UserPremiumAccount premiumAccountDetails = new UserPremiumAccount();
        premiumAccountDetails.getUserDetails().setUserID(Long.parseLong(requestData.get(IConstants.UserPreferences.USER_ID)));
        premiumAccountDetails.setPriceUserId(Long.parseLong(requestData.get(IConstants.UserPreferences.PRICE_LOGIN_ID)));
        premiumAccountDetails.setPriceProfileId(Long.parseLong(requestData.get(IConstants.UserPreferences.PRICE_PROFILE_ID)));
        premiumAccountDetails.setPriceUsername(requestData.get(IConstants.UserPreferences.PRICE_USER));
        premiumAccountDetails.setStatus(Integer.parseInt(requestData.get(IConstants.UserPreferences.STATUS)));
        premiumAccountDetails.setServices(requestData.get(IConstants.UserPreferences.SERVICES));
        premiumAccountDetails.getSalesRep().setSalesRepId(Integer.parseInt(requestData.get(IConstants.UserPreferences.SALES_REP)));
        premiumAccountDetails.setExpiryDate(java.sql.Date.valueOf(requestData.get(IConstants.UserPreferences.EXP_DATE)));
        premiumAccountDetails.setCreatedBy(Integer.parseInt(requestData.get(IConstants.UserPreferences.UPDATED_BY)));
        premiumAccountDetails.setLastUpdatedBy(premiumAccountDetails.getCreatedBy());
        premiumAccountDetails.setLastUpdatedOn(new Timestamp(System.currentTimeMillis()));
        premiumAccountDetails.setVersion(0);
        return premiumAccountDetails;
    }

    private UserPremiumAccount getUpdatePremiumAccountObject(Map<String, String> requestData, PremiumAccountDetails premiumAccount) {

        UserPremiumAccount premiumAccountDetails = new UserPremiumAccount();
        premiumAccountDetails.setAccountDetailsID(Long.parseLong(requestData.get(IConstants.UserPreferences.PREMIUM_ACCT_ID)));

        premiumAccountDetails.getUserDetails().setUserID(Long.parseLong(requestData.get(IConstants.UserPreferences.USER_ID)));
        premiumAccountDetails.setPriceUsername(premiumAccount.getPriceUsername());

        SalesRepresentative salesRepresentative = new SalesRepresentative();

        if (requestData.get(IConstants.UserPreferences.SALES_REP) != null) {
            salesRepresentative.setSalesRepId(Integer.parseInt(requestData.get(IConstants.UserPreferences.SALES_REP)));
        } else {
            salesRepresentative.setSalesRepId(premiumAccount.getSalesRepDetails().getSalesRepId());
        }

        premiumAccountDetails.setSalesRep(salesRepresentative);

        if (requestData.get(IConstants.UserPreferences.PRICE_LOGIN_ID) != null) {
            premiumAccountDetails.setPriceUserId(Long.parseLong(requestData.get(IConstants.UserPreferences.PRICE_LOGIN_ID)));
        } else {
            premiumAccountDetails.setPriceUserId(premiumAccount.getPriceUserId());
        }

        if (requestData.get(IConstants.UserPreferences.PRICE_PROFILE_ID) != null) {
            premiumAccountDetails.setPriceProfileId(Long.parseLong(requestData.get(IConstants.UserPreferences.PRICE_PROFILE_ID)));
        } else {
            premiumAccountDetails.setPriceProfileId(premiumAccount.getProfileId());
        }

        if (requestData.get(IConstants.UserPreferences.STATUS) != null) {
            premiumAccountDetails.setStatus(Integer.parseInt(requestData.get(IConstants.UserPreferences.STATUS)));
        } else {
            premiumAccountDetails.setStatus(premiumAccount.getStatus());
        }

        if (requestData.get(IConstants.UserPreferences.SERVICES) != null) {
            premiumAccountDetails.setServices(requestData.get(IConstants.UserPreferences.SERVICES));
        }

        if (requestData.get(IConstants.UserPreferences.EXP_DATE) != null) {
            premiumAccountDetails.setExpiryDate(java.sql.Date.valueOf(requestData.get(IConstants.UserPreferences.EXP_DATE)));
        } else if(premiumAccount.getExpDate() != null){
            premiumAccountDetails.setExpiryDate(new java.sql.Date(premiumAccount.getExpDate().getTime()));
        }

        premiumAccountDetails.setLastUpdatedBy(Integer.parseInt(requestData.get(IConstants.UserPreferences.UPDATED_BY)));
        premiumAccountDetails.setLastUpdatedOn(new Timestamp(System.currentTimeMillis()));
        premiumAccountDetails.setVersion(premiumAccount.getVersion() + 1);

        return premiumAccountDetails;
    }

    private ArchiveUserPremiumAccount getArchivePremiumAccount(PremiumAccountDetails premiumAccountDetails) {
        ArchiveUserPremiumAccount archiveUserPremiumAccount = new ArchiveUserPremiumAccount();

        archiveUserPremiumAccount.setUserID(premiumAccountDetails.getUserDetails().getUserId());
        archiveUserPremiumAccount.setAccountDetailsID(premiumAccountDetails.getAccountId());
        archiveUserPremiumAccount.setPriceUserId(premiumAccountDetails.getPriceUserId());
        archiveUserPremiumAccount.setPriceProfileId(premiumAccountDetails.getProfileId());
        archiveUserPremiumAccount.setPriceUsername(premiumAccountDetails.getPriceUsername());
        archiveUserPremiumAccount.setStatus(premiumAccountDetails.getStatus());
        archiveUserPremiumAccount.setSalesRepId(premiumAccountDetails.getSalesRepDetails().getSalesRepId());
        if(premiumAccountDetails.getExpDate() != null) {
            archiveUserPremiumAccount.setExpiryDate(new java.sql.Date(premiumAccountDetails.getExpDate().getTime()));
        }
        archiveUserPremiumAccount.setVersion(premiumAccountDetails.getVersion());

        return archiveUserPremiumAccount;
    }

    private UserDetails getUpdateUser(Map<String, String> requestData, UserDetailsDAO userDAO, CustomUserDetails currentUser) {
        UserDetails userDetails = userDAO.getUser(requestData.get(IConstants.UserPreferences.USER_NAME));

        //userDetails.setUsername(requestData.get(IConstants.UserPreferences.USER_NAME));

        if (requestData.get(IConstants.UserPreferences.USER_ID) != null) {
            userDetails.setUserID(Long.parseLong(requestData.get(IConstants.UserPreferences.USER_ID)));
        }

        if (requestData.get(IConstants.UserPreferences.COMPANY) != null) {
            userDetails.setCompany(requestData.get(IConstants.UserPreferences.COMPANY));
        } else if (currentUser != null) {
            userDetails.setCompany(currentUser.getCompany());
        }

        if (requestData.get(IConstants.UserPreferences.FIRST_NAME) != null) {
            userDetails.setFirstName(requestData.get(IConstants.UserPreferences.FIRST_NAME));
        } else if (currentUser != null) {
            userDetails.setFirstName(currentUser.getFirstName());
        }

        if (requestData.get(IConstants.UserPreferences.LAST_NAME) != null) {
            userDetails.setLastName(requestData.get(IConstants.UserPreferences.LAST_NAME));
        } else if (currentUser != null) {
            userDetails.setLastName(currentUser.getLastName());
        }

        if (requestData.get(IConstants.UserPreferences.PHONE) != null) {
            userDetails.setPhone(requestData.get(IConstants.UserPreferences.PHONE));
        } else if (currentUser != null) {
            userDetails.setPhone(currentUser.getPhone());
        }

        if (requestData.get(IConstants.UserPreferences.WORK_TEL) != null) {
            userDetails.setWorkTel(requestData.get(IConstants.UserPreferences.WORK_TEL));
        } else if (currentUser != null) {
            userDetails.setWorkTel(currentUser.getWorkTel());
        }

        if (requestData.get(IConstants.UserPreferences.COUNTRY) != null) {
            userDetails.setCountry(requestData.get(IConstants.UserPreferences.COUNTRY));
        } else if (currentUser != null) {
            userDetails.setCountry(currentUser.getCountry());
        }

        if (requestData.get(IConstants.UserPreferences.STATUS) != null) {
            userDetails.setAccountStatus(Integer.parseInt(requestData.get(IConstants.UserPreferences.STATUS)));
        } else if (currentUser != null) {
            userDetails.setAccountStatus(currentUser.getAccountStatus());
        }

        if (requestData.get(IConstants.UserPreferences.EMAIL) != null) {
            userDetails.setEmail(requestData.get(IConstants.UserPreferences.EMAIL));
        } else if (currentUser != null) {
            userDetails.setEmail(currentUser.getEmail());
        }

        if (requestData.get(IConstants.UserPreferences.EXP_DATE) != null) {
            userDetails.setAccountExpiryDate(java.sql.Date.valueOf(requestData.get(IConstants.UserPreferences.EXP_DATE)));
        } else if (currentUser != null) {
            userDetails.setAccountExpiryDate(currentUser.getAccountExpiryDate());
        }

        if (requestData.get(IConstants.UserPreferences.CREATE_DATE) != null) {
            userDetails.setAccountCreationDate(java.sql.Date.valueOf(requestData.get(IConstants.UserPreferences.CREATE_DATE)));
        } else if (currentUser != null) {
            userDetails.setAccountCreationDate(currentUser.getAccountCreationDate());
        }

        if (requestData.get(IConstants.UserPreferences.DESIGNATION) != null) {
            userDetails.setDesignation(requestData.get(IConstants.UserPreferences.DESIGNATION));
        } else if (currentUser != null) {
            userDetails.setDesignation(currentUser.getDesignation());
        }

        if (requestData.get(IConstants.UserPreferences.CITY) != null) {
            userDetails.setCity(requestData.get(IConstants.UserPreferences.CITY));
        } else if (currentUser != null) {
            userDetails.setCity(currentUser.getCity());
        }

        if (requestData.get(IConstants.UserPreferences.ADDRESS_1) != null) {
            userDetails.setAddress1(requestData.get(IConstants.UserPreferences.ADDRESS_1));
        } else if (currentUser != null) {
            userDetails.setAddress1(currentUser.getAddress1());
        }

        if (requestData.get(IConstants.UserPreferences.ADDRESS_2) != null) {
            userDetails.setAddress2(requestData.get(IConstants.UserPreferences.ADDRESS_2));
        } else if (currentUser != null) {
            userDetails.setAddress2(currentUser.getAddress2());
        }

        if (requestData.get(IConstants.UserPreferences.ACCOUNT_TYPE) != null) {
            userDetails.setAccountType(Integer.parseInt(requestData.get(IConstants.UserPreferences.ACCOUNT_TYPE)));
        } else if (currentUser != null) {
            userDetails.setAccountType(currentUser.getAccountType());
        }
        if (requestData.get(IConstants.UserPreferences.SALES_REP) != null) {
            if (userDetails.getUserPremiumAccount() != null) {
                userDetails.getUserPremiumAccount().getSalesRep().setSalesRepId(Integer.parseInt(requestData.get(IConstants.UserPreferences.SALES_REP)));
            }
        } else if (currentUser != null) {
            if (userDetails.getUserPremiumAccount() != null) {
                userDetails.getUserPremiumAccount().getSalesRep().setSalesRepId(currentUser.getPremiumAccountDetails().getSalesRepDetails().getSalesRepId());
            }
        }

        if (requestData.get(IConstants.UserPreferences.UPDATED_BY) != null) {
            userDetails.setLastUpdatedBy(Integer.parseInt(requestData.get(IConstants.UserPreferences.UPDATED_BY)));
        } else if (currentUser != null) {
            userDetails.setLastUpdatedBy(currentUser.getLastUpdatedBy());
        }

        if (requestData.get(IConstants.UserPreferences.USER_ROLES) != null) {
            userDetails.setUserRoles(requestData.get(IConstants.UserPreferences.USER_ROLES));
        } else if (currentUser != null) {
            userDetails.setUserRoles(currentUser.getUserRoles());
        }

        if (currentUser != null) {
            userDetails.setUserVersion(currentUser.getUserVersion());
        }

        return userDetails;
    }

    private ArchiveUserDetails getArchiveUser(CustomUserDetails customUserDetails) {
        ArchiveUserDetails archiveUserDetails = new ArchiveUserDetails();

        archiveUserDetails.setUserID(customUserDetails.getUserId());
        archiveUserDetails.setAccountCreationDate(customUserDetails.getAccountCreationDate());
        archiveUserDetails.setAccountExpiryDate(customUserDetails.getAccountExpiryDate());
        archiveUserDetails.setAccountStatus(customUserDetails.getAccountStatus());
        archiveUserDetails.setAddress1(customUserDetails.getAddress1());
        archiveUserDetails.setAddress2(customUserDetails.getAddress2());
        archiveUserDetails.setAccountType(customUserDetails.getAccountType());
        archiveUserDetails.setCity(customUserDetails.getCity());
        archiveUserDetails.setCompany(customUserDetails.getCompany());
        archiveUserDetails.setCountry(customUserDetails.getCountry());
        archiveUserDetails.setDesignation(customUserDetails.getDesignation());
        archiveUserDetails.setEmail(customUserDetails.getEmail());
        archiveUserDetails.setFirstName(customUserDetails.getFirstName());
        archiveUserDetails.setLastLoginCountry(customUserDetails.getLastLoginCountry());
        archiveUserDetails.setLastLoginDate(customUserDetails.getLastLoginDate());
        archiveUserDetails.setLastLoginIp(customUserDetails.getLastLoginIp());
        archiveUserDetails.setLastName(customUserDetails.getLastName());
        archiveUserDetails.setLastUpdatedBy(customUserDetails.getLastUpdatedBy());
        archiveUserDetails.setLastWebSession(customUserDetails.getLastWebSession());
        archiveUserDetails.setLoginStatus(customUserDetails.getLoginStatus());
        archiveUserDetails.setNoOfFailedAttempts(customUserDetails.getNoOfFailedAttempts());
        archiveUserDetails.setPassword(customUserDetails.getPassword());
        archiveUserDetails.setPasswordChangedDate(customUserDetails.getPasswordChangedDate());
        archiveUserDetails.setPasswordExpiryDate(customUserDetails.getPasswordExpiryDate());
        archiveUserDetails.setPhone(customUserDetails.getPhone());
        archiveUserDetails.setTitle(customUserDetails.getTitle());
        archiveUserDetails.setUsername(customUserDetails.getUsername());
        archiveUserDetails.setUserRoles(customUserDetails.getUserRoles());
        archiveUserDetails.setUserVersion(customUserDetails.getUserVersion());
        archiveUserDetails.setWorkTel(customUserDetails.getWorkTel());
        archiveUserDetails.setArchivedDate(new Date());

        return archiveUserDetails;
    }

    private UserPreference getUserPreferencesObj(Map<String, String> requestData, UserPreferenceDetails currentUserPreference) {

        String userId = requestData.get(IConstants.UserPreferences.USER_ID);

        //Default settings
        String defaultTimeZone = requestData.get(IConstants.UserPreferences.DEFAULT_TIME_ZONE);
        String defaultCurrency = requestData.get(IConstants.UserPreferences.DEFAULT_CURRENCY);
        String defaultMarket = requestData.get(IConstants.UserPreferences.DEFAULT_MARKET);
        String defaultCompany = requestData.get(IConstants.UserPreferences.DEFAULT_COMPANY);
        String defaultCountry = requestData.get(IConstants.UserPreferences.DEFAULT_COUNTRY);
        String defaultRegion = requestData.get(IConstants.UserPreferences.DEFAULT_REGION);
        String editionControlType = requestData.get(IConstants.UserPreferences.EDITION_CONTROL_TYPE);

        //financial settings
        String periodBasis = requestData.get(IConstants.UserPreferences.FINANCIAL_PERIOD_BASIS);
        String defaultFinancialTab = requestData.get(IConstants.UserPreferences.FINANCIAL_DEFAULT_TAB);
        String defaultFinancialView = requestData.get(IConstants.UserPreferences.FINANCIAL_DEFAULT_VIEW);
        String breakdown = requestData.get(IConstants.UserPreferences.FINANCIAL_BREAKDOWN);
        String futureEstimates = requestData.get(IConstants.UserPreferences.FUTURE_ESTIMATES);

        //News settings
        String preferredNewsReadingLanguages = requestData.get(IConstants.UserPreferences.NEWS_READ_LANGUAGES);
        String newsReadingLanguagePreference=requestData.get(IConstants.UserPreferences.NEWS_READ_LAN_PREFERENCE);
        String preferredNewsHeadLanguages = requestData.get(IConstants.UserPreferences.NEWS_HEADLINE_LANGUAGES);
        String saveNewsStreamingSettings = requestData.get(IConstants.UserPreferences.SAVE_NEWS_STREAM_SETTINGS);


        //File settings
        String preferredFilesLanguage = requestData.get(IConstants.UserPreferences.PREFER_FILES_LANGUAGE);

        // Top Main Panel Settings
        String marketsInTimeLine = requestData.get(IConstants.UserPreferences.MARKETS_IN_TIMELINE);
        String marketsInActivePanel = requestData.get(IConstants.UserPreferences.MARKETS_IN_ACTIVE_PANEL);
        String commodities = requestData.get(IConstants.UserPreferences.COMMODITIES);
        String currencies = requestData.get(IConstants.UserPreferences.CURRENCIES);

        //page settings
        String screenerSettings = requestData.get(IConstants.UserPreferences.SCREENER_SETTINGS);
        String watchListSettings = requestData.get(IConstants.UserPreferences.WATCH_LIST_SETTINGS);
        String newsStreamingSettings = requestData.get(IConstants.UserPreferences.NEWS_STREAM_SETTINGS);

           /*Release note*/
        String showReleaseNotes = requestData.get(IConstants.UserPreferences.RELEASE_NOTE_NOTIFY_AFTER_LOGIN);
        String emailReleaseNotes = requestData.get(IConstants.UserPreferences.RELEASE_NOTE_EMAIL_NOTIFICATION);
        String showLatestReleaseNote = requestData.get(IConstants.UserPreferences.RELEASE_NOTE_SHOW_LATEST);

        /*stock chart  settings*/
        String stockChartType = requestData.get(IConstants.UserPreferences.STOCK_CHART_TYPE);

        UserPreference userPreference = new UserPreference();
        userPreference.setUserID(Long.parseLong(userId));

        //Default settings
        if (defaultTimeZone != null) {
            userPreference.setDefaultTimeZone(defaultTimeZone);
        } else if (currentUserPreference != null) {
            userPreference.setDefaultTimeZone(currentUserPreference.getDefaultTimeZone());
        }
        if (defaultCurrency != null) {
            userPreference.setDefaultCurrency(defaultCurrency);
        } else if (currentUserPreference != null) {
            userPreference.setDefaultCurrency(currentUserPreference.getDefaultCurrency());
        }
        if (defaultMarket != null) {
            userPreference.setDefaultMarket(defaultMarket);
        } else if (currentUserPreference != null) {
            userPreference.setDefaultMarket(currentUserPreference.getDefaultMarket());
        }
        if (defaultMarket != null) {
            userPreference.setDefaultMarket(defaultMarket);
        } else if (currentUserPreference != null) {
            userPreference.setDefaultMarket(currentUserPreference.getDefaultMarket());
        }
        if (defaultCompany != null) {
            userPreference.setDefaultCompany(defaultCompany);
        } else if (currentUserPreference != null) {
            userPreference.setDefaultCompany(currentUserPreference.getDefaultCompany());
        }
        if (defaultCountry != null) {
            userPreference.setDefaultCountry(defaultCountry);
        } else if (currentUserPreference != null) {
            userPreference.setDefaultCountry(currentUserPreference.getDefaultCountry());
        }
        if (defaultRegion != null) {
            userPreference.setDefaultRegion(defaultRegion);
        } else if (currentUserPreference != null) {
            userPreference.setDefaultRegion(currentUserPreference.getDefaultRegion());
        }
        if (editionControlType != null) {
            userPreference.setEditionControlType(editionControlType);
        } else if (currentUserPreference != null) {
            userPreference.setEditionControlType(currentUserPreference.getEditionControlType());
        }

        //News settings
        if (preferredNewsReadingLanguages != null) {
            userPreference.setPreferredNewsReadingLanguages(preferredNewsReadingLanguages.trim().toUpperCase());
        } else if (currentUserPreference != null) {
            userPreference.setPreferredNewsReadingLanguages(currentUserPreference.getPreferredNewsReadingLanguages());
        }
        if(newsReadingLanguagePreference != null){
            userPreference.setNewsReadingLanguagePreference(newsReadingLanguagePreference.toUpperCase());
        } else if(currentUserPreference != null){
            userPreference.setNewsReadingLanguagePreference(currentUserPreference.getPreferredNewsReadingLanguages());
        }
        if (preferredNewsHeadLanguages != null) {
            userPreference.setPreferredNewsHeadLanguages(preferredNewsHeadLanguages.trim().toUpperCase());
        } else if (currentUserPreference != null) {
            userPreference.setPreferredNewsHeadLanguages(currentUserPreference.getPreferredNewsHeadLanguages());
        }
        if (saveNewsStreamingSettings != null) {
            userPreference.setSaveNewsStreamingSettings(Boolean.parseBoolean(saveNewsStreamingSettings));
        } else if (currentUserPreference != null) {
            userPreference.setSaveNewsStreamingSettings(currentUserPreference.isSaveNewsStreamingSettings());
        }

        //File settings

        if (preferredFilesLanguage != null) {
            userPreference.setPreferredFilesLanguage(preferredFilesLanguage.trim().toUpperCase());
        } else if (currentUserPreference != null) {
            userPreference.setPreferredFilesLanguage(currentUserPreference.getPreferredFilesLanguage());
        }

        // Top Main Panel Settings
        if (marketsInTimeLine != null) {
            userPreference.setMarketsInTimeLine(marketsInTimeLine);
        } else if (currentUserPreference != null) {
            userPreference.setMarketsInTimeLine(currentUserPreference.getMarketsInTimeLine());
        }
        if (marketsInActivePanel != null) {
            userPreference.setMarketsInActivePanel(marketsInActivePanel);
        } else if (currentUserPreference != null) {
            userPreference.setMarketsInActivePanel(currentUserPreference.getMarketsInActivePanel());
        }
        if (commodities != null) {
            userPreference.setCommodities(commodities);
        } else if (currentUserPreference != null) {
            userPreference.setCommodities(currentUserPreference.getCommodities());
        }
        if (currencies != null) {
            userPreference.setCurrencies(currencies);
        } else if (currentUserPreference != null) {
            userPreference.setCurrencies(currentUserPreference.getCurrencies());
        }

        //page settings
        if (screenerSettings != null) {
            userPreference.setScreenerSettings(screenerSettings);
        } else if (currentUserPreference != null) {
            userPreference.setScreenerSettings(currentUserPreference.getScreenerSettings());
        }

        if (watchListSettings != null) {
            userPreference.setWatchListSettings(watchListSettings);
        } else if (currentUserPreference != null) {
            userPreference.setWatchListSettings(currentUserPreference.getWatchListSettings());
        }

        if (newsStreamingSettings != null) {
            userPreference.setNewsStreamSettings(newsStreamingSettings);
        } else if (currentUserPreference != null) {
            userPreference.setNewsStreamSettings(currentUserPreference.getNewsStreamSettings());
        }

        /*Release Note Section*/

        if (showReleaseNotes != null) {
            if (showReleaseNotes.equalsIgnoreCase(CHECKBOX_SELECTED)) {
                userPreference.setShowReleaseNotes(NUM_1);
            }
        } else {
            userPreference.setShowReleaseNotes(NUM_0);
        }

        if (emailReleaseNotes != null) {
            if (emailReleaseNotes.equalsIgnoreCase(CHECKBOX_SELECTED)) {
                userPreference.setEmailReleaseNotes(NUM_1);
            }
        } else {
            userPreference.setEmailReleaseNotes(NUM_0);
        }

        if (showLatestReleaseNote != null && StringUtils.isNumeric(showLatestReleaseNote)) {
            userPreference.setShowLatestReleaseNote(Integer.parseInt(showLatestReleaseNote));
        } else if (currentUserPreference != null) {
            userPreference.setShowLatestReleaseNote(currentUserPreference.getShowLatestReleaseNote());
        }

        /**
         * financial settings
         */
        if (!CommonUtils.isNullOrEmptyString(periodBasis)) {
            userPreference.setPeriodBasis(periodBasis);
        } else if (currentUserPreference != null) {
            userPreference.setPeriodBasis(currentUserPreference.getPeriodBasis());
        }

        if (!CommonUtils.isNullOrEmptyString(defaultFinancialTab)) {
            userPreference.setDefaultFinancialTab(defaultFinancialTab);
        } else if (currentUserPreference != null) {
            userPreference.setDefaultFinancialTab(currentUserPreference.getDefaultFinancialTab());
        }

        if (!CommonUtils.isNullOrEmptyString(defaultFinancialView)) {
            userPreference.setDefaultFinancialView(defaultFinancialView);
        } else if (currentUserPreference != null) {
            userPreference.setDefaultFinancialView(currentUserPreference.getDefaultFinancialView());
        }

        if (breakdown != null && StringUtils.isNumeric(breakdown)) {
            userPreference.setBreakdown(Integer.parseInt(breakdown));
        } else if (currentUserPreference != null) {
            userPreference.setBreakdown(currentUserPreference.getBreakdown());
        }

        if (futureEstimates != null) {
            if (futureEstimates.equalsIgnoreCase(CHECKBOX_SELECTED)) {
                userPreference.setFutureEstimates(NUM_1);
            }
        } else {
            userPreference.setFutureEstimates(NUM_0);
        }

        /**
         * stock chart settings
         */
        if (!CommonUtils.isNullOrEmptyString(stockChartType)) {
                userPreference.setStockChartType(stockChartType);
        } else {
            if (currentUserPreference != null) {
                userPreference.setStockChartType(currentUserPreference.getStockChartType());
            }
        }

        return userPreference;
    }

    public UserActivationData getUserActObj(long userId, String activationCode) {

        UserActivationData userActData = new UserActivationData();
        userActData.setUserID(userId);
        userActData.setActCode(activationCode);

        //Get current time
        Calendar calendar = Calendar.getInstance();
        userActData.setActCodeSentTime(calendar.getTime());

        //Get the date after 24 hours
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        Date expiryDate = calendar.getTime();

        userActData.setActCodeExpiryTime(expiryDate);
        userActData.setStatus(UserDetailsConstants.UserActivationStatus.USER_ACTIVATION_PENDING);
        return userActData;
    }

    @Override
    public int updateData(DataAccessRequestDTO requestDTO) {
        return 0;
    }

    @Override
    public void initUpdateData() {
        //
    }


    @Override
    public Object getMemoryData(Map<String, String> requestData) {
        return null;
    }

    @Override
    protected String generateCacheKey(Map<String, String> requestData) {
        return requestData.get(IConstants.CustomDataField.PRICE_USERNAME);
    }

    @Override
    public Object getData(Map<String, String> requestData, boolean isDirectData, boolean isJasonResponse) {
        return null;
    }


    public void setRequestGenerator(IRequestGenerator requestGenerator) {
        this.requestGenerator = requestGenerator;
    }

    /**
     * get cache key for sessions
     * @return cache key
     */
    private String generateActiveUserSessionCacheKey() {
        return CacheKeyConstant.CACHE_KEY_ACTIVE_SESSIONS;
    }

    /**
     * get all active sessions to cache
     * @return Map<String, List<UserSessionDetails>>
     */
    public Map<String, List<UserSessionDetails>> getActiveUserSessions() {
        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        String cacheKey = generateActiveUserSessionCacheKey();
        List<Object> userSessionObjectList = userDetailsDAO.getAllActiveUserSessions();
        Map<String, List<UserSessionDetails>> userSessionDetailsMap = new HashMap<>(userSessionObjectList.size());

        if (userSessionObjectList != null && !userSessionObjectList.isEmpty()) {
            for (Object userSession : userSessionObjectList) {
                UserSessionDetails userSessionDetails = (UserSessionDetails) userSession;
                if (userSessionDetailsMap.containsKey(userSessionDetails.getUsername())) {
                    userSessionDetailsMap.get(userSessionDetails.getUsername()).add(userSessionDetails);
                } else {
                    List<UserSessionDetails> userSessionDetailsList = new ArrayList<>(1);
                    userSessionDetailsList.add(userSessionDetails);
                    userSessionDetailsMap.put(userSessionDetails.getUsername(), userSessionDetailsList);
                }
            }
        }

        if (!userSessionDetailsMap.isEmpty()) {
            redisCacheManager.put(cacheKey, new ActiveSessionDetails(userSessionDetailsMap));
        }
        return userSessionDetailsMap;
    }

    /**
     * get session details for username
     * @param username String
     * @return UserSessionDetails list
     */
    public List<UserSessionDetails> getUserSessions(String username) {
        String cacheKey = generateActiveUserSessionCacheKey();
        ActiveSessionDetails activeSessionDetails = (ActiveSessionDetails) redisCacheManager.get(cacheKey, ActiveSessionDetails.class);
        Map<String, List<UserSessionDetails>> userSessionDetailsMap = activeSessionDetails.getUserSessionDetailsMap();
        List<UserSessionDetails> userSessionDetailsForUsername = null;

        if (userSessionDetailsMap != null && !userSessionDetailsMap.isEmpty()) {
            if (userSessionDetailsMap.containsKey(username)) {
                userSessionDetailsForUsername = userSessionDetailsMap.get(username);
            }
        }

        return userSessionDetailsForUsername;
    }

    /**
     * Load all active user sessions
     *
     * @return Active session users list
     */
    public List<Map<String, String>> loadActiveUserSessions() {
        List<Map<String, String>> userList = null;
        Map<String, String> sessionDetails;
        UserSessionDetails userSession;
        long durationInMin;

        UserDetailsDAO userDetailsDAO = this.hibernateDaoFactory.getUerDAO();
        List<Object> users = userDetailsDAO.getAllActiveUserSessions();

        if (users != null && !users.isEmpty()) {
            userList = new ArrayList<Map<String, String>>(users.size());
            for (Object user : users) {
                userSession = (UserSessionDetails) user;

                sessionDetails = userSession.generateUserSessionDetails();
                durationInMin = (DataAccessUtils.getUTCDateTimeAsDate().getTime() - userSession.getLastLoginDate().getTime()) / IN_MINUTES;
                sessionDetails.put(SESSION_DURATION, String.valueOf(durationInMin));

                userList.add(sessionDetails);

            }
        }
        return userList;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    //region EMAIL-AUDIT

    /**
     * populate email notification object
     *
     * @param status           notification status 0 - fail
     * @param emailId          email id
     * @param notificationTime original notification time
     * @param notificationData notification data for email
     * @return hibernate object
     */
    @Deprecated
    private static EmailNotification getEmailRecordObj(int status, int emailId, long notificationTime, NotificationData notificationData, long userId, int emailType) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setEmailId(emailId);
        emailNotification.setStatus(status);
        emailNotification.setUserId(userId);
        emailNotification.setEmailType(emailType);
        emailNotification.setFromEmail(notificationData.getNotificationData().get(IConstants.CustomDataField.EMAIL_FROM));
        emailNotification.setFromName(notificationData.getNotificationData().get(IConstants.CustomDataField.EMAIL_FROM_NAME));
        emailNotification.setTo(notificationData.getNotificationData().get(IConstants.CustomDataField.EMAIL_RECEPIENT_LIST));
        emailNotification.setBcc(notificationData.getNotificationData().get(IConstants.CustomDataField.EMAIL_BCC_LIST));
        emailNotification.setCc(notificationData.getNotificationData().get(IConstants.CustomDataField.EMAIL_CC_LIST));
        emailNotification.setTitle(notificationData.getNotificationData().get(IConstants.CustomDataField.EMAIL_TITLE));
        emailNotification.setContent(notificationData.getNotificationData().get(IConstants.CustomDataField.EMAIL_CONTENT));
        emailNotification.setNotificationTime(new Timestamp(notificationTime));
        emailNotification.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
        return emailNotification;
    }

    /**
     * populate email notification object - New implementation

     * @return hibernate object
     */
    public EmailDTO getEmailObj(long userId, IConstants.EmailType emailType,
                                        String from, String fromName, String to, String bcc, String cc, String subject, String content, String attachmentLocation, String attachmentName, boolean isAttachment) {
        EmailDTO emailMetaData = new EmailDTO();
        emailMetaData.setSource((int) userId);
        emailMetaData.setType(emailType);
        emailMetaData.setFrom(from);
        emailMetaData.setFromName(fromName);
        emailMetaData.setTo(to);
        emailMetaData.setBcc(bcc);
        emailMetaData.setCc(cc);
        emailMetaData.setSubject(subject);
        emailMetaData.setBody(content);
        emailMetaData.setAttachmentLocation(attachmentLocation);
        emailMetaData.setAttachment(isAttachment);
        emailMetaData.setAttachmentName(attachmentName);
        return emailMetaData;
    }

    /**
     * get email summary data
     *
     * @return Map<String,Map<String,String>>
     */
    public Map<String, Map<String, String>> getEmailNotificationSummary() {
        UserDetailsDAO userDAO = jdbcDaoFactory.getUerDAO();
        RequestDBDTO requestDBDTO = new RequestDBDTO();
        requestDBDTO.setQuery(EMAIL_AUDIT_SUMMARY_QUERY);
        return userDAO.getEmailNotificationSummary(requestDBDTO);
    }

    /**
     * update DB email notification only if successfully send
     *
     * @param status  send/failed(1/0)
     * @param emailId email id
     * @return UserDetailsConstants.DBUpdateStatus
     */
    public int updateEmailRecord(int status, int emailId) {
        int dbUpdateStatus;
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        dbUpdateStatus = userDAO.updateEmailNotification(emailId, status);
        return dbUpdateStatus;
    }

    /**
     * load all failed email notifications
     *
     * @param retryCount max retries
     * @return failed email notifications
     */
    public List<FailedEmailNotificationDTO> getFailedEmailUserNotifications(int retryCount) {
        return this.hibernateDaoFactory.getUerDAO().getFailedEmailNotifications(retryCount);
    }


    public Map<Long , MiniUserDetails> searchUsers(Map<String, String> searchParameters , int numberOfRecords){
         return this.jdbcDaoFactory.getUerDAO().searchUsers(searchParameters , numberOfRecords);
    }

    public int updateUserPreferences(List<Long> userIDs, Map<String, String> updateParameters) {
        return this.jdbcDaoFactory.getUerDAO().updateUserPreferences(userIDs , updateParameters);
    }

    /**
     * archive emails
     */
    public void archiveEmails() {
        this.hibernateDaoFactory.getUerDAO().archiveEmailNotifications();
    }

    public int updateAuthenticationRequest(Map<String, String> requestData) {
        LOG.debug(" Authentication API request save ");
        AuthenticationAPIResponse authenticationAPIResponse = getAuthenticationAPIResponseObject(requestData);
        UserDetailsDAO userDAO = this.hibernateDaoFactory.getUerDAO();
        return userDAO.saveAuthResponse(authenticationAPIResponse);
    }

    private AuthenticationAPIResponse getAuthenticationAPIResponseObject(Map<String, String> requestData) {
        AuthenticationAPIResponse authenticationAPIResponseDTO = new AuthenticationAPIResponse();
        authenticationAPIResponseDTO.setIP(requestData.get("IP"));
        authenticationAPIResponseDTO.setResponse(requestData.get("response"));
        Timestamp requestDateTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
        authenticationAPIResponseDTO.setTimeStamp(requestDateTime);
        authenticationAPIResponseDTO.setStatus(requestData.get("status"));
        authenticationAPIResponseDTO.setUserID(requestData.get("userID"));
        return authenticationAPIResponseDTO;
    }
    //endregion
}
