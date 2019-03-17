package com.example.test1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Comment extends AppCompatActivity {
    final ArrayList<String> commentList = new ArrayList<>();
    private DatabaseReference mComment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
     //   ArrayList<Post> arrPosts= new ArrayList<>();
        TextView postView = findViewById(R.id.c_Post);
        final TextView hint = findViewById(R.id.hint);
        final LinearLayout commentLayout =findViewById(R.id.commentLayout);
        final ListView listView = findViewById(R.id.commentList);
        Intent intent = getIntent();
        final EditText commentText = findViewById(R.id.commnet_content);
        Button addCom = findViewById(R.id.comment_btn);
        String postContent = getIntent().getExtras().getString("post");
        postView.setText(postContent);
        String postId=intent.getStringExtra("postId");
        mComment = FirebaseDatabase.getInstance().getReference().child("Post/"+postId).child("Comment");
        addCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String StrCom = commentText.getText().toString();
                mComment.push().setValue(StrCom);
            }
        });
        final CustomComment commentAdapter = new CustomComment();
        listView.setAdapter(commentAdapter);

        mComment.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String Comment = dataSnapshot.getValue(String.class);
                commentList.add(Comment);
                commentAdapter.notifyDataSetChanged();
                listView.setSelection(commentAdapter.getCount()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Comment custom adapter
    class CustomComment extends BaseAdapter{

        @Override
        public int getCount() {
            return commentList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.comment_content,null);
            TextView textView = convertView.findViewById(R.id.comment_txt);
            textView.setText(commentList.get(position));
            return convertView;
        }
    }
}
