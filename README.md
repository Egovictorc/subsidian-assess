This is a [springboot](https://spring.io/projects/spring-boot) project bootstrapped
with [`start.spring.io`](start.spring.io)

## Getting Started

First, run the application:

```bash
./mvnw spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080) with your browser to see the result.

### Using the Application

<p align="center">
  <a href="https://github.com/Egovictorc/subsidian-assess/blob/main/src/main/resources/static/uploads/upload_file.png">
    <img alt="File upload" src="https://github.com/Egovictorc/subsidian-assess/blob/main/src/main/resources/static/uploads/upload_file.png" width="60" />
  </a>
</p>
Click the upload button to upload an existing file to be parsed   

<p align="center">
  <a href="https://github.com/Egovictorc/subsidian-assess/blob/main/src/main/resources/static/uploads/download_file.png">
    <img alt="File download" src="https://github.com/Egovictorc/subsidian-assess/blob/main/src/main/resources/static/uploads/download_file.png" width="60" />
  </a>
</p>
After uploading the file, the download button will appear, so you can download the uploaded file

You can start editing the page by modifying `src/main/resources/templates/index.html`.

[File API route](http://localhost:8080/api/file) can be accessed
on [http://localhost:8080/api/file](http://localhost:8080/api/file). This endpoint can be edited
in `src/main/java/com/vicego/subsidianAssessment/controller/FileRestController.java`.  

