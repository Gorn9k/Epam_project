package entity;

import java.util.UUID;

public abstract class BaseEntity {
    private UUID id;

    BaseEntity(){
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
