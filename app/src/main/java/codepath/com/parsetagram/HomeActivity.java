package codepath.com.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseUser;

import java.io.File;

public class HomeActivity extends AppCompatActivity implements CameraFragment.OnItemSelectedListener {

    public final String APP_TAG = "Parsetagram";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg"; // todo-- what goes here?
    final FragmentManager fragmentManager = getSupportFragmentManager();
    File photoFile;
    // define your fragments here
    final Fragment fragment1 = new FeedFragment();
    final Fragment fragment2 = new CameraFragment();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
        fragmentTransaction1.replace(R.id.placeholder, fragment1).commit();

        setContentView(R.layout.activity_home);


        // final Fragment fragment3 = new ThirdFragment();

        // handle navigation selection

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.home:
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.placeholder, fragment1).commit();
                                return true;
                            case R.id.camera:
                                Log.d("HomeActivity", "going to camera fragment");
                                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                                fragmentTransaction2.replace(R.id.placeholder, fragment2).commit();
                                return true;
//                            case R.id.profile:
//                                FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
//                                fragmentTransaction3.replace(R.id.placeholder, fragment3).commit();
//                                return true;
                        }
                        return false;
                    }
                });
    }

//    public void onLaunchCamera(View view) {
//        // create Intent to take a picture and return control to the calling application
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Create a File reference to access to future access
//        photoFile = getPhotoFileUri(photoFileName);
//
//        // wrap File object into a content provider
//        // required for API >= 24
//        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
//        Uri fileProvider = FileProvider.getUriForFile(HomeActivity.this, "com.codepath.fileprovider", photoFile);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
//
//        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
//        // So as long as the result is not null, it's safe to use the intent.
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // Start the image capture intent to take photo
//            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//        }
//    }
//
//    // Returns the File for a photo stored on disk given the fileName
//    public File getPhotoFileUri(String fileName) {
//        // Get safe storage directory for photos
//        // Use `getExternalFilesDir` on Context to access package-specific directories.
//        // This way, we don't need to request external read/write runtime permissions.
//        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
//            Log.d(APP_TAG, "failed to create directory");
//        }
//
//        // Return the file target for the photo based on filename
//        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
//
//        return file;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                Bitmap rawTakenImage = BitmapFactory.decodeFile(photoFile.getPath());
//                Display display = getWindowManager().getDefaultDisplay();
//                Point size = new Point();
//                display.getSize(size);
//                int width = size.x;
//                // int height = size.y;
//                Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(rawTakenImage, width);
//                // Configure byte output stream
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//// Compress the image further
//                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
//// Create a new file for the resized bitmap (`getPhotoFileUri` defined above)
//                // File resizedFile = new File()
//                File resizedFile = getPhotoFileUri(photoFileName);
//                try {
//                    resizedFile.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                FileOutputStream fos = null;
//                try {
//                    fos = new FileOutputStream(resizedFile);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//// Write the bytes of the bitmap to file
//                // file file = getPhotoFileUri(name + path)
//
//                try {
//                    fos.write(bytes.toByteArray());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                // by this point we have the camera photo on disk
//                // Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                // RESIZE BITMAP, see section below
//                // Load the taken image into a preview
//                // todo-- pass just the url instead and save bitmap to external storage
////                Intent i = new Intent(HomeActivity.this, PostActivity.class);
////                i.putExtra("BitmapImage", takenImage);
////                startActivity(i);
//                ImageView ivPreview = (ImageView) findViewById(R.id.ivPreview);
//                ivPreview.setImageBitmap(resizedBitmap);
//                EditText etCaption = (EditText) findViewById(R.id.etCaption);
//                etCaption.setVisibility(View.VISIBLE);
//                // Button btnPost = (Button) findViewById(R.id.btnPost);
//                // RelativeLayout mRlayout = (RelativeLayout) findViewById(R.id.mRlayout);
////                EditText caption = new EditText(this); // Pass it an Activity or Context
////                caption.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
////                R.layout.activity_home.addView(caption);
//            } else { // Result was a failure
//                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
    // logging out user
    public void onClick(View view) {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(i);
    }
//
//    // when user clicks post button, go to timeline fragment
//    public void onPostClick(View view) {
//        // uploads photo just taken and "posts" to Instagram
//        File photoFile = getPhotoFileUri(photoFileName);
//        final Post post = new Post("post");
//        final EditText etCaption = findViewById(R.id.etCaption);
//        post.setOwner(ParseUser.getCurrentUser());
//        post.setBody(etCaption.getText().toString());
//        ParseFile parseFile = new ParseFile(photoFile);
//        post.setMedia(parseFile);
//        post.saveInBackground(new SaveCallback() {
//                                  @Override
//                                  public void done(ParseException e) {
//                                      Log.d("HomeActivity", "Post id: " + post.getObjectId());
//                                      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                      fragmentTransaction.replace(R.id.placeholder, fragment1).commit();
//                                  }
//                              });
//
//    }

    @Override
    public void onHitPostButton() {
            // launch feed fragment
            Log.d("HomeActivity", "onHitPostButton");
            bottomNavigationView.setSelectedItemId(R.id.home);

    }
}
