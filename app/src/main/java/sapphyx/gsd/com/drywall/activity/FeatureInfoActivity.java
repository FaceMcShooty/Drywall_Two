package sapphyx.gsd.com.drywall.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import sapphyx.gsd.com.drywall.R;

/**
 * Created by ry on 2/18/18.
 */

public class FeatureInfoActivity extends AppCompatActivity {

    public String wallDescription, wallTitle;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        wallTitle = getIntent().getStringExtra("wallTitle");
        wallDescription = getIntent().getStringExtra("wallDescription");

        setContentView(R.layout.activity_feature_info);

        View touchOutSide = findViewById(R.id.touch_outside);
        touchOutSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView wallTitleText = findViewById(R.id.wallTitle);
        TextView wallDescriptionText = findViewById(R.id.wallDescription);

        wallTitleText.setText(wallTitle);
        wallDescriptionText.setText(wallDescription);
    }
}
