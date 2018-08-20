package com.robot.controller;


import com.robot.entiy.ImageResult;
import com.robot.until.Cache;
import com.robot.until.Image;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Controller
public class LoginController{
    @RequestMapping("/identify")
    public String identify(Model model, ServletResponse response,ServletRequest request) {
        try {

            ImageResult ir = Image.generateImage(request);
            model.addAttribute("file", ir.getName());
            model.addAttribute("tip", ir.getTip());
            Cache.put(ir.getUniqueKey(), ir);
            Cookie cookie = new Cookie("note", ir.getUniqueKey());
            ((HttpServletResponse) response).addCookie(cookie);
        } catch (IOException e) {

        }
        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(String location, ServletRequest request, String userName, String password) {
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        Cookie note = null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("note")){
                note = cookie;
                break;
            }
        }
        if (null == note) {
            return "ERROR";
        }
        ImageResult ir = Cache.get(note.getValue());
        Cache.remove(note.getName());
        if (null == location || "".equals(location)) {
            return "ERROR";
        }
        if (validate(location, ir)) {
            return "OK";
        }
        return "ERROR";
    }

    private boolean validate(String locationString, ImageResult imageResult) {

        String[] resultArray = locationString.split(";");
        int[][] array = new int[resultArray.length][2];
        for (int i = 0; i < resultArray.length; i++) {
            String[] temp = resultArray[i].split(",");
            array[i][0] = Integer.parseInt(temp[0]) + 150 - 10;
            array[i][1] = Integer.parseInt(temp[1]) + 300;
        }

        for (int i = 0; i < array.length; i++) {
            int location = location(array[i][1], array[i][0]);
            System.out.println("解析后的坐标序号：" + location);
            if (!imageResult.getKeySet().contains(location)) {
                return false;
            }
        }
        return true;
    }

    private static int location(int x, int y) {
        if (y >= 0 && y < 75) {
            return xLocation(x);
        } else if (y >= 75 && y <= 150) {
            return xLocation(x) + 4;
        } else {
            // 脏数据
            return -1;
        }
    }

    private static int xLocation(int x) {
        if (x >= 0 && x < 75) {
            return 0;
        } else if (x >= 75 && x < 150) {
            return 1;
        } else if (x >= 150 && x < 225) {
            return 2;
        } else if (x >= 225 && x <= 300) {
            return 3;
        } else {
            // 脏数据
            return -1;
        }
    }
}


