# Restaurant 

Here is an assignment, Which you can see wasn't much complicated but challenging.
There is a list of restaurants. We have to load the list from a JSON file. Then you can apply different types of sorting and filter the list. 
I have used the TDD methodology for implementing the project.


# Architecture
The Project architecture is MVVM. 

![1*BqFy9rd2_hCtOeHgUY72gg](https://user-images.githubusercontent.com/38876424/205403060-dc6c08ae-f707-471b-a571-5cb6ca6bca6e.png)

Model     -> all model are in com.restaurant.core.dto
View      -> RestaurantActivity, RvAdapterRestaurant
ViewModel -> RestaurantViewModel

we will request all data from RestaurantRepository by the view state from ViewModel.

# Dependency injection
I have used the Dagger-hilt for providing all dependencies. 
You can find the providers in the com.restaurant.di [AppModule]    


# Instrument test
We have just one instrument test for the Restaurant feature. here is the following tests 

 - RestaurantActivity
   - test title 
   - test showing the search icon and sort icon
   - test of using the previous selected sort.
 - RvAdapterRestaurant
   - test of showing items
   - test of filtering 
   - test of sorting items (open status, popularity descending, distance ascending)

# Unit test
 - RestaurantViewModel
    - test of calling the repository load list item
    - test of loading the list
    - test of handling the error
 - RestaurantRepository (I'm using a sample Json file which is saved in resources for testing)
    - test of loading the list.
    - test of applying the filter
    - test of sorting items (open status, popularity descending, distance ascending)
    - test of saving the latest sort which is requested by load list method.


Prerequisites
=============

    - Android Studio 7.3.1
    - Gradle version 7.4
    - Kotlin version 1.7.10

## Author

[Ali Shatergholi](https://github.com/alishatergholi)
