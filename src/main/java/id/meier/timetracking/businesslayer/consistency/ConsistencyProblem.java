package id.meier.timetracking.businesslayer.consistency;

public enum ConsistencyProblem {

    MISSING_START_DATE(ProblemLevel.ERROR, "time.tracking.conistency.error.start.date.missing"),
    MISSING_END_DATE(ProblemLevel.ERROR, "time.tracking.conistency.error.end.date.missing"),
    MISSING_START_TIME(ProblemLevel.ERROR, "time.tracking.conistency.error.start.time.missing"),
    MISSING_END_TIME(ProblemLevel.ERROR, "time.tracking.conistency.error.end.time.missing"),
    DATE_TIME_OVERLAP(ProblemLevel.ERROR, "time.tracking.conistency.error.date.time.overlap"),
    MISSING_PROJECT(ProblemLevel.ERROR, "time.tracking.conistency.missing.project"),
    MISSING_PHASE(ProblemLevel.WARN, "time.tracking.conistency.missing.phase"),
    MISSING_TASK(ProblemLevel.WARN, "time.tracking.conistency.missing.task"),
    MISSING_DESCRIPTION(ProblemLevel.INFO, "time.tracking.conistency.missing.description");

    private ProblemLevel level;
    private String messageKey;

    ConsistencyProblem(ProblemLevel level, String messageKey) {
        this.level = level;
        this.messageKey = messageKey;
    }

    public ProblemLevel getLevel() {
        return level;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setLevel(ProblemLevel level) {
        this.level = level;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
