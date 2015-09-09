package com.dision.android.flickrgallery.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.dision.android.flickrgallery.R;
import com.dision.android.flickrgallery.adapters.GalleryItemAdapter;
import com.dision.android.flickrgallery.application.App;
import com.dision.android.flickrgallery.interfaces.GotPicasso;
import com.dision.android.flickrgallery.interfaces.GotToolbar;
import com.dision.android.flickrgallery.listeners.HidingScrollListener;
import com.dision.android.flickrgallery.rest.model.ApiResponse;
import com.dision.android.flickrgallery.utils.LogUtil;
import com.dision.android.flickrgallery.utils.Util;
import com.melnykov.fab.FloatingActionButton;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity
        extends AppCompatActivity
        implements GotPicasso, GotToolbar {

    // constants
    public static final String BASIC_TAG = MainActivity.class.getName();
    public static final int SPAN_COUNT = 2;

    // variables
    private Picasso mPicasso;
    private GalleryItemAdapter mAdapter;
    private int mToolbarHeight;
    private MaterialMenuDrawable materialMenu;

    // UI variables
    @Bind(R.id.toolbar_activity_main) Toolbar toolbar;
    @Bind(R.id.nv_activity_home) NavigationView nv;
    @Bind(R.id.dl_activity_main) DrawerLayout dl;
    @Bind(R.id.rv_gallery_items) RecyclerView rvGalleryItems;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.pw_activity_main) ProgressWheel pw;

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
        ButterKnife.bind(this);
        initListeners();
        setUiSettings();
        setupRecyclerView();
        fetchPhotos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                dl.openDrawer(GravityCompat.START);

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            materialMenu.animateIconState(MaterialMenuDrawable.IconState.BURGER);
        }
    }

    private void initVariables() {
        mPicasso = Picasso.with(this);
        mAdapter = new GalleryItemAdapter(this, mPicasso);
        mToolbarHeight = Util.getToolbarHeight(this);
        materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
    }

    private void setUiSettings() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(materialMenu);

        fab.attachToRecyclerView(rvGalleryItems);
    }

    private void initListeners() {
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.menu_profile:
                        // TODO

                        break;

                    case R.id.menu_settings:
                        // TODO

                        break;

                    case R.id.menu_log_out:
                        // TODO

                        break;

                }

                menuItem.setChecked(true);
                dl.closeDrawers();

                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchPhotos();

                ObjectAnimator anim = ObjectAnimator.ofFloat(fab, View.ROTATION, 0f, 360f, 0f);
                anim.start();
            }
        });
    }

    private void setupRecyclerView() {
        rvGalleryItems.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));

        rvGalleryItems.addOnScrollListener(new HidingScrollListener(this) {

            @Override
            public void onMoved(int distance) {
                toolbar.setTranslationY(-distance);
            }

            @Override
            public void onShow() {
                toolbar
                        .animate()
                        .translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void onHide() {
                toolbar
                        .animate()
                        .translationY(-mToolbarHeight)
                        .setInterpolator(new AccelerateInterpolator(2)).start();
            }
        });

        rvGalleryItems.setAdapter(mAdapter);
    }

    private void fetchPhotos() {
        final String TAG = Util.stringsToPath(BASIC_TAG, "fetchPhotos");

        pw.setVisibility(View.VISIBLE);
        App.getRestClient().getAppService().getRecentPhotos(new Callback<ApiResponse>() {


            @Override
            public void success(ApiResponse apiResponse, Response response) {
                pw.setVisibility(View.GONE);

                mAdapter.clearData(true);
                mAdapter.addData(apiResponse.getPhotoGallery().getPhotos(), true);
            }

            @Override
            public void failure(RetrofitError error) {
                LogUtil.log(TAG, error.getCause().getMessage());
                error.printStackTrace();

                pw.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public Picasso getPicasso() {
        return mPicasso;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }
}
