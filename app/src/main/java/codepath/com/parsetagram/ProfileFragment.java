package codepath.com.parsetagram;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.ParseUser;


public class ProfileFragment extends Fragment implements View.OnClickListener{


    private ProfileFragment.OnItemSelectedListener listener;
    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        public void onHitLogOutButton();
    }

    Button logOut;

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CameraFragment.OnItemSelectedListener) {
            listener = (ProfileFragment.OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ProfileFragment.OnItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        // view setups and attaching listeners
        logOut = view.findViewById(R.id.button);
        logOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            listener.onHitLogOutButton();
    }
}

