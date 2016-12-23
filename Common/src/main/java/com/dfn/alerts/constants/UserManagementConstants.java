package com.dfn.alerts.constants;

/**
 * Created by IntelliJ IDEA.
 * User: Lasanthak
 * Date: 9/13/13
 * Time: 9:58 AM
 */
@SuppressWarnings("unused")
public class UserManagementConstants {

    public enum UserRoles{
        ROLE_PREMIUM_USER (30),
        ROLE_REGISTERED_USER(20),
        ROLE_ANONYMOUS(10);

        UserRoles(int weight){
            this.weight = weight;
        }

        private final int weight;

        public int weight(){
            return weight;
        }

    }

    public enum AdminUserRoles{
        ROLE_SUPER_ADMIN(1000) ,
        ROLE_USER_ADMIN (900),
        ROLE_CONTENT_ADMIN(900) ,
        ROLE_SYSTEM_ADMIN(900);

        AdminUserRoles(int weight){
            this.weight = weight;
        }

        private final int weight;

        public int weight(){
            return weight;
        }
    }

    ///Load from db
    public enum AccountType {
        ROLE_SUPER_ADMIN("1") ,
        ROLE_USER_ADMIN("2") ,
        ROLE_CONTENT_ADMIN("3") ,
        ROLE_SYSTEM_ADMIN("4") ,
        ROLE_PREMIUM_USER("7") ,
        ROLE_REGISTERED_USER ("6");

        AccountType(String id){
            this.id = id;
        }

        private final String id;

        public String getId(){
            return id;
        }

    }

    public enum SubscriptionServices {
        PRO
    }

    public enum UserAccountType {
        PREMIUM(1) ,
        TRIAL(3),
        DFN(4),
        CDC(5),
        DECYPHA_SALES(7),
        ECC(8);

        UserAccountType(int id){
            this.id = id;
        }

        private final int id;

        public int getId(){
            return id;
        }

    }

}
