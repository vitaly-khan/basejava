package com.vitalykhan.webapps;

public class MultiThreading2 {
    private int counter;

    public static void main(String[] args) {
        Person p1 = new Person("Adam", 10);
        Person p2 = new Person("Patrick", 20);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(fight(p1, p2).getName());
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(fight(p2, p1).getName());
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }

    public static Person fight(Person p1, Person p2) {
        Person winner;

        synchronized (p1) {
            synchronized (p2) {
                winner = p1.getPower() > p2.getPower() ? p1 : p2;
            }
        }
        return winner;
    }

    public static class Person {
        private String name;
        private int power;

        public Person(String name, int power) {
            this.name = name;
            this.power = power;
        }

        public int getPower() {
            return power;
        }

        public String getName() {
            return name;
        }
    }
}
