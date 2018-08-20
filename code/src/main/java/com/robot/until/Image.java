package com.robot.until;



import com.robot.entiy.BufferedImageWarp;
import com.robot.entiy.GenerateImageGroup;
import com.robot.entiy.ImageGroup;
import com.robot.entiy.ImageResult;

import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class Image {

    private static Map<String, ImageGroup> imageGroupMap = new HashMap<String, ImageGroup>();
    private static Map<Integer, Map<String, ImageGroup>> countGroupMap = new HashMap<Integer, Map<String, ImageGroup>>();

    /**
     * 生成图片     * @throws IOException
     */
    public static ImageResult generateImage(ServletRequest request) throws IOException {
        initImage();
        GenerateImageGroup generateImageGroup = randImageGroups();
        List<BufferedImageWarp> imageWarps = new ArrayList<BufferedImageWarp>();
        String realPath = request.getServletContext().getRealPath("/assets/");

        for (ImageGroup group : generateImageGroup.getGroups()) {
            for (String imgName : group.getImages()) {
                imageWarps.add(new BufferedImageWarp(false, getBufferImage(realPath+File.separator+imgName)));
            }
        }
        for (String imgName : generateImageGroup.getKeyGroup().getImages()) {
            imageWarps.add(new BufferedImageWarp(true,getBufferImage(realPath+File.separator+imgName)));
        }

        return meregeImage(request,imageWarps, generateImageGroup.getKeyGroup().getName());

    }

    /**
     * 随机生成图片
     *
     * @return
     */
    public static GenerateImageGroup randImageGroups() {
        List<ImageGroup> result = new ArrayList<ImageGroup>();
        int num = random(0, imageGroupMap.size() - 1);
        //获取相关的需要选中的key
        String name = new ArrayList<String>(imageGroupMap.keySet()).get(num);
        ImageGroup keyGroup = imageGroupMap.get(name);

        Map<Integer, Map<String, ImageGroup>> thisCountGroup = new HashMap<Integer, Map<String, ImageGroup>>(countGroupMap);

        thisCountGroup.get(keyGroup.getCount()).remove(name);
        // 假设总量8个，每种名称图片只有2个或者4个，为了逻辑简单些
        int leftCount = 8 - keyGroup.getCount();
        if (leftCount == 4) {
            // 继续产生随机数
            if (new Random().nextInt() % 2 == 0) {
                //判断产生的随机数是否被二整除是则产生4个图片的组合
                List<ImageGroup> groups = new ArrayList<ImageGroup>(thisCountGroup.get(4).values());

                if (groups.size() > 1) {
                    num = random(0, groups.size() - 1);
                } else {
                    num = 0;
                }
                result.add(groups.get(num));
            } else {
                //为奇数的时候则是2个2个的组合
                List<ImageGroup> groups = new ArrayList<ImageGroup>(thisCountGroup.get(2).values());
                int num1 = random(0, groups.size() - 1);
                result.add(groups.get(num1));

                int num2 = random(0, groups.size() - 1, num1);
                result.add(groups.get(num2));
            }
        } else if (leftCount == 6) {
            if (new Random().nextInt() % 2 == 0) {
                //偶数2+4+2
                List<ImageGroup> groups1 = new ArrayList<ImageGroup>(thisCountGroup.get(4).values());
                int num1 = random(0, groups1.size() - 1);
                result.add(groups1.get(num1));

                List<ImageGroup> groups2 = new ArrayList<ImageGroup>(thisCountGroup.get(2).values());
                int num2 = random(0, groups2.size() - 1);
                result.add(groups2.get(num2));
            } else {
                List<ImageGroup> groups = new ArrayList<ImageGroup>(thisCountGroup.get(2).values());
                int num1 = random(0, groups.size() - 1);
                result.add(groups.get(num1));

                int num2 = random(0, groups.size() - 1, num1);
                result.add(groups.get(num2));

                int num3 = random(0, groups.size() - 1, num1, num2);
                result.add(groups.get(num3));
            }
        } else if (leftCount == 2) {
            List<ImageGroup> groups = new ArrayList<ImageGroup>(thisCountGroup.get(2).values());
            result.add(groups.get(random(0, groups.size() - 1)));
        }
        return new GenerateImageGroup(keyGroup, result);
    }


    private static BufferedImage getBufferImage(String fileUrl) throws IOException {
        //这个目录是你自己存放照片的目录，这里我存放在G盘下
        File f = new File(fileUrl);
        return ImageIO.read(f);
    }

    /**
     * 初始化图片
     */
    public static void initImage() {
        ImageGroup group1 = new ImageGroup("包包", 4, "baobao/1.jpg", "baobao/2.jpg", "baobao/3.jpg", "baobao/4.jpg");
        ImageGroup group2 = new ImageGroup("老虎", 4, "laohu/1.jpg", "laohu/2.jpg", "laohu/3.jpg", "laohu/4.jpg");
        ImageGroup group3 = new ImageGroup("糖葫芦", 4, "tanghulu/1.jpg", "tanghulu/2.jpg", "tanghulu/3.jpg", "tanghulu/4.jpg");
        ImageGroup group4 = new ImageGroup("小慕", 4, "xiaomu/1.jpg", "xiaomu/2.jpg", "xiaomu/3.jpg", "xiaomu/4.jpg");
        ImageGroup group5 = new ImageGroup("柚子", 4, "youzi/1.jpg", "youzi/2.jpg", "youzi/3.jpg", "youzi/4.jpg");
        ImageGroup group6 = new ImageGroup("订书机", 2, "dingshuji/1.jpg", "dingshuji/2.jpg");
        ImageGroup group7 = new ImageGroup("蘑菇", 2, "mogu/1.jpg", "mogu/2.jpg");
        ImageGroup group8 = new ImageGroup("磁铁", 2, "xitieshi/1.jpg", "xitieshi/2.jpg");
        ImageGroup group9 = new ImageGroup("土豆", 2, "tudou/1.jpg", "tudou/2.jpg");
        ImageGroup group10 = new ImageGroup("兔子", 2, "tuzi/1.jpg", "tuzi/2.jpg");
        ImageGroup group11 = new ImageGroup("仙人球", 2, "xianrenqiu/1.jpg", "xianrenqiu/2.jpg");

        initMap(group1, group2, group3, group4, group5, group6, group7, group8, group9, group10, group11);
    }

    /**
     * 初始化图     * @param groups
     */
    public static void initMap(ImageGroup... groups) {
        for (ImageGroup group : groups) {
            imageGroupMap.put(group.getName(), group);
            if (!countGroupMap.containsKey(group.getCount())) {
                countGroupMap.put(group.getCount(), new HashMap<String, ImageGroup>());
            }
            countGroupMap.get(group.getCount()).put(group.getName(), group);
        }
    }

    /**
     * 获取随机数
     */
    private static int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private static int random(int min, int max, Integer... not) {
        int num = random(min, max);
        List<Integer> notList = Arrays.asList(not);
        while (notList.contains(num)) {
            num = random(min, max);
        }
        return num;
    }


    private static ImageResult meregeImage(ServletRequest request, List<BufferedImageWarp> imageWarps, String tip) throws IOException {
        Collections.shuffle(imageWarps);
        int width = 100;
        int height = 100;
        int totalWidth = width*4;

        BufferedImage destImage = new BufferedImage(totalWidth, 200, BufferedImage.TYPE_INT_BGR);
        int x1 = 0;
        int x2 = 0;
        int order = 0;
        List<Integer> keyOrderList = new ArrayList<Integer>();
        StringBuilder keysOrder = new StringBuilder();
        Set<Integer> keySet = new HashSet<Integer>();
        for (BufferedImageWarp image : imageWarps) {
            int[] rgb = image.getBufferedImage().getRGB(0, 0, width, height, null, 0, width);
            if (image.isKey()) {
                keyOrderList.add(order);
                int x = (order % 4) * 200;
                int y = order < 4 ? 0 : 200;
                keySet.add(order);
                keysOrder.append(order).append("(").append(x).append(",").append(y).append(")|");
            }
            if (order < 4) {
                destImage.setRGB(x1, 0, width, height, rgb, 0, width);
                x1 += width;
            } else {
                destImage.setRGB(x2, height, width, height, rgb, 0, width);
                x2 += width;
            }
            order++;
        }
        keysOrder.deleteCharAt(keysOrder.length() - 1);
        System.out.println("答案的位置:" + keysOrder);
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        String fileUrl=request.getServletContext().getRealPath("assets/daan/")+File.separator+fileName+".jpg";
        saveImage(destImage, fileUrl, "jpeg");

        ImageResult ir = new ImageResult();
        ir.setName(fileName + ".jpg");
        ir.setKeySet(keySet);
        ir.setUniqueKey(fileName);
        ir.setTip(tip);
        return ir;
    }

    private static boolean saveImage(BufferedImage destImage, String fileUrl, String format) throws IOException {
        File file = new File(fileUrl);
        return ImageIO.write(destImage, format, file);
    }


}
