package com.robot.until;


import com.robot.entiy.ImageResult;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private static Map<String, ImageResult> cache=new HashMap<String, ImageResult>();
    public static void put(String note,ImageResult ir){
        cache.put(note,ir);
    }
    public static ImageResult get(String note){
        return cache.get(note);

    }
    public static void remove(String note){
        cache.remove(note);
    }
}
