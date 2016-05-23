package com.example.admin.angpangii.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.angpangii.Adapters.MainAdapter;
import com.example.admin.angpangii.R;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;

/**
 * Created by Admin on 4/15/2016.
 */
public class MainActivity extends AppCompatActivity {

    private AccountHeader headerResult = null;
    private Drawer result = null;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the back arrow in the toolbar
        getSupportActionBar().setTitle("Home");

        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more detail
        final IProfile prof_huy = new ProfileDrawerItem().withName("Devushka Krasivaya").withEmail("devushkaK158@gmail.com").withIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.f5, null)).withIdentifier(100);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder().withActivity(this).withSelectionListEnabledForSingleProfile(false).withCompactStyle(true).withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header).addProfiles(prof_huy).withSavedInstance(savedInstanceState).build();



        //Create the drawer
        result = new DrawerBuilder().withActivity(this).withToolbar(toolbar).withHasStableIds(true).withDrawerLayout(R.layout.layout_crossfade_navigation_drawer).withDrawerWidthDp(72).withGenerateMiniDrawer(true).withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(FontAwesome.Icon.faw_home).withIdentifier(1).withSelectable(false),
                        new SectionDrawerItem().withName("Functions"),
                        new PrimaryDrawerItem().withName("Notice").withIcon(FontAwesome.Icon.faw_heartbeat).withIdentifier(2).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_album).withIcon(FontAwesome.Icon.faw_picture_o).withIdentifier(3).withSelectable(false),
                        new PrimaryDrawerItem().withName("Daily Menu").withIcon(FontAwesome.Icon.faw_cutlery).withIdentifier(4).withSelectable(false)

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
                        }
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 1) { intent = new Intent(MainActivity.this, ContactActivity.class); }
                            else if (drawerItem.getIdentifier() == 2) { intent = new Intent(MainActivity.this, HealthActivity.class); }
                            else if (drawerItem.getIdentifier() == 3) { intent = new Intent(MainActivity.this, AlbumActivity.class); }
                            else if (drawerItem.getIdentifier() == 4) {intent = new Intent(MainActivity.this, MenuActivity.class);}
                            else if (drawerItem.getIdentifier() == 8) {intent = new Intent(MainActivity.this, SettingActivity.class);}
                            else if (drawerItem.getIdentifier() == 9) {intent = new Intent(MainActivity.this, LogoutActivity.class);}
                            if (intent != null){ MainActivity.this.startActivity(intent);}
                        }
                        //we do not consume the event and want the Drawer to continue with the event chain
                        return false;
                    }
                })
                .addStickyDrawerItems(
                        new SectionDrawerItem().withName("Help and Setting"),
                        new SecondaryDrawerItem().withName("Setting").withIcon(GoogleMaterial.Icon.gmd_brightness_5).withIdentifier(8).withSelectable(false),
                        new SecondaryDrawerItem().withName("Log out").withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(9).withSelectable(false)
                )
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();
        //if you have many different types of DrawerItems you can magically pre-cache those items to get a better scroll performance
        //make sure to init the cache after the DrawerBuilder was created as this will first clear the cache to make sure no old elements are in
        //RecyclerViewCacheUtil.getInstance().withCacheSize(2).init(result);
        //new RecyclerViewCacheUtil<IDrawerItem>().withCacheSize(2).apply(result.getRecyclerView(), result.getDrawerItems());

        //get the CrossfadeDrawerLayout which will be used as alternative DrawerLayout for the Drawer
        //the CrossfadeDrawerLayout library can be found here: https://github.com/mikepenz/CrossfadeDrawerLayout
        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));
        //add second view (which is the miniDrawer)
        final MiniDrawer miniResult = result.getMiniDrawer();
        //build the view for the MiniDrawer
        View view = miniResult.build(this);
        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });
        // Number of unseen notices appear next to Notice section on Menu Sidebar
        //result.updateBadge(2, new StringHolder(5 + ""));

        // create 2 tab: wall and notify
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.bull_fade));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.not_fade));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#ED891F"), PorterDuff.Mode.SRC_IN);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(Color.parseColor("#ED891F"), PorterDuff.Mode.SRC_IN);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#ffdcd8d8"), PorterDuff.Mode.MULTIPLY);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }*/

    //@Override
    // Menu in toolbar
    /*public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_1:

                return true;
            case R.id.menu_2:
                //show the arrow icon
                result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                return true;
            case R.id.menu_3:
                //show the hamburger icon
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
                return true;
            case R.id.menu_4:

                return true;
            case R.id.menu_5:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


}
