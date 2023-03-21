package kr.co.javacafe.controller;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import kr.co.javacafe.dto.UploadFileDTO;
import kr.co.javacafe.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;


@RestController
@Log4j2
public class UpDownController {

	@Value("${upload.path}")
	private String uploadPath;
	
	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
		
		File file = new File(uploadPath+File.separator+fileName);
		HttpHeaders headers = new HttpHeaders();
		Resource resource = null;
		String resourceName = null;
		
		if(fileName=="" || fileName==null) {
			System.out.println("파일의 이름을 입력하세요.");
			resource = new FileSystemResource(uploadPath+File.separator+"noimg.jpg");
		}
		if(file.exists()) {
			System.out.println("해당 파일이 존재합니다.");
			resource = new FileSystemResource(uploadPath+File.separator+fileName);
			resourceName = resource.getFilename();
		}else {
			System.out.println("해당 파일이 존재하지 않습니다.");
			resource = new FileSystemResource(uploadPath+File.separator+"noimg.jpg");
		}
		try {
			headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
		} catch(Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok().headers(headers).body(resource);			
	}
}
