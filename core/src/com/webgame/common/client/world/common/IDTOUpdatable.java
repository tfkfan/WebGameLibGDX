package com.webgame.common.client.world.common;
import com.webgame.common.server.dto.DTO;

public interface IDTOUpdatable<T extends DTO> {
    void updateBy(T dto);
}
