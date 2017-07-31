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
	
	//һ����������ˢ�µ�listView����
    private PullToRefreshListView mPullRefreshListView;
	//��ͨ��listview����
    private ListView actualListView;
    //���һ���������飬�����string���飬�����Ϳ��Զ�̬����string�����е�������
    private LinkedList<String> mListItems;
    //��listview���һ����ͨ��������
    private ArrayAdapter<String> mAdapter;

	public void init_data(int pageIndex, String parameter1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.mybookshelf, container, false);  
	   //һ��Ӧ�þ��Զ�ˢ�£�����������д��ˢ�°�ť����
		initView(view);
	    mPullRefreshListView.setRefreshing(true);
		return view;
	}
	
	private void initView(View v) {
        initPTRListView(v);
        initListView();
    }
	
	 /**
     * ��������ˢ�µ�listview�Ķ���
     */
    private void initPTRListView(View v) {
        mPullRefreshListView = (PullToRefreshListView) v.findViewById(R.id.pull_refresh_list);
        //��������������
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //��������ʱ��ʾ�����ں�ʱ��
                String label = DateUtils.formatDateTime(MainActivity.myActivity, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // ������ʾ��label
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                // ��ʼִ���첽���񣬴������������������ݸı�
//                new GetDataTask(mPullRefreshListView, mAdapter,mListItems).execute();
            }
        });

        // ��ӻ������ײ��ļ�����
        mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
            
            @Override
            public void onLastItemVisible() {
                Toast.makeText(MainActivity.myActivity, "�Ѿ�������", Toast.LENGTH_SHORT).show();
            }
        });
        
        //mPullRefreshListView.isScrollingWhileRefreshingEnabled();//��ˢ��ʱ�Ƿ�������
        //��ˢ��ʱ�����������
        mPullRefreshListView.setScrollingWhileRefreshingEnabled(true);
        //mPullRefreshListView.getMode();//�õ�ģʽ
        //���¶�����ˢ�µ�ģʽ������������ѡ��Mode.PULL_FROM_START��Mode.BOTH��PULL_FROM_END
        mPullRefreshListView.setMode(Mode.BOTH);
        
    }
    
    /**
     * ����listview��������
     */
    private void initListView() {
        //ͨ��getRefreshableView()���õ�һ��listview����
        actualListView = mPullRefreshListView.getRefreshableView();
        
        String []data = new String[] {"android","ios","wp","java","c++","c#"};
        mListItems = new LinkedList<String>();
        //��string�����е�string��ӵ�������
        mListItems.addAll(Arrays.asList(data));
        
        mAdapter = new ArrayAdapter<>(MainActivity.myActivity, 
                android.R.layout.simple_list_item_1, mListItems);
        actualListView.setAdapter(mAdapter);
    }

}
