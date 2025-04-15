package com.uniquindio.reporte.model.DTO.user.register;

public record FollowerRequestDto(
        String followerId,
        int rating,
        String idUserLoggin
) {
}
