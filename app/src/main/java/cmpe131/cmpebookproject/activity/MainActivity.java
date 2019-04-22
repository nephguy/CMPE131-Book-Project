package cmpe131.cmpebookproject.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cmpe131.cmpebookproject.R;

public class MainActivity extends AppCompatActivity {

    private String data;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private static final String MESSAGE = "HELLO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // Go to other activity when clicked
    public void onButtonClick(View view) {

      Intent intent = new Intent(this, TestingActivity.class);
       startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
        }

    // Call when other activity is done
    // Data should be changed to whatever data needs to be altered
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Check other activity is fine with ok result
            if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {

                    // Get String data from Intent
                    String returnString = data.getStringExtra(MainActivity.MESSAGE);

                    // Set text view with new string
                    TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText(returnString);
                }
            }
        }
    }
