<h1 align="center">Check Internet</h1>

<p align="center">
  <a href="http://developer.android.com/index.html"><img alt="Android" src="https://img.shields.io/badge/platform-android-green.svg"/></a>
  <a href="https://jitpack.io/#raheemadamboev/check-internet-android"><img alt="Version" src="https://jitpack.io/v/raheemadamboev/check-internet-android.svg"/></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">
🌐 Light library to check internet connection in android apps easily. It checks real internet connection by connecting to Google's DNS server. If call is successful then internet is working else not working.
</p>

# Setup

Add it in your root **build.gradle** at the end of repositories:
```groovy
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

Include below dependency in build.gradle of application and sync it:
```groovy
implementation 'com.github.raheemadamboev:check-internet-android:1.1.1'
```

# Implementation

**Check internet connection (callback API):**
```kotlin
CheckInternet().check { connected ->
  if (connected) { 
      // there is internet                
  } else { 
      // there is no internet                  
  }
}
```

**Check internet connection (suspend API):**
```kotlin
viewModelScope.launch {
  val connected = CheckInternet().check()
  if (connected) { 
      // there is internet                
  } else { 
      // there is no internet                  
  }
}
```

# Demo

Checked internet connection via wifi and mobile network. <a href="https://github.com/raheemadamboev/check-internet-android/blob/master/app-debug.apk">Download demo</a>

<img src="https://github.com/raheemadamboev/check-internet-android/blob/master/demo-check-internet.gif" alt="Italian Trulli" width="200" height="400">

# Projects using this library

**GoTest** 150 000+ downloads in <a href="https://play.google.com/store/apps/details?id=xyz.teamgravity.gotest">Google Play Store</a>

**Buxgalteriya schyotlar rejasi** 20 000+ downloads in <a href="https://play.google.com/store/apps/details?id=xyz.teamgravity.uzbekistanaccountingcode">Google Play Store</a>

**Irregular Verbs**  20 000+ downloads in <a href="https://play.google.com/store/apps/details?id=xyz.teamgravity.irregularverbs">Google Play Store</a>

# License

```xml
Designed and developed by raheemadamboev (Raheem) 2022.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
