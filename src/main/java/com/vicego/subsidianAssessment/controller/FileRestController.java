package com.vicego.subsidianAssessment.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/file")
public class FileRestController {
    @Value("${upload-dir}")
    private String UPLOAD_DIR;

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
            parseFile(new BufferedInputStream(new FileInputStream(file)));
        } else {

            System.out.println("file does not exist");
        }
    }

    public void parseFile(InputStream fis) throws IOException {

        try (InputStreamReader isr = new InputStreamReader(fis,
                StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            //br.lines().forEach(line -> System.out.println(line));
            List<String> data = br.lines()
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .map(String::toLowerCase).sorted()
                    .collect(Collectors.toList());

            System.out.println("data: " + data);

        }
    }

 /*   @ResponseBody
    //@GetMapping
    public ResponseEntity<?> downloadFile(@RequestParam(value = "filePath") String filePath) {
        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .toUriString();
        //return new FileSystemResource(new File("uploads/HTML Elements.pdf"));
        //Path path = Paths.get(baseUri + "/" + filePath);
        Resource resource = null;
        *//*try {
            //  resource = new UrlResource(path.toUri());
            resource = new UrlResource(baseUri + "/" + filePath);
            resource = new FileSystemResource(baseUri + "/" + filePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*//*
        resource = new FileSystemResource(filePath);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
        //return new FileSystemResource(new File(baseUri + filePath));

    }*/
}