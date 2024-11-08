package image.module.convert;

import image.module.convert.service.RedisService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void testGetImage2() {
        // 이미지 이름
        String imageName = "test.jpg";

        // Redis에서 이미지 조회
        String base64Image = String.valueOf(redisService.getImage(imageName));



        System.out.println("Retrieved image (Base64): " + base64Image);
    }
}