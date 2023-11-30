package tweakup.ru.waitlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tweakup.ru.waitlist.data.TestUtil;
import tweakup.ru.waitlist.data.WaitListContract;
import tweakup.ru.waitlist.data.WaitListDbHelper;

public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;

    // TODO (1) Create a local field member of type SQLiteDatabase called mDb

    // Local field for database
    private SQLiteDatabase mDd;

    private EditText mNewGuestNameEditText;
    private EditText mNewPartySizeEditText;

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewGuestNameEditText = findViewById(R.id.person_name_edit_text);
        mNewPartySizeEditText = findViewById(R.id.party_count_edit_text);

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
        mAdapter = new GuestListAdapter(this, cursor);
        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);
    }

    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {
        // TODO first thing, check if any of the EditTexts are empty, return if so
        if (mNewGuestNameEditText.getText().length() == 0 ||
                mNewPartySizeEditText.getText().length() == 0) {
            return;
        }

        // Initialize partySize to 1
        int partySize = 1;

        // TODO Use Integer.parseInt to parse mNewPartySizeEditText to an integer
        // TODO Make sure you surround the Integer.parseInt with try ctach and log any exception
        try {
            partySize = Integer.parseInt(mNewPartySizeEditText.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong..!", Toast.LENGTH_LONG).show();
        }

        // TODO call addNewGuest with the guest name and party size
        addNewGuest(mNewGuestNameEditText.getText().toString(), partySize);

        // TODO call mAdapter.swapCusror to update the cursor by passing in getAllGuests()
        mAdapter.swapCursor(getAllGuests());

        // TODO make the UI look nice, call .getText().clear() on both EditTexts, also call clearFocus() on mNewPartSize
        // COMPLETED (20) To make the UI look nice, call .getText().clear() on both EditTexts, also call clearFocus() on mNewPartySizeEditText
        //clear UI text fields
        mNewPartySizeEditText.clearFocus();
        mNewGuestNameEditText.getText().clear();
        mNewPartySizeEditText.getText().clear();
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

    private long addNewGuest(String name, int partySize) {
        // TODO create a ContentValues instance to pass the value onto the insert query
        ContentValues contentValues = new ContentValues();

        // TODO call the put to insert name value with they key COLUMN_GUEST_NAME
        contentValues.put(WaitListContract.WaitlistEntry.COLUMN_GUEST_NAME, name);

        // TODO call the put to insert party size value with the key COLUMN_PARTY_SIZE
        contentValues.put(WaitListContract.WaitlistEntry.COLUMN_PARTY_SIZE, partySize);

        long operationId = mDd.insert(WaitListContract.WaitlistEntry.TABLE_NAME, null, contentValues);
        Log.d(LOG_TAG, "Opeartion ID_ " + operationId);
        return operationId;
    }
}