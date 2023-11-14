package com.panoseko.devtrack.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagePreview {

    private Long id;
    private byte[] data;

}