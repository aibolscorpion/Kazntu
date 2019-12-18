package com.example.kazntu.ui.notification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kazntu.R;
import com.example.kazntu.data.entity.notification.Notification;
import com.example.kazntu.databinding.NotificationShortNewsBinding;
import com.example.kazntu.ui.notification.webViewNotification.WebViewNotification;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> implements NotificationListener {
    private List<Notification> notifications;
    private Context context;

    public NotificationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationShortNewsBinding notificationShortNewsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.notification_short_news, parent, false);
        return new NotificationAdapter.ViewHolder(notificationShortNewsBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        Notification currentNotification = notifications.get(position);
        holder.notificationShortNewsBinding.setNotification(currentNotification);
        holder.notificationShortNewsBinding.getWebClickListener();
        holder.notificationShortNewsBinding.setWebClickListener(this);
    }

    @Override
    public int getItemCount() {
        return (notifications == null) ? 0 : notifications.size();
    }

    void setResponseNotificationList(List<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    @Override
    public void WebClicked(Notification notification) {
        Intent in = new Intent(context, WebViewNotification.class);
        in.putExtra("WebViewNotification", notification);
        context.startActivity(in);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        NotificationShortNewsBinding notificationShortNewsBinding;
        public ViewHolder(@NonNull NotificationShortNewsBinding notificationShortNewsBinding) {
            super(notificationShortNewsBinding.getRoot());
            this.notificationShortNewsBinding = notificationShortNewsBinding;
        }
    }
}
