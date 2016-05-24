package com.example.admin.angpangii.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.admin.angpangii.R;
import com.example.admin.angpangii.utils.AppController;

/**
 * Created by Admin on 5/24/2016.
 */
public class SubAlbumAdapter extends BaseAdapter {

    String[] imageId;
    private LayoutInflater layoutInflater;
    private Context context;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public SubAlbumAdapter(Context aContext, String[] albImages) {
        this.context = aContext;
        this.imageId = albImages;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return imageId.length;
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
            convertView = layoutInflater.inflate(R.layout.layout_subalbum, null);
            holder = new ViewHolder();
            holder.albumImageView = (NetworkImageView) convertView.findViewById(R.id.imageAlbum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        holder.albumImageView.setImageUrl(imageId[position], imageLoader);

        return convertView;
    }

    public static class ViewHolder {
        NetworkImageView albumImageView;
    }
}
