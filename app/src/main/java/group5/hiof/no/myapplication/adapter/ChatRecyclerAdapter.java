package group5.hiof.no.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import group5.hiof.no.myapplication.R;
import group5.hiof.no.myapplication.model.Chat;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ChatViewHolder> {

    private List<Chat> data;
    private LayoutInflater mInflater;
    private View.OnClickListener clickListener;

    private FirebaseAuth mAuth;

    public ChatRecyclerAdapter(Context context, List<Chat> data, View.OnClickListener clickListener) {
        this.data = data;
        this.mInflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflates the movie_list_item.xml to a view for us
        View view = mInflater.inflate(R.layout.chat_list_item, parent, false);
        mAuth = FirebaseAuth.getInstance();
        // Create the viewholder with the corresponding view (list item)
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        // Gets the movie data we are going to use at the given position
        Chat currentChat = data.get(position);

        // Gives the movie data and clickListener to the ViewHolder
        // Makes it fill up the given position with the new data and add the clicklistener to the view
        holder.bind(currentChat, clickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView chatPartner;
        ImageView partnerAvatar;
        Button chatOptions;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            chatPartner = itemView.findViewById(R.id.chatPartnerName);
            partnerAvatar = itemView.findViewById(R.id.chatPartnerAvatar);

            chatOptions = itemView.findViewById(R.id.buttonChatOptions);
        }

        public void bind(Chat currentChat, View.OnClickListener clickListener) {
            // Fills the views with the given data
            partnerAvatar.setImageResource(currentChat.getAvatar());
            String partner = getPartner(mAuth.getUid(), currentChat);
            chatPartner.setText(partner);

            // Sets the onClickListener
            this.itemView.setOnClickListener(clickListener);

            // Sets onClickListener for the 'options' button
            chatOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OPTIONS", "CLICKED");
                }
            });
        }

        private String getPartner(String uid, Chat chat) {
            for(String participants : chat.getParticipants()) {
                if(!participants.equals(uid)) {
                    return participants;
                }
            }
            return "";
        }
    }
}
