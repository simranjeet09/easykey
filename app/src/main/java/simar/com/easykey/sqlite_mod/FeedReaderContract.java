package simar.com.easykey.sqlite_mod;

import android.provider.BaseColumns;

    public final class FeedReaderContract {
        public FeedReaderContract() {}

        /* Inner class that defines the table contents */
        public static abstract class FeedEntry implements BaseColumns {
            public static final String ID = "id";
            public static final String CATEGORY_TABLE_NAME = "category";


            public static final String EMAIL_TABLE_NAME = "social";
            public static final String EMAIL_TABLE_TITLE= "title";
            public static final String EMAIL_TABLE_EMAIL= "email";
            public static final String EMAIL_TABLE_PASSWORD= "password";
            public static final String EMAIL_TABLE_LINK= "link";
            public static final String EMAIL_TABLE_NOTE= "note";
            public static final String SOCIAL_CAT= "social_cat";

            public static final String COLUMN_NAME_CAT_LABEL = "cat_lbl";
            public static final String COLUMN_NAME_TABLE_NAME = "tbl_name";
            public static final String COLUMN_CAT_ID = "cat_id";

            public static final String WIFI_TABLE_NAME = "wifi";

            public static final String WIFI_TABLE_TITLE = "title";
            public static final String WIFI_TABLE_USERNAME = "username";

            public static final String WIFI_TABLE_PASSWORD = "password";
            public static final String WIFI_TABLE_IP = "ip_address";
            public static final String WIFI_TABLE_PORT = "port";
            public static final String WIFI_TABLE_DNS = "dns";

            public static final String TABLE_COLUMN_NAMES = "column_name";
            public static final String TABLE_COLUMN_label = "column_name_lbl";




        }
    }