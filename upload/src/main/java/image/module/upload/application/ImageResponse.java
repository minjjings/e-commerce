package image.module.upload.application;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private UUID id;
    private String originalFileName;
    private String storedFileName;
    private String cdnUrl;
    private String fileType;
    private Integer width;
    private Integer height;
    private UUID originalFileUUID;
}