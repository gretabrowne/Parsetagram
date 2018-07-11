package codepath.com.parsetagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ProfileFragment extends Fragment {

    Button logOut;
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
//        logOut = view.findViewById(R.id.button);
//        logOut.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View v) {
//            ParseUser.logOut();
//            ParseUser currentUser = ParseUser.getCurrentUser();
//            // log out
//        }
//    }
}
