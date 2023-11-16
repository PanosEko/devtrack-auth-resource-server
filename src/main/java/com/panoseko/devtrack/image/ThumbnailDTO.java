package com.panoseko.devtrack.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailDTO {
    @JsonProperty("id")
    private String imageId;
    @JsonProperty("data")
    private byte[] thumbnailData;

}