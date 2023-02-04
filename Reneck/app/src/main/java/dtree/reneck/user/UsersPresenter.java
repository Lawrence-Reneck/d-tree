package dtree.reneck.user;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import dtree.reneck.helpers.Constants;
import dtree.reneck.networking.VolleyController;

public class UsersPresenter implements UsersContract.Presenter {
    private final UsersContract.View view;
    private final Context context;

    public UsersPresenter(UsersContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void getUsers() {
        view.showLoading();
        JsonArrayRequest getUsersRequest = new JsonArrayRequest(
                Constants.API_URL,
                jsonArray -> {
                    view.hideLoading();
                    try {
                        view.onGetUsersSuccess(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, volleyError -> {
                    view.hideLoading();
                    view.onGetUsersError(volleyError);
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> authHeader = new HashMap<>();
                authHeader.put("x-apikey", Constants.API_KEY);
                return authHeader;
            }
        };

        VolleyController.getInstance(context).addToRequestQueue(getUsersRequest);

    }
}
