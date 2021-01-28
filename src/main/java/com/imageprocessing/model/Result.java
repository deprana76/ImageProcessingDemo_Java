package com.imageprocessing.model;

import lombok.*;

/**
 * Result object after processing the image
 */
@Data
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String url;
    private String firstColor;
    private String secondColor;
    private String thirdColor;
}
