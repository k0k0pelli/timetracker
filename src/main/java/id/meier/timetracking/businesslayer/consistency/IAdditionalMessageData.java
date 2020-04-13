package id.meier.timetracking.businesslayer.consistency;

public interface IAdditionalMessageData<T> {
    T getData();

    Class<T> getClazz();
}
