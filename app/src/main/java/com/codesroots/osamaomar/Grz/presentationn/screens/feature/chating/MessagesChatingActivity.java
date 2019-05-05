package com.codesroots.osamaomar.Grz.presentationn.screens.feature.chating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

import com.codesroots.osamaomar.Grz.R;
import com.codesroots.osamaomar.Grz.models.entities.chatmessages;
import com.codesroots.osamaomar.Grz.presentationn.screens.feature.chating.adapters.ChatListAdapter;

import java.util.List;

public class MessagesChatingActivity extends AppCompatActivity {


    ChatListAdapter chatListAdapter;
    private List<chatmessages.DataBean> allMessage;
    EditText etMessage;
    private static final int LOAD_IMG_REQUEST_CODE = 123;
    ImageView send, getimage , ivNotFound;
    ChatViewModel chatViewModel;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recylerview);
        chatListAdapter = new ChatListAdapter(MessagesChatingActivity.this);
        recyclerView.setAdapter(chatListAdapter);
        recyclerView.scrollToPosition(chatListAdapter.getItemCount() - 1);

    }
}
