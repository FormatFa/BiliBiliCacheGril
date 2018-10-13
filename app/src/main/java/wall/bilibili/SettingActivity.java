package wall.bilibili;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import formatfa.common.UpdateUtils;

public class SettingActivity extends PreferenceActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        findPreference("checkversion").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new UpdateUtils(SettingActivity.this).checkUpdate("http://api.formatfa.top/bilibili/update.txt");

                return false;
            }
        });
        
    }
}
