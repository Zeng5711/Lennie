package com.hxyd.dyt.main.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.widget.RecyclerSpace;
import com.hxyd.dyt.widget.adapter.main.ShowPageAllAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/9/7.
 */

public class ShowPageAllFragment extends Fragment {


    @BindView(R.id.rv)
    RecyclerView rv;


    private ShowPageAllAdapter mAdapter;

    public static ShowPageAllFragment newInstance(int page) {
        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, page);
        ShowPageAllFragment pageFragment = new ShowPageAllFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_page_all, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        mAdapter = new ShowPageAllAdapter();
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new RecyclerSpace(20));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
