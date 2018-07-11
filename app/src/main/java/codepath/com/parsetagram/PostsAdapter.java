package codepath.com.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private List<Post> myPosts;
    Context context;
    public PostsAdapter (List<Post> posts) {
        this.myPosts = posts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView photo;
        public TextView caption;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            photo = (ImageView) itemView.findViewById(R.id.ivPost);
            caption = (TextView) itemView.findViewById(R.id.tvCaption);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick (View view) {
            int position = getAdapterPosition();
            // ensure valid position
            if (position != RecyclerView.NO_POSITION) {
                // get tweet at position
                Post post = myPosts.get(position);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("image_url", post.getMedia().getUrl());
                intent.putExtra("caption", post.getBody());
                // todo-- set timestamp in home activity, then get it here?

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                String stringDate = df.format(post.getCreatedAt());
                intent.putExtra("timestamp", stringDate);
                // show the activity
                context.startActivity(intent);
            }
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // get context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_post, parent, false);
        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Post post = myPosts.get(position);
        ImageView imageView = viewHolder.photo;

        String imageUrl = post.getMedia().getUrl();

        Glide.with(context)
                .load(imageUrl)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return myPosts.size();
    }


    // Clean all elements of the recycler
    public void clear() {
        myPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        myPosts.addAll(list);
        notifyDataSetChanged();
    }
}
