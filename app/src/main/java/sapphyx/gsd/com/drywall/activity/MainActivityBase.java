package sapphyx.gsd.com.drywall.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import sapphyx.gsd.com.drywall.R;
import sapphyx.gsd.com.drywall.fragments.Categories;
import sapphyx.gsd.com.drywall.fragments.Device;
import sapphyx.gsd.com.drywall.fragments.Featured;
import sapphyx.gsd.com.drywall.views.RevealLayout;

public class MainActivityBase extends AppCompatActivity {

    public static final String ARGS_INSTANCE = "sapphyx.gsd.com.drywall.argsInstance";

    private static final int extReadMemory_code = 100;
    private static final int extWriteMemory_code = 101;

    private Context context;
    private RevealLayout mRevealLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new);
        context = this;

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(MainActivityBase.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, extReadMemory_code);

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(MainActivityBase.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, extWriteMemory_code);


        toolbar = findViewById(R.id.toolbar);
        mRevealLayout = findViewById(R.id.frame_layout);

        AppCompatActivity activity = this;
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Explore");
        activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = Device.newInstance(0);
                                mRevealLayout.show(700);
                                break;
                            case R.id.action_item2:
                                selectedFragment = Featured.newInstance(1);
                                mRevealLayout.show(700);
                                break;
                            case R.id.action_item3:
                                selectedFragment = Categories.newInstance(2);
                                mRevealLayout.show(700);
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, Device.newInstance(0));
        transaction.commit();
    }

}