<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1> Bem vindo, primeiro administrador!</h1>
<h2> Modifique seu login e sua senha</h2>
 <div class="row">
	<div class="col-sm-4">
		<form action="administratorSettings" class="form-signin" method="POST">
			<h2 class="form-signin-heading">Login</h2>
			
			<label for="inputLogin" class="sr-only">Login</label>
			<input name="user.login" id="inputLogin" class="form-control" placeholder="Login" required>
			
			<label for="inputPassword" class="sr-only">Password</label>
			<input name="user.password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
					  
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
		</form>
	</div>

</body>
</html>