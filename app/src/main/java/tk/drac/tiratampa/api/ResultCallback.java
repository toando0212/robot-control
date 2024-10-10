package tk.drac.tiratampa.api;

public interface ResultCallback<R, E> {
    void onSuccess(R result);
    void onError(E error);
}
