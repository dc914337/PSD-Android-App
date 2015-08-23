package anon.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity
{

    Messenger mService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private class MyServiceConnection implements ServiceConnection
    {
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mService = new Messenger(service);
            Log.d("DBG", "[ ACTIVITY! ] Service connected");
            try {
                Message msg = Message.obtain(null, 1);
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName name)
        {
            mService = null;
            Log.d("DBG", "[ ACTIVITY! ] Service disconnected");
        }
    }


    public void onClick(View view)
    {
        ServiceConnection mConnection = new MyServiceConnection();
        startService(new Intent(this, TestService.class));
        bindService(new Intent(this, TestService.class), mConnection, BIND_AUTO_CREATE);


       /* try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        // unbindService(mConnection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
