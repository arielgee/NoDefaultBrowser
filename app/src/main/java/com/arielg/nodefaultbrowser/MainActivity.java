package com.arielg.nodefaultbrowser;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView m_browserListView;
    private List<ResolveInfo> m_browserList;
    private Uri m_urlToOpen;

    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_urlToOpen = getIntent().getData();

        if (m_urlToOpen == null) {

            PackageManager pm = getPackageManager();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"));

            if (intent.resolveActivity(pm) != null) {
                String defBrowserPackName = intent.resolveActivity(pm).getPackageName();

                if ( !defBrowserPackName.equals(getApplicationContext().getPackageName()) ) {
                    Toast.makeText(this, "Set NoDefaultBrowser as default browser", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "No URL received", Toast.LENGTH_LONG).show();
                }
            }
            finishAndRemoveTask();
        } else {
            m_browserListView = findViewById(R.id.browserListView);
            loadBrowsers();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onPause() {
        super.onPause();
        finishAndRemoveTask();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onStop() {
        super.onStop();
        finishAndRemoveTask();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    private void loadBrowsers() {

        // get browsers
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, m_urlToOpen);
        PackageManager pm = getPackageManager();
        m_browserList = new ArrayList<>(pm.queryIntentActivities(browserIntent, PackageManager.MATCH_ALL));
        m_browserList.removeIf(resolveInfo -> resolveInfo.activityInfo.packageName.equals(getApplicationContext().getPackageName()));

        Drawable[]      icons = new Drawable[m_browserList.size()];
        String[]        labels = new String[m_browserList.size()];

        for(int i=0, len=m_browserList.size(); i<len; ++i) {
            ResolveInfo browser = m_browserList.get(i);
            icons[i] = browser.loadIcon(pm);
            labels[i] = browser.loadLabel(pm).toString();
        }

        ListViewAdapter adapter = new ListViewAdapter(this, icons, labels);
        m_browserListView.setAdapter(adapter);

        m_browserListView.setOnItemClickListener((parent, view, position, id) -> {

            ResolveInfo selectedBrowser = m_browserList.get(position);

            Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, m_urlToOpen);
            browserIntent1.setPackage(selectedBrowser.activityInfo.packageName);
            browserIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(browserIntent1);
            finishAndRemoveTask();
        });
    }
}
