package com.hxyd.dyt.widget.adapter.accountManager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hxyd.dyt.R;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

/**
 * Created by win7 on 2017/9/11.
 */

public class BannerAdapter extends StaticPagerAdapter {
    @Override
    public View getView(ViewGroup container, int position) {
        ImageView imageView = (ImageView) container.getChildAt(position);
        if (imageView == null) {
            imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (position == 0) {
            ImageloadTools.load(container.getContext(), R.drawable.banner_one, imageView);
        } else if (position == 1) {
            ImageloadTools.load(container.getContext(), R.drawable.banner_tow, imageView);
        }
        return imageView;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
