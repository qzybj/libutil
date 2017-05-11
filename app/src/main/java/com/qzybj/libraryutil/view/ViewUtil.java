package com.qzybj.libraryutil.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.qzybj.libraryutil.CLog;


public class ViewUtil {

    /**
     * 调整view根据指定的宽度以及图片的宽高，按照比例计算view的高度，并设置值
     * @param adjustView  目标view
     * @param adjustViewWidth view期望的宽度
     * @param imgWidth	图片的宽度
     * @param imgHeiht	图片的高度
     */
    public static void autoAdjustView(View adjustView, int adjustViewWidth, float imgWidth, float imgHeiht) {
        if (adjustView != null &&adjustViewWidth > 0 && imgWidth > 0 && imgHeiht > 0) {
            ViewGroup.LayoutParams lp = adjustView.getLayoutParams();
            float rate = adjustViewWidth / imgWidth;//算出宽度缩放比
            int height = (int) (imgHeiht * rate);//根据缩放比算出高度
            if (lp == null) {
                lp = new ViewGroup.LayoutParams(adjustViewWidth, height);
            } else {
                lp.width = adjustViewWidth;
                lp.height = height;
            }
            adjustView.setLayoutParams(lp);
        }
    }
    /**
     * 获取view的内部可用宽度
     * @param view
     * @return  int[]  [0]可用宽     [1]可用高
     */
    public static int[] getViewContainerWidth(View view) {
        int[] dimens = {0,0};
        dimens[0] = view.getMeasuredWidth() - (view.getPaddingLeft()+view.getPaddingRight());
        dimens[1] = view.getMeasuredHeight() - (view.getPaddingTop()+view.getPaddingBottom());
        return dimens;
    }
    public static void setListViewHeightBasedOnChildren(AdapterView<ListAdapter> listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        //params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public  static int  getChildHeight(AdapterView<ListAdapter> listView) {
        int childHeight = 0;
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return childHeight;
        }
        if (listAdapter.getCount()>0) {
            View listItem = listAdapter.getView(0, null, listView);
            listItem.measure(0, 0);
            childHeight = listItem.getMeasuredHeight();
        }
        return childHeight;
    }

    /**
     * 根据layout布局填充View
     * @param con
     * @param layoutResId
     * @return
     */
    public static View inflateView(Context con, int layoutResId) {
        try {
            if (layoutResId > 0) {
                return LayoutInflater.from(con).inflate(layoutResId, null, false);
            }
        } catch (Exception e) {
            CLog.e(e);
        }
        return null;
    }

    /**
     * View 当前是否为显示的
     * @param view
     */
    public static boolean isVisibleView(View view) {
        if(view!=null&&view.getVisibility()== View.VISIBLE){
            return true;
        }
        return false;
    }
}
