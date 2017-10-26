<div ng-controller="galleryController" style="width: 800px">

	<!-- Property slider container -->
    <div class="propSlider" style="width: 853px;">

        <!-- enumerate all Properties -->
        <table>
        	<tr>
        		<td>
	        		<!-- prev / next controls -->
	        		<a class="arrow prev" href="#/gallery" ng-click="showPrevProp()"></a>
        		</td>
        		<td>
        			<ul ng-repeat="property in properties" class="slide" ng-swipe-right="showNextProp()" 
        				ng-swipe-left="showPrevProp()" ng-show="isActiveProp($index)">
        				<li></t>
        					<h4 style="margin-left: 20%;">Name: {{property.propName}}</h4>
        					<h4 style="margin-left: 20%;">Address: {{property.propAddress}}</h4>
        					<h4 style="margin-left: 20%;">Details: {{property.propDetails}}</h4>
        				</li>        				
        			</ul>
        		</td>
        		<td>
        			<a class="arrow next" href="#/gallery" ng-click="showNextProp()"></a>
        		</td>
        	</tr>
        </table>       
    </div>
    
   
   <div class="tab" ng-init="tab=1">
	<div class="l"></div>
	<div class="r"></div>
	<ul class="art-menu">
		<li ng-class="{active:tab===1}"> 
      		<a href ng-click="tab = 1" style="margin-left: 16px;"><span class="l"></span><span class="r"></span><span class="t">Images</span></a>  
        </li>           
        
   		<li ng-class="{active:tab===2}"> 
      	     <a href ng-click="tab = 2" ><span class="l"></span><span class="r"></span><span class="t">Videos</span></a>              
        </li>   
	</ul>
	<div class="art-contentLayout slider" style="margin-top:0px;border-color: navy;border-width: thin;margin-left: 20px;">
	
    
    	<!-- Image slider container-->
    <div ng-show="tab === 1">
    
       <!--  enumerate all Images -->
        
        <img ng-repeat="image in images" class="slide" ng-swipe-left="showPrev()" ng-swipe-right="showNext()" 
        	ng-show="isActive($index)" 	ng-src="rest/imageService/loadImage/{{image.imageid}}" />
        	
      
									
        <!-- prev / next controls -->
        <a class="arrow prev" href="#/gallery" ng-click="showPrev()"></a>
        <a class="arrow next" href="#/gallery" ng-click="showNext()"></a>

       <!--  extra navigation controls -->
        <ul class="nav">
            <li ng-repeat="image in images" ng-class="{'active':isActive($index)}">
                <img src="rest/imageService/loadImageThumb/{{image.imageid}}" 
                	alt="{{image.imagename}}" title="{{image.imagename}}" ng-click="showImage($index);" />
            </li>
        </ul>
   </div>
        
        <!-- <Video Slider> -->
   <div ng-show="tab === 2" >
          <video ng-repeat="video in videos" class="slide" ng-swipe-left="showPrevVid()" 
          	ng-swipe-right="showNextVid()" ng-show="isActiveVid($index)" src="{{video.videopath | trusted}}"> </video>
         <!-- ul class="nav">
            <li ng-repeat="video in videos" class="slide" ng-class="{'active':isActive($index)}">
        		<iframe width="800" height="500" src="{{video.videopath | trusted}}" frameborder="0"></iframe>
         	</li>
         </ul-->
          
           <!-- prev / next controls -->
        <a class="arrow prev" href="#/gallery" ng-click="showPrev()"></a>
        <a class="arrow next" href="#/gallery" ng-click="showNext()"></a>
    
    </div>      
  </div>
</div>