package com.vitalykhan.webapps;

import com.vitalykhan.webapps.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("asd123");
        Field field = resume.getClass().getDeclaredField("uuid");
        field.setAccessible(true);
        System.out.println(field.get(resume));

        Method method = resume.getClass().getDeclaredMethod("toString");
        System.out.println(method.invoke(resume));


    }
}
