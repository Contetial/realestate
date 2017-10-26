<div class="art-nav">
	<div class="l"></div>
	<div class="r"></div>
	<ul class="art-menu">
		<li><a href="#home"><span class="l"></span><span class="r"></span><span class="t">Home</span></a></li>
		<li><a href="#properties"><span class="l"></span><span class="r"></span><span class="t">Properties</span></a></li>
		<li><a href="#aboutus"><span class="l"></span><span class="r"></span><span class="t">About</span></a></li>
		<li><a href="#gallery"><span class="l"></span><span class="r"></span><span class="t">Gallery</span></a></li>
		<li ng-show="hasPermission()"><a href="#admin"><span class="l"></span><span class="r"></span><span class="t">Admin</span></a></li>
		<li ng-hide="user" style="margin-left:300px;"><a href="#login"><span class="l"></span><span class="r"></span><span class="t">Login</span></a></li>
		<li ng-show="user" style="margin-left:300px;" class="dropdown">
			<a href="" class="dropdown-toggle" data-toggle="dropdown" style=" overflow: visible;">{{user.name}} <b class="caret"></b></a>
			<ul class="dropdown-menu" style="overflow: visible;">
				<li><a href="#logout" ng-click="logout()"><span class="glyphicon" ></span><font face="Comic sans ms"> Logout</font></a></li>
			</ul>
		</li>
	</ul>
</div>