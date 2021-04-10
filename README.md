# ShareNow
It is an advanced file sharing system, that uses backup servers and simple load balancing in backend along with multiple features in frontend to make user experience smooth.

It helps users to store and retrieve files across different platforms and devices with web or desktop software. Basically users can create backup of data to be recoverd on time using single or zip mode for multiple files.

## Demo video link : [here](https://youtu.be/wvLMr-pG2gE)
## Presentation link : [ppt link](https://drive.google.com/file/d/1GJ8cmpjqho6VvNwTgfITJpu2CoOCWVUH/view?usp=sharing)

## Basic Features:
### User Features:
1. A user should be able to register login and logout to the server.
2. Every user should be able to upload and download files.
3. File uploaded must be transferred to the server along with all the relevant
details like owner, name, size, type.
4. Uploader should be able to tell how many times the given file can be downloaded.
After that file is downloaded the mentioned number of times it should be
deleted from the server.
5. Every uploaded file must have a code generated which will be shared.
6. Every user should be able to download files from the server with the code given
to him.
### Admin Features:
1. Separate login for Admin.
2. Admin should be able to see how many files have been shared by all the users
along with all file info and number of downloads left.
3. Admin should be able to remove any file on ground of violation of code of
conduct.
### Advanced Features:
1. Uploader can set the time for which the file should be available for download,
after that time passes the file should be deleted from the server
2. Uploader can revoke a file using its identifier. If he does so then that file
identifier can't be used to download a file anymore or file gets deleted from the
server
3. There can be multiple storage servers to store files with a proper load balancing
based on some parameter.
4. File can be stored to multiple servers so that if a storage server goes down the
user is still able to download the file.

## Technology used:
Java, Java Swing, Spring Boot(for backend), SQL, JDBC, JSON/GSON
### Tools and IDE used:
Postman, Github, IntelliJ

## Contributors :
Team name : come_sort
1. [Aashish Tandon](https://github.com/aashishat718)
2. [Bhanu Pratap Singh](https://github.com/bhallaldev22)
3. [Shivam Gupta](https://github.com/samsoldeinstein)
4. [Sujeet Kushwaha](https://github.com/su050300)


![image](https://user-images.githubusercontent.com/54476719/114285013-8186a900-9a71-11eb-832d-20ecd6bff451.png)

