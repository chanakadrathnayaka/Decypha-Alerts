package com.dfn.alerts.constants;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 6/19/13
 * Time: 11:45 AM
 */
public class UserDetailsConstants {

    public enum AdminRoles {
        ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN"), ROLE_USER_ADMIN("ROLE_USER_ADMIN"), ROLE_SYSTEM_ADMIN("ROLE_SYSTEM_ADMIN");

        private String defaultValue;

        private AdminRoles(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public static final class UserStatus {

        private UserStatus() {
        }

        public static final int USER_STATUS_WAITING_FOR_CONFIRMATION = 1;
        public static final int USER_STATUS_ACTIVE_FIRST_TIME = 2;
        public static final int USER_STATUS_ACTIVE = 3;
        public static final int USER_STATUS_LOCKED = 4;
        public static final int USER_STATUS_EXPIRED = 5;
        public static final int USER_STATUS_PASSWORD_EXPIRED = 6;
        public static final int USER_STATUS_PENDING_APPROVAL = 7;
        public static final int USER_STATUS_SUBSCRIPTION_FAILURE = 8;
        public static final int USER_STATUS_PREMIUM_ACCOUNT_FAILURE = 9;


    }

    public static final class UerDetails {
        public static final int STATUS_FAILED = -1;
        public static final int STATUS_ZOHO_FAILED = -2;
        public static final int STATUS_SUCCESS = 0;
        public static final int STATUS_ZOHO_SUCCESS = 2;
        public static final int STATUS_USER_EXIST = 3;
        public static final int STATUS_USER_NOT_EXIST = 4;
        public static final int STATUS_EMAIL_EXIST = 5;
        public static final int STATUS_USER_VALIDATION_FAILED = 10;
        public static final int STATUS_USER_VALIDATION_SUCCESS = 11;
        public static final int STATUS_USER_VALIDATION_PASS_MISS_MATCH = 12;
        public static final int STATUS_USER_CAPTCHA_FAILED = 13;
        public static final int STATUS_USER_SUBSCRIPTION_FAILED = 14;
        public static final int STATUS_PREMIUM_USER_CREATION_FAILED = 15;
        public static final int STATUS_RESUBSCRIBE_USER = 16;
        public static final int STATUS_INVALID_USERNAME_LENGTH = 17;
        public static final int STATUS_INVALID_PROMO_CODE = 18;
        public static final int STATUS_USED_PROMO_CODE = 19;
        public static final int STATUS_INVALID_EMAIL_DOMAIN = 20;
        public static final int STATUS_CAMP_IS_NOT_ACTIVE = 21;
        public static final int STATUS_INVALID_USERNAME_FORMAT = 22;
    }

    public static final class ChangePassword {

        private ChangePassword() {
        }

        public static final int CP_INCORRECT_OLD_PASSWORD = -1;
        public static final int CP_SUCCESS = 1;
        public static final int CP_FAILED = 0;
    }

    public static final class UpdateData {

        private UpdateData() {
        }

        public static final int UPDATE_DATA_FAILED = -1;
        public static final int UPDATE_DATA_SUCCESS = 1;
    }

    public static final class UserActivationStatus {
        private UserActivationStatus() {
        }

        public static final int USER_ACTIVATION_PENDING = 0;
        public static final int USER_ACTIVATION_ACTIVATED = 1;
        public static final int USER_ACTIVATION_RETRY = 2;
        public static final int USER_ACTIVATION_EXPIRED = 3;
        public static final int USER_ACTIVATION_WRONG_PARAMS = 4;
        public static final int USER_ACTIVATION_UNSUCCESSFUL = 5;
        public static final int USER_ACTIVATION_ALREADY_ACTIVATED = 6;
        public static final int USER_ACTIVATION_RESEND = 7;
        public static final int USER_ACTIVATION_RESEND_USER_NOT_EXIST = 8;
    }

    public static final class DBUpdateStatus {
        public static final int STATUS_FAILED = 0;
        public static final int STATUS_SUCCESS = 1;
    }

    public static final class AjaxStatus {
        public static final int STATUS_FAILED           = 0;
        public static final int STATUS_SUCCESS          = 1;
        public static final int STATUS_INVALID_INPUTS   = 3;
    }

    public enum LoginStatus {
        STATUS_FAILED(0),
        STATUS_SUCCESS(1),
        STATUS_USER_EXPIRED(2),
        STATUS_INVALID_INPUTS(3),
        STATUS_USER_NOT_ACTIVATED(4),
        STATUS_USER_PENDING_APPROVAL(5),
        STATUS_USER_SESSION_EXPIRED(6),
        STATUS_USER_SESSION_ACTIVE(7);

        private int defaultValue;

        private LoginStatus(int defValue) {
            this.defaultValue = defValue;
        }

        public int getDefaultValue() {
            return defaultValue;
        }
    }

    public enum ClientType{
        INTERNAL("I"), EXTERNAL("X");

        private String defaultValue;

        private ClientType(String defValue) {
            this.defaultValue = defValue;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }
}
