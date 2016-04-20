package com.example.admin.angpangii.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.angpangii.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private TextView lbl1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lbl1 = (TextView) findViewById(R.id.textView);
        button1 = (Button) findViewById(R.id.btn1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lbl1.setVisibility(View.VISIBLE);
            }
        });


        //======================= Navigation Drawer ================================================

        //List item in Menu Sidebar
        PrimaryDrawerItem item_home = new PrimaryDrawerItem().withName(R.string.drawer_item_home);
        SecondaryDrawerItem item_notice = new SecondaryDrawerItem().withName(R.string.drawer_item_notice);
        SecondaryDrawerItem item_album = new SecondaryDrawerItem().withName(R.string.drawer_item_album);
        SecondaryDrawerItem item_menu = new SecondaryDrawerItem().withName(R.string.drawer_item_menu);
        SecondaryDrawerItem item_bus = new SecondaryDrawerItem().withName(R.string.drawer_item_bus);
        SecondaryDrawerItem item_logout = new SecondaryDrawerItem().withName(R.string.drawer_item_logout);

        //Profile of users
        ProfileDrawerItem prof_huy = new ProfileDrawerItem()
                                        .withName("Trần Tất Huy")
                                        .withEmail("huyict58@gmail.com")
                                        .withIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.huy_profile, null)
                                        );
        ProfileDrawerItem prof_linh = new ProfileDrawerItem()
                                        .withName("Trần Quang Linh")
                                        .withEmail("linhict58@gmail.com")
                                        .withIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.linh_profile, null)
                                        );
        ProfileDrawerItem prof_luong = new ProfileDrawerItem()
                                        .withName("Nguyễn Văn Lương")
                                        .withEmail("luongict58@gmail.com")
                                        .withIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.luong_profile, null)
                                        );
        ProfileDrawerItem prof_long = new ProfileDrawerItem()
                                        .withName("Nguyễn Thành Long")
                                        .withEmail("longict58@gmail.com")
                                        .withIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.long_profile, null)
                                        );

        // Create the Header
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        prof_huy,
                        prof_linh,
                        prof_luong,
                        prof_long
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item_home,
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Main Function"),
                        item_notice,
                        item_album,
                        item_menu,
                        item_bus,
                        new DividerDrawerItem(),
                        item_logout
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        //result.setSelection(1, true);
                        return false;
                    }
                })
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
