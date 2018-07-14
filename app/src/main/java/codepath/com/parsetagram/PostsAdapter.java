package codepath.com.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private List<Post> myPosts;
    Context context;
    public static boolean isLiked = false;
    public PostsAdapter (List<Post> posts) {
        this.myPosts = posts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView photo;
        public TextView caption;
        public TextView username;
        public TextView dateCreated;
        public ImageButton likeButton;
        public ImageView propic;
        public ImageButton commentButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            photo = (ImageView) itemView.findViewById(R.id.ivPost);
            caption = (TextView) itemView.findViewById(R.id.tvCaption0);
            username = (TextView) itemView.findViewById(R.id.tvUsername);
            dateCreated = (TextView) itemView.findViewById(R.id.tvDateCreated);
            likeButton = (ImageButton) itemView.findViewById(R.id.ibLike);
            propic = (ImageView) itemView.findViewById(R.id.ivProfile);
            commentButton = (ImageButton) itemView.findViewById(R.id.ibComment);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick (View view) {
            int position = getAdapterPosition();
            // ensure valid position
            if (position != RecyclerView.NO_POSITION) {
                // get position
                Post post = myPosts.get(position);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("image_url", post.getMedia().getUrl());
                intent.putExtra("caption", post.getBody());
                // todo-- set timestamp in home activity, then get it here?

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                String stringDate = df.format(post.getCreatedAt());
                intent.putExtra("timestamp", stringDate);
                intent.putExtra("numLikes", post.getNumLikes());
                intent.putExtra("post", Parcels.wrap(post));
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
        final Post post = myPosts.get(position);
        ImageView imageView = viewHolder.photo;
        ImageView profilePic = viewHolder.propic;

        String imageUrl = post.getMedia().getUrl();
        ParseFile pfile = null;
        try {
            pfile = post.getUser().fetchIfNeeded().getParseFile("profilepic");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (pfile != null) {
            String profilePic2 = pfile.getUrl();
            if (profilePic2 != null) {
                Glide.with(context).load(profilePic2).into(profilePic);
            }
        }

        Glide.with(context)
                .load(imageUrl)
                .into(imageView);


        TextView timestamp = viewHolder.dateCreated;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String stringDate = df.format(post.getCreatedAt());
        timestamp.setText(stringDate);


        TextView user = viewHolder.username;
        String username = null;
        if (ParseUser.getCurrentUser() == null || post == null) {
            Intent i = new Intent (context, LoginActivity.class);
            context.startActivity(i);
        }
        try {
            username = post.getUser().fetchIfNeeded().getUsername();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setText(username);
        Log.d("username", username);

        String postCaption = post.getBody();
        Log.d("caption", postCaption);
        TextView caption = viewHolder.caption;
        caption.setText(String.format("%s:  %s", username, postCaption));

        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (final View v) {
                if (!isLiked) {
                    v.setSelected(true);
                    post.setNumLikes(post.getNumLikes() + 1);
                    try {
                        post.save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    isLiked = true;
                }

                else {
                    v.setSelected(false);
                    post.setNumLikes(post.getNumLikes() - 1);
                    try {
                        post.save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    isLiked = false;
                }

            }
        });



        viewHolder.commentButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (final View v) {
                Intent i = new Intent (context, DetailsActivity.class);
                i.putExtra("image_url", post.getMedia().getUrl());
                i.putExtra("caption", post.getBody());
                // todo-- set timestamp in home activity, then get it here?

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                String stringDate = df.format(post.getCreatedAt());
                i.putExtra("timestamp", stringDate);
                i.putExtra("numLikes", post.getNumLikes());
                i.putExtra("addComment", true);
                i.putExtra("username", ParseUser.getCurrentUser().getUsername());
                i.putExtra("post", Parcels.wrap(post));
                // show the activity
                context.startActivity(i);
            }
        });

        viewHolder.propic.setOnClickListener(new View.OnClickListener() {
            public void onClick (final View v) {
                Intent i = new Intent(context, UserProfileActivity.class);
                String propicUrl = post.getUser().getParseFile("profilepic").getUrl();
                String username = post.getUser().getUsername();
                i.putExtra("propicUrl", propicUrl);
                i.putExtra("objectId", Parcels.wrap(post.getUser()));
                i.putExtra("username", username);
                context.startActivity(i);
            }
        });
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
