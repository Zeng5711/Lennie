package com.hxyd.dyt.widget.adapter.accountManager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hxyd.dyt.accountManager.view.fragment.HelpRoutineFragment;
import com.hxyd.dyt.accountManager.view.fragment.HelpSingleFragment;
import com.hxyd.dyt.accountManager.view.fragment.HelpTaskFragment;

/**
 * Created by win7 on 2017/5/17.
 */

public class HelpFragmentAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"常规", "录单", "任务"};
    private Context context;

    public HelpFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = HelpRoutineFragment.newInstance(position + 1);
        } else if (position == 1) {
            fragment = HelpSingleFragment.newInstance(position + 1);
        } else if (position == 2) {
            fragment = HelpTaskFragment.newInstance(position + 1);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
