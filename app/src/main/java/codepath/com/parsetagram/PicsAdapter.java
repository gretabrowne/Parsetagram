package codepath.com.parsetagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PicsAdapter extends RecyclerView.Adapter<PicsAdapter.ViewHolder>{

    private List<Post> myPics;
    Context context;
    // public static boolean isLiked = false;
    public PicsAdapter (List<Post> posts) {
        this.myPics = posts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            photo = (ImageView) itemView.findViewById(R.id.ivPic);
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // get context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_pic, parent, false);
        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Post post = myPics.get(position);
        ImageView imageView = viewHolder.photo;

        String imageUrl = post.getMedia().getUrl();

        Glide.with(context)
                .load(imageUrl)
                .into(imageView);

    }

    @Override
    public int getItemCount() {
        return myPics.size();
    }


    // Clean all elements of the recycler
    public void clear() {
        myPics.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        myPics.addAll(list);
        notifyDataSetChanged();
    }
}
