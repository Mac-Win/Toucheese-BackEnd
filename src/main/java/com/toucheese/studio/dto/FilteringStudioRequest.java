package com.toucheese.studio.dto;

import com.toucheese.studio.entity.Location;
import lombok.Builder;

import java.util.List;

@Builder
public record FilteringStudioRequest(
    Integer price,
    Float rating,
    List<Location> locations
) {

}
