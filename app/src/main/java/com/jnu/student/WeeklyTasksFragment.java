package com.jnu.student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeeklyTasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeklyTasksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeeklyTasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeeklyTasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeeklyTasksFragment newInstance(String param1, String param2) {
        WeeklyTasksFragment fragment = new WeeklyTasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private com.tencent.tencentmap.mapsdk.maps.TextureMapView mapView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weekly_tasks, container, false);
        mapView = rootView.findViewById(R.id.mapView);

        TencentMap tencentMap = mapView.getMap();
        tencentMap.setBuildingEnable(false);

        LatLng jinanUniversityLatLng = new LatLng(22.249942,113.534341);

        tencentMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jinanUniversityLatLng, tencentMap.getMaxZoomLevel()));

        MarkerOptions markerOptions = new MarkerOptions(jinanUniversityLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        Marker iconMarker = tencentMap.addMarker(markerOptions);

        iconMarker.setTitle("珠海暨南大学");

        tencentMap.setOnMarkerClickListener(marker -> {
            // 处理标记点击事件
            if (marker.getTitle() != null && marker.getTitle().equals("珠海暨南大学")) {
                // 在这里执行相应的操作
            }
            return false;
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
            mapView = null;
        }
    }

}