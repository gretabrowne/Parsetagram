package codepath.com.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        String postId = getIntent().getStringExtra("post_id");
        Log.d("Stringid", String.format("%s", postId));
        ImageView ivPost;
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
// First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
// Execute the query to find the object with ID
        query.getInBackground(postId, new GetCallback<Post>() {
                    public void done(Post item, ParseException e) {
                        if (e == null) {
                             // item was found
                            Log.d("TimelineActivity", "post found");
                        } else {
                            Log.d("TimelineActivity", "post not found");
                        }
                    }
                });
        // Glide.with(this).load(item.getMedia().getUrl()).into(ivPost);
    }
}
