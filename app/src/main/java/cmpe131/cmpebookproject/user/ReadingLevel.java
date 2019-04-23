package cmpe131.cmpebookproject.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public enum ReadingLevel implements Parcelable {
    KINDER ("Kindergarten"),
    ELEMENTARY ("Elementary"),
    MIDDLESCHOOL ("Middle School"),
    HIGHSCHOOL ("High School"),
    COLLEGE ("College"),
    PROFESSIONAL ("Professional");



    private String friendlyName;

    ReadingLevel(String name) {
        friendlyName = name;
    }

    /*Thank the gods of Stack Overflow*/
    private static final Map<String, ReadingLevel> valueMap;
    static {
        final Map<String, ReadingLevel> initMap = new HashMap<String, ReadingLevel>();
        for (final ReadingLevel e : ReadingLevel.values())
            initMap.put(e.toString(),e);
        valueMap = initMap;
    }

    public static ReadingLevel getEnum(String strVal) {
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

    public static final Creator<ReadingLevel> CREATOR = new Creator<ReadingLevel>() {
        @Override
        public ReadingLevel createFromParcel(Parcel in) {
            return ReadingLevel.getEnum(in.readString());
        }

        @Override
        public ReadingLevel[] newArray(int size) {
            return new ReadingLevel[size];
        }
    };
}
