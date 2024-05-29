package com.zzw.iCache.core.mock;

public class Test {

    public static Object lock = new Object();
    public static Boolean flag = true;
    public static void main(String[] args) {


        new Thread(()->{
                synchronized (lock){

                    for(int i=0;i<26;++i){
                        if (!flag){
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        System.out.println((char)(i+'a'));

                        lock.notify();
                        flag = !flag;
                    }

            }


        }).start();

        new Thread(()->{

            synchronized (lock){
                for(int i=0;i<26;++i){
                if (flag){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


                    System.out.println(i);

                    lock.notify();
                    flag = !flag;
                }


            }


        }).start();
    }
}
