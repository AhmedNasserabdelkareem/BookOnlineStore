# BookOnlineStore
Goals
The goal is to analyze, design and implement a database system
to support the operations of a simplified online bookstore.
Also, you to create the necessary forms to perform some operations
Screenshots :
Sign Up

![](/imgs/signup.PNG)

Sign in

![](/imgs/signin.PNG)

searching window 
![](/imgs/1.PNG)
Here the user/Manager can search using any of the filters shown 
(category, title, author name, publisher name, publish year or price) or even show all results as shown below:
![](/imgs/2.PNG)

And here is example for filtering:
![](/imgs/3.PNG)

The user/Manager can specify the books he want to add to cart and also the quantity.
The items will be added to the cart unless the quantity required is more than the available.
the user/Manager can remove items from the cart  :

![](/imgs/4.PNG)



Order books in cart :
The user presses the order button then he enters his credit card number
if the credit card number is valid the order is placed

![](/imgs/5.PNG)


Profile window :
Clicking on the profile button in the top right of the window , all the information of the user will appear( excluding the encrypted password) in textfield ready to be edited …
Here are the info before and after editing :

![](/imgs/6.PNG)


Manager window
This button (Manager) in the screenshot below will appear only for managers as it will open a window that contains the managers’ special actions. 
![](/imgs/7.PNG)

Here is the manager window
![](/imgs/8.PNG)

First we will add publisher With as many addresses and phones as we need:
![](/imgs/9.PNG)

Then we will add author :
![](/imgs/10.PNG)

Then we will add book with the publisher added before :
![](/imgs/11.PNG)





We can add as many authors as we need to the added book with 2211 isbn
![](/imgs/12.PNG)

We can also modify the book Quantity :
Finally we Search using publisher filter in the home window:

![](/imgs/13.PNG)



Total sales for previous month

![](/imgs/14.PNG)

Top 5 customers in the last 3 months
![](/imgs/15.PNG)



Top 10 selling books in last 3 months
![](/imgs/16.PNG)


Orders is placed  if book quantity became less than the threshold (trigger) or by manager.

![](/imgs/17.PNG)









Confirmation :

![](/imgs/18.PNG)

![](/imgs/19.PNG)
![](/imgs/20.PNG)

![](/imgs/21.PNG)


![](/imgs/22.PNG)





Promote user by manager :
![](/imgs/23.PNG)

EER MODEL
![](/imgs/eer.PNG)

Stored Procedures

Add_book_authors: Insert the relation between author and book because the book can have many authors.
Add_new_authors: Insert new author with the name attribute to author table. 
Add_new_book: Insert new book with its properties to book table.
Add_new_publishers: Insert new publisher using his name to publisher table. 
Add_publisher_address: Insert relation between publisher and its address because address can be multi-valued.
Add_publisher_phone: Insert relation between publisher and its phone number because phone can be multi-valued.
Confirm_order: Change the status of the order to be confirmed to indicate that books are delivered to increase the quantity.
Insert_order_history: Save the user orders to use it in Get statistics on Top sales books and Top users.
Modify_book_quantity: Edit the quantity of selected book using ISBN.
Place_book_order:  insert new order from the admin to save the orders history.
Promote_user: Change the privileges of users from normal user to admin. 
Retreive_total_sales: Get the total sales for books in the previous month.
Retreive_top_customers: Get the top 5 customers who purchase the most purchase amount in descending order for the last three months.
Retreive_top_sales: Get the top 10 selling books for the last three months.
Retreive_unconfirmed_orders: Get the unconfirmed orders for manager to change the status of the orders.
Retreive_user_info: get the user personal data to edit them in profile window.
Search_for_book: search for books with any attribute one or more.
Signup: Insert a new record to users table with username,firstname,lastname,encrypted password and address.
Update_usr_info: used to update personal data for the user like password or address.
Triggers

BOOK_BEFORE_UPDATE : Check if the quantity is not less than zero and if the quantity of book is less than the threshold of the book then a new order of this book will automatically added.
ORDERTABLE_BEFORE_INSERT: Check if the quantity of the book is less than the threshold to accept the order from manager.
ORDERTABLE_BEFORE_UPDATE: Check of confirmation status and update the quantity after confirmed the order by the manager.
ORDERTABLE_BEFORE_DELETE: Check if the order is not confirmed, and the manager is going to  delete the order so the trigger will update the quantity of the book as it should be delivered from the publisher. 
TOPSALES_AFTER_INSERT: After user ordered the books the quantity of the books in cart will be decreased and updated in database. 
USERS_BEFORE_INSERT: Check if username is already existed or not for sign up process. 
Events

TOPSALES_UPDATE_EVENT: a scheduled event that removes the previous month sales every month.
