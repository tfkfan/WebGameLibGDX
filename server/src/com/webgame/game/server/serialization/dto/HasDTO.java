package com.webgame.game.server.serialization.dto;

public interface HasDTO <T extends DTO> {
    T createDTO();
}
