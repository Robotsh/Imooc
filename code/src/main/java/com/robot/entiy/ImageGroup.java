package com.robot.entiy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ImageGroup {

    private String name;

    private int count;

    private Set<String> images;

    public ImageGroup(String name,int count,String...imageNames){
        this.name=name;
        this.count=count;
        this.images=new HashSet<String>();
        this.images.addAll(Arrays.asList(imageNames));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }
}
