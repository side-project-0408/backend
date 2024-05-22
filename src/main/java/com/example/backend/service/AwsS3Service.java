package com.example.backend.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file) throws IOException {
        if(file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
            throw new RuntimeException("파일이 존재하지 않습니다.");
        }
        return this.uploadFileToS3(file);
    }
    private String uploadFileToS3(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //원본 파일명
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자명

        String s3FileName = UUID.randomUUID().toString().substring(1, 10) + originalFilename; //변경 파일명

        InputStream is = file.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is); //file을 byte[]로 변환

        ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
        metadata.setContentType("image/" + extension);
        metadata.setContentLength(bytes.length);

        //S3에 요청할 때 사용할 byteInputStream 생성
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            //S3로 putObject 할 때 사용할 요청 객체
            //생성자 : bucket 이름, 파일명, byteInputStream, metadata
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucket, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);
            //실제로 S3에 파일 데이터를 넣는 부분
            amazonS3Client.putObject(putObjectRequest); //이미지를 S3으로
        } catch (Exception e) {
            throw new IOException("S3 전송 오류");
        } finally {
            byteArrayInputStream.close();
            is.close();
        }
        return amazonS3Client.getUrl(bucket, s3FileName).toString();
    }

    //파일의 public url을 이용하여 S3에서 해당 이미지 제거

    public void deleteFileFromS3(String fileAddress) {
        if(fileAddress.isEmpty() || fileAddress == null) return;

        String key = getKeyFromFileAddress(fileAddress);
        if(key.equals("default_images_0520.jpg")) return;

        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
        } catch (Exception e) {
            throw new RuntimeException("이미지 삭제 실패");
        }
    }

    //url 값으로 잘라주기
    private String getKeyFromFileAddress(String fileAddress) {
        try {
            URL url = new URL(fileAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨앞 "/" 제거
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw new RuntimeException("이미지 삭제 실패");
        }
    }

}

