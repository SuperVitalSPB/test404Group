package ru.supervital.test404group.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.Date;

import ru.supervital.test404group.R;
import ru.supervital.test404group.fragment.StartFragment;
import ru.supervital.test404group.fragment.ChartFragment;
import ru.supervital.test404group.service.Point;
import ru.supervital.test404group.service.PointsService;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    public final static int STATUS_START = 100;
    public final static int STATUS_WORK = 200;
    public final static int STATUS_FINISH = 300;

    public final static String PARAM_TIME = "time";
    public final static String PARAM_YVAL = "yVal";
    public final static String PARAM_STATUS = "status";

    public final static String BROADCAST_ACTION = "ru.supervital.test404group.p0961servicebackbroadcast";
    BroadcastReceiver br;

    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        InitBroadCast();

        onNavigationItemSelected(navigationView.getMenu().getItem(0));


    }

    private void InitBroadCast(){
        br = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(PARAM_STATUS, 0);
                // Ловим сообщения о старте задач
                switch (status){
                    case STATUS_START:
                        Log.d(TAG, "STATUS_START");
                        break;
                    case STATUS_FINISH:
                        Log.d(TAG, "STATUS_FINISH");
                        break;
                    case STATUS_WORK:
                        Log.d(TAG, "STATUS_WORK: " + new Point(new Date((intent.getLongExtra(PARAM_TIME, 0))*1000L),
                                            intent.getDoubleExtra(PARAM_YVAL, 0)
                                        ).toString());
                        break;
                }
            }
        };
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(br, intFilt);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();

        if (id == R.id.nav_start_base) {
            fragmentClass = StartFragment.class;
        } else if (id == R.id.nav_graph) {
            fragmentClass = ChartFragment.class;
        } else if (id == R.id.nav_start_service) {
            StartService();
            fragmentClass = ChartFragment.class;
            item.setEnabled(false);
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        item.setChecked(true);
        setTitle(item.getTitle());

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void StartService(){
        Intent intent;
        intent = new Intent(this, PointsService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }
}
