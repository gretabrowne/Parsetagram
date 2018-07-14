package codepath.com.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    public static final int EDIT_REQUEST_CODE = 20;
    public static final String ITEM_TEXT = "itemText";
    public static final String ITEM_POSITION = "itemPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ImageView ivDetailPhoto = findViewById(R.id.ivDetailPhoto);
        TextView caption = findViewById(R.id.tvCaption0);
        TextView tvTimestamp = findViewById(R.id.tvTimestamp);
        caption.setText(getIntent().getStringExtra("caption"));
        tvTimestamp.setText(getIntent().getStringExtra("timestamp"));
        Glide.with(this).load(getIntent().getStringExtra("image_url")).into(ivDetailPhoto);
        TextView tvLikeCount = findViewById(R.id.tvLikeCount);
        int numLikes = getIntent().getIntExtra("numLikes", 0);
        tvLikeCount.setText(Integer.toString(numLikes) + " likes");
        items = new ArrayList<>();
        lvItems = findViewById(R.id.lvComments);
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        boolean addCaption = getIntent().getBooleanExtra("addComment", false);
        if (addCaption) {
            // user wants to write a caption, load edit text
            EditText etComment = findViewById(R.id.etComment);
            etComment.setVisibility(View.VISIBLE);
        }

    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etComment);
        String itemText = etNewItem.getText().toString();
        Log.d("onAddItem", itemText);
        String username = getIntent().getStringExtra("username");
        itemsAdapter.add(username + ": " + itemText);
        etNewItem.setText("");
        Post p = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        p.put("comments", itemText);
    }


}
