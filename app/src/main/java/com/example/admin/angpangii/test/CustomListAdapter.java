package com.example.admin.angpangii.test;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.angpangii.R;

import java.util.List;

/**
 * Created by Admin on 4/20/2016.
 */
public class CustomListAdapter extends BaseAdapter {
    private List<Status> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context aContext,  List<Status> listData) {
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
            convertView = layoutInflater.inflate(R.layout.status_layout, null);
            holder = new ViewHolder();
            holder.statusImageView = (ImageView) convertView.findViewById(R.id.statusImageId);
            holder.userNameView = (TextView) convertView.findViewById(R.id.userName);
            holder.statusDateView = (TextView) convertView.findViewById(R.id.statusDate);
            holder.statusTextView = (TextView) convertView.findViewById(R.id.statusText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Status status = this.listData.get(position);
        holder.userNameView.setText(status.getUsername());
        holder.statusDateView.setText("Hanoi, " + status.getStatusTime());
        holder.statusTextView.setText(status.getStatusText());
        int imageId = this.getMipmapResIdByName(status.getStatusImage());

        holder.statusImageView.setImageResource(imageId);

        return convertView;
    }

    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

    static class ViewHolder {
        ImageView statusImageView;
        TextView userNameView;
        TextView statusDateView;
        TextView statusTextView;
    }
}
