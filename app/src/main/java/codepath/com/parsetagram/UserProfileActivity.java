package codepath.com.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    PicsAdapter adapter;
    RecyclerView rvPics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView ivProfileDetail = findViewById(R.id.ivProfileDetail);
        TextView tvUsernameDetail = findViewById(R.id.tvUsernameDetail);
        String proUrl = getIntent().getStringExtra("propicUrl");
        ParseUser user = Parcels.unwrap(getIntent().getParcelableExtra("objectId"));
        Glide.with(this).load(proUrl).into(ivProfileDetail);

        String postOwner = getIntent().getStringExtra("username");
        tvUsernameDetail.setText(postOwner);

        rvPics = findViewById(R.id.rvPics);
        rvPics.setLayoutManager(new GridLayoutManager(this, 3));


        // Define the class we would like to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
// Define our query conditions
        query.whereEqualTo("owner", user);
        query.setLimit(10);
        query.orderByDescending("createdAt");
// Execute the find asynchronously
        query.findInBackground(new FindCallback<Post>() {
            public void done(List<Post> itemList, ParseException e) {
                if (e == null) {
                    adapter = new PicsAdapter(itemList);
                    rvPics.setAdapter(adapter);

                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });


    }

}
