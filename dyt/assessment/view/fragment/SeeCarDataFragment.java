package com.hxyd.dyt.assessment.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.assessment.modle.entity.CarData;
import com.hxyd.dyt.assessment.presenter.AssessmentP;
import com.hxyd.dyt.assessment.presenter.in.AssessmentPI;
import com.hxyd.dyt.assessment.view.AssessmentActivity;
import com.hxyd.dyt.assessment.view.in.AssessmentVI;
import com.hxyd.dyt.common.entity.ImageItem;
import com.hxyd.dyt.common.uitl.BusEvent;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.adapter.assessment.SeeCarDataAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by win7 on 2017/5/25.
 */

public class SeeCarDataFragment extends Fragment implements AssessmentVI {

    @BindView(R.id.see_car_data_rv)
    RecyclerView rv;

    private AssessmentPI P;
    private String systemType = "";
    private String orderNo = "";

    private SeeCarDataAdapter seeCarDataAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        P = new AssessmentP(this);
        orderNo = getArguments().getString("orderNo");
        systemType = getArguments().getString("systemType");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.see_car_data_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderNo);
        map.put("systemType", systemType);

        P.evaluateInformationDetails(map);


        seeCarDataAdapter = new SeeCarDataAdapter(getContext());
        rv.setAdapter(seeCarDataAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(final BusEvent event) {
        switch (event.event) {
            case BusEvent.SEE_CAR_DATA_IMAGE:
                if (event.imageItem != null) {
                    showImageDetail(event.imageItem);
                }
                break;
            default:
                break;
        }
    }

    private void showImageDetail(ImageItem item) {
        PhotoPreview.builder()
                .setPhotos((ArrayList<String>) item.list)
                .setAction(1)
                .setCurrentItem(item.currentItem)
                .setShowActiononly()
                .setKey(item.stype)
                .setPosition(item.position)
                .isShowDelete(item.isShowDelete)
                .start(getContext(), this);
    }

    @Override
    public void onPrompt(int type, String message) {
        Tools.makeText(message);
    }

    @Override
    public void showDialog(int type, String title, String message) {
        ((AssessmentActivity) getActivity()).showProgressDialog(message);
    }

    @Override
    public void colseDialog() {
        ((AssessmentActivity) getActivity()).dismiss();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(CarData carData) {

        seeCarDataAdapter.setCarData(carData);
    }
}
