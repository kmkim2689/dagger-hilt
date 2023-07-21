## Dependency Injection using Dagger-Hilt

### What is Dependency Injection?
> Giving an Object Instance Variables

* Example of Dependency Injection

  * Bad Habit
    * instance without any constructor arguments
    * problem?
      * every single instance of the Person class gonna have the same name...
      * Problematic if an instance with different name is needed
    

        class MainActivity: ComponentActivity() {

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                val person = Person()
            }

        }

        class Person {
            val name  = "km"
        }


  * Good Habit
    * giving MainActivity class the instance variable of Person class
    * the name "km" depends on Person class

        class MainActivity: ComponentActivity() {

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                val test = Person("km")
              }

          }

          class Person(val name: String) {}

* Cases to use DI in Android Project => usually constructor injection + library
  * ViewModel depending on a specific repository instance
    * repository instance => dependency of viewmodel that is injected in viewmodel
    * you should pass the instance of viewmodel -> in order that you can pass the different instances of specific viewmodel
      * why different type of repository in a viewmodel is needed?
        * for testing : fake version of repository
          * to ensure the flexibility of viewmodel
        * to pass repositories in viewmodels with any type of class

* Dagger-Hilt is the easiest way to manage dependencies.
      
---
### What is Dagger-Hilt?

> A library from Google with flexibility and cool tools when it comes to dependency injections

* Dagger2 : too complex to use
  * Dagger-Hilt : more simple version built on top of Dagger2

* Characteristics & Advantages
  * help us to easily **inject dependencies using annotations**
    * annotations is for defining which dependency should go where
  * help us to have a **central place** where we manage the dependencies in project code 
    * to tell dagger-hilt that this is how it can create an api interface, room database etc...
    * mainly by using Module...
      * allow us to easily inject whatever we want 
  * easily **control the lifetime** of specific dependency
    * singleton
    * activity - some kind of object would be needed as long as one specific activity lives
    * ...

  * Dependencies

    * app
    

        plugins {
            // ...
            id 'kotlin-kapt'
            id 'dagger.hilt.android.plugin'
        }
  
        dependencies {
  
            //Dagger - Hilt
            implementation "com.google.dagger:hilt-android:2.45"
            kapt "com.google.dagger:hilt-android-compiler:2.45"
            kapt "androidx.hilt:hilt-compiler:1.0.0"
            implementation 'androidx.hilt:hilt-navigation-compose:1.0.0'

        }

---

### Project Structure

* Structure
  * Repository
  * ViewModel : to use repository
  * UI

* Package
  * data
    * remote
      * MyApi.kt(Interface) : http request functions with retrofit
    * repository
      * MyRepositoryImpl : the implementation of MyRepository Interface
  * di
    * AppModule.kt(Object)
  * domain
    * repository
      * MyRepository(Interface) : interface to get data from data layer
  * ui

---
### Implementation

* How to get MyApi Interface into the repository? => Use Module Concept!
  * need to pass MyApi "Instance Variable" into the object "MyRepositoryImpl"
  * the basic method : to pass constructor argument
    ex) class MyRepositoryImpl(private val api: MyApi)
  * using dagger-hilt : use "modules"
    * module : containers for specific types of dependencies
    * gonna make a module that live as long as 'application' lives => singleton
    
* AppModule.kt(Object)
  * annotate with @Module
  * anootate with @InstallIn(SingletonComponent::class)
    * with dagger-hilt, there are different kinds of components
      * api interface는 애플리케이션이 동작하는 한 계속 주입되어야 할 것이므로, SingletonComponent
    * InstallIn의 인자로 들어가는 Component 종류에 따라, 해당 Dependency가 얼마나 오랜 기간동안 주입될 것인지 결정
      * 다른 종류들로, ActivityComponent(해당 애플리케이션이 살아있는 동안에 공급), ViewModelComponent, ActivityRetainedComponent(스크린이 회전되거나 액티비티가 재생성될 때 주입), ServiceComponent 등
  * what we want to inject in MyRepositoryImpl is "MyApi" interface
    * dagger-hilt needs to know how to create the instance of retrofit client class
    * function : provideMyApi() => returns the instance of MyApi(Retrofit)
      => whenever we request an instance of MyApi(it is injected in MyRepositoryImpl),
      => dagger-hilt look in module if it can find the instance
      => if it can find an instance in module, it will take that then pass it to the constructor(MyRepositoryImpl)

* Implementing a ViewModel 
  * injecting  repository to take the repository data into ViewModel
  * MyViewModel.kt