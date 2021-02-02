package br.com.congregationreport.task;

public interface CustomCallable<R> {
    public void setUiForLoading();
    public void setDataAfterLoading(R result);
    public R callInBackground() throws Exception;
}
