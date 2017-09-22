package com.hxyd.dyt.widget.holder.assessment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.hxyd.dyt.R;
import com.hxyd.dyt.widget.ImageItemLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by win7 on 2017/5/24.
 */

public class AssessmentViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.assessment_item_spinner)
    public Spinner assessment_item_spinner;

    @BindView(R.id.assessment_item_again)
    public EditText assessment_item_again;

    @BindView(R.id.vehicleinfo_scd)
    public EditText vehicleinfo_scd;

    @BindView(R.id.assessment_submit)
    public Button assessment_submit;

    @BindView(R.id.image_item_ll)
    public ImageItemLinearLayout image_item_ll;

    @BindView(R.id.assesment_again_rl)
    public RelativeLayout assesment_again_rl;

    @BindView(R.id.assesment_image_rl)
    public RelativeLayout assesment_image_rl;

    @BindView(R.id.assesment_image_ll)
    public RelativeLayout assesment_image_ll;

    public AssessmentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public List<View> getImageItemViews(int item, Context context) {
        List<View> list = new ArrayList<View>();
        for (int i = 0; i < item; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, null);
            list.add(view);
        }
        return list;
    }

    public void setSpinnerAdapter(Context context, ArrayList<String> list) {
        ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessment_item_spinner.setAdapter(adapter);
    }
}
