## HTTP GET Params ##
  Send parameters in a GET request

## Usage ##

1. First launch: complete list of buildings by name (English)
    * Toast building count: 165
2. **Menu > Choose a Category >** select *Academic Institutions*
    * Toast building count: 10
3. **Menu > Show All Buildings**
    * same as 1 (above)
4. Scroll the list, and select a building to see its detailed information
    * Click **<-** to navigate back to MainActivity

App remembers selected category between launches:

1. **Menu > Choose a Category**. Remember the building category and building count.
2. Quit app.
3. Launch app again. You should see the list of buildings belonging to the selected category.

## Features ##

* Master / Detail Activities (list of buildings; select a building for detail)
* Display animated circle to User when app is busy fetching JSON data over network (ProgressBar)
* Sliding Navigation Drawer (DrawerLayout)
* Fetch building's photo using Picasso. Implemented in 1 line --- see DetailBuildingActivity.

## Code Inspection ##

  See my //TODO comments: **View > Tool Windows > TODO**

## Source Code ##

  Available from GitHub:

  https://github.com/hurdleg/GETParams.git

## Reference ##

  Based on _Send parameters in a GET request_  in _Android App Development: RESTful Web Services_ with David Gassner