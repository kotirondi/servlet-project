<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style>
  body {
    background-color: Tomato;
  }
  .form {
    border: 4px solid black;
    padding: 20px;
    width: 400px;
    margin: 100px auto;
    background-color: yellow;
    border-radius: 20px;
  }
</style>

</head>
<body>
  <div class="form">
    <center><br><br>
      <form method="POST" action="./login">
        <h2>Name</h2><input type="text" name="name">
        <h2>PIN:</h2><input type="text" name="pin"><br><br>
        <button type="submit" name="sub">submit</button>
        <br>
        <h4>If you're a new user <a href="account.html">Register Now</a></h4>
      </form>
    </center>
  </div>
</body>
</html>
    