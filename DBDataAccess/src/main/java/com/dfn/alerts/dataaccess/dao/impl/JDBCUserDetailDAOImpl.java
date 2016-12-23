package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.beans.LastLoginDetails;
import com.dfn.alerts.beans.RequestDBDTO;
import com.dfn.alerts.beans.user.*;
import com.dfn.alerts.dataaccess.orm.impl.*;
import com.dfn.alerts.dataaccess.dao.UserDetailsDAO;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by udaras on 3/19/2014.
 */
public class JDBCUserDetailDAOImpl implements UserDetailsDAO {

    private DataSource driverManagerDataSource = null;
    private static final Logger LOG = LogManager.getLogger(UserDetailsDAOImpl.class);

    @Override
    public Map<String,Map<String,String>> getEmailNotificationSummary(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<String,Map<String,String>> emailSummaryData = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));

            rs = preparedStatement.executeQuery();
            emailSummaryData = DBUtils.setEmailSummaryData(rs);

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }
        return emailSummaryData;
    }

    @Override
    public boolean isUserNameExists(String username) {
        return false;
    }

    @Override
    public boolean isEmailExists(String email) {
        return false;
    }

    @Override
    public int createUser(UserDetails userDTO) {
        return 0;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }

    @Override
    public boolean updateUser(UserDetails userDTO) {
        return false;
    }

    public boolean archiveUser(ArchiveUserDetails archiveUser) {
        return false;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        return null;
    }

    @Override
    public UserDetails getUser(String username) {
        return null;
    }

    @Override
    public UserDetails getUserByEmail(String email) {
        return null;
    }

    @Override
    public CustomUserDetails loadUserByEmail(String email) {
        return null;
    }

    @Override
    public CustomUserDetails loadUserByUserId(long userID) {
        return null;
    }

    @Override
    public Map<Long, CustomUserDetails> loadUsersByUserIdMap(Set<Long> value) {
        return null;
    }

    @Override
    public int createPremiumAccount(UserPremiumAccount userPremiumAccount) {
        return 0;
    }

    @Override
    public boolean updatePremiumAccount(UserPremiumAccount userPremiumAccount) {
        return false;
    }

    @Override
    public PremiumAccountDetails loadPremiumAccount(long userId) {
        return null;
    }

    public boolean archivePremiumAccount(ArchiveUserPremiumAccount archiveUserPremiumAccount) {
        return false;
    }

    @Override
    public UserPreferenceDetails loadUserPreference(long userId) {
        return null;
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        return false;
    }

    @Override
    public int updateLastLoginDetails(String username, LastLoginDetails lastLoginDetails) {
        return 0;
    }

    @Override
    public boolean updateFailedAttempts(String username, boolean isReset) {
        return false;
    }

    @Override
    public int createUserAccActivationStatus(UserActivationData userActivationData) {
        return 0;
    }

    @Override
    public int updateUserAccActivationStatus(UserActivationData userActivationData) {
        return 0;
    }

    @Override
    public int updateActiveSessionDetails(SessionUser sessionUser, int productId) {
        return 0;
    }

    @Override
    public int removeSessionDetails(String userId) {
        return 0;
    }

    @Override
    public int removeAllSessionData(int productId) {
        return 0;
    }

    @Override
    public int validateEmailConfirmation(int userId, String actCode) {
        return 0;
    }

    @Override
    public boolean doLogout(String username) {
        return false;
    }

    @Override
    public int updateUserPreference(UserPreference userPreferenceDTO) {
        return 0;
    }

    @Override
    public List<UserPremiumAccount> getAllPremiumUsers() {
        return null;
    }

    @Override
    public List<UserDetails> getAccountExpireNotificationRequiredUsers(int numberOfDays) {
        return null;
    }

    @Override
    public List<UserDetails> getAccountExpireNotificationRequiredUsers(Map<Integer, String> accountTypeRelExpDays) {
        return null;
    }

    @Override
    public List<UserPremiumAccount> searchPremiumUsers(Map<String, String> searchParameters) {
        return null;
    }

    @Override
    public List<Object> getAllActiveUserSessions() {
        return null;
    }

    @Override
    public List<UserSessionDetailsDTO> getUserSessions(long userId, int productId) {
        return null;
    }

    @Override
    public List<CustomUserDetails> getNonExpiredDemoUsers() {
        return null;
    }

    @Override
    public int updateLastLogoutTime(Long userId, String sessionId, Date lastLogoutDate, int productId) {
        return 0;
    }

    @Override
    public int insertEmailNotification(EmailNotification emailNotification) {
        return 0;
    }

    @Override
    public int updateEmailNotification(int emailId, int status) {
        return 0;
    }

    @Override
    public List<FailedEmailNotificationDTO> getFailedEmailNotifications(int retryCount) {
        return null;
    }

    @Override
    public List<FailedEmailNotificationDTO> getAllFailedEmailNotifications() {
        return null;
    }

    @Override
    public boolean archiveEmailNotifications() {
        return false;
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {

    }

    @Override
    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
           this.driverManagerDataSource = driverManagerDataSource;
    }

    @Override
    public int saveAuthResponse(AuthenticationAPIResponse authenticationAPIResponse) {
        return 0;
    }

    @Override
    public int saveFeedbackCase(ContactCase contactCase) {
        return 0;
    }

    @Override
    public Map<Long , MiniUserDetails> searchUsers(Map<String, String> searchParameters , int numberOfRecords) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT UAD.USER_ID , ACCOUNT_EXPIRE_DATE, ACCOUNT_STATUS , ACCOUNT_TYPE , EMAIL ,IS_EMAIL_SENT , EMAIL_RELEASE_NOTES , FIRST_NAME, LAST_NAME, TITLE ");
        stringBuilder.append(" FROM USR_ACCOUNT_DETAILS UAD ");
        stringBuilder.append(" LEFT JOIN USR_PREFERENCES UP ON UAD.USER_ID = UP.USER_ID");



        if(searchParameters != null && !searchParameters.isEmpty()){
            stringBuilder.append(" WHERE ");
            int index = 1;
            int size = searchParameters.keySet().size();
            for(String key : searchParameters.keySet()){
                stringBuilder.append( key );
                stringBuilder.append( " = " );
                stringBuilder.append( searchParameters.get(key) );

                if(hasNext(size,index)){
                    stringBuilder.append( " AND " );
                }

                index ++ ;

            }

        }

        String sql =  stringBuilder.toString();

        if(numberOfRecords > 0){
            sql = addRowNum(sql , numberOfRecords);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Map<Long , MiniUserDetails> userDetailsMap = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql); //TODO change to statement

            rs = preparedStatement.executeQuery();
            userDetailsMap = DBUtils.setMiniUserDetails(rs);

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }

        return userDetailsMap;
    }

    private String addRowNum(String sql , int numberOfRecords){
        return  "SELECT * FROM (" + sql + ") WHERE ROWNUM <=" + numberOfRecords;
    }


    private boolean hasNext(int size , int currentIndex){


        return size > currentIndex;

    }


    @Override
    public int updateUserPreferences(List<Long> userIDs ,Map<String, String> updateParameters){

        if(userIDs == null || userIDs.isEmpty() || updateParameters == null || updateParameters.isEmpty()){
            return -1;

        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE USR_PREFERENCES ");

        stringBuilder.append(" SET ");

        int index = 1 ;
        int size = updateParameters.keySet().size();
        for(String key : updateParameters.keySet()){
            stringBuilder.append( key );
            stringBuilder.append( " = " );
            stringBuilder.append(updateParameters.get(key) );


            if(hasNext(size , index)){
                stringBuilder.append( "," );
            }

            index ++ ;

        }

        stringBuilder.append(" WHERE");
        stringBuilder.append(" USER_ID IN");
        stringBuilder.append(" (");

        int userIdIndex = 1;
        int userIdSize  = userIDs.size();

        for(Long key : userIDs){
            stringBuilder.append( "'" );
            stringBuilder.append( key );
            stringBuilder.append( "'" );
            if(hasNext(userIdSize , userIdIndex)){
                stringBuilder.append( "," );
            }

            userIdIndex ++;
        }

        stringBuilder.append(" )");


        int resultCount = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(stringBuilder.toString());

            resultCount = preparedStatement.executeUpdate();

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, null);
        }

        return resultCount;
    }

    /**
     * get user session details with user details
     * @param requestDBDTO requestDBDTO
     */
    public List<Map<String,Object>> getUserSessionDetails(RequestDBDTO requestDBDTO) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Map<String,Object>> userSessionDetails = null;

        try {
            connection = driverManagerDataSource.getConnection();
            preparedStatement = connection.prepareStatement(requestDBDTO.getQuery().replace("\\", ""));

            rs = preparedStatement.executeQuery();
            userSessionDetails = DBUtils.setUserSessionDetails(rs);

        } catch (SQLException sqlE) {
            LOG.error(sqlE.getMessage(), sqlE);
        } finally {
            DBUtils.finalizeDBResources(connection, preparedStatement, rs);
        }

        return userSessionDetails;
    }
}
