package dtree.reneck.user;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public interface UsersContract {
    interface View{
        void onGetUsersError(VolleyError error);
        void onGetUsersSuccess(JSONArray users) throws JSONException;
        void showLoading();
        void hideLoading();

    }

    interface Presenter{
        void getUsers();
    }
}
