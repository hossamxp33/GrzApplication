package com.codesroots.osamaomar.Grz.presentationn.screens.feature.chating.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.codesroots.osamaomar.Grz.models.helper.PreferenceHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.CustomView> {


    private Context context;
  //  private List<chatmessages.DataBean> allMessage;
    PreferenceHelper preferenceHelper;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    Integer companyId;

    public ChatListAdapter(FragmentActivity activity) {
        this.context =  activity;
        preferenceHelper =new PreferenceHelper(context);
        SharedPreferences prefs = context.getSharedPreferences("My_Pref", Context.MODE_PRIVATE);
        if (prefs.getString("userId", "").matches("-?\\d+"))
            companyId =Integer.valueOf(prefs.getString("userId", ""));
    }


    @Override
    public ChatListAdapter.CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;

//            if (viewType != 1)
//                view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recycler_item_chat_send, parent, false);
//
//            else
//                view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.recycler_item_chat_recievied, parent, false);


        return new ChatListAdapter.CustomView(null);

    }


    @Override
    public int getItemViewType(int position) {
//
//        if (allMessage.get(position).getFromm()==companyId)
//            return VIEW_TYPE_MESSAGE_SENT;
//        else
//            return  VIEW_TYPE_MESSAGE_RECEIVED;

        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, final int position) {
//
//        if (!allMessage.get(position).getPost().matches("")) {
//            holder.message.setText(allMessage.get(position).getPost());
//            holder.cardwithimage.setVisibility(View.GONE);
//            holder.cardwithmessage.setVisibility(View.VISIBLE);
//            holder.time.setText(getTime(allMessage.get(position).getCreated()));
//        }
//        else if (!allMessage.get(position).getPhoto().matches(""))
//        {
//            holder.cardwithimage.setVisibility(View.VISIBLE);
//            holder.cardwithimage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showImage(context ,Uri.parse(allMessage.get(position).getPhoto()));
//                }
//            });
//            holder.cardwithmessage.setVisibility(View.GONE);
//            Glide.with(context).load(allMessage.get(position).getPhoto())
//
//                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
//
//                    .into(holder.itemImage);
//            holder.imTime.setText(getTime(allMessage.get(position).getCreated()));
//        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

     class CustomView extends RecyclerView.ViewHolder {

        private final View mView;
        private ImageView itemImage;
        private TextView message,time ,imTime;
        CardView cardwithimage;
        CardView cardwithmessage;


        private CustomView(View view) {
            super(view);
            mView = view;
//
//            message=mView.findViewById(R.id.tvMessage);
//            time=mView.findViewById(R.id.tvTime);
//            imTime = mView.findViewById(R.id.tvTimeomages);
//            itemImage=mView.findViewById(R.id.image);
//            cardwithmessage=mView.findViewById(R.id.cardmessage);
//            cardwithimage=mView.findViewById(R.id.cardforimage);

        }
    }

    private String  getTime(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        try {
            Date dateObj= sdf.parse(date);
            String timestamp = String.valueOf(dateObj.getTime());//  //Example -> in ms
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
            String dateString = formatter.format(new Date(Long.parseLong(timestamp)));
            return dateString;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Shows image  in full screen using  dialog
    public static void showImage(Context context , Uri uri) {
        Dialog dialog = new Dialog(context , android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.BLACK));
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(context);
        Glide.with(context)
                .load(uri) // Uri of the picture
                .into(imageView);
        dialog.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.show();

}
}
