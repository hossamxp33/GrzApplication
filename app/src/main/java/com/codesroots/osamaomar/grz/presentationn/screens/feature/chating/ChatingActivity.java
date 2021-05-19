package com.codesroots.osamaomar.grz.presentationn.screens.feature.chating;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.codesroots.osamaomar.grz.R;
import com.codesroots.osamaomar.grz.databinding.ActivityChatingBinding;
import com.codesroots.osamaomar.grz.models.entities.ChatList;
import com.codesroots.osamaomar.grz.models.helper.ChatListCallbacks;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.chating.adapters.ChatAllListAdapter;
import com.codesroots.osamaomar.grz.presentationn.screens.feature.home.mainfragment.MainViewModelFactory;

public class ChatingActivity extends AppCompatActivity implements ChatListCallbacks {


    RecyclerView contacts;
    private ViewDataBinding activityChatingBinding;
    private ChatViewModel chatViewModel;
    private ChatAllListAdapter chatAllListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatingBinding = DataBindingUtil.setContentView(this,R.layout.activity_chating);
        chatViewModel = ViewModelProviders.of(this, getViewModelFactory()).get(ChatViewModel.class);
        //chatViewModel.getChatList(PreferenceHelper.getUserId());

        chatViewModel.chatListMutableLiveData.observe(this,chatList ->
                {
                    chatAllListAdapter = new ChatAllListAdapter(this,chatList);
                   // activityChatingBinding.setChatadapter(chatAllListAdapter);
                });

        chatViewModel.error.observe(this,throwable -> Toast.makeText(this,getText(R.string.erroroccure),Toast.LENGTH_SHORT).show());
    }

    private MainViewModelFactory getViewModelFactory() {
        return new MainViewModelFactory(getApplication());
    }


    @Override
    public void onItemClick(ChatList.DataBean item) {

    }

    @Override
    public void onCallItem(String phone) {

    }
}
