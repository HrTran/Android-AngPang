package com.example.admin.angpangii.Items;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.angpangii.Fragments.LoginActivity;
import com.example.admin.angpangii.Fragments.StatusActivity;
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
        * Hien thi stt tren HomeScreen
        * */
        List<Status > image_details = getListData();
        final ListView listView = (ListView) view.findViewById(R.id.listW);
        listView.setAdapter(new CustomStatusAdapter(getActivity(), image_details));
        listView.setFastScrollEnabled(true);
        listView.setScrollingCacheEnabled(false);

        // Khi người dùng click vào các ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Status status = (Status) o;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                /* Create an Intent that will start the Main-Activity. */
                        Intent mainIntent = new Intent(getActivity(), StatusActivity.class);
                        startActivity(mainIntent);
                        getActivity().finish();
                    }
                }, 0);

            }
        });
        return view;
    }



    private List<Status> getListData() {
        List<Status> list = new ArrayList<Status>();
        Status stt1 = new Status("Cô giáo Thảo", "f5", "21/04/2016 5:59p.m", "Ngày đẹp trời", "anh1");
        Status stt2 = new Status("Cô giáo Nga", "f5", "20/04/2016 4:55 p.m", "Ngày mát trời", "anh2");
        Status stt3 = new Status("Cô giáo Thu", "f5", "19/04/2016 3:30 p.m", "Ngày xấu trời", "anh3");
        Status stt4 = new Status("Cô giáo Hà", "f5", "19/04/2016 10:01 a.m", "Ngày đen trời", "anh4");
        Status stt5 = new Status("Cô giáo Thủy", "f5", "18/04/2016 9:23 a.m", "Ngày ẩm trời", "anh5");
        Status stt6 = new Status("Cô giáo Thúy", "f5", "18/04/2016 8:36 a.m", "Ngày bầu trời", "anh6");
        Status stt7 = new Status("Cô giáo Tú", "f5", "18/04/2016 8:00 a.m", "Ngày ông trời", "anh7");


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
