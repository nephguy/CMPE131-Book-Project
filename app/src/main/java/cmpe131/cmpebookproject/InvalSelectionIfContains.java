package cmpe131.cmpebookproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class InvalSelectionIfContains<T extends AdapterSelectable> extends ArrayAdapter<T> {

    private Object itemToCheck;

    public InvalSelectionIfContains(@NonNull Context context, int resource, @NonNull List objects, Object itemToCheck) {
        super(context, resource, objects);
        this.itemToCheck = itemToCheck;
    }

    public void setItemToCheck (Object itemToCheck) {
        this.itemToCheck = itemToCheck;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (convertView == null) convertView = inflater.inflate(R.layout.support_simple_spinner_dropdown_item,parent,false);
        AdapterSelectable list = getItem(position);

        TextView name = convertView.findViewById(android.R.id.text1);
        name.setText(list.getListName());
        if (list.contains(itemToCheck)) {
            name.setTextColor(name.getHintTextColors());
            name.setFocusableInTouchMode(false);
        }

        return convertView;
    }

    @Override
    public int getPosition(@Nullable T item) {
        return super.getPosition(item);
    }
}
