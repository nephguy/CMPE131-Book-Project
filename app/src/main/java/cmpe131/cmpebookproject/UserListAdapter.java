package cmpe131.cmpebookproject;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.book.BookList;

public class UserListAdapter extends ArrayAdapter<BookList> {

    private Book itemToCheck;
    private int layoutRes;

    public UserListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects, Book itemToCheck) {
        super(context, resource, objects);
        this.itemToCheck = itemToCheck;
        this.layoutRes = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (convertView == null) convertView = inflater.inflate(layoutRes,parent,false);
        BookList list = getItem(position);

        TextView name = convertView.findViewById(android.R.id.text1);
        name.setText(list.getListName());
        if (list.contains(itemToCheck)) {
            name.setTextColor(name.getHintTextColors());
            name.setFocusableInTouchMode(false);
        }

        return convertView;
    }

    @Override
    public int getPosition(@Nullable BookList item) {
        return super.getPosition(item);
    }
}
