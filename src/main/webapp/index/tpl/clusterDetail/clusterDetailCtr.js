"use strict";function _defineProperty(e,n,t){return n in e?Object.defineProperty(e,n,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[n]=t,e}function _toConsumableArray(e){if(Array.isArray(e)){for(var n=0,t=Array(e.length);n<e.length;n++)t[n]=e[n];return t}return Array.from(e)}!function(e,n){void 0!==e&&e.controller("ClusterDetailCtr",["$scope","$LunarCluster","$stateParams","$state","dialog","$LunarModel","$modal","api",function(e,t,a,i,o,r,l,s){function c(e){return e?e.replace(/\d+/g,function(e){return("000"+e).slice(-3)}):"0"}function u(e){var t=!0,a=!1,i=n;try{for(var o,r=e[Symbol.iterator]();!(t=(o=r.next()).done);t=!0){var l=o.value;l.ipNumber=c(l.ip),l.workEnv=[],"TESTENV"in l.labels&&l.workEnv.push("测试"),"PRODENV"in l.labels&&l.workEnv.push("生产"),l.workEnv=l.workEnv.join(",")||"未知",l.capacity&&(l.memoryNumber=parseInt(l.capacity.memory,10),l.cpuNumber=parseInt(l.capacity.cpu,10),l.capacity.memory=(l.capacity.memory/1024/1024).toFixed(2)),l.labels||(l.labels={}),l.isUsedByBuild=void 0!==l.labels.BUILDENV}}catch(e){a=!0,i=e}finally{try{!t&&r.return&&r.return()}finally{if(a)throw i}}}function d(){m.getNodeList(f).then(function(n){var t=n.data.result||[];u(t),e.nodeListIns.init(t,!1)}).finally(function(){e.labels.selectedNodeLabelList=[],e.isWaitingHost=!1})}a.id||i.go("clusterManage");var f=e.clusterId=a.id,m=t.getInstance("NodeService"),p=t.getInstance("ClusterService"),b=void 0;e.labels={selectedNodeLabelList:[],selectedLabelForInstance:[]},e.nodeLabelSelectorList=[],e.nodeListIns=new r.SelectListModel("nodeList"),e.resourceType="CLUSTER",e.resourceId=f,e.isWaitingHost=!0,e.isWaitingNamespace=!0,e.isWaitingModify=!1,e.hasWatcher=!1,e.hasActiveVersions=!1,e.watcher={},e.valid={needValid:!1},e.namespaceTxt={namespace:""},e.isEdit=!1,e.tabActive=[{active:!1},{active:!1},{active:!1},{active:!1},{active:!1},{active:!1}],e.hostOrderBy={item:"",isReverse:!1},e.toggleHostOrderBy=function(n){e.hostOrderBy.item===n?e.hostOrderBy.isReverse=!e.hostOrderBy.isReverse:(e.hostOrderBy.item=n,e.hostOrderBy.isReverse=!1)},e.orderBy={item:"",isReverse:!1},e.toggleOrderBy=function(n){e.orderBy.item===n?e.orderBy.isReverse=!e.orderBy.isReverse:(e.orderBy.item=n,e.orderBy.isReverse=!1)},d(),e.getNodeLabel=function(){e.isLoadingNodeLabel=!0,s.cluster.listHostLabel(f).then(function(n){e.nodeLabelSelectorList=(n||[]).filter(function(e){return"USER_LABEL_VALUE"===e.content}).map(function(e){return{value:e,text:e.name}})}).catch(function(e){}).then(function(){e.isLoadingNodeLabel=!1})},e.getNodeLabel(),e.getNodeListByLabel=function(){0===e.labels.selectedNodeLabelList.length?d():(e.isWaitingHost=!0,s.cluster.listNodeByLabels(f,e.labels.selectedNodeLabelList).then(function(n){var t=n||[];u(t),e.nodeListIns.init(t,!1)}).catch(function(){}).then(function(){e.isWaitingHost=!1}))},e.hideHostColumn={cpu:{name:"CPU总量",isShow:!0},memory:{name:"内存总量",isShow:!0},workEnv:{name:"工作场景",isShow:!0},runningPods:{name:"运行实例",isShow:!0},dockerVersion:{name:"Docker版本",isShow:!1},k8sVersion:{name:"Kubernete版本",isShow:!1}},e.nodeSearch={displaySearchType:"name",searchKey:""},e.changeSearchType=function(){e.labels.selectedNodeLabelList=[],e.nodeSearch.searchKey="",d(),"label"===e.nodeSearch.displaySearchType&&e.getNodeLabel()},e.modifyLabels=function(){var n=(e.nodeListIns.nodeList||[]).filter(function(e){return e.isSelected===!0});if(0===n.length)return void o.alert("选择主机","请至少选择一台主机！");e.getNodeLabel();var t=[],a=n.map(function(e){return Object.keys(e.labels).filter(function(n){return"USER_LABEL_VALUE"===e.labels[n]})}),i=a.reduce(function(e,n){return e.filter(function(e){return n.includes(e)})});o.common({title:"修改主机标签",buttons:o.buttons.BUTTON_OK_CANCEL,controller:["$scope",function(e){t=e.commonLabelList=angular.copy(i),e.formData={hasExistedLabel:"true",selectedNodeLabel:[],filledNodeLabel:null},e.addSelectedLabel=function(){var n=e.formData.selectedNodeLabel;t=e.commonLabelList=e.commonLabelList.concat(n.filter(function(n){return!e.commonLabelList.includes(n.name)}).map(function(e){return e.name}))},e.addInputLabel=function(){var n=e.formData.filledNodeLabel;n&&((e.commonLabelList||[]).includes(n)||(e.commonLabelList.push(n),t=e.commonLabelList),e.formData.filledNodeLabel=null)},e.removeLabel=function(n){var a=e.commonLabelList;a.splice(a.indexOf(n),1),t=a}}],value:{nodeLabelList:e.nodeLabelSelectorList},template:'\n              <form id="input_label_form" ng-submit="addInputLabel()"></form>\n              <form>\n                <form-container left-column-width="60px">\n                  <form-config-group>\n                    <form-help-line>\n                        <icon-info></icon-info><span>用户可“添加”已有标签/新标签，也可点击×删除所选主机共同标签。</span>\n                    </form-help-line>\n                    <form-config-item config-title="主机标签">\n                      <form-input-container>\n                        <form-input-radio-group ng-model="formData.hasExistedLabel" name="labelStatus" fallback-value="\'true\'" options="[{value: \'true\', text: \'已有标签\'}, {value: \'false\', text: \'新建标签\'}]"></form-input-radio-group>\n                      </form-input-container>\n                    </form-config-item>\n                    <form-config-item ng-if="formData.hasExistedLabel === \'true\'">\n                      <form-input-container>\n                        <form-with-button width="50px">\n                          <content-area>\n                            <form-multiple-select name="nodeLabelSelect" ng-model="formData.selectedNodeLabel" options="value.nodeLabelList" placeholder="选择主机标签" empty-text="无标签信息"></form-multiple-select>\n                          </content-area>\n                          <button-area>\n                            <button type="button" style="color: #fff; background-color: /*[[BUTTON_COLOR*/#188ae2/*]]*/;" ng-click="addSelectedLabel(formData.selectedNodeLabel)">添加</button>\n                          </button-area>\n                        </form-with-button>\n                      </form-input-container>\n                    </form-config-item>\n                    <form-config-item ng-if="formData.hasExistedLabel === \'false\'">\n                      <form-input-container>\n                        <form-with-button width="50px">\n                          <content-area>\n                            <input form="input_label_form" type="text" name="nodeLabelInput" ng-model="formData.filledNodeLabel" placeholder="输入主机标签"/>\n                          </content-area>\n                          <button-area>\n                            <button type="submit" form="input_label_form" style="color: #fff; background-color: /*[[BUTTON_COLOR*/#188ae2/*]]*/;">添加</button>\n                          </button-area>\n                        </form-with-button>\n                      </form-input-container>\n                    </form-config-item>\n                    <form-config-item style="line-height: 20px" config-title="共同标签">\n                      <form-input-container>\n                        <div ng-if="commonLabelList.length === 0">无</div>\n                        <div class="ui-label" ng-repeat="label in commonLabelList track by $index">\n                           <a class="icon-cancle icon-cancle-former" ng-click="removeLabel(label)"></a><span ng-bind="label"></span>\n                        </div>\n                      </form-input-container>\n                    </form-config-item>\n                  </form-config-group>\n                </form-container>\n              </form>  \n            ',size:600}).then(function(a){if(a===o.button.BUTTON_OK){var r=t.filter(function(e){return!i.includes(e)&&t.includes(e)}),l=n.map(function(e){return{nodeName:e.name,labels:Object.assign.apply(Object,[{}].concat(_toConsumableArray((r||[]).map(function(e){return _defineProperty({},e,"USER_LABEL_VALUE")}))))}}),c=i.filter(function(e){return i.includes(e)&&!t.includes(e)}),u=n.map(function(e){return{nodeName:e.name,labels:Object.assign.apply(Object,[{}].concat(_toConsumableArray((c||[]).map(function(e){return _defineProperty({},e,"USER_LABEL_VALUE")}))))}});if(0===c.length&&0===r.length)return void o.tip("无修改","无修改操作");o[c.length?"danger":"continue"]("修改主机标签","是否"+[c.length?"删除主机标签"+c.join("、"):"",r.length?"添加主机标签"+r.join("、"):""].filter(function(e){return e}).join("，")+"？").then(function(n){n===o.button.BUTTON_OK&&(e.isUpdateLabel=!0,s.SimplePromise.resolve().then(function(){if(c.length)return s.cluster.deleteNodeLabels(f,u)}).then(function(){if(r.length)return s.cluster.addNodeLabels(f,l)}).then(function(){o.tip("修改成功","修改主机标签成功"),e.getNodeLabel(),d()},function(e){o.error("修改主机标签失败",e.message)}).then(function(){e.isUpdateLabel=!1}))})}})},e.modifyWorkEnv=function(){var n=(e.nodeListIns.nodeList||[]).filter(function(e){return e.isSelected===!0});if(0===n.length)return void o.alert("选择主机","请至少选择一台主机！");var t={testEnv:!1,prodEnv:!1};o.common({title:"修改工作场景",buttons:o.buttons.BUTTON_OK_CANCEL,value:{workEnv:t},template:'\n                <form-container left-column-width="60px">\n                  <form>\n                    <form-config-group>\n                      <form-help-line>\n                        <icon-info></icon-info><span>该操作将会对勾选的主机添加同样的工作场景。如需删除主机工作场景，可进入该主机详情进行删除。</span>\n                      </form-help-line>\n                      <form-config-item config-title="工作场景">\n                        <form-input-container>\n                          <form-multiple-inline>\n                            <form-multiple-inline-item>\n                              <form-input-checkbox ng-model="value.workEnv.testEnv" text="测试环境" value="true" value-false="false"></form-input-checkbox>\n                            </form-multiple-inline-item>\n                            <form-multiple-inline-item>\n                              <form-input-checkbox ng-model="value.workEnv.prodEnv" text="生产环境" value="true" value-false="false"></form-input-checkbox>\n                            </form-multiple-inline-item>\n                          </form-multiple-inline>\n                        </form-input-container>\n                      </form-config-item>\n                    </form-config-group>\n                  </form>\n                </form-container>\n              ',size:600}).then(function(a){if(a===o.button.BUTTON_OK){var i={};t.testEnv&&(i.TESTENV="HOSTENVTYPE"),t.prodEnv&&(i.PRODENV="HOSTENVTYPE");var r=n.map(function(e){return{nodeName:e.name,labels:i}});e.isUpdateLabel=!0,s.cluster.addNodeLabels(f,r).then(function(){o.tip("修改成功","工作场景修改成功"),d()}).catch(function(e){o.error("修改工作场景失败",e.message)}).then(function(){e.isUpdateLabel=!1})}})},e.displayCondition=!0,e.toggleFilterCondition=function(){e.displayCondition=!e.displayCondition};var g=function(){m.getData(f).then(function(n){e.clusterIns=t.getInstance("Cluster",n.data.result),b=angular.copy(e.clusterIns.config),e.config=e.clusterIns.config,1===b.buildConfig?e.$emit("pageTitle",{title:e.config.name,descrition:"该集群是构建集群，需要保证集群内主机可用于构建。",mod:"cluster"}):e.$emit("pageTitle",{title:e.config.name,descrition:"",mod:"cluster"}),m.getData().then(function(n){e.clusterList=n.data.result||[];for(var t=0;t<e.clusterList.length;t++)if(e.clusterList[t].name===b.name){e.clusterList.splice(t,1);break}})},function(){o.error("警告","请求失败！"),i.go("clusterManage")}),p.getWatcher(f).then(function(n){"200"==n.data.resultCode?(e.watcher=angular.copy(n.data.result),e.hasWatcher=!0,e.watcher.versionSelectorInfos&&(e.hasActiveVersions=!0),"PROD"===e.watcher.hostEnv?e.watcher.hostEnv="生产环境":"TEST"===e.watcher.hostEnv&&(e.watcher.hostEnv="测试环境")):e.hasWatcher=!0}).then(function(){m.getNodeList(e.clusterId).then(function(n){var a=n.data.result||[];e.nodeListIns2=t.getInstance("NodeList",a)},function(){e.nodeListIns2=t.getInstance("NodeList")})})};g(),e.showHost=function(n){for(var t in e.nodeListIns2.labelsInfo)e.nodeListIns2.labelsInfo[t].isSelected=!1;e.nodeListIns2.toggleLabelNodes();for(var a in n)e.nodeListIns2.toggleLabel(n[a].name,!0);return l.open({animation:!0,templateUrl:"/index/tpl/modal/hostListModal/hostListModal.html",controller:"HostListModalCtr",size:"lg",resolve:{hostList:function(){return e.nodeListIns2.nodeList}}}).result},e.getNamespace=function(){m.getNamespace(f).then(function(n){var t=n.data.result||[];e.namespaceList=[];for(var a=0,i=t.length;a<i;a++)e.namespaceList.push(t[a].name)},function(){e.namespaceList=[]}).finally(function(){e.isWaitingNamespace=!1})},e.addHost=function(n){e.mayEditCluster()?i.go("addHost",{id:n}):o.error("警告","您没有权限添加主机")},e.addNamespace=function(){e.isLoadingNamespace=!0;var n=e.namespaceTxt.namespace;if(n){for(var t=0,a=e.namespaceList.length;t<a;t++)if(e.namespaceList[t]===n)return o.error("警告","已存在！"),void(e.isLoadingNamespace=!1);m.setNamespace(f,[n]).then(function(){e.namespaceList.push(n),e.namespaceTxt.namespace=""},function(){o.error("警告","添加失败！")}).finally(function(){e.isLoadingNamespace=!1})}},e.createWatcher=function(n){e.mayEditCluster()?i.go("createWatcher",{id:n}):o.error("您没有权限添加监听器")},e.checkEdit=function(){e.isEdit=!e.isEdit,e.isEdit?g():(e.valid.needValid=!1,e.clusterIns.config=angular.copy(b),e.config=e.clusterIns.config)},e.deleteCluster=function(){m.deleteData(f).then(function(){i.go("clusterManage")})},e.exitToList=function(){i.go("clusterManage")},e.modifyCluster=function(){var n=e.clusterIns.validItem("etcd"),t=e.clusterIns.validItem("kafka"),a=e.clusterIns.validItem("zookeeper");n&&t&&a&&(e.isWaitingModify=!0,e.valid.needValid=!1,e.clusterIns.modify().then(function(){o.alert("提示","修改成功！"),g(),e.checkEdit()},function(e){o.error("修改失败",e.data.resultMsg)}).finally(function(){e.isWaitingModify=!1}))},e.toggleNodeLabel=function(t){t.isUsedByBuild=!t.isUsedByBuild;var a=!1;if(!t.isUsedByBuild){a=!0;var i=!0,r=!1,l=n;try{for(var s,c=e.nodeListIns.nodeList[Symbol.iterator]();!(i=(s=c.next()).done);i=!0){if(s.value.isUsedByBuild){a=!1;break}}}catch(e){r=!0,l=e}finally{try{!i&&c.return&&c.return()}finally{if(r)throw l}}}a&&(o.error("警告","请保证集群内至少有一台用于构建的主机！"),t.isUsedByBuild=!t.isUsedByBuild);var u=[{node:t.name,labels:{BUILDENV:"HOSTENVTYPE"}}];t.isUsedByBuild?m.addLabel(f,u).catch(function(e){t.isUsedByBuild=!t.isUsedByBuild,o.error("修改失败",e.data.resultMsg)}):m.deleteLabel(f,u).catch(function(e){t.isUsedByBuild=!t.isUsedByBuild,o.error("修改失败",e.data.resultMsg)})};var h=i.$current.name;h.indexOf("info")!==-1?e.tabActive[1].active=!0:h.indexOf("namespace")!==-1?(e.tabActive[2].active=!0,e.getNamespace()):h.indexOf("users")!==-1?e.tabActive[3].active=!0:h.indexOf("instances")!==-1?e.tabActive[4].active=!0:h.indexOf("watcher")!==-1?e.tabActive[5].active=!0:e.tabActive[0].active=!0,e.userRole=null,e.setRole=function(n){e.userRole=n},e.mayEditCluster=function(){return"MASTER"===e.userRole||"DEVELOPER"===e.userRole},e.instanceSearch={displaySearchType:"name",searchKey:""},e.changeInstanceSearchType=function(){e.labels.selectedLabelForInstance=[],e.instanceSearch.searchKey="",L(),"label"===e.instanceSearch.displaySearchType&&e.getNodeLabel()},e.hideInstanceColumn={hostName:{name:"主机名称",isShow:!0},podIp:{name:"实例IP",isShow:!0},status:{name:"实例状态",isShow:!0},deployName:{name:"部署名称",isShow:!0},deployVersion:{name:"部署版本",isShow:!1},namespace:{name:"namespace",isShow:!1},startTime:{name:"启动时间",isShow:!1},containerId:{name:"容器ID",isShow:!1},imageName:{name:"镜像名称",isShow:!1}};var L=function(){e.isWaitingInstances=!0,s.cluster.listInstance(f).then(function(n){e.instanceList=n.map(function(e){return e.podIpNumber=c(e.podIp),e})}).catch(function(e){}).then(function(){e.labels.selectedLabelForInstance=[],e.isWaitingInstances=!1})};L(),e.listInstanceByLabel=function(){e.labels.selectedLabelForInstance.length>0?(e.isWaitingInstances=!0,s.cluster.listInstanceByLabel(f,e.labels.selectedLabelForInstance).then(function(n){e.instanceList=(n||[]).map(function(e){return e.podIpNumber=c(e.podIp),e})}).catch(function(e){}).then(function(){e.isWaitingInstances=!1})):L()},e.goToDeployInstance=function(e){i.go("deployDetail.instance",{id:e.deloyId,collectionId:0,collectionName:"all-deploy"})}}]).directive("status",function(){return{restrict:"E",replace:"true",template:"<b><span>{{statusVal}}</span></b>",link:function(e,n,t){switch(t.type){case"RUNNING":n.addClass("txt-success"),e.statusVal="运行中";break;case"DEPLOYING":n.addClass("txt-normal"),e.statusVal="部署中";break;case"STOP":n.addClass("txt-warning"),e.statusVal="停止";break;case"ERROR":n.addClass("txt-warning"),e.statusVal="异常";break;case"BACKROLLING":n.addClass("txt-mormal"),e.statusVal="回滚中";break;case"STOPPING":n.addClass("txt-mormal"),e.statusVal="停止中";break;case"UPDATING":n.addClass("txt-mormal"),e.statusVal="升级中";break;case"UPSCALING":n.addClass("txt-mormal"),e.statusVal="扩容中";break;case"DOWNSCALING":n.addClass("txt-mormal"),e.statusVal="缩容中";break;case"ABORTING":n.addClass("txt-mormal"),e.statusVal="中断中";break;case"BACKROLL_ABORTED":n.addClass("txt-warning"),e.statusVal="回滚已中断";break;case"UPDATE_ABORTED":n.addClass("txt-warning"),e.statusVal="升级已中断";break;default:e.statusVal="-"}}}})}(angular.module("LunarApp")),function(e){e.component("customTableColumn",{template:'\n      <style>\n        .setting-table-column{ line-height: 40px; }\n      </style>\n      <div class="setting-table-column">\n        <icon-setting ng-click="$ctrl.operateHostTableColumn()" tooltip="显示更多列"></icon-setting>\n      </div>\n    ',bindings:{ngModel:"="},controller:["$scope","dialog",function(e,n){var t=this;t.operateHostTableColumn=function(){a(),n.common({title:"自定义列表",buttons:n.buttons.BUTTON_OK_CANCEL,value:{columns:t.value},template:'\n              <form-container left-column-width="60px">\n                <form>\n                  <form-config-group>\n                    <form-help-line>\n                      <icon-info></icon-info><span>请选择您想显示的列表详细信息。</span>\n                    </form-help-line>\n                    <form-config-item config-title="可选项">\n                      <form-input-container>\n                        <form-multiple-inline>\n                          <form-multiple-inline-item style="flex-basis: 120px;" ng-repeat="(key, column) in value.columns">\n                            <form-input-checkbox ng-model="column.isShow" text="{{ column.name }}" value="true" value-false="false"></form-input-checkbox>\n                          </form-multiple-inline-item>\n                        </form-multiple-inline>\n                      </form-input-container>\n                    </form-config-item>\n                  </form-config-group>\n                </form>\n              </form-container>\n            ',size:600}).then(function(e){if(e!==n.button.BUTTON_OK)throw"";t.ngModel=t.value})};var a=function(){t.ngModel&&(t.value=angular.copy(t.ngModel))};e.$watch("$ctrl.ngModel",a)}]})}(angular.module("formInputs"));