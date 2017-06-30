package io.weimu.www.imagepicker.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.yongchun.library.view.ImagePreviewActivity;


import io.weimu.www.imagepicker.activity.PhotoViewPagerActivity;
import io.weimu.www.imagepicker.R;
import io.weimu.www.imagepicker.fragment.base.BaseFragment;

public class ImagePreviewFragment extends BaseFragment {
    private PhotoView photo_view;


    @Override
    protected void findViewByIDS() {
        photo_view = myFindViewsById(R.id.photo_view);
    }


    public static final String PATH = "path";

    public static ImagePreviewFragment newInstance(String path) {
        ImagePreviewFragment fragment = new ImagePreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PATH, path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_preview;
    }

    @Override
    protected void onGenerate() {

        Glide.with(mContext)
                .load(getArguments().getString(PATH))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(480, 800) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        photo_view.setImageBitmap(resource);
                    }
                });

        photo_view.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                PhotoViewPagerActivity activity = (PhotoViewPagerActivity) getActivity();
                activity.switchBarVisibility();
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
    }

}
