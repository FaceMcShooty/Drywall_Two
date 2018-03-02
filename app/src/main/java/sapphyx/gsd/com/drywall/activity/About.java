package sapphyx.gsd.com.drywall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import de.psdev.licensesdialog.LicensesDialog;
import sapphyx.gsd.com.drywall.R;
import sapphyx.gsd.com.drywall.fragments.WallpaperActions;

/**
 * Created by ry on 2/11/18.
 */

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.wallpaper_actions);

        View touchOutSide = findViewById(R.id.touch_outside);
        touchOutSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getFragmentManager().beginTransaction()
                .replace(R.id.quick_content, new About.AboutFragment())
                .commit();
    }

    public static class AboutFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.about);

            Preference prefLicense = findPreference("pref_license");
            prefLicense.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new LicensesDialog.Builder(getActivity())
                            .setNotices(R.raw.licenses)
                            .build()
                            .show();
                    return false;
                }
            });

            Preference prefMore = findPreference("pref_more");
            prefMore.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    final Intent intent = new Intent(getActivity(), AboutDev.class);
                    startActivity(intent);
                    return false;
                }
            });
        }
    }
}