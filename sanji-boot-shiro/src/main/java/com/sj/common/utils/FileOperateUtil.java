package com.sj.common.utils;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by sunxyz on 2016/12/21.
 */
public final class FileOperateUtil {

    private FileOperateUtil() {

    }

    public static void download(HttpServletRequest request, HttpServletResponse response, String storeName, Workbook book) throws Exception {

        response.setContentType("application/ms-excel");
        String agent = request.getHeader("USER-AGENT");//用户代理

        // 防止中文文件名乱码
        String codedFileName = storeName;
        if (null != agent && -1 != agent.indexOf("MSIE")) {
            codedFileName = URLEncoder.encode(storeName, "UTF-8").replaceAll("+", "%20");
        } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
            codedFileName = MimeUtility.encodeText(storeName, "UTF-8", "B");
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + codedFileName);
        book.write(response.getOutputStream());
    }


}