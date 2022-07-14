package com.huangkailong.learning.source.jdk;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author huangkl
 * @date 2022-07-13 17:43:54
 */
public class ThreadTests {

    @Test
    void create_and_run() throws ExecutionException, InterruptedException {
        // 第一种方式
        Thread thread0 = new Thread(){
            @Override
            public void run() {
                System.out.println(getName());
            }
        };
        thread0.start();

        // 第二种方式
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        thread1.start();


        // 第三种方式
        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1+1);
        Thread thread2 = new Thread(futureTask);
        thread2.start();
        System.out.println("线程返回结果为："+futureTask.get());
    }


    private static volatile boolean notStart = true;
    private static volatile boolean notEnd = true;

    @Test
    void thread_priority() throws InterruptedException {
        List<Job> jobs = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            int threadPriority = i + 1;
            Job job = new Job(threadPriority);
            jobs.add(job);
            Thread thread = new Thread(job);
            thread.setName("thread-"+i);
            thread.setPriority(threadPriority);
            thread.start();
        }

        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;
        TimeUnit.SECONDS.sleep(1);

        for (Job job : jobs) {
            System.out.println(StrUtil.format("thread priority is {}, job count is {}", job.priority, job.jobCount));
        }
    }


    static class Job implements Runnable {
        long jobCount;
        int priority;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            while (notStart) {
                Thread.yield();
            }

            while (notEnd){
                jobCount++;
            }
        }
    }
}
