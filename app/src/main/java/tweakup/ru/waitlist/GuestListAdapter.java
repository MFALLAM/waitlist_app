package tweakup.ru.waitlist;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import tweakup.ru.waitlist.data.WaitListContract;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    private Context mContext;
    // TODO (8) Add a new local variable mCount to store the count of items to be displayed in the recycler view

    private Cursor mCursor;

    /**
     * Constructor using the context and the db cursor
     *
     * @param context the calling context/activity
     */
    // TODO (9) Update the Adapter constructor to accept an integer for the count along with the context
    public GuestListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        // TODO (10) Set the local mCount to be equal to count
        this.mCursor = cursor;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        // TODO (5) Move the cursor to the passed in position, return if moveToPosition returns false
        if (!mCursor.moveToPosition(position)) return;

        // TODO (6) Call getString on the cursor to get the guest's name
        String name = mCursor.getString(mCursor.getColumnIndex(WaitListContract.WaitlistEntry.COLUMN_GUEST_NAME));

        // TODO (7) Call getInt on the cursor to get the party size
        String partySize = mCursor.getString(mCursor.getColumnIndex(WaitListContract.WaitlistEntry.COLUMN_PARTY_SIZE));

        // TODO (8) Set the holder's nameTextView text to the guest's name
        holder.nameTextView.setText(name);

        // TODO (9) Set the holder's partySizeTextView text to the party size
        holder.partySizeTextView.setText(String.valueOf(partySize));
    }

    // TODO (11) Modify the getItemCount to return the mCount value rather than 0
    @Override
    public int getItemCount() {
        return this.mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // COMPLETED (16) Inside, check if the current cursor is not null, and close it if so
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        // COMPLETED (17) Update the local mCursor to be equal to  newCursor
        mCursor = newCursor;
        // COMPLETED (18) Check if the newCursor is not null, and call this.notifyDataSetChanged() if so
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class GuestViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView nameTextView;
        // Will display the party size number
        TextView partySizeTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link GuestListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public GuestViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            partySizeTextView = (TextView) itemView.findViewById(R.id.party_size_text_view);
        }

    }
}
