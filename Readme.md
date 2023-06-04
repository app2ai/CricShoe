### CrickBuzz Application

# Please find below doc for this assignment to understand work flow

1. This project is developed in clean MVVM architecture
2. Used Room framework for DB integration
3. Used Piccasso lib for Image loading
4. Used Dagger lib for Dependency Injection

# View 
1. In the 1st page we loaded all the sneakers in recyclerview
2. Cart Page contains, checkout products with total amount
3. In the details page of Sneaker, it is showing basic info along with price and one button to add in cart

# ViewModel (Mediator)
1. MainActivity View Model is gathering data from data.json file and added in the local DB
2. I follow Single responsibility pattern hence many view model created to communicate with DB
3. Similarly, there is dedicated repository for each views

# Model (Remote)
1. Integrate Room framework which stores 100 sneakers
2. Dao is acting like a ORM tool, and hence perform DB operations
3. Flow is used as data carrier here

----------------

# Benefits
1. I follow clean MVVM arch which is most robust than other architecture
2. This project is loosely coupled, so less chance of bug and crash
3. Dagger is used for DI
4. Using flow tool for reactive programming, which is built on top of coroutines

# Some more IMP class
1. [asset/data.json]
2. [utils.Listeners]
3. [ShoeApplication]
4. [module/build.gradle] for dependencies

----------------

# Links
https://www.pngwing.com/en/free-png-agney  -> empty cart link