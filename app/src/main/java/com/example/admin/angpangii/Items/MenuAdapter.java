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
 * Created by Admin on 5/3/2016.
 */
public class MenuAdapter extends BaseAdapter {
    private List<MenuList> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public MenuAdapter(Context aContext, List<MenuList> listData) {
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
            convertView = layoutInflater.inflate(R.layout.activity_menu, null);
            holder = new ViewHolder();
            holder.userImageView = (ImageView) convertView.findViewById(R.id.img_user_menu);
            holder.userPostView = (TextView) convertView.findViewById(R.id.lbl_user_menu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MenuList menuList = this.listData.get(position);
        holder.userImageView.setImageResource(R.drawable.ava_null);
        holder.userPostView.setText(menuList.getUserPost());
        return convertView;
    }

    public static class ViewHolder {
        ImageView userImageView;
        TextView userPostView;
    }
}
