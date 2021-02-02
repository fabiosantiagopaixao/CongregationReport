package br.com.congregationreport.task;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRunner {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newCachedThreadPool();

    public <R> void executeAsync(CustomCallable<R> callable) {
        try {
            callable.setUiForLoading();
            executor.execute(new RunnableTask<R>(handler, callable));
        } catch (Exception e) {
            Log.println(e.hashCode(), "", e.getMessage());
        }
    }

    public static class RunnableTask<R> implements Runnable {
        private final Handler handler;
        private final CustomCallable<R> callable;

        public RunnableTask(Handler handler, CustomCallable<R> callable) {
            this.handler = handler;
            this.callable = callable;
        }

        @Override
        public void run() {
            try {
                final R result = callable.callInBackground();
                handler.post(new RunnableTaskForHandler(callable, result));
            } catch (Exception e) {
                Log.println(e.hashCode(), "", e.getMessage());
            }
        }
    }

    public static class RunnableTaskForHandler<R> implements Runnable {

        private CustomCallable<R> callable;
        private R result;

        public RunnableTaskForHandler(CustomCallable<R> callable, R result) {
            this.callable = callable;
            this.result = result;
        }

        @Override
        public void run() {
            callable.setDataAfterLoading(result);
        }
    }
}