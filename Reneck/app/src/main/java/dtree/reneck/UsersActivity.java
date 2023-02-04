package dtree.reneck;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dtree.reneck.user.User;
import dtree.reneck.user.UsersContract;
import dtree.reneck.user.UsersPresenter;

public class UsersActivity extends AppCompatActivity implements UsersContract.View {

    private UsersPresenter mUserPresenter;
    private ProgressBar mLoadUsersProgressBar;
    private static final String TAG = "UsersActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mLoadUsersProgressBar = findViewById(R.id.loadUsersProgress);
        mUserPresenter = new UsersPresenter(this, this);
        mUserPresenter.getUsers();

    }

    @Override
    public void onGetUsersError(VolleyError error) {

    }

    @Override
    public void onGetUsersSuccess(JSONArray users) {
        List<User> userList = new ArrayList<>();
        for(int index = 0; index < users.length(); index++){
            try {
                JSONObject userObject = users.getJSONObject(index);
                User user = new User();
                user.setName(userObject.getString("NAME"));
                user.setAge(userObject.getInt("AGE"));
                user.setCity(userObject.getString("CITY"));
                user.setSurname(userObject.getString("SURNAME"));
                userList.add(user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        Log.e(TAG, userList.toString());

    }

    @Override
    public void showLoading() {
        mLoadUsersProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoadUsersProgressBar.setVisibility(View.GONE);
    }
}