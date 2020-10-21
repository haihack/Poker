package com.atone.poker.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.atone.poker.R;
import com.scwang.smart.drawable.ProgressDrawable;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;


public class CustomFragmentHeader extends LinearLayout implements RefreshHeader {

    LottieAnimationView mLottieAnimationView;
    private TextView mHeaderText;//标题文本
    private ImageView mArrowView;//下拉箭头
    private ImageView mProgressView;//刷新动画视图
    private ProgressDrawable mProgressDrawable;//刷新动画

    public CustomFragmentHeader(Context context) {
        this(context, null);
    }

    public CustomFragmentHeader(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_header, this, false);
        mLottieAnimationView = view.findViewById(R.id.lottieAnimationView);
        addView(view);
//            addView(mProgressView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
//            addView(mArrowView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
//            addView(new Space(context), DensityUtil.dp2px(20), DensityUtil.dp2px(20));
//            addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setMinimumHeight(dp2px(60));
        setMinimumHeight(dp2px(60));
    }

    public int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout layout, int height, int maxDragHeight) {
        mLottieAnimationView.loop(true);
        mLottieAnimationView.setAnimation(R.raw.pull_refresh_animation);
        mLottieAnimationView.playAnimation();

    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {

        if (success) {
            mLottieAnimationView.pauseAnimation();
        } else {

        }
        return 50;// Trả lại sau thời gian trễ 500 mili giây
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mLottieAnimationView.setAnimation(R.raw.loading_red_animation);
                break;
            case Refreshing:

                break;
            case ReleaseToRefresh:

                break;
        }
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        mLottieAnimationView.setProgress(percent);
    }

//        @Override
//        public void onReleasing(float percent, int offset, int height, int maxDragHeight) {
//
//        }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
}