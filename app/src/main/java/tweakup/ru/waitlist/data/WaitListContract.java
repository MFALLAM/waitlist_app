package tweakup.ru.waitlist.data;

import android.provider.BaseColumns;

public class WaitListContract {

    public static final class WaitListEntry implements BaseColumns {
        // Table name waitList
        public static final String TABLE_NAME = "waitList";

        // Column guest name
        public static final String COLUMN_GUEST_NAME = "guestName";

        // Column party size
        public static final String COLUMN_PARTY_SIZE = "partySize";

        // Column timestamp
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
