package codepath.com.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

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
    }
}
