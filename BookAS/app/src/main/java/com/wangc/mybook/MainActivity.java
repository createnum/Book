package com.wangc.mybook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.wangc.mybook.assembly.MainViewPager;
import com.wangc.mybook.modle.BookFragment;
import com.wangc.mybook.modle.BookFragmentGridHasSlide;
import com.wangc.mybook.modle.BookFragmentStore;
import com.wangc.mybook.modle.LeftFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener {

	private Fragment mContent;
	LeftFragment leftFragment;
	private RelativeLayout main_relative_top;
	private String[] CONTENT = new String[] { "书架", "书城" };
	public static final int START_PAGE_INDEX = 0;
	public BookAdapter adapter;
	public BookFragmentGridHasSlide bookFragmentGridHasSlide;
	public BookFragment bookFragment;
	public BookFragmentStore bookFragmentStore;
	private Button btnShelf, btnStore;
	private ArrayList<Fragment> fragmentList;
	private ArrayList<Button> buttontList;
	public static Activity myActivity;
	private int currIndex;//当前页卡编号
	MainViewPager pager;
	ImageView menu_btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		myActivity=this;
		initSlidingMenu(savedInstanceState);
		initImageLoaderConfiguration();
		initView();
		initListener();
	}


	private void initListener() {
		// TODO Auto-generated method stub
		btnShelf.setOnClickListener(this);
		btnStore.setOnClickListener(this);
		menu_btn.setOnClickListener(this);
	}


	private void initView() {
		// TODO Auto-generated method stub
		main_relative_top = (RelativeLayout) findViewById(R.id.main_relative_top);
		// 类型
		menu_btn = (ImageView) findViewById(R.id.book_menu_btn);
		bookFragmentGridHasSlide = (BookFragmentGridHasSlide) BookFragment
				.newInstance(BookFragment.BK_MAIN);
		bookFragmentStore = (BookFragmentStore) BookFragment
				.newInstance(BookFragment.BK_STORE);
		btnShelf = (Button) findViewById(R.id.book_shelf_btn);
		btnStore = (Button) findViewById(R.id.book_store);
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(bookFragmentGridHasSlide);
		fragmentList.add(bookFragmentStore);
		buttontList = new ArrayList<Button>();
		buttontList.add(btnShelf);
		buttontList.add(btnStore);
		adapter = new BookAdapter(getSupportFragmentManager(),fragmentList);
		pager = (MainViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		pager.setCurrentItem(0);// 设置当前显示标签页为第一页
		pager.setOnPageChangeListener(new MyOnPageChangeListener());// 页面变化时的监听器


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.book_shelf_btn:
				pager.setCurrentItem(0,true);
				setButtonUI(0);
				break;
			case R.id.book_store:
				pager.setCurrentItem(1,true);
				setButtonUI(1);
				break;
			case R.id.book_menu_btn:
				toggle();
				break;
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initSlidingMenu(Bundle savedInstanceState) {
		// 如果保存的状态不为空则得到之前保存的Fragment，否则实例化MyFragment
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}
		leftFragment = new LeftFragment();
		// 设置左侧滑动菜单
		// setContentView(R.layout.menu_frame_left);
		setBehindContentView(R.layout.menu_frame_left);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, leftFragment).commit();

		// 实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		// 设置可以左右滑动的菜单
		sm.setMode(SlidingMenu.LEFT);
		// 设置滑动阴影的宽度
		// sm.setShadowWidthRes(R.dimen.ranking_height);
		// // 设置滑动菜单阴影的图像资源
		// sm.setShadowDrawable(null);
		// 设置滑动菜单视图的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.35f);
		// 设置触摸屏幕的模式,TOUCHMODE_NONE 不能滑动打开 TOUCHMODE_FULLSCREEN 全屏
		// TOUCHMODE_MARGIN 边缘
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// 设置下方视图的在滚动时的缩放比例
		sm.setBehindScrollScale(0.0f);
		// 设置滑动时菜单的是否淡入淡出
		sm.setFadeEnabled(true);

		// 监听slidingmenu打开
		sm.setOnOpenListener(new OnOpenListener() {
			@SuppressLint("NewApi")
			@Override
			public void onOpen() {
				main_relative_top.setAlpha((float) 0.5);
			}
		});
		// 监听slidingmenu关闭
		sm.setOnCloseListener(new OnCloseListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClose() {
				main_relative_top.setAlpha(1);
			}
		});

	}



	private void setButtonUI(int i) {
		// TODO Auto-generated method stub
		for(int j=0;j<buttontList.size();j++){
			if(i==j){
				buttontList.get(j).setBackgroundResource(R.drawable.bg_tab_select);
				buttontList.get(j).setTextColor(getResources().getColor(R.color.book_default_red));
			}else{
				buttontList.get(j).setBackgroundResource(0);
				buttontList.get(j).setTextColor(getResources().getColor(R.color.white));
			}
		}
	}


	class BookAdapter extends FragmentPagerAdapter{
		private List<Fragment> fragmentList;
		public BookAdapter(FragmentManager fm,List<Fragment> fragments) {
			super(fm);
			this.fragmentList=fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			int i = arg0 + 1;
			pager.setCurrentItem(arg0,true);
			setButtonUI(arg0);
		}

	}

	protected void initImageLoaderConfiguration() {
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "imageloader/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
				// null) // Can slow ImageLoader, use it carefully (Better don't
				// use it)/设置缓存的详细信息，最好不要设置这个
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				// 缓存的文件数量
				.discCache(new UnlimitedDiskCache(cacheDir))
				// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout
				// (5
				// s),
				// readTimeout
				// (30
				// s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();// 开始构建
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}

}
