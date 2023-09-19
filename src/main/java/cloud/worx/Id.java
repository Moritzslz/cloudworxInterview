package cloud.worx;

import java.util.UUID;

public class Id {
    private UUID uuid;

    public Id() {
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
