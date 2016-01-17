package anon.psd.gui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import anon.psd.R;

/**
 * Created by Dmitry on 13.11.2015.
 */
public class RollbackActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rollback);
    }

    public void onDoNothingClick(View view)
    {
        finish();
    }

    public void onRollKeysClick(View view)
    {

    }
}
