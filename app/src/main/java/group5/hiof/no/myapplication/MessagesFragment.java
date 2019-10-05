package group5.hiof.no.myapplication;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import group5.hiof.no.myapplication.adapter.ChatRecyclerAdapter;
import group5.hiof.no.myapplication.model.Chat;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private ArrayList<Chat> chatList;
    private RecyclerView chatRecyclerView;

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        chatList = Chat.getChats();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);

        chatRecyclerView.setAdapter(new ChatRecyclerAdapter(view.getContext(), chatList, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gets the position of the item that's clicked
                int position = chatRecyclerView.getChildAdapterPosition(view);

                // Gets the movie based on which item got clicked
                Chat clickedChat = chatList.get(position);

                Intent fullChat = new Intent(getContext(), ChatActivity.class);
                fullChat.putExtra("CHAT", clickedChat);
                startActivity(fullChat);

            }
        }));


        chatRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
    }
}
