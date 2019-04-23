package cmpe131.cmpebookproject.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public enum Gender implements Parcelable {
    MALE ("Male"),
    FEMALE ("Female"),
    OTHER ("Other");

    
    
    
    private String friendlyName;

    Gender(String name) {
        friendlyName = name;
    }

    /*Thank the gods of Stack Overflow*/
    private static final Map<String, Gender> valueMap;
    static {
        final Map<String, Gender> initMap = new HashMap<String, Gender>();
        for (final Gender e : Gender.values())
            initMap.put(e.toString(),e);
        valueMap = initMap;
    }

    public static Gender getEnum(String strVal) {
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

    public static final Parcelable.Creator<Gender> CREATOR = new Parcelable.Creator<Gender>() {
        @Override
        public Gender createFromParcel(Parcel in) {
            return Gender.getEnum(in.readString());
        }

        @Override
        public Gender[] newArray(int size) {
            return new Gender[size];
        }
    };
}
