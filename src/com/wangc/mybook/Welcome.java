package com.wangc.mybook;



import com.wangc.mybook.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Welcome extends Activity {
	
	public static Welcome context;
	boolean isStop=true;
	private Handler handler;
	private Runnable runnable;
	private ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.welcome);  
        context = this;
        imageView=(ImageView) findViewById(R.id.imageView2);
        Bitmap bm = BitmapFactory.decodeResource(this.getResources(), R.drawable.welcome_new);
        BitmapDrawable bd = new BitmapDrawable(this.getResources(), bm);
        imageView.setBackgroundDrawable(bd);
        new Handler().postDelayed(new Runnable() {
			public void run() {
				Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.welcome_new);
		        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
		        imageView.setBackgroundDrawable(bd);
//				textView1.setVisibility(View.VISIBLE);
				
				Intent intent = new Intent();
				intent.setClass(Welcome.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, 1000);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		super.onDestroy();
		BitmapDrawable bd = (BitmapDrawable)imageView.getBackground();
		imageView.setBackgroundResource(0);//别忘了把背景设为null，避免onDraw刷新背景时候出现used a recycled bitmap错误
		bd.setCallback(null);
		bd.getBitmap().recycle();
		bd= null;
	}
}
