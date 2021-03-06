package com.example.admin.angpangii.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.AppController;

import java.util.List;

/**
 * Created by Admin on 5/23/2016.
 */
public class AlbumAdapter extends BaseAdapter {
    String [] imageName;
    String [] imageId;
    private LayoutInflater layoutInflater;
    private Context context;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public AlbumAdapter(Context aContext, String[] albNameList, String[] albImages) {
        this.context = aContext;
        this.imageName = albNameList;
        this.imageId = albImages;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return imageName.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (layoutInflater == null)
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_album, null);
            holder = new ViewHolder();
            holder.albumImageView = (NetworkImageView) convertView.findViewById(R.id.imageAlbum);
            holder.albumNameView = (TextView) convertView.findViewById(R.id.txt_Album);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        holder.albumNameView.setText(imageName[position]);
        holder.albumImageView.setImageUrl(imageId[position],imageLoader);

        return convertView;
    }

    public static class ViewHolder {
        NetworkImageView albumImageView;
        TextView albumNameView;
    }
}
