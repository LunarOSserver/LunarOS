"use strict";function _classCallCheck(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}var _createClass=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}();!function(e,t){function n(e,t,n){function r(){var t=this;this._url="/api/image",this.getBaseImages=function(){return e.get(t._url+"/base")},this.getProjectImages=function(){return e.get(t._url)},this.getForBuildImages=function(){return e.get(t._url+"/forbuild")},this.getAllImages=function(){return e.get(t._url+"/all")},this.getImageInfo=function(n){return e.get(t._url+"/all/detail?name="+n)},this.getImageTags=function(n,r){return e.get(t._url+"/detail?name="+n+"&registry="+r)},this.getCustomImages=function(){return e.get(t._url+"/custom")},this.getCustomImageInfo=function(n){return e.get(t._url+"/custom/"+n)},this.createCustomImage=function(n){return e.post(t._url+"/custom",angular.toJson(n))},this.buildCustomImage=function(n){return e.post(t._url+"/custom/build/"+n)},this.deleteCustomImage=function(n){return e.delete(t._url+"/custom/"+n)},this.validImageName=function(n,r){return e.post(t._url+"/custom/validate?imageName="+n+"&imageTag="+r)},this.createBaseImage=function(n){return e.post(t._url+"/base",angular.toJson(n))},this.deleteBaseImage=function(n){return e.delete(t._url+"/base/"+n)},this.getExclusiveImages=function(n){return e.get(t._url+"/exclusive/"+n)},this.getCollectionImages=function(n){return e.post(t._url+"/all/detail",angular.toJson(n))},this.deletePrivateImage=function(n,r,i){return e.delete(t._url+"/all/detail/tag?name="+n+"&tag="+r+"&registry="+i)},this.getTagDetail=function(n){return e.post(t._url+"/all/detail/tag",angular.toJson(n))}}var i=new r,a=function(e){var r=t.defer();return n.danger("确认删除","确认要删除吗？").then(function(e){if(e!==n.button.BUTTON_OK)throw""}).then(function(){i.deleteBaseImage(e).then(function(){r.resolve()},function(){r.reject(),n.error("删除失败",res.data.resultMsg)})},function(){r.reject()}),r.promise},u=function(e,r,a){var u=t.defer();return n.danger("删除","该镜像名下，image id相同的所有版本都会被删除，请慎重操作").then(function(e){if(e!==n.button.BUTTON_OK)throw""}).then(function(){i.deletePrivateImage(e,r,a).then(function(){u.resolve()},function(e){u.reject(),n.error("删除失败！",e.data.errors.message+e.data.resultMsg)})},function(){u.reject()}),u.promise},o=function(){function e(){_classCallCheck(this,e)}return _createClass(e,[{key:"init",value:function(){this.config={autoCustom:0,imageName:"",imageTag:"",description:"",dockerfileContent:"",files:[{fileName:"",filePath:"",content:""}],envSettings:[{key:"",value:"",description:""}],sourceImage:{thirdParty:0,imageName:"",imageTag:"",registryUrl:""},publish:1}}},{key:"addEnvConfDefault",value:function(){this.config.envSettings.push({key:"",value:"",description:""})}},{key:"deleteArrItem",value:function(e,t){this.config[e].splice(t,1)}},{key:"addFileDefault",value:function(){this.config.files.push({fileName:"",filePath:"",content:""})}},{key:"clearFileWrite",value:function(e){this.config.files[e].content=""}}]),e}();return{imageService:i,Mirror:o,deleteBaseImage:a,getMirrorInstance:function(){var e=new o;return e.init(),e},deletePrivateImage:u}}var r=angular.module("imageModule",[]);n.$inject=["$http","$q","dialog"],r.factory("$LunarImage",n),e.imageModule=r}(window);