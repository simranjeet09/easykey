package simar.com.easykey.sqlite_mod;

import android.provider.BaseColumns;

    public final class FeedReaderContract {
        public FeedReaderContract() {}

        /* Inner class that defines the table contents */
        public static abstract class FeedEntry implements BaseColumns {
            public static final String TABLE_NAME = "category";
            public static final String COLUMN_NAME_CAT_LABEL = "cat_lbl";
            public static final String COLUMN_NAME_TITLE = "cat_name";
            public static final String COLUMN_CAT_ID = "cat_id";

        }
    }