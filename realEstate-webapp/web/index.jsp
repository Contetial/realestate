<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="builderApp">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">	
	<title>Builder Site</title>
	<link  rel="stylesheet" href="CSS/style.css" media="screen"/>
	<link  rel="stylesheet" href="CSS/custom.css" media="screen"/>
	<link  rel="stylesheet" href="CSS/gallery.css" media="screen"/>
	<link  rel="stylesheet" href="CSS/bootstrap.css" media="screen"/>
	
		
	<script type="text/javascript" src="JavaScripts/script.js"></script>
	
    <script type="text/javascript" src="JavaScripts/angular.js"></script>    
    <script type="text/javascript" src="JavaScripts/angular-animate.min.js"></script>
    <script type="text/javascript" src="JavaScripts/angular-route.js"></script>
    <script type="text/javascript" src="JavaScripts/angular-resource.js"></script>
    <script type="text/javascript" src="JavaScripts/angular-touch.min.js"></script>
   	<script type="text/javascript" src="JavaScripts/angular-cookies.js"></script>
   	<!-- script type="text/javascript" src="JavaScripts/TweenMax.min.js"></script>
   	<script type="text/javascript" src="JavaScripts/jquery.min.js"></script-->
	<script type="text/javascript" src="JavaScripts/config.js"></script>
    <script type="text/javascript" src="JavaScripts/builderApp.js"></script>
    <script type="text/javascript" src="JavaScripts/builderControllers.js"></script>
    <script type="text/javascript" src="JavaScripts/loginController.js"></script>
    <script type="text/javascript" src="JavaScripts/loginService.js"></script>
    <script type="text/javascript" src="JavaScripts/customer.js"></script>
    <script type="text/javascript" src="JavaScripts/property.js"></script>
    <script type="text/javascript" src="JavaScripts/appUser.js"></script>
    <script type="text/javascript" src="JavaScripts/galleryController.js"></script>
    <script type="text/javascript" src="JavaScripts/adminGallery.js"></script>    
    
</head>
<body>
	<!-- Main Page Starts here -->
	<div id="art-page-background-gradient">
    <div id="art-main">
        <div class="art-Sheet">
            <div class="art-Sheet-tl"></div>
            <div class="art-Sheet-tr"></div>
            <div class="art-Sheet-bl"></div>
            <div class="art-Sheet-br"></div>
            <div class="art-Sheet-tc"></div>
            <div class="art-Sheet-cl"></div>
            <div class="art-Sheet-cr"></div>
            <div class="art-Sheet-cc"></div>
            <div class="art-Sheet-body" ng-controller="genericController">
				<div id="contentTitle" ng-include src="'pages/templates/main.jsp'"> </div>
							
				<div id="content" class="art-contentLayout">
					<div class="art-contentLayout ng-hide" ng-show="initialized">
						<div class="alert alert-danger" ng-show="error">{{error}}</div>
						<div ng-view></div>
					</div>					
				</div>	
								
				<div id="footer" class="art-Footer">
					<div id="footerTitle" ng-include src="'pages/templates/Footer.jsp'"> </div>	
				</div>
			</div>
		</div>
	</div>
	</div>					            
	<!-- Main Page ends here -->	
</body>
</html>