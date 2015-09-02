package com.dision.android.flickrgallery.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.dision.android.flickrgallery.R;
import com.dision.android.flickrgallery.ui.CustomTextView;
import com.dision.android.flickrgallery.utils.CompatibilityUtil;
import com.dision.android.flickrgallery.utils.PaletteTransformation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

public class PhotoDetailsActivity extends AppCompatActivity {

    // constants
    public static final String BASIC_TAG = PhotoDetailsActivity.class.getName();

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_SUBTITLE = "subtitle";

    // variables
    private Picasso mPicasso;
    private String mUrl;
    private String mTitle;
    private String mSubtitle;
    private Palette mPalette;
    private MaterialMenuDrawable mMaterialMenu;
    @BindColor(R.color.primary) int mColorPrimary;
    @BindColor(R.color.primary_dark) int mColorPrimaryDark;
    @BindColor(R.color.primary_light) int mColorPrimaryLight;

    // UI variables
    @Bind(R.id.toolbar_activity_photo_details) Toolbar toolbar;
    @Bind(R.id.cv_activity_photo_details) CardView cv;
    @Bind(R.id.iv_activity_photo_details) ImageView iv;
    @Bind(R.id.tv_title) CustomTextView tvTitle;
    @Bind(R.id.tv_subtitle) CustomTextView tvSubtitle;
    @Bind(R.id.ll_text_container) LinearLayout llTextContainer;

    // static methods
    public static Intent getIntent(Context context,
                                   String url,
                                   String title,
                                   String subtitle) {
        Intent i = new Intent(context, PhotoDetailsActivity.class);
        i.putExtra(EXTRA_URL, url);
        i.putExtra(EXTRA_TITLE, title);
        i.putExtra(EXTRA_SUBTITLE, subtitle);

        return i;
    }

    // methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);

        if (CompatibilityUtil.hasLollipopApi()) {
            postponeEnterTransition();
        }

        initExtras();
        initVariables();
        ButterKnife.bind(this);
        setToolbarSettings();
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mMaterialMenu.animateIconState(MaterialMenuDrawable.IconState.BURGER);

        if (CompatibilityUtil.hasLollipopApi()) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    private void initExtras() {
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        mSubtitle = getIntent().getStringExtra(EXTRA_SUBTITLE);
    }

    private void initVariables() {
        mPicasso = Picasso.with(this);
        mMaterialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
    }

    private void setToolbarSettings() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setHomeButtonEnabled(true);

        toolbar.setNavigationIcon(mMaterialMenu);
    }

    private void updateUI() {
        tvTitle.setText(String.format("Capture: %s", mTitle));
        tvSubtitle.setText(String.format("Owner ID: %s", mSubtitle));

        mPicasso
                .load(mUrl)
                .noFade()
                .fit()
                .centerCrop()
                .transform(PaletteTransformation.instance())
                .into(iv, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                        mPalette = PaletteTransformation.getPalette(bitmap);

                        if (mPalette != null) {
                            getWindow().setStatusBarColor(mPalette.getDarkVibrantColor(mColorPrimaryDark));
                            toolbar.setBackgroundColor(mPalette.getVibrantColor(mColorPrimary));
                            llTextContainer.setBackgroundColor(mPalette.getLightMutedColor(mColorPrimaryLight));
                        }

                        if (CompatibilityUtil.hasLollipopApi()) {
                            startPostponedEnterTransition();
                            mMaterialMenu.animateIconState(MaterialMenuDrawable.IconState.ARROW);
                        }
                    }
                });
    }


}
