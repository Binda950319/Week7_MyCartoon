package com.wbh.week7_mycartoon.mainfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wbh.week7_mycartoon.R;
import com.wbh.week7_mycartoon.manager.CollectActivity;
import com.wbh.week7_mycartoon.manager.MapActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerFragment extends Fragment {


    private TextView manage_collect_tv, manage_allCart_tv;
    private Intent intent;
    private RelativeLayout manage_clearCache_tv, manage_clearLoad_tv;

    public ManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager, container, false);
        manage_collect_tv = ((TextView) view.findViewById(R.id.manage_collect_tv));
        manage_allCart_tv = ((TextView) view.findViewById(R.id.manage_allCart_tv));
        manage_clearCache_tv = ((RelativeLayout) view.findViewById(R.id.manage_clearCache_tv));
        manage_clearLoad_tv = ((RelativeLayout) view.findViewById(R.id.manage_clearLoad_tv));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        manage_collect_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent);
            }
        });
        manage_allCart_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });
        manage_clearCache_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "已清除内存缓冲", Toast.LENGTH_SHORT).show();
            }
        });
        manage_clearLoad_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "已清除本地缓冲", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
