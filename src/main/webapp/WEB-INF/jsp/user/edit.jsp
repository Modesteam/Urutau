<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="/WEB-INF/layouts/messages.jsp"%>

<div class="row">
	<div class="col-md-6 col-md-offset-3">
		<h1>Your Settings</h1>
	</div>

	<div class="col-md-6 col-md-offset-3">
		<div class="panel panel-default">
		 	<div class="panel-heading">
		 		<h3>Basic information</h3>
		 	</div>
		 	<div class="panel-body">
				<form action="<c:url value="/user/updateBasic"/>" method="POST" 
                    class="form-group">
					<label for="login">Login</label>
					<input type="text" name="user.login" value="${user.login}"
						class="form-control" placeholder="Your login" id="login">
		
					<label for="desc">Email</label>
					<input type="text" name="user.email" value="${user.email}"
						class="form-control" placeholder="What email you want use"/>

					<label for="desc">Name</label>
					<input type="text" name="user.name" value="${user.name}"
						class="form-control" placeholder="First Name"/>
					<label for="desc">Lastname</label>
					<input type="text" name="user.lastName" value="${user.lastName}"
						class="form-control" placeholder="Last Name"/>

					<button type="submit" name="_method" value="PUT" 
                        class="btn btn-success pull-right">Update</button>
				</form>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-md-6 col-md-offset-3 boxed">
		<div class="panel panel-default">
	 		<div class="panel-heading">
				<h3>Change password</h3>
			</div>
		 	<div class="panel-body">
				<form action="<c:url value="/user/updatePassword"/>" method="POST" 
                    class="form-group">
					<label>Old Password</label>
					<input name="oldPassword" type="password" class="form-control" 
                        placeholder="Your current password"/>
					<label>New Password</label>
					<input name="newPassword" type="password" class="form-control" 
                        placeholder="New password"/>
					<label>Confirm new Password</label>
					<input name="confirmPassword" type="password" class="form-control" 
                        placeholder="Confirm new password"/>
					<button type="submit" class="btn btn-success pull-right">Change</button>
				</form>
			</div>
		</div>
	</div>
</div>

<div class="row">
    <div class="col-md-6 col-md-offset-3 boxed">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <h3>Danger area</h3>
            </div>
            <div class="panel-body">
                <form action="<c:url value="/user"/>" method="POST" class="col-xs-12">
                    <div class="row row-eq-height">
                    <div class="col-xs-6">
                        <input name="userId" value="${user.userID}" type="hidden"/>
                        <label>Your password</label>
                        <input name="password" type="password" class="form-control" 
                            placeholder="Your current password"/>
                    </div>
                    <div class="col-xs-6" style="margin-top: 20px">
                         <button type="submit" style="vertical-align: bottom" class="btn btn-danger" name="_method" 
                             value="DELETE">Delete Account</button>
                    </div>
                    </div>
                </form>
                <div class="col-xs-12">
                    <p class="text-danger">* Make sure about this, because this operation
                    is irreversible!</p>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
