package com.webgame.game.world.common;

import com.webgame.game.server.dto.DTO;

public interface IDTOUpdatable<T extends DTO> {
    void updateBy(T dto);
}
