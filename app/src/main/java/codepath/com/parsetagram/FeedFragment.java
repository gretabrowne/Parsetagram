package codepath.com.parsetagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;


public class FeedFragment extends Fragment {

    PostsAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    RecyclerView rvPosts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        // view setups and attaching listeners
        rvPosts = view.findViewById(R.id.rvPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        final TextView caption = view.findViewById(R.id.tvCaption0);
        Log.d("Feedfragment", "here");
        sendPostsQuery();

    }

    public void sendPostsQuery () {
        // Define the class we would like to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
// Define our query conditions
        // query.whereEqualTo("owner", ParseUser.getCurrentUser());
        query.setLimit(20);
        query.orderByDescending("createdAt");
// Execute the find asynchronously
        query.findInBackground(new FindCallback<Post>() {
            public void done(List<Post> itemList, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    // String firstItemId = itemList.get(0).getObjectId();
                    adapter = new PostsAdapter(itemList);
                    rvPosts.setAdapter(adapter);
//                    rvPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
                    // Toast.makeText(TimelineActivity.this, firstItemId, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void fetchTimelineAsync(int page) {
        // Define the class we would like to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
// Define our query conditions
        // query.whereEqualTo("owner", ParseUser.getCurrentUser());
        query.setLimit(20);
        query.orderByDescending("createdAt");
// Execute the find asynchronously
        query.findInBackground(new FindCallback<Post>() {
            public void done(List<Post> itemList, ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    adapter.clear();
                    // String firstItemId = itemList.get(0).getObjectId();
                    adapter.addAll(itemList);
                    // Toast.makeText(TimelineActivity.this, firstItemId, Toast.LENGTH_SHORT).show();
                    swipeContainer.setRefreshing(false);
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }
}
