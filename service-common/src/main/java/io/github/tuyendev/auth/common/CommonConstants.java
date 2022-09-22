package io.github.tuyendev.auth.common;

public abstract class CommonConstants {

    public static final String APP_HOSTNAME = "localhost";

    public static final String APP_PORT = "9000";

    public static abstract class EntityStatus {
        public static final Integer ACTIVE = 1;

        public static final Integer INACTIVE = 0;

        public static final Boolean LOCKED = Boolean.TRUE;

        public static final Boolean UNLOCKED = Boolean.FALSE;

        public static final Integer DELETED = 9;

        public static final Boolean ENABLED = Boolean.TRUE;

        public static final Boolean DISABLED = Boolean.FALSE;

        public static final Boolean VERIFIED = Boolean.TRUE;

        public static final Boolean UNVERIFIED = Boolean.FALSE;
    }

    public static abstract class Authority {
        public static final String ADMIN = "ADMIN";
        public static final String ADMIN_DEPUTY = "ADMIN_DEPUTY";
        public static final String GROUP = "GROUP";
        public static final String GROUP_DEPUTY = "GROUP_DEPUTY";
        public static final String BASIC = "BASIC";

        public static final String CONSTRAINT = String.join(" > ", ADMIN, ADMIN_DEPUTY, GROUP, GROUP_DEPUTY, BASIC);
    }

    public static abstract class Gender {
        public static final Integer MALE = 1;
        public static final Integer FEMALE = 2;
        public static final Integer UNKNOWN = 0;
    }

    public static abstract class User {

        public static final Long ADMIN_ID = 1L;

        public static final Long ANONYMOUS_ID = -1L;
    }

}
