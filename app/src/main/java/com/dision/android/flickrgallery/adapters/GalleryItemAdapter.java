package com.dision.android.flickrgallery.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dision.android.flickrgallery.R;
import com.dision.android.flickrgallery.activities.PhotoDetailsActivity;
import com.dision.android.flickrgallery.helpers.ItemTouchHelperAdapter;
import com.dision.android.flickrgallery.helpers.ItemTouchHelperViewHolder;
import com.dision.android.flickrgallery.interfaces.OnStartDragListener;
import com.dision.android.flickrgallery.models.Photo;
import com.dision.android.flickrgallery.ui.CustomTextView;
import com.dision.android.flickrgallery.utils.CompatibilityUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryItemAdapter
        extends RecyclerView.Adapter<GalleryItemAdapter.GalleryItemHolder>
        implements ItemTouchHelperAdapter {

    // constants
    public static final String BASIC_TAG = GalleryItemAdapter.class.getName();

    private static final int REQUEST_PHOTO_DETAILS = 1;

    // variables
    private Context mContext;
    private List<Photo> mData;
    private Picasso mPicasso;
    private OnStartDragListener mDragStartListener;

    // constructor
    public GalleryItemAdapter(Context context,
                              Picasso picasso,
                              OnStartDragListener dragStartListener) {

        mContext = context;
        mPicasso = picasso;
        mDragStartListener = dragStartListener;

        mData = new ArrayList<>();
    }

    // methods
    public void addData(Photo[] data, boolean notify) {
        addData(Arrays.asList(data), notify);
    }

    public void addData(List<Photo> data, boolean notify) {
        int startCount = mData.size();

        mData.addAll(data);

        if (notify) {
            try {
                notifyItemRangeInserted(startCount, data.size());
            } catch (IndexOutOfBoundsException e) {
                notifyDataSetChanged();
                e.printStackTrace();
            }
        }
    }

    public void clearData(boolean notify) {
        mData.clear();

        if (notify) {
            notifyDataSetChanged();
        }
    }

    public List<Photo> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public GalleryItemHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.gallery_item, viewGroup, false);

        return new GalleryItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GalleryItemHolder viewHolder, int position) {
        Photo item = mData.get(position);

        mPicasso
                .load(item.getUrl())
                .noFade()
                .into(viewHolder.iv, new ImageLoadedCallback(viewHolder.pb));

        viewHolder.bindGalleryItem(mContext, item);

        viewHolder.iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP) {
                    mDragStartListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        // TODO
    }

    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public  ImageLoadedCallback(ProgressBar progBar){
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onError() {

        }
    }

    // inner classes
    public final static class GalleryItemHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener, ItemTouchHelperViewHolder {

        // variables
        private Photo mItem;
        private Context mContext;

        // UI variables
        @Bind(R.id.cv_adapter_gallery_item) CardView cv;
        @Bind(R.id.iv_adapter_gallery_item) ImageView iv;
        @Bind(R.id.tv_title_adapter_gallery_item) CustomTextView tvTitle;
        @Bind(R.id.tv_subtitle_adapter_gallery_item) CustomTextView tvSubtitle;
        @Bind(R.id.pb_adapter_gallery_item) ProgressBar pb;

        public GalleryItemHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            cv.setOnClickListener(this);
        }

        public void bindGalleryItem(Context context, Photo item) {
            mItem = item;
            mContext = context;

            initUI();
        }

        private void initUI() {
            String reformattedString;
            if (mItem.getCaption().length() > 12) {
                reformattedString = mItem.getCaption().substring(0,12) + "...";
            } else {
                reformattedString = mItem.getCaption();
            }

            tvTitle.setText(reformattedString);
            tvSubtitle.setText(mItem.getOwner());
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onClick(View v) {
            Intent i = PhotoDetailsActivity.getIntent(mContext,
                    mItem.getUrl(),
                    mItem.getCaption(),
                    mItem.getOwner());

            Pair<View, String> p1 = Pair.create((View) iv, "profile");
            Pair<View, String> p2 = Pair.create((View) tvTitle, "title");
            Pair<View, String> p3 = Pair.create((View) tvSubtitle, "subtitle");

            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) mContext, p1, p2, p3);


            if(CompatibilityUtil.hasJellyBeanApi()) {
                ((Activity) mContext).startActivityForResult(i,
                        REQUEST_PHOTO_DETAILS,
                        options.toBundle());
            } else {
                ((Activity) mContext).startActivityForResult(i, REQUEST_PHOTO_DETAILS);
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
