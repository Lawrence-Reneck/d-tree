package dtree.reneck;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dtree.reneck.user.User;
import dtree.reneck.user.UsersContract;
import dtree.reneck.user.UsersPresenter;
import dtree.reneck.user.UsersRecyclerViewAdapter;

public class UsersActivity extends AppCompatActivity implements UsersContract.View {

    private UsersPresenter mUserPresenter;
    private ProgressBar mLoadUsersProgressBar;
    private static final String TAG = "UsersActivity";
    private RecyclerView mRecyclerView ;
    private EditText mEdFilterByCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mLoadUsersProgressBar = findViewById(R.id.loadUsersProgress);
        mRecyclerView = findViewById(R.id.recyclerView);
        mEdFilterByCity = findViewById(R.id.edFilterByCity);
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

        UsersRecyclerViewAdapter usersRecyclerViewAdapter = new UsersRecyclerViewAdapter(userList,this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(usersRecyclerViewAdapter);


        mEdFilterByCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mEdFilterByCity.getText().toString().length()==0){
                    usersRecyclerViewAdapter.filterByCity("");

                }else {
                    String filterCityText = mEdFilterByCity.getText()
                            .toString().toLowerCase(Locale.getDefault());
                    usersRecyclerViewAdapter.filterByCity(filterCityText);
                }

            }
        });



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