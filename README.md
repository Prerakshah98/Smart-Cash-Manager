# README #

This README would normally document whatever steps are necessary to get your application up and running.

                                   Project Application - Smart Cash Manager

Project Name: Smart Cash Manager

Project Team Members: Sandeep Mulakala S528752,Prathibha Kamani S528804, Venkata Naga Kamal Nadh Meduri S528747, Jyothsna Pala S528755

		              
		        
What is this project all about?

In this project we are developing an application in which it will allow users to maintain a digital automated diary for expenses. User will login or sign up (for new user) to use this application. Once user has logged in with their credentials they can track their income and expense on a day to day basis.

What are the achieved Features of the application?

•	This application create unique account to all the users. 
•	This application allows user to add expenses and income.
•	This application has special feature to see remaining balance which user can spend.
•	This applications allows user to add records at any time.
•	This Application allows user to view the report of expense and income for a certain period.
•	This application allows the user can take reciept phot for the expenses or user can select it from gallery.

What is the minimum APk & version required for the application?
APK: Minimum required for this application would be "23".
Version 5.1 and above.

Which are supporting devices for this appliction?
This Application is supported to all the physicl devices(like Smart Phones e.t.c.) that uses android operating system that has the capability of storing the data internally.  

What is the Sequence Information of the application ?
New User ---> SignUp --->SignIn (with generated credentials) --->Home page --> Actionbar (NavigationControls)  ---> (Home, income, add income, expenses, add expenses, reports, logout).

We can logout from any fragment that is present in the actionbar navigation list. We can also go from one fragment to another using these navigation control(to and fro).

Which DataBase is used for storing data and how are the entities related?
We have used internal database for storing data of the application usig SQL Lite.

In this Smart cash manager entity, we have attributes like add income, add expense, view income/ expense, and add savings. In this table add expense, add income, and add savings are the composite primary key in which we can uniquely identify one cash transaction (which is nothing but addition of income, expense and savings to the database).

In this data model, we do have relations between the entities. Smart cash manager can be access by many users and many user can access the Smart cash manager at the same time, so here we have chance to get an associative entity in which store the primary keys of the respective entities and the extra information if needed. This associative entity (Access) will be a table in the data base. In similar way we have relation between the smart cash manager and new registration entity where Smart Cash Manager has many registrations and many new registrations can be done to smart cash manager at a time, so we have another potential chance of getting associative entity (Individual Details).


What are the test credentials used for testing application?

Test Credenctials- We have tested our application with oppo smart phone with android version 5.1, API - 23, As we have used the internal storage to store the data of the application, so we have registered into the application, which will create a unique user id like series which starts with 1000, by the user_id and password which is created by the user will be used to login to the application.
Income: 
source of income: salary, Amount :$500, Mode of Income : Deposit, Date:(2017/11/17), Time : 14:55
Expense: 
Source of Expense :Walmart, Amount:$200, Mode of Expense: Credit card, Date : 2017/11/17 Time: 15:00, Balance(Savings) : $300 would be displayed in the add expense fragment.
Report : A line graph is generated showing the income and expenses ratio in a line.

What are the Roles and Responsibilities of Team Members?

All the team members has put forward their ideas and helped in successful running of the application.The Functionalities of the application are divided among the team members as below,

Sandeep Mulakala - Has mainly implemented functionalities in  add income page, retrieving the added income as a listview in income page, logout Functionality,  kept his ideas towards connecting the application to the internal storage.

Jyothsna Pala - Has mainly implemented functionalities in add expenses, retrieving the added expenses as a listview in expense page, kept her ideas towards connecting the application to the internal storage.

Kamal Nadh Meduri - Has mainly implemented the Reports page functionalities, Worked on the user interface, Worked on home page fragment, kept his ideas towards connecting the application to the internal storage.

Prathibha Kamani - Has implemented the login and register functionalities using internal stored data with shared preferences with SQLLite, also enchanced the user interface with attractive design with proper actionBar.
