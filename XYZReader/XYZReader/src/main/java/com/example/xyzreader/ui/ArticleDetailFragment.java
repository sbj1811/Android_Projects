package com.example.xyzreader.ui;

import android.graphics.drawable.Drawable;
import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "ArticleDetailFragment";

    public static final String ARG_ITEM_ID = "item_id";

    private Toolbar mToolbar;
    private Cursor mCursor;
    private long mItemId;
    private SimpleDraweeView mPhotoView;
    private TextView dateView, titleView , authorView , bodyView;
    private FloatingActionButton mShareButton;
    private CollapsingToolbarLayout collapsingToolbarLayout;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance(long itemId) {
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_ITEM_ID, itemId);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItemId = getArguments().getLong(ARG_ITEM_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // In support library r8, calling initLoader for a fragment in a FragmentPagerAdapter in
        // the fragment's onCreate may cause the same LoaderManager to be dealt to multiple
        // fragments because their mIndex is -1 (haven't been added to the activity yet). Thus,
        // we do this in onActivityCreated.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_article_detail, container, false);

        collapsingToolbarLayout = (CollapsingToolbarLayout) mRootView.findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        mToolbar = (Toolbar) mRootView.findViewById(R.id.detail_toolbar);
        mPhotoView = (SimpleDraweeView) mRootView.findViewById(R.id.photo);
        dateView = (TextView) mRootView.findViewById(R.id.article_date);
        titleView = (TextView) mRootView.findViewById(R.id.article_title);
        authorView = (TextView) mRootView.findViewById(R.id.article_author);
        bodyView = (TextView) mRootView.findViewById(R.id.article_body);
        mShareButton = (FloatingActionButton) mRootView.findViewById(R.id.share_fab);

        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }

        return mRootView;
    }

    private void bindViews() {

        if (mCursor != null) {
            final String title = mCursor.getString(ArticleLoader.Query.TITLE);

            String date = DateUtils.getRelativeTimeSpanString(
                    mCursor.getLong(ArticleLoader.Query.PUBLISHED_DATE),
                    System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_ALL).toString();

            String author = mCursor.getString(ArticleLoader.Query.AUTHOR);
            final String body = Html.fromHtml(mCursor.getString(ArticleLoader.Query.BODY)).toString();
            String photo = mCursor.getString(ArticleLoader.Query.PHOTO_URL);

            if (mToolbar != null) {
                mToolbar.setTitle(title);
            }

            //ImageLoadingUtils.load(mPhotoView, photo);
            Picasso.with(mPhotoView.getContext())
                    .load(photo)
                    .fit()
                    .into(mPhotoView);
  //          processImageWithPaletteApi(ImageLoadingUtils.getCurrentImageRequest());
            Picasso.with(mPhotoView.getContext())
                    .load(photo)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            Palette.from(bitmap).maximumColorCount(24).generate(new Palette.PaletteAsyncListener() {
                                @Override public void onGenerated(Palette palette) {

                                    int defaultColor = 0x000000;
                                    int lightMutedColor = palette.getLightMutedColor(defaultColor);
                                    int darkMutedColor = palette.getDarkMutedColor(defaultColor);
                                    if (collapsingToolbarLayout != null) {
                                        collapsingToolbarLayout.setContentScrimColor(lightMutedColor);
                                        collapsingToolbarLayout.setStatusBarScrimColor(darkMutedColor);
                                    }
                                }
                            });
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

            titleView.setText(title);
            dateView.setText(date);
            authorView.setText(author);
            bodyView.setText(body);

            mShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                            .setType("text/plain")
                            .setText(body)
                            .getIntent(), getString(R.string.action_share)));
                }
            });

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newInstanceForItemId(getActivity(), mItemId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursor = cursor;
        if (mCursor != null && !mCursor.moveToFirst()) {
            Log.e(TAG, "Error reading item detail cursor");
            mCursor.close();
            mCursor = null;
        }

        bindViews();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }

}