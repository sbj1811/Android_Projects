# Build It Bigger App

An app with multiple flavors that uses
multiple libraries and Google Cloud Endpoints. The app consist
of four modules. A Java library that provides jokes, a Google Cloud Endpoints
(GCE) project that serves those jokes, an Android Library containing an
activity for displaying jokes, and an Android app that fetches jokes from the
GCE module and passes them to the Android Library for display.

![Build It Bigger][Build-It-Bigger]

[Build-It-Bigger]: ./media/app_banner.jpg

## Features

* Java library for supplying jokes
* Android library with an activity that displays jokes passed to it as intent extras.
* A Google Cloud Endpoints module that supplies jokes from the Java library. Project loads jokes from GCE module via an async task.
* Connected tests to verify that the async task is indeed loading jokes.
* Contains paid/free flavors. The paid flavor has no ads, and no unnecessary dependencies.
* App retrieves jokes from Google Cloud Endpoints module and displays them via an Activity from the Android Library.
* The free app variant display interstitial ads between the main activity and the joke-displaying activity.
* The app display a loading indicator while the joke is being fetched from the server.
* A Gradle task that starts the GCE dev server, runs all the Android tests, and shuts down the dev server.

## License:
```
  Copyright 2018, Shaunak Jani

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