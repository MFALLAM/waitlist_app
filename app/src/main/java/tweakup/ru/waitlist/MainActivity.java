package tweakup.ru.waitlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import tweakup.ru.waitlist.data.TestUtil;
import tweakup.ru.waitlist.data.WaitListDbHelper;
import tweakup.ru.waitlist.data.WaitListContract;

public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;

    // TODO (1) Create a local field member of type SQLiteDatabase called mDb

    // Local field for database
    private SQLiteDatabase mDd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView waitlistRecyclerView;

        // Set local attributes to corresponding views
        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guest_list_view);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO (2) Create a WaitlistDbHelper instance, pass "this" to the constructor as context
        WaitListDbHelper dbHelper = new WaitListDbHelper(this);

        // TODO (3) Get a writable database reference using getWritableDatabase and store it in mDb
        mDd = dbHelper.getWritableDatabase();

        // TODO (4) call insertFakeData from TestUtil and pass the database reference mDb
        TestUtil.insertFakeData(mDd);

        // TODO (7) Run the getAllGuests function and store the result in a Cursor variable
        Cursor cursor = getAllGuests();

        // TODO (12) Pass the resulting cursor count to the adapter
        mAdapter = new GuestListAdapter(this, cursor.getCount());
        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);
    }

    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {

    }

    // TODO (5) Create a private method called getAllGuests that returns a cursor
    private Cursor getAllGuests() {
        // TODO (6) Inside, call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP
        return mDd.query(
                WaitListContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitListContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }

}