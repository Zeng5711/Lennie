package com.hxyd.dyt.widget.adapter.accountManager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hxyd.dyt.accountManager.view.fragment.TaskFragment;

/**
 * Created by win7 on 2017/5/17.
 */

public class AccountTaskAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{ "待办",  "已办"};
    private Context context;
    private int type;

    public AccountTaskAdapter(FragmentManager fm, Context context,int type) {
        super(fm);
        this.context = context;
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        return TaskFragment.newInstance(position,type);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
