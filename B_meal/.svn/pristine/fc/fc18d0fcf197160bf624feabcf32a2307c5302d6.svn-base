package com.tacademy.b_meal;

import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.tacademy.b_meal.manager.LoginPropertyManager;
import com.tacademy.b_meal.manager.NetworkManager;
import com.tacademy.b_meal.manager.NetworkManager.OnResultListener;
import com.tacademy.b_meal.search.RecommendList;
import com.tacademy.b_meal.search.SearchResult;

public class Frag_Search extends Fragment {
	// Views
	Button btn;
	EditText editKeyword;
	ListView recommendList;
	KeywordAdapter keyAdapter;
	View header;
	InputMethodManager imm;
	
	public static final String SEARCH_KEYWORD = "SEARCH_KEYWORD";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_frag_search, container,
				false);
		
		// find view
		editKeyword = (EditText) view.findViewById(R.id.editKeyword);
		recommendList = (ListView) view.findViewById(R.id.listKeyword);
		header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_search_lastheader, null);
		recommendList.addHeaderView(header, null, false);
		imm = (InputMethodManager)((getActivity())).getSystemService(Context.INPUT_METHOD_SERVICE);
		keyAdapter = new KeywordAdapter(getActivity());
		recommendList.setAdapter(keyAdapter);
		initData();


		// recommend
		editKeyword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(final CharSequence s, int start,
					int before, int count) {

				String text = s.toString();
				Log.i("string", text);
				if (text.length() != 0) {
					Log.i("string", "text exist");
					// set header
					recommendList.removeHeaderView(header);
					header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_search_recommendheader, null);
					recommendList.addHeaderView(header, null, false);
					NetworkManager.getInstnace().getRecommend(getActivity(), text,
							new OnResultListener<RecommendList>() {

								@Override
								public void onSuccess(RecommendList rec) {

									if (s.toString().length() != 0) {
										keyAdapter.clear();
										for (String recommend : rec.result) {
											keyAdapter.add(recommend);
										}
									}
								}

								@Override
								public void onFail(int code) {
									// TODO Auto-generated method stub

								}
							});
				} else {
					recommendList.removeHeaderView(header);
					header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_search_lastheader, null);
					recommendList.addHeaderView(header, null, false);
					keyAdapter.clear();
					initData();
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		//keyboard action
		editKeyword.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_SEARCH)
				{
					String keyword = editKeyword.getText().toString();
					if(keyword.length() != 0)
					{	LoginPropertyManager.getInstance().setKeyword(keyword);
						Intent intent = new Intent(getActivity(), SearchResult.class);
						intent.putExtra(SEARCH_KEYWORD, keyword);
						startActivity(intent);
						return true;
					}else
					{ Toast.makeText(getActivity(), "검색어를 입력해 주세요", Toast.LENGTH_SHORT); }
				}
				return false;
			}
		});
		// recommend list click
		recommendList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), SearchResult.class);
				String keyword = keyAdapter.getItem(position-1).toString();
				LoginPropertyManager.getInstance().setKeyword(keyword);
				intent.putExtra(SEARCH_KEYWORD, keyword);
				startActivity(intent);
			}
		});

		// delete button click
		btn = (Button) view.findViewById(R.id.buttonSearch);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				editKeyword.setText("");
			}
		});
		return view;
	}

	private void initData() {
		Set<String> keywords = LoginPropertyManager.getInstance().keywords;
		for (String str : keywords) {
			String mData = str;
			keyAdapter.add(mData);
		}
	}

}
