package com.vitalykhan.webapps;

import com.vitalykhan.webapps.model.Resume;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFileRecursion {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("asd123");
        Field field = resume.getClass().getDeclaredField("uuid");
        field.setAccessible(true);
        System.out.println(field.get(resume));

        Method method = resume.getClass().getDeclaredMethod("toString");
        System.out.println(method.invoke(resume));

        List<File> workingDirFiles;

        workingDirFiles = getAllFiles(".");
        System.out.println(workingDirFiles.size());
    }

    private static List<File> getAllFiles(String path) {
        List<File> result = new ArrayList<>();

        List<File> currentDirFiles = Arrays.asList(new File(path).listFiles());
        for (File file : currentDirFiles) {
            if (file.isDirectory()) {
                result.addAll(getAllFiles(file.getPath()));
            } else {
                result.add(file);
            }
        }
        return result;
    }
}
