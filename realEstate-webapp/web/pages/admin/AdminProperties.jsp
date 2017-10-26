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
		    <div ng-controller="propertyController">			
		    <div id="searchBody" ng-show="searchScreen">
		        <form id="searchEngineForm">
		            <div id="searchEngine" align="center"  class="form-group">
		                <h3 align="center">Search Property</h3>
		                
		                <table id="searchEngineTable">
		                    <tr>
		                        <td>Property Name</td>
		                        <td>
		                            <input ng-model="propName" type="text" class="form-control" name="propName">
		                        </td>
		                    </tr>
		                    <tr>
		                        <td>Property Address</td>
		                        <td>
		                            <input ng-model="propAddress" type="text" class="form-control" name="propAddress">
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
		                	id="btndelete" ng-show="isSearchResult" ng-click="deleteProperty()">Delete</button>                
		                <br>
		                {{messageSave}}
		            </div>
		                        
		            <div id="searchResults" ng-show="searchResultScreen">
		                <h3>Property List</h3>
		                <h4>{{noDataMessage}}</h4>                
		                <table border="1" ng-show="isSearchResult" style="width:70%">
		                	<tr>
		                		<th>&nbsp;</th>
								<th><a href=#AdminProperties ng-click="orderByField='propertyName'; reverseSort = !reverseSort">Property Name</a></th>
								<th><a href=#AdminProperties ng-click="orderByField='address'; reverseSort = !reverseSort">Address</a></th>
								<th><a href=#AdminProperties ng-click="orderByField='details'; reverseSort = !reverseSort">Details</a></th>
								<th><a href=#AdminProperties ng-click="orderByField='status'; reverseSort = !reverseSort">Status</a></th>
							</tr>
							<tr ng-repeat="property in properties | orderBy:orderByField:reverseSort" >
								<td><input type="radio" name="propIndex" value={{$index}} ng-model="$parent.selectedProperty"/></td>
								<td>{{property.propName}}</td>
								<td>{{property.propAddress}}</td>
								<td>{{property.propDetails}}</td>
								<td>{{property.propStatus}}</td>				
							</tr>             
		                </table>                
		            </div>
		        </form>
		    </div>
		   
		   <div id="addUpdateBody" ng-show="addScreen">
		      <form name="addUpdateForm" ng-submit="submitForm(addUpdateForm.$valid)" novalidate>
		        <div><h2>Property Registration</h2></div>
		        <input type="hidden" name="propertyId" ng-model="property.propertyId"/>
		        <input type="hidden" name="status" ng-model="property.propStatus"/>
		        <div class="col-sm-8 col-sm-offset-2">       
		        <!-- NAME -->
		        <div class="form-group">
		            <label>Name</label>
		            <input type="text" name="propertyName" class="form-control" ng-model="property.propName" 
		            	ng-class="{invalidField:addUpdateForm.propertyName.$invalid && submitted}" required>
		             <p class="help-block" 
		             ng-show="addUpdateForm.propertyName.$invalid && submitted">Property name is required.</p>
		        </div>
		
		        <!-- ADDRESS -->
		        <div class="form-group">
		            <label>Address</label>
		            <input type="text" name="address" class="form-control"  ng-model="property.propAddress" 
		            	ng-class="{invalidField:addUpdateForm.address.$invalid && submitted}" required>
		             <p class="help-block" 
		            	ng-show="addUpdateForm.address.$invalid && submitted">Address is required.</p>                    
		        </div>
		        
		        <!-- DETAILS -->
		        <div class="form-group">
		            <label>Details</label>
		            <input type="text" name="details" class="form-control" ng-model="property.propDetails" 
		               ng-class="{invalidField:addUpdateForm.details.$invalid && submitted}" required>
		            <p class="help-block" ng-show="addUpdateForm.details.$invalid && submitted">Details are required.</p>
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
