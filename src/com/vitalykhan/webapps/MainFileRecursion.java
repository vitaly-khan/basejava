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
        printAllFiles(".\\src", 0);

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

    private static void printAllFiles(String path, int spaces) {
        File file = new File(path);
        ArrayList<File> directories = new ArrayList<>();
        ArrayList<File> files = new ArrayList<>();
        shift(spaces);
        String[] subdirs = path.split("\\\\");
        System.out.println(subdirs[subdirs.length - 1].toUpperCase());

        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                directories.add(f);
            } else {
                files.add(f);
            }
        }
        directories.forEach(x -> printAllFiles(x.toString(), spaces + 3));
        for (File f : files) {
            shift(spaces + 3);
            System.out.println(f.getName());
        }
    }

    private static void shift(int spaces) {
        for (int i = 0; i < spaces; i++) {
            System.out.print(" ");
        }
    }
}
