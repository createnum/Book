package com.wangc.mybook.modle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BookFragment extends Fragment {
	public final static int BK_MAIN = 0;
	public final static int BK_STORE = 1;

	public static BookFragment newInstance(int pageIndex) {
		return newInstance(pageIndex, null);
	}

	public static BookFragment newInstance(int pageIndex, String parameter1) {
		switch (pageIndex) {

		// ���������չʾ
		case BK_MAIN: {
			BookFragmentGridHasSlide fragment = new BookFragmentGridHasSlide();
			fragment.init_data(pageIndex, parameter1);
			return fragment;
		}
		// ���������չʾ
		case BK_STORE: {
			BookFragmentStore fragment = new BookFragmentStore();
			fragment.init_data(pageIndex, parameter1);
			return fragment;
		}

		default:
			break;
		}
		return null;
	}
}
