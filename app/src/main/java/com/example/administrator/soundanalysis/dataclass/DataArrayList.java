package com.example.administrator.soundanalysis.dataclass;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/25 0025.
 */

public class DataArrayList {
    public static ArrayList<short[]> mArrayLists = new ArrayList<short[]>();
    public static ArrayList<float[]> mArrayListf = new ArrayList<float[]>();

    public  DataArrayList(){
        //mArrayLists = new ArrayList<short[]>();
        //mArrayListf = new ArrayList<float[]>();
    }

    public ArrayList<float[]> getmArrayListf() {
        return mArrayListf;
    }

    public void setmArrayListf(float[] mArrayListf) {
        this.mArrayListf.add(mArrayListf);
    }

    public ArrayList<short[]> getmArrayLists() {
        return mArrayLists;
    }

    public void setmArrayLists(short[] mArrayLists) {
        this.mArrayLists.add(mArrayLists);
    }
}
