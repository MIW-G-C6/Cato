Technical Spike:
Find out what session attributes are and how to use them

https://www.intertech.com/understanding-spring-mvc-model-and-session-attributes/

On an incoming request, any methods annotated with @ModelAttribute are called before any controller handler method

Spring’s @SessionAttributes is used on a controller to designate which model attributes should be stored in the session.
In actually, what @SessionAttributes allows you to do is tell Spring which of your model attributes will also be copied to HttpSession before rendering the view

Spring provides a means to remove Spring session attributes, and thereby also remove it from HttpSession (without having to kill the entire HttpSession). Simply add a Spring SessionStatus object as a parameter to a controller handler method.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
https://help.goacoustic.com/hc/en-us/articles/360043738894-Session-attributes

A session attribute is a pre-defined variable that is persistent throughout the life of a Tealeaf session. Session attributes can be used to store various data that may be referenced by events at any point during the session.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
https://www.javadevjournal.com/spring-mvc/spring-mvc-session-attributes/

used in a situation where the same attributes referred to in multiple pages.

The annotation @SessionAttributes(“object”) tells Spring MVC that the object should be considered as a session scope object. 

The annotation @SessionAttribute(“object”) retrieves the existing attribute from the session. 
