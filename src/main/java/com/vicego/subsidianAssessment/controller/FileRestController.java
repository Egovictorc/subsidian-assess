package com.vicego.subsidianAssessment.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@RestController
@RequestMapping("api/file")
@Slf4j
public class FileRestController {
    @Value("${upload-dir}")
    private String UPLOAD_DIR;

    public String uploadFile(MultipartFile file) {
        return null;
    }

    @GetMapping()
    public void downloadResource(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(name = "fileName") String name) throws IOException {
        log.info("Started downloading of: {}", name);
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
             */
            /**
             * Here we have mentioned it to show inline
             */
            // response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
            response.setContentLength((int) file.length());

            parseFile(filePath, new BufferedInputStream(new FileInputStream(file)));
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());

        } else {
            System.out.println("file does not exist");
        }
        log.info("Completed downloading of {}", name);
    }

    public void parseFile(String filePath, InputStream fis) throws IOException {
        log.info("Started parsing contents of: {}", filePath);
        try (InputStreamReader isr = new InputStreamReader(fis,
                StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            br.lines()
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .map(el -> {
                        String formatedEl = el.toLowerCase();
                        return formatedEl.concat("\n");
                    }).sorted()
                    .forEach(sb::append);

            //         System.out.println("data: " + sb.toString());
// Creates an output stream
            Files.writeString(Path.of(
                    filePath), sb.toString());
        }
        log.info("Successfully written contents of: {}", filePath);
    }
}