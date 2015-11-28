package com.tacademy.b_meal.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tacademy.b_meal.BmealApplication;
import com.tacademy.b_meal.ProfileInfo;
import com.tacademy.b_meal.extra.DataNotice;
import com.tacademy.b_meal.foodlist.AddMyplace;
import com.tacademy.b_meal.foodlist.DeleteMyplace;
import com.tacademy.b_meal.foodlist.MyplaceList;
import com.tacademy.b_meal.foodlist.ThemeList;
import com.tacademy.b_meal.foods.DeleteReview;
import com.tacademy.b_meal.foods.DistanceInfo;
import com.tacademy.b_meal.foods.FoodGalInfo;
import com.tacademy.b_meal.foods.RestInfo;
import com.tacademy.b_meal.foods.RestList;
import com.tacademy.b_meal.foods.RestMap;
import com.tacademy.b_meal.foods.RestReview;
import com.tacademy.b_meal.foods.RestReviewUp;
import com.tacademy.b_meal.login.ChangePWInfo;
import com.tacademy.b_meal.login.DeleteAccount;
import com.tacademy.b_meal.login.FacebookAccount;
import com.tacademy.b_meal.login.FindPWInfo;
import com.tacademy.b_meal.login.ImageUploade;
import com.tacademy.b_meal.login.JoinInfo;
import com.tacademy.b_meal.login.LoginInfo;
import com.tacademy.b_meal.login.Logout;
import com.tacademy.b_meal.search.RecommendList;
import com.tacademy.b_meal.search.SearchList;

public class NetworkManager {
	private static NetworkManager instance;
	public static NetworkManager getInstnace() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	AsyncHttpClient client;
	private NetworkManager() {
		
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
			socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client = new AsyncHttpClient();
			client.setSSLSocketFactory(socketFactory);			
			client.setCookieStore(new PersistentCookieStore(BmealApplication.getContext()));
			client.setTimeout(30000);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
	}
	
	public HttpClient getHttpClient() {
		return client.getHttpClient();
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T movie);
		public void onFail(int code);
	}
	
	Gson gson = new Gson();
	public static final String SERVER = "http://54.65.230.50";
	
	//1. Theme List
	public static final String THEME_LIST = SERVER + "/thema";
	public void getThemeList(Context context, final OnResultListener<ThemeList> listener)
	{
		client.get(context, THEME_LIST, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				ThemeList result = gson.fromJson(responseString, ThemeList.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
				
			}
		});
	}
	
	//2. Restaurant List
	public static final String RESTAURANT_LIST = SERVER + "/thema/%s/%s";
	public void getRestaurantList(Context context, int themaId, int page, final OnResultListener<RestList> listener)
	{
		String url = String.format(RESTAURANT_LIST, themaId, page);
		client.get(context, url, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				RestList result = gson.fromJson(responseString, RestList.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
				
			}
		});
	}
	
	//3. restaurant info
	public static final String RESTAURANT_INFO = SERVER + "/store/%s";
	public void getRestaurantInfo(Context context, int storeId, final OnResultListener<RestInfo> listener)
	{
		String url = String.format(RESTAURANT_INFO, storeId);
		client.get(context, url, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				RestInfo result = gson.fromJson(responseString, RestInfo.class);
				listener.onSuccess(result);
				
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
				
			}
		});
	}
	
	//4. food review
	public static final String RESTAURANT_REVIEW = SERVER + "/store/review/%s/%s";
	public void getRestaurantReview(Context context, int storeId, int page, final OnResultListener<RestReview> listener)
	{
		String url = String.format(RESTAURANT_REVIEW, storeId, page);
		client.get(context, url, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				RestReview result = gson.fromJson(responseString, RestReview.class);
				listener.onSuccess(result);
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
		
	//5. upload food review
	public static final String RESTAURANT_REVIVIEW_UPLOAD = SERVER + "/store/review/";
	public void getRestaurantReviewUpload(Context context, int storeId, String reviewContent, final OnResultListener<RestReviewUp> listener)
	{
		RequestParams params = new RequestParams();
		params.put("storeId", ""+storeId);
		params.put("reviewContent", reviewContent);
		
		client.post(context, RESTAURANT_REVIVIEW_UPLOAD, params, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				RestReviewUp result = gson.fromJson(responseString, RestReviewUp.class);
				listener.onSuccess(result);
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	//6. delete review
	public static final String RESTAURANT_DELETE_REVIEW = SERVER + "/store/review/delete";
	public void getDeleteReview(Context context, int reviewId, int storeId, final OnResultListener<DeleteReview> listener)
	{
		RequestParams params = new RequestParams();
		params.put("reviewId", ""+reviewId);
		params.put("storeId", ""+storeId);
				
		client.post(context, RESTAURANT_DELETE_REVIEW, params, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				DeleteReview result = gson.fromJson(responseString, DeleteReview.class);
				listener.onSuccess(result);
			}
					
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}

	//7. add my place
	public static final String RESTAURANT_ADD_MYPLACE = SERVER + "/store/like/";
	public void getAddMyplace(Context context, int storeId, final OnResultListener<AddMyplace> listener)
	{
		RequestParams params = new RequestParams();
		params.put("storeId", ""+storeId);
			
		client.post(context, RESTAURANT_ADD_MYPLACE, params, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				AddMyplace result = gson.fromJson(responseString, AddMyplace.class);
				listener.onSuccess(result);
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	//8. delete my place
	public static final String RESTAURANT_DELETE_MYPLACE = SERVER + "/store/dislike";
	public void getDeleteMyplace(Context context, int storeId, final OnResultListener<DeleteMyplace> listener)
	{
		RequestParams params = new RequestParams();
		params.put("storeId", ""+storeId);
				
		client.post(context, RESTAURANT_DELETE_MYPLACE, params, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				DeleteMyplace result = gson.fromJson(responseString, DeleteMyplace.class);
				listener.onSuccess(result);
			}
					
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
		
	//9. food map
	public static final String RESTAURANT_MAP = SERVER + "/store/%s/location/";
	public void getRestaurantMap(Context context, int storeId, final OnResultListener<RestMap> listener)
	{
		String url = String.format(RESTAURANT_MAP, storeId);
		client.get(context, url, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				RestMap result = gson.fromJson(responseString, RestMap.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	//10. food gallery
	public static final String FOOD_GALLERY = SERVER + "/store/%s/gallery";
	public void getFoodGal(Context context, int storeId, final OnResultListener<FoodGalInfo> listener)
	{
		String url = String.format(FOOD_GALLERY, storeId);
		client.get(context, url, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				FoodGalInfo result = gson.fromJson(responseString, FoodGalInfo.class);
				listener.onSuccess(result);		
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
		
	}
	
	//11. recommend keyword
	public static final String RECOMMEND_KEYWORD = SERVER + "/search";
	public void getRecommend(Context context, CharSequence keyword, final OnResultListener<RecommendList> listener)
	{
		RequestParams params = new RequestParams();
		params.put("keyword", keyword.toString());
		
		client.post(context, RECOMMEND_KEYWORD, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				RecommendList result = gson.fromJson(responseString, RecommendList.class);
				listener.onSuccess(result);
				
			}
		
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
			
	}
		
	//12. search result
	public static final String SEARCH_RESULT = SERVER + "/search/result";
	public void getSearchResult(Context context, String keyword, final OnResultListener<SearchList> listener)
	{
		RequestParams params = new RequestParams();
		params.put("keyword", keyword);
		
		client.post(context, SEARCH_RESULT, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				SearchList result = gson.fromJson(responseString, SearchList.class);
				listener.onSuccess(result);
				
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
		
	}
	
	//13. join new user
	public static final String JOIN_NEW_USER = SERVER + "/users/signup";
	public void getJoin(Context context, String name, String email, String pass, String sex, String birthdate, 
		 final OnResultListener<JoinInfo> listener)
	{
		RequestParams params = new RequestParams();
		params.put("name", name);
		params.put("email", email);
		params.put("pass", pass);
		params.put("sex", sex);
		params.put("birthdate", birthdate);
				
		client.post(context, JOIN_NEW_USER, params, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				JoinInfo result = gson.fromJson(responseString, JoinInfo.class);
				listener.onSuccess(result);
					
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
			
	}
	
	//14. login
	public static final String LOGIN = SERVER + "/users/login";
	public void getLogin(Context context, String email, String pass, final OnResultListener<LoginInfo> listener)
	{
		RequestParams params = new RequestParams();
		params.put("email", email);
		params.put("pass", pass);
				
		client.post(context, LOGIN, params, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				LoginInfo result = gson.fromJson(responseString, LoginInfo.class);
				listener.onSuccess(result);
					
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
			
	}
		
	//15. user profile
	public static final String USER_PROFILE = SERVER + "/users/profile";
	public void getProfile(Context context, final OnResultListener<ProfileInfo> listener)
	{
			
		client.post(context, USER_PROFILE, null, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				ProfileInfo result = gson.fromJson(responseString, ProfileInfo.class);
				listener.onSuccess(result);
					
			}
			
			@Override	
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
			
	}
	
	//16. my place
	public static final String MY_PLACE = SERVER + "/users/like/list/%s/%s";
	public void getMyplace(Context context, int page, int order, final OnResultListener<MyplaceList> listener)
	{
		String url = String.format(MY_PLACE, page, order);
		client.get(context, url, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				MyplaceList result = gson.fromJson(responseString, MyplaceList.class);
				listener.onSuccess(result);		
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
		
	}
	
	//17. change password
	public static final String CHANGE_PASSWORD = SERVER + "/users/edit";
	public void getPassword(Context context, String userPw, String userNewPw, final OnResultListener<ChangePWInfo> listener)
	{
		RequestParams params = new RequestParams();
		params.put("userPw", userPw);
		params.put("userNewPw", userNewPw);
					
		client.post(context, CHANGE_PASSWORD, params, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				ChangePWInfo result = gson.fromJson(responseString, ChangePWInfo.class);
				listener.onSuccess(result);
					
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
				
	}
	
	//18.Find Password
	public static final String FIND_PASSWORD = SERVER + "/users/pwsearch";
	public void getFindPassword(Context context, String userEmail, final OnResultListener<FindPWInfo> listener)
	{
		RequestParams params = new RequestParams();
		params.put("userEmail", userEmail);
					
		client.post(context, FIND_PASSWORD, params, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				FindPWInfo result = gson.fromJson(responseString, FindPWInfo.class);
				listener.onSuccess(result);
					
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
				
	}
	
	//19. delete account
	public static final String DELETE_ACCOUNT = SERVER + "/users/delete";
	public void getDeleteAccount(Context context, final OnResultListener<DeleteAccount> listener)
	{
		
		client.post(context, DELETE_ACCOUNT, null, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				DeleteAccount result = gson.fromJson(responseString, DeleteAccount.class);
				listener.onSuccess(result);
					
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
				
	}
	
	//20. facebook login&join
	public static final String FACEBOOK_LOGIN_JOIN = SERVER + "/users/fbjoin";
	public void getFaceBook(Context context, String accessToken, final OnResultListener<FacebookAccount> listener)
	{
		RequestParams params = new RequestParams();
		params.put("accessToken", accessToken);
		
		client.post(context, FACEBOOK_LOGIN_JOIN, params, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				FacebookAccount result = gson.fromJson(responseString, FacebookAccount.class);
				listener.onSuccess(result);
					
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
				
	}
	
	//21. facebook login
	public static final String FACEBOOK_LOGIN = SERVER + "/users/fblogin";
	public void getFaceBookLogin(Context context, String accessToken, final OnResultListener<FacebookAccount> listener)
	{
		RequestParams params = new RequestParams();
		params.put("accessToken", accessToken);
		
		client.post(context, FACEBOOK_LOGIN, params, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				FacebookAccount result = gson.fromJson(responseString, FacebookAccount.class);
				listener.onSuccess(result);
					
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
				
	}
	
	//22. prifile image upload
	public static final String IMAGE_UPLOAD = SERVER + "/users/photo";
	public void getUploadImage(Context context, String upfile, final OnResultListener<ImageUploade> listener)
	{
		RequestParams params = new RequestParams();
		try {
			params.put("upfile", new File(upfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.post(context, IMAGE_UPLOAD, params, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				ImageUploade result = gson.fromJson(responseString, ImageUploade.class);
				listener.onSuccess(result);
					
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});		
	}
	
	//23. logout
	public static final String LOGOUT = SERVER + "/users/logout";
	public void getLogout(Context context, final OnResultListener<Logout> listener)
	{
		client.post(context, LOGOUT, null, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Logout result = gson.fromJson(responseString, Logout.class);
				listener.onSuccess(result);
					
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});		
	}
	
	//24. supprot
	public static final String SUPPORT = SERVER + "/support";
	public void getSupprot(Context context, final OnResultListener<DataHelp> listener)
	{
		client.get(context, SUPPORT, null, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				DataHelp result = gson.fromJson(responseString, DataHelp.class);
				listener.onSuccess(result);
					
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});		
	}
		
	//25. notice
	public static final String NOTICE = SERVER + "/info";
	public void getNotice(Context context, final OnResultListener<DataNotice> listener)
	{
		client.get(context, NOTICE, null, new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				DataNotice result = gson.fromJson(responseString, DataNotice.class);
				listener.onSuccess(result);
					
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});		
	}
	
	//t-map api 
	public static final String PATH_DISTANCE = "https://apis.skplanetx.com/tmap/routes/pedestrian";
	public void getDistance(Context context, double startX, double startY, double endX, double endY,
			String startName, String endName, final OnResultListener<DistanceInfo> listener)
	{
		RequestParams params = new RequestParams();
		params.put("version", "1");
		params.put("startX", ""+startX);
		params.put("startY", ""+startY);
		params.put("endX", ""+endX);
		params.put("endY", ""+endY);
		params.put("startName", startName);
		params.put("endName", endName);
		params.put("reqCoordType", "WGS84GEO");
		params.put("resCoordType", "WGS84GEO");
		
		Header[] headers = new Header[2];
		headers[0] = new BasicHeader("Accept", "application/json");
		headers[1] = new BasicHeader("appKey", "216a6462-e398-34ec-a733-33002d406c70");
					
		client.post(context, PATH_DISTANCE, headers, params, "application/x-www-form-urlencoded", new TextHttpResponseHandler() {
				
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				DistanceInfo result = gson.fromJson(responseString, DistanceInfo.class);
				listener.onSuccess(result);
					
			}
				
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
					listener.onFail(statusCode);
			}
		});
			
	}

}
