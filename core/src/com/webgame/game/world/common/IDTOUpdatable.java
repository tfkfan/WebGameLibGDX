package com.webgame.game.world.common;

import com.webgame.game.server.serialization.dto.DTO;

public interface IDTOUpdatable<T extends DTO> {
    void updateBy(T dto);
}
