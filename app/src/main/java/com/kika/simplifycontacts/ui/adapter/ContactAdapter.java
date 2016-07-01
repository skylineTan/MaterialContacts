package com.kika.simplifycontacts.ui.adapter;

import android.content.Context;
import android.util.Log;
import com.kika.simplifycontacts.R;
import com.kika.simplifycontacts.bean.Contact;
import java.util.Collections;
import java.util.List;

/**
 * Created by skylineTan on 2016/6/30.
 */
public class ContactAdapter extends BaseRecyclerAdapter<Contact>{

    public ContactAdapter(Context context, int layoutResId, List<Contact> data) {
        super(context, layoutResId, data);
    }


    @Override protected void bindData(BaseViewHolder viewHolder, Contact
            item,int position) {
        viewHolder.setText(R.id.main_name,item.getName())
                .setImageName(R.id.main_avatar,String.valueOf(item.getFirstChar()));
        if(position != 0) {
            Contact prevContact = mData.get(position - 1);
            if (item.getFirstChar() != prevContact.getFirstChar()) {
                viewHolder.setVisible(R.id.main_letter, true);
                viewHolder.setText(R.id.main_letter, item.getFirstChar() + "");
            }
            else {
                viewHolder.setVisible(R.id.main_letter, false);
            }
        }else {
            viewHolder.setVisible(R.id.main_letter, true);
            viewHolder.setText(R.id.main_letter, item.getFirstChar() + "");
        }
    }
}
