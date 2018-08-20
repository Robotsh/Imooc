package com.robot.entiy;

import java.awt.image.BufferedImage;

public class BufferedImageWarp {

    private boolean key;

    private BufferedImage bufferedImage;

    public BufferedImageWarp(boolean key, BufferedImage bufferedImage) {
        this.key = key;
        this.bufferedImage = bufferedImage;
    }

    public boolean isKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
