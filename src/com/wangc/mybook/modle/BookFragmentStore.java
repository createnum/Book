package com.wangc.mybook.modle;

import com.wangc.mybook.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BookFragmentStore extends BookFragment{

	public void init_data(int pageIndex, String parameter1) {
		// TODO Auto-generated method stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.mybookstore, container, false);  
		return view;
	}

}
