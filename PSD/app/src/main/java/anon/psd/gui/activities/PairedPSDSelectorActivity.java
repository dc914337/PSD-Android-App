package anon.psd.gui.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

import anon.psd.R;
import anon.psd.gui.adapters.PairedDevicesAdapter;

/**
 * Created by Dmitry on 14.08.2015.
 */
public class PairedPSDSelectorActivity extends Activity
{
    private static final int REQUEST_ENABLE_BT = 3433;
    ListView lvPairedDevices;
    ArrayList<BluetoothDevice> pairedDevices = new ArrayList<>();
    PairedDevicesAdapter<BluetoothDevice> btArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psd_selector);

        lvPairedDevices = (ListView) findViewById(android.R.id.list);
        TextView emptyText = (TextView) findViewById(android.R.id.empty);
        lvPairedDevices.setEmptyView(emptyText);

        btArrayAdapter = new PairedDevicesAdapter<>(this, pairedDevices);
        lvPairedDevices.setAdapter(btArrayAdapter);
        lvPairedDevices.setClickable(true);
        lvPairedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                returnSelectedDeviceToParentActivity((BluetoothDevice) parent.getItemAtPosition(position));
            }
        });
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        fillPairedDevices();
    }

    private void returnSelectedDeviceToParentActivity(BluetoothDevice selectedDevice)
    {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.disable();

        Intent intent = this.getIntent();
        intent.putExtra(getString(R.string.extras_return_name), selectedDevice.getName());
        intent.putExtra(getString(R.string.extras_return_mac), selectedDevice.getAddress());
        this.setResult(RESULT_OK, intent);
        finish();
    }

    private void fillPairedDevices()
    {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            fillDevices();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                fillDevices();
            }
        }
    }


    private void fillDevices()
    {
        BluetoothAdapter bluetoothAdapter
                = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        btArrayAdapter.clear();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                btArrayAdapter.add(device);
            }
        }
    }

    public void openBluetoothSettings(View view)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName("com.android.settings",
                "com.android.settings.bluetooth.BluetoothSettings");
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
