# Android Projects
#### By: Shaunak Jani

# National Park Guide App

National Park Guide boasts rich, detailed information on America’s most popular National
Parks, including key features, history, maps, campgrounds, hiking trails, current weather and
alerts, with links to additional information resources using [NPS Data API](https://www.nps.gov/subjects/digital/nps-data-api.htm) , [Hiking Project Data API](https://www.hikingproject.com//data)
and [OpenWeatherMap API](https://openweathermap.org/api) .

The application is aimed at Tourists and Hikers visiting any of the National Parks in United States of America.   

![NPS App][NPS-app]

[NPS-app]: ./USNationalParkGuide/media/app_banner.jpg

## Features

* Descriptions of each park’s key features and history, with links to additional resources.
* Current weather condition.
* List of Trails and Campground with additional details. 
* Park-related news, alerts, and events. 
* Tag your favorite park sights.
* Supports UI for Mutiple Phone and Tablet sizes.
* Uses Expresso Unit tests for UI and Intents.
* Google and Email signin.
* Share Trail and Campgound location with friends.
* The free app variant displays banner ads.
* Homescreen Widget with Favorite Park deatils.
* Accessibility support.
* Offline mode.
* Libraries:
    * [Retrofit](http://square.github.io/retrofit/)
    * [Butter Knife](jakewharton.github.io/butterknife/)
    * [Firebase Authentication](https://firebase.google.com/)
    * [Admob](https://www.google.com/admob/)
    * [Facebook Stetho](http://facebook.github.io/stetho/)
    * [Picasso](http://square.github.io/picasso/)
    * [Glide](https://github.com/bumptech/glide)
    * [Facebook Fresco](http://frescolib.org/)

===================================
# XYZ Reader App

Redesigned the existing news reading app using [Material Design Guidelines](https://material.google.com/). This includes consistent and meaningful use of Material Design UI components, fonts, color, motion and surfaces.

![XYZ Reader][XYZ-Reader]

[XYZ-Reader]: ./XYZReader/media/app_banner.jpg

## Features

* Uses the Design Support library and its provided widget types (FloatingActionButton, AppBarLayout, SnackBar, etc).
* Uses CoordinatorLayout for the main Activity.
* Theme extends from AppCompat.
* Provides a Floating Action Button for the most common action(s).
* Properly specifies elevations for * bars, FABs, and other elements specified in the Material Design pecification.
* Has a consistent color theme defined in styles.xml. Color theme does not impact usability of the app.
* Provides sufficient space between text and surrounding elements.
* Uses images that are high quality, specific, and full bleed.
* Uses fonts that are either the Android defaults, are complementary, and aren't otherwise distracting.
* Libraries:
    * [Facebook Fresco](http://frescolib.org/)
    * [Okhttp](http://square.github.io/okhttp/)
    * [Picasso](http://square.github.io/picasso/)

===================================
# Build It Bigger App

An app with multiple flavors that uses
multiple libraries and Google Cloud Endpoints. The app consist
of four modules. A Java library that provides jokes, a Google Cloud Endpoints
(GCE) project that serves those jokes, an Android Library containing an
activity for displaying jokes, and an Android app that fetches jokes from the
GCE module and passes them to the Android Library for display.

![Build It Bigger][Build-It-Bigger]

[Build-It-Bigger]: ./BuildItBigger/media/app_banner.jpg

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

===================================
# Baking App

This app shows recipes. 

![Baking App][Baking-app]

[Baking-app]: ./BakingApp/media/app_banner.jpg

## Features

* Uses Exoplayer to show recipe step videos
* Mark Favorite to display Widget on home screen.
* Supports UI for Mutiple Phone and Tablet sizes
* Uses Expresso Unit tests for UI and Intents
* Libraries:
    * [Facebook Stetho](http://facebook.github.io/stetho/)
    * [Picasso](http://square.github.io/picasso/)
    * [Retrofit](http://square.github.io/retrofit/)
    * [Butter Knife](jakewharton.github.io/butterknife/)
    * [ExoPlayer](https://github.com/google/ExoPlayer)
    * [Schematic](https://github.com/SimonVT/schematic)

===================================
# Popular Movies App

This app fetches movie data using [themoviedb.org](https://www.themoviedb.org/) API. 

![Popular Movies App][Popular-Movies-app]

[Popular-Movies-app]: ./PopularMovies/media/app_banner.jpg

## Features

* Browse Popular and Top-Rates movies
* Watch trailers and read reviews
* Mark Favorites for future viewing
* Supports UI for Mutiple Phone and Tablet sizes
* Offline Viewing mode
* Automatic Daily update.
* Libraries:
    * [Facebook Stetho](http://facebook.github.io/stetho/)
    * [Picasso](http://square.github.io/picasso/)
    * [Retrofit 2](http://square.github.io/retrofit/)
    * [Butter Knife](jakewharton.github.io/butterknife/)

===================================
## Miwok Translator App

This app uses Fragments, ViewPager and TabView to display Miwok to English translation. It also implemets MediaPlayer and AudioManager to play Miwok audio pronunciation. 

![Miwok Translator App][Miwok-Translator-app]

[Miwok-Translator-app]: ./Miwok_translator/media/app_screenshot.png

===================================
## San Francisco Tour Guide

This app uses Fragments, ViewPager and TabView to display various categoirs of attactions in SF. For every attraction, when clicked on it, it provides details like website, phone umbers and addresss. Uses web, phone and location intents to interact with other apps.

![San Francisco Tour Guide App][SF-TourGuide-app]

[SF-TourGuide-app]: ./SanFranciscoTourGuide/media/app_screenshot.png

===================================
## Earthquake Report App

This app displays a list of recent earthquakes in the world
from the U.S. Geological Survey (USGS) organization. User can 
modify the preference on the search.

More info on the USGS Earthquake API available at:
https://earthquake.usgs.gov/fdsnws/event/1/

![Earthquake Report App][Earthquake-Report-App]

[Earthquake-Report-App]: ./EarthquakeReport/media/app_screenshot.png

===================================
## Book Listing App

This app searches and displays a list of books on the Google Play Book Store.

More info on the Google Books APIs available at:
https://developers.google.com/books/docs/v1/getting_started#intro

![Book Listing App][Book-Listing-App]

[Book-Listing-App]: ./BookListing/media/app_screenshot.png

===================================
## News App

This app searches and displays list of news articles based on search query.

More info on the The Guardian APIs available at:
http://open-platform.theguardian.com/

![News App][News-App]

[News-App]: ./NewsApp/media/app_screenshot.png

===================================
## Inventory App

This app tracks the inventory of a retail store using SQLite database.

![Inventory App][Inventory-App]

[Inventory-App]: ./InventoryApp/media/app_screenshot.png

===================================
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