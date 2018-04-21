package com.webgame.client.world.common;
import com.webgame.server.dto.DTO;

public interface IDTOUpdatable<T extends DTO> {
    void updateBy(T dto);
}
