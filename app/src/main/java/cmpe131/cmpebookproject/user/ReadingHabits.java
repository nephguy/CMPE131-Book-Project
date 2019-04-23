package cmpe131.cmpebookproject.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public enum ReadingHabits implements Parcelable {
    SKIM ("Skim"),
    SCAN ("Scan"),
    INTENSIVE ("Intensive"),
    EXTENSIVE ("Extensive");

    
    
    
    private String friendlyName;

    ReadingHabits(String name) {
        friendlyName = name;
    }

    /*Thank the gods of Stack Overflow*/
    private static final Map<String, ReadingHabits> valueMap;
    static {
        final Map<String, ReadingHabits> initMap = new HashMap<String, ReadingHabits>();
        for (final ReadingHabits e : ReadingHabits.values())
            initMap.put(e.toString(),e);
        valueMap = initMap;
    }

    public static ReadingHabits getEnum(String strVal) {
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

    public static final Creator<ReadingHabits> CREATOR = new Creator<ReadingHabits>() {
        @Override
        public ReadingHabits createFromParcel(Parcel in) {
            return ReadingHabits.getEnum(in.readString());
        }

        @Override
        public ReadingHabits[] newArray(int size) {
            return new ReadingHabits[size];
        }
    };
}
