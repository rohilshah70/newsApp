package com.example.newsapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.newsapp.R;

public class MotionFragment extends Fragment {

    private String TAG = MotionFragment.class.getName();
    private TextView mTextView;
    private View mRootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.motion_layout, container, false);
        mTextView = mRootView.findViewById(R.id.open_news_list);

        //Use navigation on text view to go to ArticleListFragment
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_motionFragment_to_articleListFragment);

            }
        });

        return mRootView;
    }
}
