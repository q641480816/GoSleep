package mimanor.com.gosleep.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mimanor.com.gosleep.R;

public class Sleep extends Fragment {

    private View view;
    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gs_fg_sleep, container, false);

        bindView();
        return view;
    }

    private void bindView(){
        text = view.findViewById(R.id.txt_content);
    }

}
