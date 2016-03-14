package org.mixare;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import org.mixare.data.DataSourceList;
import org.mixare.map.MapActivity;
import org.mixare.settings.SettingsActivity;

/**
 * Created by MelanieW on 30.12.2015.
 */
public class DrawerMenuActivity extends SherlockActivity {

    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle drawerToggle;

    MenuListAdapter menuListAdapter;

    String[] menuItemTitles;
    private CharSequence drawerTitle;
    private CharSequence mTitle;
    TypedArray menuItemIcons;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //killOnError();
            //requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.drawermenu_screen);

            // Get the Title
            mTitle = drawerTitle = getTitle();
            Resources res = getResources();

            // retrieve icons
            menuItemIcons = res.obtainTypedArray(R.array.menu_item_icons);

            // retrieve menuItemTitles
            menuItemTitles = res.getStringArray(R.array.menu_item_titles);



            drawerLayout = (DrawerLayout) findViewById(R.id.drawermenu_screen_drawerlayout);
            drawerList = (ListView) findViewById(R.id.drawermenu_list);

            menuListAdapter = new MenuListAdapter(DrawerMenuActivity.this, menuItemTitles, menuItemIcons);
            drawerList.setAdapter(menuListAdapter);
            drawerList.setOnItemClickListener(new DrawerItemClickListener());
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                    R.drawable.ic_launcher, R.string.drawer_open,
                    R.string.drawer_close) {

                public void onDrawerClosed(View view) {
                    // TODO Auto-generated method stub
                    super.onDrawerClosed(view);
                }

                public void onDrawerOpened(View drawerView) {
                    // TODO Auto-generated method stub
                    super.onDrawerOpened(drawerView);
                }
            };

            drawerLayout.setDrawerListener(drawerToggle);

        }
        catch (Exception ex) {
          //  doError(ex, GENERAL_ERROR);
        }
    }


  public boolean onKeyDown(int keyCode, KeyEvent event) {
      switch(keyCode) {
          case KeyEvent.KEYCODE_MENU:
              if (drawerLayout.isDrawerOpen(drawerList)) {
                  drawerLayout.closeDrawer(drawerList);
              } else {
                  drawerLayout.openDrawer(drawerList);
              }
              return true;
        }
          return super.onKeyDown(keyCode, event);
      }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home ) {
            if (drawerLayout.isDrawerOpen(drawerList)) {
                drawerLayout.closeDrawer(drawerList);
            } else {
                drawerLayout.openDrawer(drawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public void selectItem(int position) {
        int menuItemId=getResources().obtainTypedArray(R.array.menu_item_titles).getResourceId(position,-1);

        switch (menuItemId) {
		    /* Marker List View */
            case R.string.menu_item_route: //fall-through intended
            case R.string.menu_item_list:
                Intent intent3 = new Intent(DrawerMenuActivity.this, MarkerListActivity.class);
                intent3.setAction(Intent.ACTION_VIEW);
                startActivityForResult(intent3, Config.INTENT_REQUEST_CODE_MARKERLIST);
                break;
		    /* Map View */
            case R.string.menu_item_map:
                Intent intent4 = new Intent(DrawerMenuActivity.this, MapActivity.class);
                startActivityForResult(intent4, Config.INTENT_REQUEST_CODE_MAP);
                break;
		    /* Search */
            case R.string.menu_item_search:
                onSearchRequested();
                break;
            case R.string.menu_item_settings:
                Intent intent5 = new Intent(DrawerMenuActivity.this, SettingsActivity.class);
               // new Intent(DrawerMenuActivity.this, );
                startActivityForResult(intent5, Config.INTENT_REQUEST_CODE_SETTINGS);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    public MixViewDataHolder getMixViewData() {
        return MixViewDataHolder.getInstance();
    }
}