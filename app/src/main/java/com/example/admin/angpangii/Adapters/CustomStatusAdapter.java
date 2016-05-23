package com.example.admin.angpangii.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.AppController;

import java.util.List;

/**
 * Created by Admin on 4/20/2016.
 */
public class CustomStatusAdapter extends BaseAdapter {
    private List<Status> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomStatusAdapter(Context aContext, List<Status> listData) {
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
            convertView = layoutInflater.inflate(R.layout.layout_subwall, null);
            holder = new ViewHolder();
            holder.statusImageView = (ImageView) convertView.findViewById(R.id.statusImageId);
            holder.avatarImageView = (ImageView) convertView.findViewById(R.id.imageAvatar);
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
        int avaImage = getDrawableResIdByName(status.getAvatarImage());
        int staImage = getDrawableResIdByName(status.getStatusImage());
        holder.avatarImageView.setImageResource(avaImage);
        holder.statusImageView.setImageResource(staImage);
        //thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
        //source.setText("Wealth Source: " + String.valueOf(m.getSource()));

        /*holder.avatarImageView.getResources().getIdentifier(status.getAvatarImage(), "drawable", MainActivity.PACKAGE_NAME);
        holder.statusImageView.getResources().getIdentifier(status.getStatusImage(), "drawable", MainActivity.PACKAGE_NAME);*/
        return convertView;
    }

    // Tìm ID của Image ứng với tên của ảnh (Trong thư mục drawable).
    public int getDrawableResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName , "drawable", pkgName);

        return resID;
    }

    public static class ViewHolder {
        ImageView statusImageView;
        ImageView avatarImageView;
        TextView userNameView;
        TextView statusDateView;
        TextView statusTextView;
    }
}
