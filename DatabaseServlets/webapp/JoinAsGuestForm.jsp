<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="CSSy/joinasguestform.css">
</head>
<body>
    <div class="login">
        <form method="post" action="JoinTournamentGuest">
        <label for="">Enter your name and surname</label><br>  
        <input type="text" name="g_name" placeholder="Enter Name"><br>
        <input type="text" name="g_surname" placeholder="Enter Surname"><br>
        <input type="submit" value="Join">
        </form>
    </div>
</body>
</html>