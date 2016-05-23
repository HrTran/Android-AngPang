package com.example.admin.angpangii.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.angpangii.Adapters.StatusAdapter;
import com.example.admin.angpangii.Adapters.Status;
import com.example.admin.angpangii.R;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Admin on 5/3/2016.
 */
public class TabWallActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_wall, container, false);
        /*
        * Display status on Home Activity
        * */
        List<Status> image_details = getListData();
        final ListView listView = (ListView) view.findViewById(R.id.listW);
        listView.setAdapter(new StatusAdapter(getActivity(), image_details));
        listView.setFastScrollEnabled(true);
        listView.setScrollingCacheEnabled(false);

        // When user click in items in listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Status status = (Status) o;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                /* Create an Intent that will start the Status-Activity. */
                        Intent mainIntent = new Intent(getActivity(), StatusActivity.class);
                        startActivity(mainIntent);

                    }
                }, 0);

            }
        });
        return view;
    }


    /**
     * Method to make data for listview
     */
    private List<Status> getListData() {
        List<Status> list = new ArrayList<Status>();
        Status stt1 = new Status("Cô giáo Thảo", "f5", "21/04/2016 5:59p.m", "Một ngày đẹp trời. Thời tiết thật dễ chịu.", "hinhanh1");
        Status stt2 = new Status("Cô giáo Nga", "f5", "20/04/2016 4:55 p.m", "Càng lúc càng yêu nghề", "hinhanh2");
        Status stt3 = new Status("Cô giáo Thu", "f5", "19/04/2016 3:30 p.m", "Nhiều lúc chỉ muốn ngồi nhìn các con chơi", "hinhanh3");
        Status stt4 = new Status("Cô giáo Hà", "f5", "19/04/2016 10:01 a.m", "Đi đâu đó cũng nhớ về đây", "hinhanh1");
        Status stt5 = new Status("Cô giáo Thủy", "f5", "18/04/2016 9:23 a.m", "Chưa bao giờ cảm thấy yêu nghề thế này", "hinhanh2");
        Status stt6 = new Status("Cô giáo Thúy", "f5", "18/04/2016 8:36 a.m", "Mùa hè đến rồi!", "hinhanh3");
        Status stt7 = new Status("Cô giáo Tú", "f5", "18/04/2016 8:00 a.m", "", "hinhanh1");


        list.add(stt1);
        list.add(stt2);
        list.add(stt3);
        list.add(stt4);
        list.add(stt5);
        list.add(stt6);
        list.add(stt7);

        return list;
    }
}
