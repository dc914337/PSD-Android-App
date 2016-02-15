package anon.psd.gui.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import anon.psd.R;

/**
 * Created by Dmitry on 14.08.2015.
 */
public class PairedDevicesAdapter<T extends BluetoothDevice> extends ArrayAdapter<T>
{
    private static class ViewHolder
    {
        TextView name;
        TextView mac;
    }

    public PairedDevicesAdapter(Context context, ArrayList<BluetoothDevice> devices)
    {
        super(context, R.layout.lv_pass_item, (java.util.List<T>) devices);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        BluetoothDevice pairedDevice = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lv_paired_bt_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textName);
            viewHolder.mac = (TextView) convertView.findViewById(R.id.textMac);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String name = pairedDevice.getName();
        String mac = pairedDevice.getAddress();
        viewHolder.name.setText(String.format("%s", name));
        viewHolder.mac.setText(String.format("%s", mac));

        return convertView;
    }
}
