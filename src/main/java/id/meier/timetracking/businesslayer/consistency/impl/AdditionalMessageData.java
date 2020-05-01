package id.meier.timetracking.businesslayer.consistency.impl;



public class AdditionalMessageData<T> implements id.meier.timetracking.businesslayer.consistency.IAdditionalMessageData<T> {
    private final T data;
    private final Class<T> clazz;
    public AdditionalMessageData(T data, Class<T> clazz) {
        this.data = data;
        this.clazz = clazz;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public Class<T> getClazz() {
        return clazz;
    }

}
