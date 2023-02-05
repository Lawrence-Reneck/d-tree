package dtree.reneck.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dtree.reneck.R;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.UsersViewHolder> {

    private final List<User> userList;
    private final List<User> searchedUsers;
    private final Context context;

    public UsersRecyclerViewAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.searchedUsers = new ArrayList<>(userList);
        this.context = context;
    }


    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersViewHolder(LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getName() + " "+user.getSurname());
        holder.city.setText("From : "+user.getCity());
        holder.age.setText(user.getAge() + " Years old");
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterByCity(String city) {
        city = city.toLowerCase(Locale.getDefault());
        userList.clear();
        if (city.length() == 0) {
            userList.addAll(searchedUsers);
        } else {
            for (User user : searchedUsers) {
                if (user.getCity().toLowerCase(Locale.getDefault())
                        .contains(city)) {
                    userList.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView userName, age, city;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.tvUserName);
            age = itemView.findViewById(R.id.tvAge);
            city = itemView.findViewById(R.id.tvCity);
        }
    }
}
