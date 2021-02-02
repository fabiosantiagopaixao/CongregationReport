package br.com.congregationreport.task;

public abstract class BaseTask<R> implements CustomCallable<R> {

    @Override
    public void setUiForLoading() {

    }

    @Override
    public R callInBackground() throws Exception {
        return null;
    }

    @Override
    public void setDataAfterLoading(R result) {

    }
}