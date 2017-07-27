package com.congress.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.congress.fragment.bills.ActiveBillsFragment;
import com.congress.fragment.bills.BillsItemAdapter;
import com.congress.fragment.bills.NewBillsFragment;
import com.congress.fragment.committee.CommitteeItemAdapter;
import com.congress.fragment.committee.HouseCommitteeFragment;
import com.congress.fragment.committee.JointCommitteeFragment;
import com.congress.fragment.committee.SenateCommitteeFragment;
import com.congress.fragment.favorite.FavoriteBillsFragment;
import com.congress.fragment.favorite.FavoriteCommitteeFragment;
import com.congress.fragment.favorite.FavoriteLegisFragment;
import com.congress.fragment.legislator.ByStatesFragment;
import com.congress.fragment.legislator.HouseFragment;
import com.congress.fragment.legislator.LegisItemAdapter;
import com.congress.fragment.legislator.SenateFragment;
import com.congress.response.BillsResponse;
import com.congress.response.CommitteeResponse;
import com.congress.response.LegisResponse;
import com.congress.util.BillsUtil;
import com.congress.util.CommitteeUtil;
import com.congress.util.LegisUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LegisUtil.LegisListener, BillsUtil.BillsListener, CommitteeUtil.CommitteeListener{

    private static final String Tag = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private TabLayout tabLayout_legis;
    private TabLayout tabLayout_bills;
    private TabLayout tabLayout_committee;
    private TabLayout tabLayout_favorite;
    private ViewPager viewPager_legis;
    private ViewPager viewPager_bills;
    private ViewPager viewPager_committee;
    private ViewPager viewPager_favorite;
    private LinearLayout layout_legis;
    private LinearLayout layout_bills;
    private LinearLayout layout_committee;
    private LinearLayout layout_favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState == null){
            setTitle("Legislators");
            initializeFragments();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    private void initializeFragments() {
        viewPager_legis = (ViewPager) findViewById(R.id.viewpager_legis);
        viewPager_legis.setOffscreenPageLimit(3);
        viewPager_bills = (ViewPager) findViewById(R.id.viewpager_bills);
        viewPager_bills.setOffscreenPageLimit(2);
        viewPager_committee = (ViewPager) findViewById(R.id.viewpager_committee);
        viewPager_committee.setOffscreenPageLimit(3);
        viewPager_favorite = (ViewPager) findViewById(R.id.viewpager_favorite);
        viewPager_favorite.setOffscreenPageLimit(3);

        tabLayout_legis = (TabLayout) findViewById(R.id.tabs_legis);
        tabLayout_bills = (TabLayout) findViewById(R.id.tabs_bills);
        tabLayout_committee = (TabLayout) findViewById(R.id.tabs_committee);
        tabLayout_favorite = (TabLayout) findViewById(R.id.tabs_favorite);

        this.setupViewPagerLegis(viewPager_legis);
        this.setupViewPagerBills(viewPager_bills);
        this.setupViewPagerCommittee(viewPager_committee);
        this.setupViewPagerFavorite(viewPager_favorite);

        tabLayout_legis.setupWithViewPager(viewPager_legis);
        tabLayout_bills.setupWithViewPager(viewPager_bills);
        tabLayout_committee.setupWithViewPager(viewPager_committee);
        tabLayout_favorite.setupWithViewPager(viewPager_favorite);

        layout_legis = (LinearLayout)findViewById(R.id.layout_legislator);
        layout_bills = (LinearLayout)findViewById(R.id.layout_bills);
        layout_committee = (LinearLayout)findViewById(R.id.layout_committee);
        layout_favorite = (LinearLayout)findViewById(R.id.layout_favorite);

        layout_legis.setVisibility(LinearLayout.VISIBLE);
        layout_bills.setVisibility(LinearLayout.GONE);
        layout_committee.setVisibility(LinearLayout.GONE);
        layout_favorite.setVisibility(LinearLayout.GONE);
        LegisResponse response = LegisResponse.getInstance();
        LegisUtil task = new LegisUtil((LegisUtil.LegisListener) this);
        task.execute();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_legislators) {
            setTitle("Legislators");
            layout_legis.setVisibility(LinearLayout.VISIBLE);
            layout_bills.setVisibility(LinearLayout.GONE);
            layout_committee.setVisibility(LinearLayout.GONE);
            layout_favorite.setVisibility(LinearLayout.GONE);
        }
        else if(id == R.id.nav_bills){
            setTitle("Bills");
            BillsResponse response = BillsResponse.getInstance();
            if(!(response.getBillsActive().size() > 0)){
                BillsUtil task = new BillsUtil((BillsUtil.BillsListener) this);
                task.execute();
            }
            else{
                onLoadedBills();
            }
            layout_legis.setVisibility(LinearLayout.GONE);
            layout_bills.setVisibility(LinearLayout.VISIBLE);
            layout_committee.setVisibility(LinearLayout.GONE);
            layout_favorite.setVisibility(LinearLayout.GONE);
        }
        else if(id == R.id.nav_committees){
            setTitle("Committees");
            CommitteeResponse response = CommitteeResponse.getInstance();

            if(!(response.getCommitteeHouse().size() > 0)){
                CommitteeUtil task = new CommitteeUtil((CommitteeUtil.CommitteeListener) this);
                task.execute();
            }
            else{
                onLoadedCommittee();
            }
            layout_legis.setVisibility(LinearLayout.GONE);
            layout_bills.setVisibility(LinearLayout.GONE);
            layout_committee.setVisibility(LinearLayout.VISIBLE);
            layout_favorite.setVisibility(LinearLayout.GONE);
        }
        else if(id == R.id.nav_favorites){
            setTitle("Favorites");
            onLoadedFavorite();
            layout_legis.setVisibility(LinearLayout.GONE);
            layout_bills.setVisibility(LinearLayout.GONE);
            layout_committee.setVisibility(LinearLayout.GONE);
            layout_favorite.setVisibility(LinearLayout.VISIBLE);
        }
        else if(id == R.id.nav_aboutme){
            Intent i = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onResume() {
        LegisResponse response = LegisResponse.getInstance();
        if(response.getLegisByState() != null && response.getLegisByState().size() > 0){
            onLoadedFavorite();
        }
        super.onResume();
    }
    private void setupViewPagerLegis(ViewPager viewPager) {
        MainActivity.ViewPagerAdapter adapter = new MainActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ByStatesFragment(), "BY STATES");
        adapter.addFragment(new HouseFragment(), "HOUSE");
        adapter.addFragment(new SenateFragment(), "SENATE");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPagerBills(ViewPager viewPager) {
        MainActivity.ViewPagerAdapter adapter = new MainActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ActiveBillsFragment(), "ACTIVE BILLS");
        adapter.addFragment(new NewBillsFragment(), "NEW BILLS");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPagerCommittee(ViewPager viewPager) {
        MainActivity.ViewPagerAdapter adapter = new MainActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HouseCommitteeFragment(), "HOUSE");
        adapter.addFragment(new SenateCommitteeFragment(), "SENATE");
        adapter.addFragment(new JointCommitteeFragment(), "JOINT");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPagerFavorite(ViewPager viewPager) {
        MainActivity.ViewPagerAdapter adapter = new MainActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FavoriteLegisFragment(), "LEGISLATOR");
        adapter.addFragment(new FavoriteBillsFragment(), "BILLS");
        adapter.addFragment(new FavoriteCommitteeFragment(), "COMMITTEES");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onLoadedBills() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) this.viewPager_bills.getAdapter();
        ActiveBillsFragment activeBillFragment = (ActiveBillsFragment)adapter.getItem(0);
        NewBillsFragment newBillFragment = (NewBillsFragment)adapter.getItem(1);
        activeBillFragment.loadListView();
        newBillFragment.loadListView();
    }

    @Override
    public void onErrorBills() {

    }

    @Override
    public void onLoadedLegis() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) this.viewPager_legis.getAdapter();
        ByStatesFragment stateFragment = (ByStatesFragment)adapter.getItem(0);
        HouseFragment houseFragment = (HouseFragment)adapter.getItem(1);
        SenateFragment senateFragment = (SenateFragment)adapter.getItem(2);
        stateFragment.loadListView();
        houseFragment.loadListView();
        senateFragment.loadListView();
    }

    @Override
    public void onErrorLegis() {

    }

    @Override
    public void onLoadedCommittee() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) this.viewPager_committee.getAdapter();
        HouseCommitteeFragment houseFragment = (HouseCommitteeFragment)adapter.getItem(0);
        SenateCommitteeFragment senateFragment = (SenateCommitteeFragment)adapter.getItem(1);
        JointCommitteeFragment jointFragment = (JointCommitteeFragment)adapter.getItem(2);
        houseFragment.loadListView();
        senateFragment.loadListView();
        jointFragment.loadListView();
    }

    @Override
    public void onErrorCommittee() {

    }

    private void onLoadedFavorite() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) this.viewPager_favorite.getAdapter();
        FavoriteLegisFragment legisFragment = (FavoriteLegisFragment)adapter.getItem(0);
        FavoriteBillsFragment billsFragment = (FavoriteBillsFragment)adapter.getItem(1);
        FavoriteCommitteeFragment committeeFragment = (FavoriteCommitteeFragment)adapter.getItem(2);
        legisFragment.loadListView();
        billsFragment.loadListView();
        committeeFragment.loadListView();
    }

    public void listItemLegisClicked(View view) {
        LegisItemAdapter.LegisHolder holder = (LegisItemAdapter.LegisHolder) view.getTag();
        HashMap values = holder.values;
        Intent i = new Intent(MainActivity.this, ViewDetailsLegisActivity.class);
        i.putExtra("legisModel", values);
        startActivity(i);
    }

    public void listItemBillsClicked(View view) {
        BillsItemAdapter.BillsHolder holder = (BillsItemAdapter.BillsHolder) view.getTag();
        HashMap values = holder.values;
        Intent i = new Intent(MainActivity.this, ViewDetailsBillsActivity.class);
        i.putExtra("billsModel", values);
        startActivity(i);
    }

    public void listItemCommitteeClicked(View view) {
        CommitteeItemAdapter.CommitteeHolder holder = (CommitteeItemAdapter.CommitteeHolder) view.getTag();
        HashMap values = holder.values;
        Intent i = new Intent(MainActivity.this, ViewDetailsCommitteeActivity.class);
        i.putExtra("committeeModel", values);
        startActivity(i);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
