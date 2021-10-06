[![](https://jitpack.io/v/raheemadamboev/check-internet-android.svg)](https://jitpack.io/#raheemadamboev/check-internet-android)

# check-internet-android
Light library to check internet connection in android apps easily. It checks real internet connection by connecting to Google's DNS server. If call is successful then internet is working else not working.

## How To use

Add it in your root **build.gradle** at the end of repositories:
```
allprojects {
  repositories {
	  ...
	  maven { url 'https://jitpack.io' }
  }
}
```  

Include below dependency in build.gradle of application and sync it:
```
implementation 'com.github.raheemadamboev:check-internet-android:1.0'
```

**Check internet connection:**
```
CheckInternet().check { connected ->
  if (connected) { 
      // there is internet                
  } else { 
      // there is no internet                  
  }
}
```
## Demo application

Checked internet connection via wifi and mobile network. <a href="https://github.com/raheemadamboev/check-internet-android/blob/master/app-debug.apk">Download demo</a>

<img src="https://github.com/raheemadamboev/check-internet-android/blob/master/demo-check-internet.gif" alt="Italian Trulli" width="200" height="400">
