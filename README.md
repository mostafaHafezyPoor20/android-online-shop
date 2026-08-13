## Shik Shop

shik shop is a childern's clothing e-commerce Android application developed for a real client 

The application allows users to browse and search for childern's clothing,view product details , select the appropriate size , add products to their shopping cart , and place orders.

The Android application was developed using Java and XML , following the MVVM architecture, and communicates with a PHP-based backend through REST APIs .
MySQL is used as the database on the server side.

The project also integrates several Android and third-part technologies to provide a smoother and more modern user experience,including Retrofit , Picasso , Firebase,Lottie,Shimmer,BlurView and CircleImageView



## Download APK
** Android APK ** Download the latest release and try the application
[Download Shik Shop APK](app-release.apk)

## Features 
1. User registration and authentication
2. Browse childern's clothing products 
3. Search and explore products 
4. View detailed product information 
5. Select clothing sizes
6. Add products to the shopping cart
7. Manage cart items
8. place orders
9. Communicate with the backend through REST APIs
10. Responsive and user-friendly Android interface
11. Smooth loading and visual animations
12. Firebase integration


## Tech Stack
### Android 
* Java
* XML
* Android SDK
* MVVM Architecture

### Networking & Backend
* Retrofit for REST API communication
* PHP for the backend
* MySQL for database management

### Libraries & Services 
* Picasso for image loading
* Lottie for animations
* Shimmer for loading placeholders
* BlurView for blur effects
* CircleImageView for circular image views
* Firebase for google Firebase services


  ## Screenshots
  <p align="center">
   <img style="padding:20px" width="200" height ="400"  alt="7" src="https://github.com/user-attachments/assets/7c586b11-104f-497a-97ae-f78c68765afa" />
    <img style="padding:20px" width="200" height ="400"  alt="8" src="https://github.com/user-attachments/assets/5a5b2a90-6118-4473-bde2-37ca915cb2c1" />
   <img style="padding:20px" width="200" height ="400" alt="9" src="https://github.com/user-attachments/assets/2f0fc1ec-7c01-4e01-862a-b1fbb58f45f5" />
     <img style="padding:20px" width="200" height ="400" alt="4" src="https://github.com/user-attachments/assets/053c4f9f-d082-4dea-b845-140b4a1aa00b" />
    <img style="padding:20px" width="200" height ="400" alt="5" src="https://github.com/user-attachments/assets/e9672c94-efea-416b-a3db-2b5646ef5717" />
    <img style="padding:20px" width="200" height ="400" alt="6" src="https://github.com/user-attachments/assets/cdda884c-a79e-400d-8412-39076edcb78e" />
   <img style="padding:20px" width="200" height ="400"  alt="10" src="https://github.com/user-attachments/assets/189f3fbc-9b9f-4fb9-abd9-ffda21f5a326" />
   <img style="padding:20px" width="200"    height ="400" alt="11" src="https://github.com/user-attachments/assets/389b2912-ff8f-4d17-a390-94bfafd534cf" />
      <img style="padding:20px" width="200" height ="400" alt="Screenshot_20260812-090755_ " src="https://github.com/user-attachments/assets/a4649373-4470-401a-9cb6-3725ba903328" />
    <img style="padding:20px" width="200" height ="400" alt="Screenshot_20260812-085949_ " src="https://github.com/user-attachments/assets/0fe31b8f-b1b0-430b-8700-d14fc96b3e55" />
    <img style="padding:20px" width="200" height ="400" alt="Screenshot_20260812-085949_ " src="https://github.com/user-attachments/assets/eec06e5c-b34a-4de2-8bde-1bf5590ba5b4" />
  </p>

 
### Backend & API
shik shap uses a custom Pure PHP backend with MySQL as the database.

The Android application communicates with the backend through REST APIs using Retrofit 

### Backend Responsibilities 
* User registration and authentication
* Product management and retrieval
* Shopping cart operations
* Order management
* Database operations using MySQL
* Handling communication between the Android application and the server

 ### Real-Time Chat
 The application also includes a chat feature implemented using Long Polling
 Instead of continuouslu sending requests at short interval , the client keeps an HTTP request open while waiting for new messages . when a new message becomes available,the server returns the response and the client starts a new request.

Android Client -- http Request --> PHP Backend --Wait for new message -->New message -----> Android Client -> New Long Polling Request

### Communication Flow

Android Application ----> Retrofit ----> REST API ----> Pure PHP ----> MySQL

for the chat functionality , Long Polling is used to provide near real-time message updates between the Android application and the PHP backend


## Technical Highlights

### MVVM Architecture
The Android application follows the MVVM architecture to separate UI components from application and data logic , making the codebase easier to maintain and extend

### REST API Communication 
The application communicates with the PHP backend through REST APIs.Retrofit is used to handle HTTP requests and APIs responses.

### Long Polling Chat 
A near real-time chat system was implemented using Long Polling . The Android Client maintains an HTTTP request while waiting for new messages and sends a new request after receiving a response.

### Client-Server Architecture
The project consists of an Android client , a Pure PHP backend , and a MySQL database.The Android application communicates with the backend through HTTP-based APIs

### Firebase Cloud Messaging
firebase cloud Messaging (FCM) is used to send push notifications to users , allowing the application to notify users about relevant events even when the application is not actively open.

### Image Loading
Picasso is used for loading and displaying product and user images efficiently within the Android application

### Loading & User Experience
Shimmer placeholders and Lottie animations are used to provide visual feedback while content is loading and to improve the overall user experience
