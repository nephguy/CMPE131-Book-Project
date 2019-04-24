package cmpe131.cmpebookproject.book;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public enum Genre implements Parcelable {
    Mystery ("Mystery"),
    Fiction ("Fiction"),
    Comedy ("Comedy"),
    Drama ("Drama"),
    Horror ("Horror"),
    Religious ("Religious"),
    Poetry ("Poetry"),
    Elegy ("Elegy"),
    Autobiography ("Autobiography"),
    Biography ("Biography"),
    Scifi ("Science Fiction"),
    Romance ("Romance"),
    Kids ("Kids"),
    Self_Development ("Self-Development"),
    Music ("Music"),
    Cooking ("Cooking"),
    Academic ("Academic"),
    Sports ("Sports"),
    Other ("Other");




    private String friendlyName;

    Genre(String name) {
        friendlyName = name;
    }

    /*Thank the gods of Stack Overflow*/
    private static final Map<String, Genre> valueMap;
    static {
        final Map<String, Genre> initMap = new HashMap<String, Genre>();
        for (final Genre e : Genre.values())
            initMap.put(e.toString(),e);
        valueMap = initMap;
    }

    public static Genre getEnum(String strVal) {
        if(!valueMap.containsKey(strVal)) {
            throw new IllegalArgumentException("Unknown String Value: " + strVal);
        }
        return valueMap.get(strVal);
    }
    /*Thank the gods of Stack Overflow*/

    public String toString() {
        return friendlyName;
    }





    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(friendlyName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return Genre.getEnum(in.readString());
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}
