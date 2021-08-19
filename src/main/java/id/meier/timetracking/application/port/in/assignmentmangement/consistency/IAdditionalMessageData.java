package id.meier.timetracking.application.port.in.assignmentmangement.consistency;

public interface IAdditionalMessageData<T> {
    T getData();

    Class<T> getClazz();
}
