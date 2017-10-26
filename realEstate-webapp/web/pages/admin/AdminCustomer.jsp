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
    <div ng-controller="customerController">			
    <div id="searchBody" ng-show="searchScreen">
        <form id="searchEngineForm">
            <div id="searchEngine" align="center"  class="form-group">
                <h3 align="center">Search Customer</h3>
                
                <table id="searchEngineTable">
                    <tr>
                        <td>Customer Name</td>
                        <td>
                            <input ng-model="custName" type="text" class="form-control" name="custName">
                        </td>
                    </tr>
                    <tr>
                        <td>Customer Address</td>
                        <td>
                            <input ng-model="custAddress" type="text" class="form-control" name="custAddress">
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
                	id="btndelete" ng-show="isSearchResult" ng-click="deleteCustomer()">Delete</button>                
                <br>
                {{messageSave}}
            </div>
                        
            <div id="searchResults" align="center" ng-show="searchResultScreen">
                <h3>Customer List</h3>
                <h4>{{noDataMessage}}</h4>                
                <table border="1" ng-show="isSearchResult">
                	<tr>
                		<th>&nbsp;</th>
						<th><a href=#AdminCustomer ng-click="orderByField='customerName'; reverseSort = !reverseSort">Customer Name</a></th>
						<th><a href=#AdminCustomer ng-click="orderByField='address'; reverseSort = !reverseSort">Address</a></th>
						<th><a href=#AdminCustomer ng-click="orderByField='contactNo'; reverseSort = !reverseSort">Contact No</a></th>
						<th><a href=#AdminCustomer ng-click="orderByField='email'; reverseSort = !reverseSort">Email</a></th>
						<th><a href=#AdminCustomer ng-click="orderByField='dob'; reverseSort = !reverseSort">Brithday</a></th>
						<th><a href=#AdminCustomer ng-click="orderByField='doa'; reverseSort = !reverseSort">Anniversary</a></th>
						<th><a href=#AdminCustomer ng-click="orderByField='referedBy.userName'; reverseSort = !reverseSort">Refered By</a></th>
						<th><a href=#AdminCustomer ng-click="orderByField='status'; reverseSort = !reverseSort">Status</a></th>
					</tr>
					<tr ng-repeat="customer in customers | orderBy:orderByField:reverseSort" >
						<td><input type="radio" name="custIndex" value={{$index}} ng-model="$parent.selectedCustomer"/></td>
						<td>{{customer.customerName}}</td>
						<td>{{customer.address}}</td>
						<td>{{customer.contactNo}}</td>
						<td>{{customer.email}}</td>
						<td>{{customer.dobStr}}</td>
						<td>{{customer.doaStr}}</td>
						<td>{{customer.referedBy.userName}}</td>
						<td>{{customer.status}}</td>						
					</tr>             
                </table>                
            </div>
        </form>
    </div>
   
    <div id="addUpdateBody" ng-show="addScreen">
      <form name="addUpdateForm" ng-submit="submitForm(addUpdateForm.$valid)" novalidate>
        <div><h2>Customer Registration</h2></div>
        <input type="hidden" name="customerId" ng-model="customer.customerId"/>
        <input type="hidden" name="status" ng-model="customer.status"/>
        <input type="hidden" name="status" ng-model="customer.referedBy"/>
         {{validationError}}
        <div class="col-sm-8 col-sm-offset-2">       
        <!-- NAME -->
        <div class="form-group">
            <label>Name</label>
            <input type="text" name="customerName" class="form-control" ng-model="customer.customerName" 
            	ng-class="{invalidField:addUpdateForm.customerName.$invalid && submitted}" required>
             <p class="help-block" 
             ng-show="addUpdateForm.customerName.$invalid && submitted">Customer name is required.</p>
        </div>

        <!-- ADDRESS -->
        <div class="form-group">
            <label>Address</label>
            <input type="text" name="address" class="form-control"  ng-model="customer.address" 
            	ng-class="{invalidField:addUpdateForm.address.$invalid && submitted}" required>
             <p class="help-block" 
            	ng-show="addUpdateForm.address.$invalid && submitted">Address is required.</p>                    
        </div>
        
        <!-- CONTACT -->
        <div class="form-group">
            <label>Contact No.</label>
            +91<input type="number" name="contactNo" class="form-control" ng-model="customer.contactNo" 
               ng-class="{invalidField:addUpdateForm.contactNo.$invalid && submitted}" required>
            <p class="help-block" ng-show="addUpdateForm.contactNo.$invalid && submitted">Valid contact is required.</p>
            <p class="help-block" ng-show="addUpdateForm.contactNo.$error.number && submitted">Only numbers are allowed</p>
        </div>
        
        <!-- EMAIL -->
        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" class="form-control" ng-model="customer.email" 
               ng-class="{invalidField:addUpdateForm.email.$invalid && submitted}">
            <p class="help-block" ng-show="addUpdateForm.email.$invalid && submitted">Valid email is required.</p>
        </div>        
        
        <!-- DOB -->
        <div class="form-group">
            <label>Birthday</label>
            <input type="date" name="dob" class="form-control" ng-model="customer.dobStr" 
               ng-class="{invalidField:addUpdateForm.dob.$invalid && submitted}">
            <p class="help-block" ng-show="addUpdateForm.dob.$error.date && submitted">Valid Birth date is required.</p>
        </div>
        
        <!-- DOA -->
        <div class="form-group">
            <label>Anniversary</label>
            <input type="date" name="doa" class="form-control" ng-model="customer.doaStr" 
               ng-class="{invalidField:addUpdateForm.doa.$invalid && submitted}">
            <p class="help-block" ng-show="addUpdateForm.doa.$error.date && submitted">Valid Anniversary date is required.</p>
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