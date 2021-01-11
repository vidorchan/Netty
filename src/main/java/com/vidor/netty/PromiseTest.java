package com.vidor.netty;

import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;

public class PromiseTest {
    public static void main(String[] args) throws InterruptedException {
        EventExecutor executor = new DefaultEventExecutor();
        Promise promise = new DefaultPromise(executor);

        promise.addListener((future) -> {
            if (future.isSuccess()) {
                System.out.println("成功。。。"+future.get());
            } else {
                System.out.println("失败。。。"+future.cause());
            }

        });
        promise.addListener((future) -> {
            System.out.println("结束");
        });

        executor.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            // 设置 promise 的结果
            // promise.setSuccess("恭喜");
            promise.setFailure(new Throwable("失败"));
        });

        promise.sync();
    }
}
