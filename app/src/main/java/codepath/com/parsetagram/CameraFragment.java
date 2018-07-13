package codepath.com.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class CameraFragment extends Fragment implements View.OnClickListener {

    public final String APP_TAG = "Parsetagram";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;
    Button btnPost;
    ImageButton launchCamera;
    EditText caption;
    ProgressBar pb;
    private CameraFragment.OnItemSelectedListener listener;

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        public void onHitPostButton();
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement CameraFragment.OnItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        btnPost = view.findViewById(R.id.btnPost);
        btnPost.setOnClickListener(this);
        launchCamera = view.findViewById(R.id.btnTakePhoto);
        launchCamera.setOnClickListener(this);
        caption = view.findViewById(R.id.etCaption);
        pb = view.findViewById(R.id.pbLoading);

    }

    @Override
    public void onClick(View v) {
        // when post button is hit
        // uploads photo just taken and "posts" to Instagram
        switch (v.getId()) {
            case R.id.btnPost:
                File photoFile = getPhotoFileUri(photoFileName);
                final Post post = new Post("post");
                post.setOwner(ParseUser.getCurrentUser());
                post.setBody(caption.getText().toString());
                post.setUser(ParseUser.getCurrentUser());
                ParseFile parseFile = new ParseFile(photoFile);
                post.setMedia(parseFile);
                pb.setVisibility(ProgressBar.VISIBLE);
                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Log.d("Fragment", "Post id: " + post.getObjectId());
                        listener.onHitPostButton();
                        pb.setVisibility(ProgressBar.INVISIBLE);
                        Log.d("CameraFragment", "just called onHitPostButton in camera fragment");
                        // go to timeline fragment
                    }
                });
                break;
            case R.id.btnTakePhoto:
                onLaunchCamera(v);
                break;
        }




    }

    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getActivity(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap rawTakenImage = BitmapFactory.decodeFile(photoFile.getPath());
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                // int height = size.y;
                Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(rawTakenImage, width);
                // Configure byte output stream
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
// Compress the image further
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
// Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
                // File resizedFile = new File()
                File resizedFile = getPhotoFileUri(photoFileName);
                try {
                    resizedFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(resizedFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
// Write the bytes of the bitmap to file
                // file file = getPhotoFileUri(name + path)

                try {
                    fos.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // by this point we have the camera photo on disk
                // Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                // todo-- pass just the url instead and save bitmap to external storage
//                Intent i = new Intent(HomeActivity.this, PostActivity.class);
//                i.putExtra("BitmapImage", takenImage);
//                startActivity(i);
                ImageView ivPreview = (ImageView) getView().findViewById(R.id.ivPhoto);
                ivPreview.setImageBitmap(resizedBitmap);
                EditText etCaption = (EditText) getView().findViewById(R.id.etCaption);
                etCaption.setVisibility(View.VISIBLE);
                btnPost.setVisibility(View.VISIBLE);
                // Button btnPost = (Button) findViewById(R.id.btnPost);
                // RelativeLayout mRlayout = (RelativeLayout) findViewById(R.id.mRlayout);
//                EditText caption = new EditText(this); // Pass it an Activity or Context
//                caption.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
//                R.layout.activity_home.addView(caption);
            } else { // Result was a failure
                // Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}