package fyp.com.charades;

import android.util.Log;

import java.util.ArrayList;

public class Variables {

    private static int count;
    private static ArrayList<String> filename = new ArrayList<>();

    public static int getCount(){
        return count;
    }

    public static void setCount(int val){
        count = val;
    }

    public static void appendFile(String name){
        filename.add(name);
        Log.d("appendingFile:", name);
    }

    public static ArrayList<String> getFilename(){
        return filename;
    }
}
