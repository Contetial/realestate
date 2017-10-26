<!--  <link rel="stylesheet" href="CSS/bootstrap.min.css" media="screen"/> -->
 
 <div style="text-align: center; width: 100%; height: auto; margin-left: 10%; margin-right: 10%;"> 
<h1 style="font-size: 25px;"></h1>

<div style="width: 100%; height: auto; text-indent: px; text-align: left; vertical-align: 100%; margin-left: -10%;">
 
<table>
	<tr>
		<td width="25%"
			style="vertical-align: top; height: 100%; background: rgb(255, 215, 0);">
			<div id="menu">
				<div class="art-menu" ng-include src="'pages/admin/AdminMenu.jsp'"></div>
			</div>
		</td>
		<td width="75%" style="margin-left: 10px;">
			<div ng-controller="adminGalleryController">
				<div id="searchBody" ng-show="searchScreen">
					<form id=searchEngineForm>
						<div id="searchEngine" align="center" class="form-group">
							<h3 align="center">Search Property</h3>
							<table id="searchEngineTable">
								<tr>
									<td>Property Name</td>
									<td><input ng-modal="propName" type="text"
										class="form-control" name="propName"></td>
								</tr>
								<tr><td>&nbsp;</td><td></td></tr>
								<tr>
									<td>&nbsp;</td>
									<td>
										<button type="button" class="btn btn-primary nav-toggle"
											id="btnsearch" ng-click="search()">Search</button> &nbsp;
										<button type="reset" class="btn btn-primary nav-toggle"
											id=clear>Clear</button>
									</td>
								</tr>
							</table>
							<br>
 							<button type="button" class="btn btn-primary nav-toggle" id="btnupdate" 
 								ng-show="isSearchResult" ng-click="startUpdate()">Update Gallery</button>
						</div>					
						<div id="searchResults" ng-show="searchResultScreen">
							<h3>Property List</h3>
							<h4>{{noPropDataMessage}}</h4>
							<table border="1" ng-show="isSearchResult"  style="width:70%">
								<tr>
									<th>&nbsp;</th>
									<th><a href=#AdminGallery 
										ng-click="orderByField='propertyName'; reverseSort = !reverseSort">Property Name</a></th>
									<th><a href=#AdminGallery
										ng-click="orderByField='address'; reverseSort = !reverseSort">Address</a></th>
									<th><a href=#AdminGallery
										ng-click="orderByField='details'; reverseSort = !reverseSort">Details</a></th>
									<th><a href=#AdminGallery
										ng-click="orderByField='status'; reverseSort = !reverseSort">Status</a></th>
								</tr>
								<tr	ng-repeat="property in properties | orderBy:orderByField:reverseSort">
									<td><input type="radio" name="propIndex" value={{$index}}
										ng-model="$parent.selectedProperty" /></td>
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
					<div>
					<form name="uplaodForm" ng-submit="submitForm(uplaodForm.$valid)" 
						enctype="multipart/form-data" novalidate>					
						<div><h3>Upload new images for gallery</h3></div>
						<h4>{{noDataMessage}}</h4>
						<h5>{{messageSave}}</h5>
						<input type="hidden" name="propertyId" ng-model="property.propertyId" />
						<input type="hidden" name="galleryId" ng-model="gallery.galleryid" />
						<div class="col-sm-5 col-sm-offset-2">
							<!-- NAME -->
					        <div class="form-group">
					            <label>Image Name</label> 
								<input type="text" name="imageName" class="form-control" ng-model="imageName" required/>
								<p class="help-block" 
	             				ng-show="uplaodForm.imageName.$invalid && submitted">Image name is required.</p>	             				
             				</div>
             				<!-- FILE -->
             				<div class="form-group">
             					<label>Image File</label> 				           
	             				<input type="file" name="myFileName" id="myFileId" file-model="myFile" accept="image/jpeg,image/jpg,image/png" required/>
								<p class="help-block" 
	             					ng-show="uplaodForm.myFileName.$invalid && submitted">Image file is required.</p>
             				</div>             				
							<br><br>
							
							<button type="submit" class="btn btn-primary">Upload</button>
        					<button type="button" class="btn btn-primary" 
        										ng-click="toggleDisplay()">Cancel</button>
        					
        				</div>
					</form>
					<form name="addVideoForm" ng-submit="submitVideoForm(addVideoForm.$valid)" novalidate>					
						<div><h3>Upload new videos for gallery</h3></div>
						<h4>{{noVideoDataMessage}}</h4>
						<h5>{{videoMessageSave}}</h5>
						<input type="hidden" name="propertyId" ng-model="property.propertyId" />
						<input type="hidden" name="galleryId" ng-model="gallery.galleryid" />
						<div class="col-sm-5 col-sm-offset-2">
							<!-- NAME -->
					        <div class="form-group">
					            <label>Video Name</label> 
								<input type="text" name="videoName" class="form-control" ng-model="videoName" required/>
								<p class="help-block" 
	             					ng-show="addVideoForm.videoName.$invalid && submitted">Video name is required.</p>	             				
             				</div>
             				<!-- Location -->
             				<div class="form-group">
             					<label>Youtube Video URL</label> 				           
	             				<input type="text" name="videopath" class="form-control" ng-model="videopath" required/>
								<p class="help-block" 
	             					ng-show="addVideoForm.videopath.$invalid && submitted">Video URL is required.</p>
             				</div>             				
							<br><br>
							
							<button type="submit" class="btn btn-primary">Upload</button>
        					<button type="button" class="btn btn-primary" 
        										ng-click="toggleDisplay()">Cancel</button>
        					
        				</div>
					</form>
						<div class="col-sm-5 col-sm-offset-2">
							<div class="form-group">
        						<ul>
									<li><h3>{{gallery.property.propName}}</h3></li>
									  <ul ng-repeat="image in gallery.images">						
										<li>{{image.imagename}} <img src="rest/imageService/loadImageThumb/{{image.imageid}}" />
											 <a href="#AdminGallery" ng-click="deleteImage({{image.imageid}})"><img src="images/del.png" style="height: 30px;width: 30px;" align="bottom"></img></a>  
										</li>
									  </ul>
									<ul ng-repeat="video in gallery.videos">
										<li>{{video.videoname}} <iframe width="320" height="175" src="{{video.videopath | trusted}}" frameborder="0" allowfullscreen/></li>
									</ul>
								</ul>
        					</div>
        				</div>
					</div>					
				</div>				
			</div>
		</td>
	</tr>
</table>
</div>
</div>
<style type="text/css">
	.invalidField {
	border: solid 1px red;
	}
</style>

