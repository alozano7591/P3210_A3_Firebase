# Introduction 
Assignment 3 for Mobile Apps class

# Contributors

- Alfredo Lozano : alozano7591@conestogac.on.ca
- Jamie Upton : jupton7804@conestogac.on.ca

# Key points to remember for future when looking back at this project:
Adding these for myself because I have a terrible memory and will forget these eventually

1.	Api calls will require internet access. This needs this line in AndroidManifest.xml:
    <uses-permission android:name="android.permission.INTERNET"/>
2.	Add okhttp:
    - In libs.versions.toml add:
      - under [versions] -> `okhttp = "4.12.0"`
      - under [libraries] -> `okhttp = { module = "com.squareup.okhttp3:okhttp", version.ref = "okhttp" }`
    - In build.gradle add to dependencies section:
      `implementation(libs.okhttp)`

# About the API
This is a simple movieModel sample db API that can be used to get simple movieModel data with api calls
Link: https://www.omdbapi.com/

# API commands and examples

Search (return multiple objects if there are matches):
- https://www.omdbapi.com/?apikey=865e2af0&s=title
- Example: https://www.omdbapi.com/?apikey=865e2af0&s=blade

Find Title (return one matching title)
- https://www.omdbapi.com/?apikey=865e2af0&t=title
- https://www.omdbapi.com/?apikey=865e2af0&t=blade

