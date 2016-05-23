package com.example.admin.angpangii.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.angpangii.Fragments.MainActivity;
import com.example.admin.angpangii.Items.NoticeAdapter;
import com.example.admin.angpangii.Items.NoticeItems;
import com.example.admin.angpangii.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/3/2016.
 */
public class TabNoticeActivity extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_notice, container, false);
        /*
        * Hien thi stt tren HomeScreen
        * */
        List<NoticeItems> notice_details = getListData();
        final ListView listView = (ListView) view.findViewById(R.id.listN);
        listView.setAdapter(new NoticeAdapter(getActivity(), notice_details));
        listView.setFastScrollEnabled(true);
        listView.setScrollingCacheEnabled(false);

        // Khi người dùng click vào các ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                NoticeItems noticeItems = (NoticeItems) o;
                Toast.makeText(getActivity(), "Selected :" + " " + noticeItems, Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


    /**
     * Method to make data for listview
     */
    private List<NoticeItems> getListData() {
        List<NoticeItems> list = new ArrayList<NoticeItems>();
        NoticeItems not1 = new NoticeItems("Cô giáo Thảo", "f5", " đã đăng một trạng thái mới ", "\"Hôm nay có buổi dã ngoại\"", "anh1");
        NoticeItems not2 = new NoticeItems("Cô giáo Nga", "f5", " đã bình luận trạng thái của bạn ", "\"Ảnh này đẹp tuyệt\"", "anh2");
        NoticeItems not3 = new NoticeItems("Cô giáo Thu", "f5", " đã thích bình luận của bạn ", "", "anh3");
        NoticeItems not4 = new NoticeItems("Cô giáo Hà", "f5", " đã đăng 1 ảnh mới vào album ", "", "anh4");
        NoticeItems not5 = new NoticeItems("Cô giáo Thủy", "f5", " đã đăng thực đơn mới của ngày hôm nay ", "", "anh5");
        NoticeItems not6 = new NoticeItems("Cô giáo Thúy", "f5", " thay đổi ảnh trang cá nhân của cô ấy ", "", "anh6");
        NoticeItems not7 = new NoticeItems("Cô giáo Tú", "f5", " cảm thấy thật tuyệt vời ", "\"các em ngoan ngoãn ngồi học\"", "anh7");
        NoticeItems not8 = new NoticeItems("Cô giáo Hà", "f5", " đã đăng 1 ảnh mới vào album ", "", "anh4");
        NoticeItems not9 = new NoticeItems("Cô giáo Hà", "f5", " đã đăng 1 ảnh mới vào album ", "", "anh4");
        NoticeItems not10 = new NoticeItems("Cô giáo Hà", "f5", " đã đăng 1 ảnh mới vào album ", "", "anh4");


        list.add(not1);
        list.add(not2);
        list.add(not3);
        list.add(not4);
        list.add(not5);
        list.add(not6);
        list.add(not7);
        list.add(not8);
        list.add(not9);
        list.add(not10);

        return list;
    }
}
