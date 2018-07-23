package com.sjani.usnationalparkguide.UI.Details.Trails;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sjani.usnationalparkguide.Data.TrailContract;
import com.sjani.usnationalparkguide.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = TrailDetailFragment.class.getSimpleName();
    private static final String TRAIL_ID = "trail_id";
    private static final String URI = "uri";
    private static final String URI_TRAIL = "uri_trail";
    private static final String POSITION = "position";
    private static final String PARK_ID = "park_id";
    private static final String PARKCODE = "parkcode";
    private static final String LATLONG = "latlong";
    private static final int LOADER_ID = 6;

    private Uri uri;
    private String trailId;
    private int position;
    private Cursor cursor;

    @BindView(R.id.trail_detail_title)
    TextView titleTv;
    @BindView(R.id.trail_address)
    TextView trailAddressTv;
    @BindView(R.id.difficulty_tv)
    TextView difficultyTv;
    @BindView(R.id.distance_tv)
    TextView distanceTv;
    @BindView(R.id.elevation_tv)
    TextView elevationTv;
    @BindView(R.id.condition_tv)
    TextView conditionTv;
    @BindView(R.id.summary_tv)
    TextView summaryTv;
    @BindView(R.id.trail_detail_photo)
    ImageView trailIv;
    @BindView(R.id.trail_address_linear_layout)
    LinearLayout addressLl;

    private static final String[] PROJECTION = new String[]{
            TrailContract.TrailEntry._ID,
            TrailContract.TrailEntry.COLUMN_TRAIL_ID,
            TrailContract.TrailEntry.COLUMN_TRAIL_NAME,
            TrailContract.TrailEntry.COLUMN_TRAIL_SUMMARY,
            TrailContract.TrailEntry.COLUMN_TRAIL_DIFFICULTY,
            TrailContract.TrailEntry.COLUMN_TRAIL_IMAGE_SMALL,
            TrailContract.TrailEntry.COLUMN_TRAIL_IMAGE_MED,
            TrailContract.TrailEntry.COLUMN_TRAIL_LENGTH,
            TrailContract.TrailEntry.COLUMN_TRAIL_ASCENT,
            TrailContract.TrailEntry.COLUMN_TRAIL_LAT,
            TrailContract.TrailEntry.COLUMN_TRAIL_LONG,
            TrailContract.TrailEntry.COLUMN_TRAIL_LOCATION,
            TrailContract.TrailEntry.COLUMN_TRAIL_CONDITION,
            TrailContract.TrailEntry.COLUMN_TRAIL_MOREINFO
    };


    private OnFragmentInteractionListener mListener;

    private Uri uriPark;
    private String parkId;
    private String parkCode;
    private String latLong;
    private String title;
    private String latitude;
    private String longitude;



    public TrailDetailFragment() {
        // Required empty public constructor
    }

    public static TrailDetailFragment newInstance(Uri uri, String trailId, int position,String parkId, String parkCode, String latLong, Uri parkUri) {
        TrailDetailFragment fragment = new TrailDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(URI_TRAIL, uri);
        args.putString(TRAIL_ID, trailId);
        args.putInt(POSITION, position);
        args.putParcelable(URI, parkUri);
        args.putString(PARK_ID, parkId);
        args.putString(LATLONG,latLong);
        args.putString(PARKCODE,parkCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_trail_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            uri = getArguments().getParcelable(URI_TRAIL);
            trailId = getArguments().getString(TRAIL_ID);
            position = getArguments().getInt(POSITION);
            uriPark = getArguments().getParcelable(URI);
            latLong = getArguments().getString(LATLONG);
            parkCode = getArguments().getString(PARKCODE);
            parkId = getArguments().getString(PARK_ID);
        }
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                break;
            case R.id.action_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, title+"\nOpen in Google Maps https://maps.google.com/?q="+latitude+","+longitude);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            default:
                break;
        }
        return true;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getActivity(), uri, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        cursor = data;
        if (cursor == null) return;
        try {
            cursor.moveToPosition(position);
            title = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_NAME));
            titleTv.setText(title);
            String distance = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_LENGTH));
            distanceTv.setText(distance+" miles");
            String elevation = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_ASCENT));
            elevationTv.setText(elevation+" ft");
            String address = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_LOCATION));
            trailAddressTv.setText(address);
            latitude = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_LAT));
            longitude = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_LONG));
            addressLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("geo:"+latitude+","+longitude+"?z=10"));
                    startActivity(intent);
                }
            });
            String summary = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_SUMMARY));
            summaryTv.setText(summary);
            String condition = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_CONDITION));
            if (!(condition == null)){
                if (!condition.equals("")) {
                    conditionTv.setText(condition);
                }
            } else {
                conditionTv.setText(getActivity().getResources().getString(R.string.na));
            }
            String difficultyMark = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_DIFFICULTY));
            String difficultyLevel;
            if (difficultyMark.equals("greenBlue")){
                difficultyLevel = getContext().getResources().getString(R.string.easy);
            } else if (difficultyMark.equals("blue")) {
                difficultyLevel = getContext().getResources().getString(R.string.moderate);
            } else if (difficultyMark.equals("blueBlack")) {
                difficultyLevel = getContext().getResources().getString(R.string.strenuous);
            } else {
                difficultyLevel = getContext().getResources().getString(R.string.unknown);
            }
            difficultyTv.setText(difficultyLevel);
            String imageUrl = cursor.getString(cursor.getColumnIndex(TrailContract.TrailEntry.COLUMN_TRAIL_IMAGE_MED));
            if (imageUrl.equals("")){
                Glide.with(trailIv.getContext())
                        .load(R.drawable.empty_detail)
                        .fitCenter()
                        .into(trailIv);
            } else {
                Glide.with(trailIv.getContext())
                        .load(imageUrl)
                        .fitCenter()
                        .into(trailIv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursor = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(URI,uriPark);
        outState.putString(PARK_ID,parkId);
        outState.putString(PARKCODE,parkCode);
        outState.putString(LATLONG,latLong);
    }
}
