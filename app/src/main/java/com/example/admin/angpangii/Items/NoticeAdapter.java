package com.example.admin.angpangii.Items;

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
 * Created by Admin on 5/3/2016.
 */

public class NoticeAdapter extends BaseAdapter {
    private List<NoticeItems> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public NoticeAdapter(Context aContext, List<NoticeItems> listData) {
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
            convertView = layoutInflater.inflate(R.layout.layout_subnotice, null);
            holder = new ViewHolder();
            //holder.statusImageView = (ImageView) convertView.findViewById(R.id.img2G);
            holder.avatarImageView = (ImageView) convertView.findViewById(R.id.img1G);
            holder.userNameView = (TextView) convertView.findViewById(R.id.lbl1G);
            holder.noticeActionView = (TextView) convertView.findViewById(R.id.lbl2G);
            //holder.statusTextView = (TextView) convertView.findViewById(R.id.lbl3G);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NoticeItems noticeItems = this.listData.get(position);
        holder.userNameView.setText(noticeItems.getUsername());
        holder.noticeActionView.setText(noticeItems.getNoticeAction());
        //holder.statusTextView.setText(noticeItems.getStatusText());
        int avaImage = getDrawableResIdByName(noticeItems.getAvatarImage());
        int staImage = getDrawableResIdByName(noticeItems.getStatusImage());
        holder.avatarImageView.setImageResource(avaImage);
        //holder.statusImageView.setImageResource(staImage);
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
        ImageView statusImageView;
        ImageView avatarImageView;
        TextView userNameView;
        TextView noticeActionView;
        TextView statusTextView;
    }
}
