package codepath.com.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    private GridView mGridView;
    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView ivProfileDetail = findViewById(R.id.ivProfileDetail);
        TextView tvUsernameDetail = findViewById(R.id.tvUsernameDetail);
        String proUrl = getIntent().getStringExtra("propicUrl");
        String user = getIntent().getStringExtra("objectId");
        Glide.with(this).load(proUrl).into(ivProfileDetail);

        String postOwner = getIntent().getStringExtra("username");
        tvUsernameDetail.setText(postOwner);

        mGridView = (GridView) findViewById(R.id.gridview);

        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, mGridData);
        mGridView.setAdapter(mGridAdapter);

        // Define the class we would like to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
// Define our query conditions
        query.whereEqualTo("owner", user);
        query.setLimit(100);
        query.orderByDescending("createdAt");
// Execute the find asynchronously
        query.findInBackground(new FindCallback<Post>() {
            public void done(List<Post> itemList, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    // String firstItemId = itemList.get(0).getObjectId();
                    Log.d("UserProfileActivity", "done");
                    int size = itemList.size();
                    GridItem item;
                    for (int i = 0; i < itemList.size(); i++) {
                        Log.d("UserProfileActivity", "hit for loop");
                        Post p = itemList.get(i);
                        item = new GridItem();
                        item.setImage(p.getMedia().getUrl());
                        mGridData.add(item);
                        Log.d("UserProfileActivity", p.getMedia().getUrl());
                    }
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });

        mGridAdapter.setGridData(mGridData);

    }

}
