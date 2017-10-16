package be.industria.industria24u.industria24u;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void openApp(View view) {
        Intent intent = new Intent(StartActivity.this, LoginListActivity.class);
        startActivity(intent);
    }
}