package com.wangc.mybook.modle;


import java.util.Arrays;
import java.util.LinkedList;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.wangc.mybook.MainActivity;
import com.wangc.mybook.R;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BookFragmentGridHasSlide extends BookFragment{
	
	//一个可以下拉刷新的listView对象
    private PullToRefreshListView mPullRefreshListView;
	//普通的listview对象
    private ListView actualListView;
    //添加一个链表数组，来存放string数组，这样就可以动态增加string数组中的内容了
    private LinkedList<String> mListItems;
    //给listview添加一个普通的适配器
    private ArrayAdapter<String> mAdapter;

	public void init_data(int pageIndex, String parameter1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.mybookshelf, container, false);  
	   //一打开应用就自动刷新，下面语句可以写到刷新按钮里面
		initView(view);
	    mPullRefreshListView.setRefreshing(true);
		return view;
	}
	
	private void initView(View v) {
        initPTRListView(v);
        initListView();
    }
	
	 /**
     * 设置下拉刷新的listview的动作
     */
    private void initPTRListView(View v) {
        mPullRefreshListView = (PullToRefreshListView) v.findViewById(R.id.pull_refresh_list);
        //设置拉动监听器
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //设置下拉时显示的日期和时间
                String label = DateUtils.formatDateTime(MainActivity.myActivity, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 更新显示的label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                // 开始执行异步任务，传入适配器来进行数据改变
//                new GetDataTask(mPullRefreshListView, mAdapter,mListItems).execute();
            }
        });

        // 添加滑动到底部的监听器
        mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
            
            @Override
            public void onLastItemVisible() {
                Toast.makeText(MainActivity.myActivity, "已经到底了", Toast.LENGTH_SHORT).show();
            }
        });
        
        //mPullRefreshListView.isScrollingWhileRefreshingEnabled();//看刷新时是否允许滑动
        //在刷新时允许继续滑动
        mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
        //mPullRefreshListView.getMode();//得到模式
        //上下都可以刷新的模式。这里有两个选择：Mode.PULL_FROM_START，Mode.BOTH，PULL_FROM_END
        mPullRefreshListView.setMode(Mode.BOTH);
        
    }
    
    /**
     * 设置listview的适配器
     */
    private void initListView() {
        //通过getRefreshableView()来得到一个listview对象
        actualListView = mPullRefreshListView.getRefreshableView();
        
        String []data = new String[] {"android","ios","wp","java","c++","c#"};
        mListItems = new LinkedList<String>();
        //把string数组中的string添加到链表中
        mListItems.addAll(Arrays.asList(data));
        
        mAdapter = new ArrayAdapter<>(MainActivity.myActivity, 
                android.R.layout.simple_list_item_1, mListItems);
        actualListView.setAdapter(mAdapter);
    }

}
