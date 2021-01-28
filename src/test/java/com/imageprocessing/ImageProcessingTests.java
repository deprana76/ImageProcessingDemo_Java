package com.imageprocessing;

import com.imageprocessing.model.Result;
import com.imageprocessing.rgbscheme.ImageProcessing;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ImageProcessingTests {

    @Test
    public void successTest() {
        ImageProcessing processing = new ImageProcessing();
        try {
            Result result = processing.processImage("https://i.redd.it/s5viyluv421z.jpg");
            assert result != null;
            assert result.getThirdColor() != null;
        } catch (IOException e) {
            assert false;
        }
    }

    @Test
    public void failTest() {
        ImageProcessing processing = new ImageProcessing();
        try {
            Result result = processing.processImage("http://i.imgur.com/FApqk3D.jpg");
            assert result != null;
            assert result.getThirdColor() == null;
        } catch (IOException e) {
            assert false;
        }
    }
}
