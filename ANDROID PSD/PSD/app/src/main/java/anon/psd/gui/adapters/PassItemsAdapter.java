package anon.psd.gui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anon.psd.R;

/**
 * Created by Dmitry on 04.07.2015.
 */
public class PassItemsAdapter<PassItem> extends ArrayAdapter<PassItem>
{
    private static class ViewHolder
    {
        TextView passTitle;
        TextView passLogin;
        TextView passUsedTimes;
    }


    private Context context;
    private ArrayList<PassItem> financeItems;

    public PassItemsAdapter(Context context, int resource, List<PassItem> objects)
    {
        super(context, R.layout.lv_pass_item, objects);
        this.context = context;
        financeItems = (ArrayList<PassItem>) objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
       /* PassItem passItem = (anon.testdesign.PassItem) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lv_pass_item, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.passTitle);
            viewHolder.tvLogin = (TextView) convertView.findViewById(R.id.passLogin);
            viewHolder.tvUsed = (TextView) convertView.findViewById(R.id.passUsed);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(String.format("%s", passItem.Title));
        viewHolder.tvLogin.setText(String.format("Login: %s %s", passItem.Login, passItem.EnterWithLogin ? "(will be entered)" : ""));//they don't have empty string.
        viewHolder.tvUsed.setText(String.format("Used %s times", "0"));*/

        return convertView;
    }
}
