The user client for the [real-time food recognition and nutrition estimating system](https://github.com/msp18034/Calories/)

- run on Android 4.3 and above
- best viewed with Nexus 5 emulator



# Guide

## Homepage

<img width="150" src=".\images\homepage.png"/>



## User setting

When users use the app for the first time, they need to fill in their personal information. These data can also be changed through the “USER SETTING” button on the home page.

<img width="150" src=".\images\userinfo.png"/>


## Photo Uploading

Through the “TAKE PHOTO” or "GALLERY"  button, users can take or select one picture that they want to upload.

Upon receipt of the response, the processes photo with bounding boxes, names of the foods and calories of each dish will be decoded and displayed. If total calorie of a meal exceeds 40% of the required calorie intake per day, the app will issue a warning.

<img width="150" src=".\images\image.png"/><img width="150" src=".\images\waiting.png"/><img width="150" src=".\images\results.png"/>



## Record Display and Analysis

When user clicks “FOOD LOGS” button on the home page, all dietary records that are of that user and inputted within 24 hours will be shown.
If the calorie intake on the day exceeds or falls below 10% of the standard, app will give corresponding prompt.
On the record page, there is a green “NUTRITION AYALYSIS” button, which will jump to the analysis page. The app will also prompt if the data exceed or fall below a certain range.

<img width="150" src=".\images\logs.png"/><img width="150" src=".\images\nutrition.png"/>