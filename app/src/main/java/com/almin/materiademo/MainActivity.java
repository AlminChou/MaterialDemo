package com.almin.materiademo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by Almin on 2015/10/31.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,AppBarLayout.OnOffsetChangedListener{
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    private TabLayout mTabLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AppBarLayout mAppBarLayout;
    private FloatingActionButton mFab;
    private static final int[] IMAGE_RES ={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.fi,R.drawable.g,R.drawable.s};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initDrawer();
        initTabLayout();
        initFabButton();
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Snackbar.make(mSwipeRefreshLayout, "refresh finished", Snackbar.LENGTH_LONG).show();
                    }
                }, 1500);
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    private void initFabButton() {
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
//                               Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Toast.makeText(MainActivity.this, "fab onClick...", Toast.LENGTH_LONG).show();
//                            }
//                        }).show();

                ViewCompat.animate(mFab)
                        .scaleX(20)
                        .scaleY(20)
                        .setInterpolator(new FastOutSlowInInterpolator())
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                mFab.hide();
                            }
                        })
                        .start();

//                Intent intent = new Intent(MainActivity.this, CollapsingDemoActivity.class);
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, fab,
//                        getString(R.string.navigation_drawer_close));
//                startActivityForResult(intent, 1, options.toBundle());
            }
        });
    }

    private void initTabLayout() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.psb));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab));
//        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab2));
//        tabLayout.addTab(tabLayout.newTab().setText("tab2"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab3"));
//        tabLayout.addTab(tabLayout.newTab().setText("tab4"));
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), 6);
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        tabLayout.getTabAt(0).setText("tab1");
//        tabLayout.getTabAt(1).setText("tab2");
//        tabLayout.getTabAt(2).setText("tab3");
//        tabLayout.getTabAt(3).setText("tab4");


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @SuppressLint("NewApi")
            @Override
            public void onPageSelected(int position) {
                   updatePattle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mSwipeRefreshLayout.setEnabled(state==0);
            }
        });
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                System.out.println("*-- onTabSelected   "+tab.getText().toString());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                System.out.println("*-- onTabUnselected   "+tab.getText().toString());
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                System.out.println("*-- onTabReselected   "+tab.getText().toString());
//            }
//        });

    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                System.out.println("*--onDrawerClosed--");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                System.out.println("*--onDrawerOpened--");
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                System.out.println("*--onDrawerSlide--");
            }
        };
        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView!=null){
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        // App Logo
        mToolbar.setLogo(R.mipmap.ic_launcher);
//        // Title
//        mToolbar.setTitle("My Title");
//        // Sub Title
//        mToolbar.setSubtitle("Sub title");
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolbar.inflateMenu(R.menu.main);//changed
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //your content view action...
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mToggle.syncState();
    }


    @Override
    public void onBackPressed() {
//        if(!mSearchView.isIconified()){
        if(mToolbar.hasExpandedActionView()){
            mToolbar.collapseActionView();
        }else if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Perform the final search
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Text has change,apply filtering?
                return false;
            }
        });


//        MenuItem shareItem = menu.findItem(R.id.action_share);
//        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
        }
        item.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
//        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        mSwipeRefreshLayout.setEnabled(i==0);
    }

    private void updatePattle(int position) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                IMAGE_RES[position]);
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
//                Palette.Swatch vibrant = palette.getVibrantSwatch();
//                Palette.Swatch vibrant = palette.getDarkVibrantSwatch();
//                Palette.Swatch vibrant = palette.getLightVibrantSwatch();
//                Palette.Swatch vibrant = palette.getLightMutedSwatch();
                Palette.Swatch vibrant = palette.getMutedSwatch();
//                Palette.Swatch vibrant = palette.getDarkMutedSwatch();
                if(vibrant!=null){
                    mTabLayout.setBackgroundColor(vibrant.getRgb());
                    mFab.setRippleColor(vibrant.getRgb());
                    mToolbar.setBackgroundColor(vibrant.getRgb());
                    if (android.os.Build.VERSION.SDK_INT >= 21) {
                        Window window = getWindow();
                        window.setStatusBarColor(vibrant.getRgb());
                        window.setNavigationBarColor(vibrant.getRgb());
                    }
                }
            }
        });
    }

}
