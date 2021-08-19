package id.meier.timetracking.application.services.consistency;


import id.meier.timetracking.application.port.in.assignmentmangement.consistency.IAdditionalMessageData;

public class AdditionalMessageData<T> implements IAdditionalMessageData<T> {
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
