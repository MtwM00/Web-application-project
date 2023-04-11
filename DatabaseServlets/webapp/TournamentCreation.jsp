<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="CSSy/tournamentcreation.css">
</head>
<body>
    <div class="create">
    <form method="post" action="CreateTournament">
        <input type="text" name="t_name" placeholder="Tournament name"><br>
 
        <select type="text" name="t_type" aria-placeholder="Tournament type"><br>
            <option value="" disabled selected>Tournament type</option>
            <option value="Rapid"> Rapid </option>
            <option value="Blitz"> Blitz </option>
            <option value="Bullet"> Bullet </option>
        </select><br>
        <select type="text" name="t_if_ranked">
            <option value="" disabled selected>Ranked</option>
            <option value="yes"> yes </option>
            <option value="no"> no </option>
        </select><br>
        <input type="date" name="t_date" value="date" placeholder="Tournament date"><br>
        <input type="text" name="t_code" placeholder="Tournament code"><br>
        <input type="text" name="Judge_idJudge" placeholder="Tournament judge Id"><br>
        <input type="submit" value="Create">
    </form>
</div>
</body>
</html>

