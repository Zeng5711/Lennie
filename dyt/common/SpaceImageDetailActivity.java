package com.hxyd.dyt.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hxyd.dyt.R;
import com.hxyd.dyt.common.uitl.ImageloadTools;
import com.hxyd.dyt.widget.SmoothImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.widget.MultiPickResultView;

public class SpaceImageDetailActivity extends Activity {

//    @BindView(R.id.recycler_view)
    MultiPickResultView multiPickResultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
        String mDatas = getIntent().getStringExtra("images");
//        int mPosition = getIntent().getIntExtra("position", 0);
//        int mLocationX = getIntent().getIntExtra("locationX", 0);
//        int mLocationY = getIntent().getIntExtra("locationY", 0);
//        int mWidth = getIntent().getIntExtra("width", 0);
//        int mHeight = getIntent().getIntExtra("height", 0);
//
//       final SmoothImageView imageView = new SmoothImageView(this);
//        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
//        imageView.transformIn();
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
//        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageView.transformOut();
//                finish();
//            }
//        });

        setContentView(R.layout.activity_space_image_detail);
        multiPickResultView = (MultiPickResultView)findViewById(R.id.recycler_view);
        ArrayList<String> photos = new ArrayList<String>();
        photos.add(mDatas);
        multiPickResultView.init(this,MultiPickResultView.ACTION_ONLY_SHOW,photos);
//        ImageloadTools.load(this,mDatas,imageView);
//        Glide.with(this)
//                .load(mDatas)
//                .placeholder(R.mipmap.load)
//                .priority(Priority.HIGH)
//                .bitmapTransform(new MyTransformation(this))
//                .into(imageView);
//        ImageLoader.getInstance().displayImage(mDatas.get(mPosition), imageView);
    }

    private static class MyTransformation extends BitmapTransformation {

        public MyTransformation(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
            // 如果BitmapPool中找不到符合该条件的Bitmap，get()方法会返回null，就需要我们自己创建Bitmap了
            if (result == null) {
                // 如果想让Bitmap支持透明度，就需要使用ARGB_8888
                result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
            }
            //创建最终Bitmap的Canvas.
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setAlpha(128);
            // 将原始Bitmap处理后画到最终Bitmap中
            canvas.drawBitmap(toTransform, 0, 0, paint);
            // 由于我们的图片处理替换了原始Bitmap，就return我们新的Bitmap就行。
            // Glide会自动帮我们回收原始Bitmap。
            return result;
        }

        @Override
        public String getId() {
            // Return some id that uniquely identifies your transformation.
            return "com.example.myapp.MyTransformation";
        }
    }

}
