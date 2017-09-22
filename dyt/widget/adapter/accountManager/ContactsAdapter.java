package com.hxyd.dyt.widget.adapter.accountManager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hxyd.dyt.R;
import com.hxyd.dyt.accountManager.modle.entity.Contact;
import com.hxyd.dyt.common.uitl.Tools;
import com.hxyd.dyt.widget.holder.accountManager.ContactsViewHolder;
import com.hxyd.dyt.widget.holder.accountManager.JgzErrorViewHolder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * Created by win7 on 2017/5/17.
 */

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Contact> contacts;

    private int CONTACT_VIEW = 0;
    private int ERROR_VIEW = 1;
    private boolean isShowEorC = true;
    private ContactsonClickCallback listener;
    private View view;
    private View view2;
    private long clickTime = 0;
    private int selectPosition = -1;


    public ContactsAdapter(ArrayList<Contact> contacts, ContactsonClickCallback listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    public void isShowEorC(boolean isShowEorC) {
        this.isShowEorC = isShowEorC;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == CONTACT_VIEW) {
            View view = inflater.inflate(R.layout.jzg_item, null);
            viewHolder = new ContactsViewHolder(view);
        } else if (viewType == ERROR_VIEW) {
            View view = inflater.inflate(R.layout.jzg_err, null);
            viewHolder = new JgzErrorViewHolder(view);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            if (holder instanceof ContactsViewHolder && contacts != null && !contacts.isEmpty()) {
                final Contact contact = contacts.get(position);
                if (position == 0 || !contacts.get(position - 1).getIndex().equals(contact.getIndex())) {
                    ((ContactsViewHolder) holder).tvIndex.setVisibility(View.VISIBLE);
                    ((ContactsViewHolder) holder).tvIndex.setText(contact.getIndex());
                } else {
                    ((ContactsViewHolder) holder).tvIndex.setVisibility(View.GONE);
                }
                ((ContactsViewHolder) holder).tvName.setText(contact.getName());
                final RelativeLayout rlView = ((ContactsViewHolder) holder).rlView;



                if (contact.isCheck()) {
                    ((ContactsViewHolder) holder).view.setBackgroundResource(R.color.jzg_color_view);
                    rlView.setBackgroundResource(R.color.jzg_color);
                } else {
                    rlView.setBackgroundResource(R.color.default_whit);
                    ((ContactsViewHolder) holder).view.setBackgroundResource(R.color.default_whit);
                }
                rlView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if ((System.currentTimeMillis() - clickTime) > 1000) {
                            clickTime = System.currentTimeMillis();
                            if (listener != null) {

                                if (selectPosition != -1 && view != null && view2!=null) {
                                    contacts.get(selectPosition).setCheck(false);
                                    view.setBackgroundResource(R.color.default_whit);
                                    view2.setBackgroundResource(R.color.default_whit);
                                    ContactsAdapter.this.notifyItemChanged(selectPosition);
                                }
                                selectPosition = position;
                                view = v;
                                view2 = ((ContactsViewHolder) holder).view;
                                view2.setBackgroundResource(R.color.jzg_color_view);
                                contacts.get(position).setCheck(true);
                                v.setBackgroundResource(R.color.jzg_color);
                                listener.onClick(v, contact);
                            }

                        }
                    }
                });
            } else if (holder instanceof JgzErrorViewHolder) {

            }
        }
    }

    @Override
    public int getItemCount() {
        if (isShowEorC) {
            return contacts.size();
        }
        return 1;

    }

    @Override
    public int getItemViewType(int position) {
        if (isShowEorC) {
            return CONTACT_VIEW;
        }
        return ERROR_VIEW;


    }

    public interface ContactsonClickCallback {
        void onClick(View v, Contact contact);
    }
}
