package andorid.example.com.myfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
                if(charSequence.toString().trim().length() > 0)
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

                // clear message box
                mMessageEditText.setText("");
            }
        });

    }
}
