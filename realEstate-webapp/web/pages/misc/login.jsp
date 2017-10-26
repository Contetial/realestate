<div class="art-contentLayout" align="right">
<table width="230" border="0" >
		<tr>
			<td width="224">
				<div class="art-sidebar1">
					<div class="art-Block">
						<div class="art-Block-body">
							<div class="art-BlockHeader">
								<div class="l"></div>
								<div class="r"></div>
								<div class="art-header-tag-icon">
									<div class="t">Login</div>
								</div>
							</div>

							<div class="art-BlockContent">
								<div class="art-BlockContent-tl"></div>
								<div class="art-BlockContent-tr"></div>
								<div class="art-BlockContent-bl"></div>
								<div class="art-BlockContent-br"></div>
								<div class="art-BlockContent-tc"></div>
								<div class="art-BlockContent-bc"></div>
								<div class="art-BlockContent-cl"></div>
								<div class="art-BlockContent-cr"></div>
								<div class="art-BlockContent-cc"></div>
								<div class="art-BlockContent-body">
									<div ng-controller="loginController">
										<form>
										{{invalidCreds}}
											<p align="center">UserName</p>
											<p>
												<input type="text" value="" name="Name" id="Name"
													ng-model="username" style="width: 95%;" />
											</p>
											<p align="center">Password</p>
											<p align="center">
												<input type="password" value="" name="Password"
													ng-model="password" id="Password" style="width: 95%;" />
											</p>
											<div>
												<div>
													<div class="checkbox">
														<label>
															<input type="checkbox" ng-model="rememberMe"> Remember me
														</label>
													</div>
												</div>
											</div>
											<p align="center">
												<button type="submit" name="login" class="art-button" ng-click="login()">Login</button>												
											</p>
										</form>
										<form>
											<button class="art-button" type="submit" name="register">REGISTER coming soon..</button>
					                   </form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>					
			</td>
		</tr>                   
</table>
</div>
                 
         