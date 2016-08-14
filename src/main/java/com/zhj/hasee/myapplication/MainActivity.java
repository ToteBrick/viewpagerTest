package com.zhj.hasee.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    protected ViewPager mViewpager;
    protected TextView tvTitle;
    protected LinearLayout mContainer;
    private List<ImageView> mDatas;
    public ADAdapter mAdapter;
    private int[] icons = new int[]{R.mipmap.icon_1, R.mipmap.icon_2, R.mipmap.icon_3, R.mipmap.icon_4, R.mipmap.icon_5};
    private String[] titles = new String[]{"图片1","图片2","图片3","图片4","图片5"};
//    private int[] icons = new int[]{R.drawable.icon_1, R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4, R.drawable.icon_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.content_main);
        initView();

        initData();
    }


    private void initView() {
        mViewpager = (ViewPager) findViewById(R.id.main_viewpager);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        mContainer = (LinearLayout) findViewById(R.id.main_point_container);
        mViewpager.setOffscreenPageLimit(2); //预加载 左右各两个（左边有的话）
    }

    //加载数据
    private void initData() {
        mAdapter = new ADAdapter();
        mViewpager.setAdapter(mAdapter);


        //加载list数据
        mDatas = new ArrayList<ImageView>();
        for (int i = 0; i < icons.length; i++) {
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            ImageView iv = new ImageView(MainActivity.this);
            iv.setImageResource(icons[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mDatas.add(iv);

            //选中中间的页面
            int item = Integer.MAX_VALUE/2;
            item = item - item % mDatas.size(); //item值
            mViewpager.setCurrentItem(item);

            mAdapter.notifyDataSetChanged();

            View point = new View(this);
            point.setBackgroundResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,10);
           if(i !=0){
            params.leftMargin = 10;
           }else {
               //默认选中
               tvTitle.setText(titles[i]);
            point.setBackgroundResource(R.drawable.point_selected);

           }
            mContainer.addView(point,params);

        }

        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //positionOffset 滑动的百分比，positionOffsetPixels 滑动的像素值
//                Log.d("MainActivity", "onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("MainActivity", "onPageSelected"); //日志为啥打不出来却能实现

                position = position % mDatas.size();
                //改变点的样式
                int count = mContainer.getChildCount();

                for (int i = 0; i < count; i++) {

                    View view = mContainer.getChildAt(i);

                    if (i == position) {
                        //选中
                        view.setBackgroundResource(R.drawable.point_selected);
                        tvTitle.setText(titles[i]);
                    } else {
                        view.setBackgroundResource(R.drawable.point_normal);
                    }
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("MainActivity", "onPageScrollStateChanged");

//                switch (state)
            }
        });



    }


    public class ADAdapter extends PagerAdapter {

        @Override
        public int getCount() {//返回最大的数量

            if (mDatas != null) {
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //展示view,标记
            return view == object; //如果一致表示加载过了
        }

        //初始化页面
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            position = position % mDatas.size();

            ImageView view = mDatas.get(position);

            container.addView(view);  //作展示

            return view;  //作标记
        }

        //销毁页面的回调
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //给viewpager移除当前view和数据 ，销毁第position个
            container.removeView((View) object);
        }
    }

}
