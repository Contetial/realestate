<!-- <link  rel="stylesheet" href="CSS/bootstrap.min.css" media="screen"/> -->
<div style="text-align: center; width: 100%; height: auto; margin-left: 10%; margin-right: 10%;"> 
<h1 style="font-size: 25px;"></h1>

<div style="width: 100%; height: auto; text-indent: px; text-align: left; vertical-align: 100%; margin-left: -10%;">

<table> 
  <tr>
    <td width="25%" style="vertical-align:top;height:100%;background:rgb(255, 215, 0);">
    	<div id="menu">
			<div class="art-menu" ng-include src="'pages/admin/AdminMenu.jsp'"> </div>
		</div>
	</td>
    <td width="75%" style="margin-left:10px;">
    <div ng-controller="appUserController">			
    <div id="searchBody" ng-show="searchScreen">
        <form id="searchEngineForm">
            <div id="searchEngine" align="center"  class="form-group">
                <h3 align="center">Search AppUser</h3>
                
                <table id="searchEngineTable">
                    <tr>
                        <td>User Name</td>
                        <td>
                            <input ng-model="appUserName" type="text" class="form-control" name="appUserName">
                        </td>
                    </tr>
                    <tr>
                        <td>User Address</td>
                        <td>
                            <input ng-model="appUserAddress" type="text" class="form-control" name="appUserAddress">
                        </td>
                    </tr>
                    <tr><td>&nbsp;</td><td></td></tr>                    
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                        	<button type="button" class="btn btn-primary nav-toggle" 
                        		id="btnsearch" ng-click="search()">Search</button>
                        	&nbsp;
                        	<button type="reset" class="btn btn-primary nav-toggle" id="clear2">Clear</button>
                        </td>
                    </tr>
                </table><br>
                
                <button type="button" class="btn btn-primary nav-toggle" 
                	id="btnadd" ng-click="startAdd()">Add</button>&nbsp;
                <button type="button" class="btn btn-primary nav-toggle" 
                	id="btnupdate" ng-show="isSearchResult" ng-click="startUpdate()">Update</button>&nbsp;
                <button type="button" class="btn btn-primary nav-toggle" 
                	id="btndelete" ng-show="isSearchResult" ng-click="deleteAppUser()">Delete</button>                
                <br>
                {{messageSave}}
            </div>
                        
            <div id="searchResults" align="center" ng-show="searchResultScreen">
                <h3>AppUser List</h3>
                <h4>{{noDataMessage}}</h4>                
                <table border="1" ng-show="isSearchResult" style="width:70%">
                	<tr>
                		<th>&nbsp;</th>
						<th><a href=#AdminAppUser ng-click="orderByField='userName'; reverseSort = !reverseSort">AppUser Name</a></th>
						<th><a href=#AdminAppUser ng-click="orderByField='address'; reverseSort = !reverseSort">Address</a></th>
						<th><a href=#AdminAppUser ng-click="orderByField='contactNo'; reverseSort = !reverseSort">Contact</a></th>
						<th><a href=#AdminAppUser ng-click="orderByField='email'; reverseSort = !reverseSort">Email</a></th>
						<th><a href=#AdminAppUser ng-click="orderByField='userRole'; reverseSort = !reverseSort">User Role</a></th>
						<th><a href=#AdminAppUser ng-click="orderByField='managerName'; reverseSort = !reverseSort">Manager</a></th>						
					</tr>
					<tr ng-repeat="appUser in appUsers | orderBy:orderByField:reverseSort" >
						<td><input type="radio" name="appUserIndex" value={{$index}} ng-model="$parent.selectedappUser"/></td>
						<td>{{appUser.userName}}</td>
						<td>{{appUser.address}}</td>
						<td>{{appUser.contactNo}}</td> 
						<td>{{appUser.email}}</td>
						<td>{{appUser.userRole}}</td>
						<td>{{appUser.managerName}}</td>								
					</tr>             
                </table>                
            </div>
        </form>
    </div>
   
   <div id="addUpdateBody" ng-show="addScreen">
      <form name="addUpdateForm" ng-submit="submitForm(addUpdateForm.$valid)" novalidate>
        <div><h1>AppUser Registration</h1></div>
        <input type="hidden" name="appUserId" ng-model="appUser.userId"/>
        {{validationError}}
        <div class="col-sm-8 col-sm-offset-2">       
        <!-- NAME -->
        <div class="form-group">
            <label>Name</label>
            <input type="text" name="appUserName" class="form-control" ng-model="appUser.userName" 
            	ng-class="{invalidField:addUpdateForm.appUserName.$invalid && submitted}" required>
             <p class="help-block" 
             ng-show="addUpdateForm.appUserName.$invalid && submitted">AppUser name is required.</p>
        </div>

        <!-- PASSWORD -->
        <div class="form-group">
            <label>Password</label>
            <input type="text" name="appUserPassword" class="form-control"  ng-model="appUser.password" 
            	ng-class="{invalidField:addUpdateForm.appUserPassword.$invalid && submitted}" required>
             <p class="help-block" 
            	ng-show="addUpdateForm.appUserPassword.$invalid && submitted">Valid password is required.</p>                    
        </div>
        
        <!-- ADDRESS -->
        <div class="form-group">
            <label>Address</label>
            <input type="text" name="appUserAddress" class="form-control" ng-model="appUser.address" 
               ng-class="{invalidField:addUpdateForm.appUserAddress.$invalid && submitted}" required>
            <p class="help-block" ng-show="addUpdateForm.appUserAddress.$invalid && submitted">Address is required.</p>
         </div>
         
         <!-- CONTACT -->
         <div class="form-group">
            <label>Contact No.</label>
            +91<input type="number" name="appUserContactNo" class="form-control" ng-model="appUser.contactNo" 
               ng-class="{invalidField:addUpdateForm.appUserContactNo.$invalid && submitted}" required>
            <p class="help-block" ng-show="addUpdateForm.appUserContactNo.$invalid && submitted">Valid contact is required.</p>
            <p class="help-block" ng-show="addUpdateForm.appUserContactNo.$error.number && submitted">Only numbers are allowed</p>
         </div>
       
         <!-- EMAIL -->
         <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" class="form-control" ng-model="appUser.email" 
               ng-class="{invalidField:addUpdateForm.email.$invalid && submitted}">
            <p class="help-block" ng-show="addUpdateForm.email.$invalid && submitted">Valid email is required.</p>
         </div>
         
         <!-- USER ROLE -->
         <div class="form-group">
            <label>User Role</label>
            <select name="appUserRole" class="form-control" ng-model="appUser.userRole"
            	ng-class="{invalidField:addUpdateForm.appUserRole.$invalid && submitted}" required>
            	<option value="agent">agent</option>
                <option value="manager">manager</option>
        	</select>   
            <p class="help-block" ng-show="addUpdateForm.appUserRole.$invalid && submitted">User role is required.</p>
         </div>
         
         <!-- Manager -->
         <div class="form-group">
            <label>Manager Name</label>
            <input type="text" name="appUserManager" class="form-control" ng-model="appUser.managerName" 
               ng-class="{invalidField:addUpdateForm.appUserManager.$invalid && submitted}" required>
            <p class="help-block" ng-show="addUpdateForm.appUserManager.$invalid && submitted">Manager Name is required.</p>
         </div>
         
        <!-- SUBMIT BUTTON -->
        <button type="submit" class="btn btn-primary">Save</button>
        <button type="button" class="btn btn-primary" 
        		ng-click="toggleDisplay()">Cancel</button>
        </div>            
      </form>
    </div>
    </div>
    </td>
  </tr>
</table>
</div>
</div>
<style type="text/css">
	.invalidField{
		border: solid 1px red;
	}   	
</style>