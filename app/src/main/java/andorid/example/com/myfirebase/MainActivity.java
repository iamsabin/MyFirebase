package andorid.example.com.myfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView mMessageListView;
    private EditText mMessageEditText;
    private TextView mNameTextView;
    private ImageButton mPhotoImageButton;
    private MessageAdapter mMessageAdapter;
    private Button mSendButton;
    private ProgressBar mProgressBar;

    private static final String ANONYMOUS = "Anonymous";

    private String mUserName;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference view to layout defined variables
        mMessageListView = (ListView) findViewById(R.id.message_list_view);
        mMessageEditText = (EditText) findViewById(R.id.message_edit_text);
        mNameTextView = (TextView) findViewById(R.id.name_text_view);
        mPhotoImageButton = (ImageButton) findViewById(R.id.photo_picker_button);
        mSendButton = (Button) findViewById(R.id.send_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mUserName = ANONYMOUS;

        // Firebase instance variables
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("messages");

        // initialize listview and adapter
        List<Message> messages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, messages);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(View.GONE);

        // Fire an intent when user clicks the button
        mPhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            }
        });

        // Only enable send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0)
                    mSendButton.setEnabled(true);
                else
                    mSendButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // When send button is clicked
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(mMessageEditText.getText().toString(),
                        null,
                        mUserName);

                mDatabaseReference.push().setValue(message);

                // clear message box
                mMessageEditText.setText("");
            }
        });

        // listening for message change
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                mMessageAdapter.add(message);

                Log.v("LOG_TAG", message.getName() + " " + message.getText());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        // adding listening for message change to database
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }
}
