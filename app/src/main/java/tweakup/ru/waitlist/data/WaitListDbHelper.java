package tweakup.ru.waitlist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import tweakup.ru.waitlist.data.WaitListDbHelper.*;

public class WaitListDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "waitlist.db";

    private static final int DATABASE_VERSION = 1;

    public WaitListDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + WaitListContract.WaitlistEntry.TABLE_NAME + " (" +
            WaitListContract.WaitlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            WaitListContract.WaitlistEntry.COLUMN_GUEST_NAME + " TEXT NOT NULL, " +
            WaitListContract.WaitlistEntry.COLUMN_PARTY_SIZE + " INTEGER NOT NULL, " +
            WaitListContract.WaitlistEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            "); ";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WaitListContract.WaitlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
