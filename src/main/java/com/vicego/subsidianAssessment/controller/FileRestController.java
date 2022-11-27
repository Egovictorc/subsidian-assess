package com.vicego.subsidianAssessment.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.URLConnection;

@RestController
@RequestMapping("api/file")
public class FileRestController {
    @Value("{upload-dir}")
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public String uploadFile(MultipartFile file) {
        return null;
    }

    @GetMapping()
    public void downloadResource(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(name = "fileName") String name) throws IOException {
        String filePath = UPLOAD_DIR + StringUtils.cleanPath(name);
        File file = new File(filePath);
        if (file.exists()) {

            //get the mimetype
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);

            /**
             * In a regular HTTP response, the Content-Disposition response header is a
             * header indicating if the content is expected to be displayed inline in the
             * browser, that is, as a Web page or as part of a Web page, or as an
             * attachment, that is downloaded and saved locally.
             *
             */

            /**
             * Here we have mentioned it to show inline
             */
            // response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());

        } else {

            System.out.println("file does not exist");
        }
    }


    @ResponseBody
    //@GetMapping
    public ResponseEntity<?> downloadFile(@RequestParam(value = "filePath") String filePath) {
        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .toUriString();
        //return new FileSystemResource(new File("uploads/HTML Elements.pdf"));
        //Path path = Paths.get(baseUri + "/" + filePath);
        Resource resource = null;
        /*try {
            //  resource = new UrlResource(path.toUri());
            resource = new UrlResource(baseUri + "/" + filePath);
            resource = new FileSystemResource(baseUri + "/" + filePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
        resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
        //return new FileSystemResource(new File(baseUri + filePath));

    }
}