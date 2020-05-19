package com.standbyside.testapi.util;

import java.io.File;

public class DeleteFileUtils {

    /**
     * 删除除html外的所有文件.
     */
    public static void deleteFilesExcludeHtml(String folderPath) {
        System.out.println("enter " + folderPath);
        File[] files = new File(folderPath).listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".html")) {
                continue;
            }
            if (file.isDirectory()) {
                deleteFilesExcludeHtml(file.getAbsolutePath());
            } else {
                System.out.println("delete " + folderPath);
                file.deleteOnExit();
            }
        }
    }
}
