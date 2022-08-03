package org.plutodjava.matchmaker.core.utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xu Ji An
 * @email 768212312@qq.com
 * 图片添加水印工具类
 */
@Slf4j
public class XuRemarkUtil {
    /**
     * 参考https://blog.csdn.net/weixin_44084096/article/details/125819799
     * 给图片添加文字水印
     * @param pressTextList 水印文字(一个集合一个值对应一条水印)
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static File pressText(List<String> pressTextList,
                                 File img, String fontName,
                                 int fontStyle, Color color, float alpha) {
        try {
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            // 计算水印字体大小 首先找出最长的水印文字
            //如果需要加水印的文字没有超过18个就按18个计算
            int maxLength=18;
            for (String pressText : pressTextList) {
                if (getLength(pressText)>maxLength){
                    maxLength=getLength(pressText);
                }
            }
            //根据图片宽度计算水字体大小
            int fontSize= (int) (((width)/(maxLength))*0.8);
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 循环在指定坐标绘制水印文字
            int count=pressTextList.size();
            for (String pressText : pressTextList) {
                //System.out.println(getLength(pressText));
                g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                        -2*fontSize, (height) -fontSize*count);
                      count--;
            }
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(img.getPath()));// 输出到文件流
            return img;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
    /**
     * 计算text的长度（一个中文算两个字符）
     * @param text
     * @return
     */
    public static int getLength(String text) { 
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

    /**
     * MultipartFile 转换为 File 文件
     *
     * @param multipartFile
     * @return
     */
    public static File transferToFile(MultipartFile multipartFile) {
        //选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            //获取文件后缀
            String prefix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //String[] filename = originalFilename.split("\\.");
            // file = File.createTempFile(filename[0], filename[1]);    //注意下面的 特别注意！！！
            file = File.createTempFile(originalFilename, prefix);    //创建零食文件
            multipartFile.transferTo(file);
            //删除
            file.deleteOnExit();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return file;
    }

    public static String getContentType(String fileUrl) {
        String contentType = null;
        try {
            contentType = new MimetypesFileTypeMap().getContentType(new File(fileUrl));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("getContentType, File ContentType is : " + contentType);
        return contentType;
    }
}

