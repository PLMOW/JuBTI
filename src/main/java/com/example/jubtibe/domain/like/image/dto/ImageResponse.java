package com.example.jubtibe.domain.like.image.dto;

import com.example.jubtibe.domain.like.image.entity.Images;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageResponse {
    private String image;
    public ImageResponse(Images image){
        this.image=image.getImage();
    }
}
