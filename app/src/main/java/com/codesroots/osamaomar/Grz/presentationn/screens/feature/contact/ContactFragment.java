package com.codesroots.osamaomar.grz.presentationn.screens.feature.contact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.models.helper.ResourceUtil;

public class ContactFragment extends Fragment {

    TextView phone1,phone2,mail;
    ImageView whats,insta;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment, container, false);

        phone1 = view.findViewById(R.id.phone1);
        phone2 = view.findViewById(R.id.phone2);
        mail = view.findViewById(R.id.mail);
        insta = view.findViewById(R.id.insta);
        whats = view.findViewById(R.id.whats);

        whats.setOnClickListener(v -> ResourceUtil.openWhatsApp("+9689211799",getContext()));
        phone1.setOnClickListener(v -> ResourceUtil.callNumber("+9689211799",getContext()));
        phone2.setOnClickListener(v -> ResourceUtil.callNumber("+9689211799",getContext()));

        return view;
    }





}
