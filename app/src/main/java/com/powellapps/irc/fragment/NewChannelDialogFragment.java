package com.powellapps.irc.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;

import com.powellapps.irc.R;
import com.powellapps.irc.firebase.FirebaseRepository;
import com.powellapps.irc.model.IrcChannel;
import com.powellapps.irc.utils.ConstantsUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewChannelDialogFragment extends DialogFragment {

    public NewChannelDialogFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    public static NewChannelDialogFragment newInstance(String id) {
        NewChannelDialogFragment fragment = new NewChannelDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_channel_dialog, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditText editTextName = getView().findViewById(R.id.editText_name);
        EditText editTextDescription = getView().findViewById(R.id.editText_description);
        Button buttonCreate = getView().findViewById(R.id.button_create);

        buttonCreate.setOnClickListener(v -> {
            String id = getArguments().getString(ConstantsUtils.ID);
            String name = editTextName.getText().toString();
            String description = editTextDescription.getText().toString();
            IrcChannel ircChannel = new IrcChannel(id);
            ircChannel.setName(name);
            ircChannel.setDescription(description);
            FirebaseRepository.save(ircChannel);
            dismiss();

        });
    }
    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

}
