package com.vicego.subsidianAssessment.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/")
public class HomeController {

    //private final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    @Value("{upload-dir}")
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    private final RestTemplate restTemplate;

    public HomeController(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @GetMapping
    public String homepage() {
        return "index";
    }

    String mediaType = null;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaType = file.getContentType();
        attributes.addFlashAttribute("fileName", fileName);
        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');


        return "redirect:/";
    }

   /* @GetMapping("/download")
    public String downloadFile(@RequestParam(name = "name") String name,
                               RedirectAttributes attributes,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException {

        boolean isName = name != null;
        if (name == null || name.equals("null")) {
            attributes.addFlashAttribute("message", "Please upload a file");
            return "redirect:/";
        } else {
            String filePath = UPLOAD_DIR + StringUtils.cleanPath(name);
            String fileDownloadUriApi = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/file")
                    .toUriString();

            //restTemplate.getForEntity(fileDownloadUri + "?filePath=" + filePath, String.class, mediaType);
            downloadResource(request, response, filePath);
            attributes.addFlashAttribute("message", "File downloaded successfully");
        }
        return "/";
    }*/

  /*  public void downloadResource(HttpServletRequest request, HttpServletResponse response,
                                 String fileUri) throws IOException {

        File file = new File(fileUri);
        if (file.exists()) {

            //get the mimetype
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);

            *//**
     * In a regular HTTP response, the Content-Disposition response header is a
     * header indicating if the content is expected to be displayed inline in the
     * browser, that is, as a Web page or as part of a Web page, or as an
     * attachment, that is downloaded and saved locally.
     *
     *//*

     *//**
     * Here we have mentioned it to show inline
     *//*
            // response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());

        } else {

            System.out.println("file does not exist");
        }
    }*/

 /*   @ResponseBody
    public ResponseEntity<?> downloadFile(String filePath) {
        String baseUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .toUriString();
        //return new FileSystemResource(new File("uploads/HTML Elements.pdf"));
        File file = new File(filePath);
        Resource resource = null;
        *//*try {
            //  resource = new UrlResource(path.toUri());
            resource = new UrlResource(baseUri + "/" + filePath);
            resource = new FileSystemResource(baseUri + "/" + filePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*//*
        resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }*/

}
