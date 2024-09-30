package com.libraryapi.controllers;

import java.io.InputStream;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.libraryapi.payloads.ApiResponse;
import com.libraryapi.payloads.ContentDto;
import com.libraryapi.services.ContentService;
import com.libraryapi.services.FileService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/content")
@EnableMethodSecurity(prePostEnabled = true)
public class ContentControllers {

    @Autowired
    private ContentService contentService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
	private FileService fileservice;
	
	@Value("${Content.image}")
	private String path;

    @PreAuthorize("hasRole('ADMIN') or hasRole('AUTHOR')")
    @PostMapping("/{bookId}")
    public ResponseEntity<ContentDto>createContent(@RequestBody ContentDto contentDto,@PathVariable Integer bookId){
        ContentDto contentDto1 = this.contentService.createContent(contentDto, bookId);
        return new ResponseEntity<ContentDto>(contentDto1,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('AUTHOR')")
    @PutMapping("/{contentId}")
    public ResponseEntity<ContentDto>updateContent(@RequestBody ContentDto contentDto,@PathVariable Integer contentId){
        ContentDto contentDto1 = this.contentService.updateContent(contentDto, contentId);
        return new ResponseEntity<ContentDto>(contentDto1,HttpStatus.CREATED);
    }

    //delete
    @PreAuthorize("hasRole('ADMIN') or hasRole('AUTHOR')")
    @DeleteMapping("/{contentId}")
    public ApiResponse deleteContent(@PathVariable Integer contentId){
        this.contentService.deleteContent(contentId);
        return new ApiResponse("Content Delet Successfully !!!",true);
    }

    // getAllContentsByBookId
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ContentDto>>getAllContentsByBookId(@PathVariable Integer bookId){
        List<ContentDto> contentDtos = this.contentService.getAllContentsByBookId(bookId);
        return new ResponseEntity<>(contentDtos,HttpStatus.OK);
    }

    //getContentByBookAndPageNo
    @GetMapping("/book/{bookId}/pageNo/{pageNo}")
    public ResponseEntity<ContentDto>getContentByBookAndPageNo(@PathVariable Integer bookId,@PathVariable Integer pageNo){
        ContentDto contentDto = this.contentService.getContentByBookAndPageNo(bookId, pageNo);
        return new ResponseEntity<>(contentDto,HttpStatus.OK);
    }

    //getContentByBookAndContentTitleContaining
    @GetMapping("/book/{bookId}/keyword/{keyword}")
    public ResponseEntity<ContentDto>getContentByBookAndContentTitleContaining(@PathVariable Integer bookId,@PathVariable String keyword){
        ContentDto contentDto = this.contentService.getContentByBookAndContentTitleContaining(bookId, keyword);
        return new ResponseEntity<>(contentDto,HttpStatus.OK);
    }
    //post image upload
	@PostMapping("/image/upload/{contentId}/{imgId}")
	public ResponseEntity<ContentDto>uploadContentImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer contentId,
            @PathVariable Integer imgId
			) throws IOException, java.io.IOException{
		ContentDto contentDto = this.modelMapper.map(this.contentService.fetchContent(contentId), ContentDto.class);
		String fileName = this.fileservice.uploadImage(path, image);
		if(imgId == 1)contentDto.setImage1(fileName);
        else if(imgId == 2)contentDto.setImage2(fileName);
        else if(imgId == 3)contentDto.setImage3(fileName);
        else if(imgId == 4)contentDto.setImage4(fileName);
        else if(imgId == 5)contentDto.setImage5(fileName);
        else if(imgId == 6)contentDto.setImage6(fileName);
        else if(imgId == 7)contentDto.setImage7(fileName);
        else if(imgId == 8)contentDto.setImage8(fileName);
        else if(imgId == 9)contentDto.setImage9(fileName);
        else if(imgId == 10)contentDto.setImage10(fileName);
		ContentDto updateBook = this.contentService.updateContent(contentDto,contentId);
		return new ResponseEntity<ContentDto>(updateBook,HttpStatus.OK);
	}
	
	//method to serve files
	@GetMapping(value="/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException, java.io.IOException {
		InputStream resource = this.fileservice.getResource(path, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
}
