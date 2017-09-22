package com.hxyd.dyt.widget.adapter.accountManager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.GPSInstall;
import com.hxyd.dyt.accountManager.modle.entity.Task;
import com.hxyd.dyt.appraiser.view.AppraiserActivity;
import com.hxyd.dyt.assessment.view.AssessmentActivity;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.gpsinstallation.view.GPSInstallActivity;
import com.hxyd.dyt.vehiclemortgage.view.VehicleMortgageActivity;
import com.hxyd.dyt.widget.holder.accountManager.FooterViewHolder;
import com.hxyd.dyt.widget.holder.accountManager.OrderViewHolder;
import com.hxyd.dyt.widget.holder.accountManager.TaskItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2017/5/24.
 */

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    //    private List<Task.LoanListBean> loanListBean;
    private int footerView = 1;
    private int footerViewStart = 0;
    private int page;
    private int type;
    private List<GPSInstall.ReturnListBean> returnList = new ArrayList<>();

    public TaskAdapter(Context context, int page, int type) {
        this.mContext = context;
        this.page = page;
        this.type = type;
    }

    public void setData(List<GPSInstall.ReturnListBean> list) {
        returnList.addAll(list);
        notifyDataSetChanged();
    }

    public void setShowRefresh(int start) {
        this.footerViewStart = start;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == footerView) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.footer_view, parent, false);
            FooterViewHolder viewHolder = new FooterViewHolder(view);
            return viewHolder;
        }
//        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
//        OrderViewHolder viewHolder = new OrderViewHolder(view);
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_item, parent, false);
        return new TaskItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof OrderViewHolder) {
//            ((OrderViewHolder) holder).mOrderLoaninfoId.setText(loanListBean.get(position).getOrderNo() + "");
//            ((OrderViewHolder) holder).mOrderFlowState.setVisibility(View.GONE);
//            ((OrderViewHolder) holder).mOrderflow.setVisibility(View.GONE);
//            ((OrderViewHolder) holder).mOrderUsername.setText(loanListBean.get(position).getCustomerName());
//            ((OrderViewHolder) holder).mOrderCarframeNo.setText(loanListBean.get(position).getLicencePlate());
//            ((OrderViewHolder) holder).mOrderCarModel.setText(loanListBean.get(position).getCarmodel());
//            String isLocked = loanListBean.get(position).getIsComplete();
//            if (isLocked.equals("1")) {
//                ImageloadTools.load(mContext, R.mipmap.order_item_up, ((OrderViewHolder) holder).mOrderState);
//            } else {
//                ImageloadTools.load(mContext, R.mipmap.order_item_on, ((OrderViewHolder) holder).mOrderState);
//            }
//            ((OrderViewHolder) holder).rl.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, AssessmentActivity.class);
//                    intent.putExtra("systemType", loanListBean.get(position).getSystemType());
//                    intent.putExtra("orderNo", loanListBean.get(position).getOrderNo() + "");
//                    mContext.startActivity(intent);
//                }
//            });
        } else if (holder instanceof TaskItemViewHolder) {


            final GPSInstall.ReturnListBean bean = returnList.get(position);

            TaskItemViewHolder task = (TaskItemViewHolder) holder;

            task.name.setText(bean.getCust_name());
            task.carNum.setText(bean.getPlate_no());
            task.model.setText(bean.getCar_brand());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type == 3) {
                        if (page == 0) {
                            Intent intent = new Intent(v.getContext(), AppraiserActivity.class);
                            intent.putExtra("businessId", bean.getBusiness_id());
                            intent.putExtra("page", page);
                            intent.putExtra("taskId", bean.getTask_id());
                            intent.putExtra("processInstanceId", bean.getProcess_instance_id());
                            intent.putExtra("brand", bean.getCar_brand());
                            v.getContext().startActivity(intent);
                        }
                    } else if (type == 4) {
                        Intent intent = new Intent(v.getContext(), GPSInstallActivity.class);
                        intent.putExtra("businessId", bean.getBusiness_id());
                        intent.putExtra("page", page);
                        intent.putExtra("taskId", bean.getTask_id());
                        intent.putExtra("processInstanceId", bean.getProcess_instance_id());
                        v.getContext().startActivity(intent); //queryGpsCarInfo
                    } else if (type == 5) {
                        Intent intent = new Intent(v.getContext(), VehicleMortgageActivity.class);
                        intent.putExtra("businessId", bean.getBusiness_id());
                        intent.putExtra("page", page);
                        intent.putExtra("taskId", bean.getTask_id());
                        intent.putExtra("processInstanceId", bean.getProcess_instance_id());
                        v.getContext().startActivity(intent);

                    }
                }
            });


        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).status(footerViewStart);
        }
    }

    @Override
    public int getItemCount() {
        return returnList.size() > 0 ? returnList.size() : 0;//null == loanListBean ? 0 : loanListBean.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
//        if (position + 1 == getItemCount()) {
//            return footerView;
//        }
        return super.getItemViewType(position);
    }
}
