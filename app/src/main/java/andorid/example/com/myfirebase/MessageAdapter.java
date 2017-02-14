package andorid.example.com.myfirebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Sabin on 2/14/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    public MessageAdapter(Context context, List<Message> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
         convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_message, parent, false);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_image_view);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name_text_view);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.message_text_view);

        Message message = getItem(position);

        boolean hasPhoto = message.getPhotoUrl() != null;

        if(hasPhoto) {
            messageTextView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(message.getPhotoUrl())
                    .into(imageView);

        } else {
            imageView.setVisibility(View.GONE);
            messageTextView.setVisibility(View.VISIBLE);
            messageTextView.setText(message.getText());
        }

        nameTextView.setText(message.getName());

        return convertView;
    }
}
