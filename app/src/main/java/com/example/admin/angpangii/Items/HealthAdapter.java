package com.example.admin.angpangii.Items;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.angpangii.R;

import java.util.List;

/**
 * Created by Admin on 5/7/2016.
 */
public class HealthAdapter extends BaseAdapter {
    private List<Health> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public HealthAdapter(Context aContext, List<Health> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_health, null);
            holder = new ViewHolder();
            holder.childAvatarView = (ImageView) convertView.findViewById(R.id.childAva);
            holder.childNameView = (TextView) convertView.findViewById(R.id.childName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Health health = this.listData.get(position);
        holder.childNameView.setText(health.getChildName());
        int avaImage = getDrawableResIdByName(health.getChildAvatar());
        holder.childAvatarView.setImageResource(avaImage);
        return convertView;
    }

    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục drawable).
    public int getDrawableResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName , "drawable", pkgName);
        Log.i("CustomListView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

    public static class ViewHolder {
        ImageView childAvatarView;
        TextView childNameView;
    }
}
