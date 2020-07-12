package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex1 {

    public static void main(String[] args) {
        ExecutorService executorService
                = Executors.newFixedThreadPool(4);




        System.out.println("abc");

    }
}
