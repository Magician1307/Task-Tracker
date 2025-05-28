package com.cli.taskTracker.Enum;

public enum Status {
    
    TODO ("todo"),
    IN_PROGRESS ("in progress"),
    DONE ("done");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Status getEnum(String value) {
        for(Status status : Status.values()) {
            if(status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new IllegalArgumentException();
    }

}
