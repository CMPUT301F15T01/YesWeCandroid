package ca.ualberta.trinkettrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 *
 *
 *
 */
public class DisplayTradesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_trades);
    }

    // PastTrades Button click
    public void openPastTrades(View view){
        Intent intent = new Intent(this, DisplayPastTradesActivity.class);
        startActivity(intent);
    }
}
