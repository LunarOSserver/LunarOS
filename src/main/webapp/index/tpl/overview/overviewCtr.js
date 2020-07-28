"use strict";function _toConsumableArray(a){if(Array.isArray(a)){for(var e=0,t=Array(a.length);e<a.length;e++)t[e]=a[e];return t}return Array.from(a)}var _slicedToArray=function(){function a(a,e){var t=[],o=!0,n=!1,r=void 0;try{for(var i,l=a[Symbol.iterator]();!(o=(i=l.next()).done)&&(t.push(i.value),!e||t.length!==e);o=!0);}catch(a){n=!0,r=a}finally{try{!o&&l.return&&l.return()}finally{if(n)throw r}}return t}return function(e,t){if(Array.isArray(e))return e;if(Symbol.iterator in Object(e))return a(e,t);throw new TypeError("Invalid attempt to destructure non-iterable instance")}}();!function(a,e){void 0!==a&&a.controller("OverviewCtr",["$scope","$timeout","$filter","api","chartHandler",function(a,e,t,o,n){a.data={};var r={},i=["project","deployment","disk","resource","alarmList","actionList","usage","id","version"].map(function(e){var t=r[e]=o.overview[e]();return t.then(function(e){return angular.merge(a.data,e)}),t});a.trackerReady=!1,o.SimplePromise.all([o.user.whoami(),o.SimplePromise.all(i)]).then(function(e){if(_slicedToArray(e,1)[0].isAdmin){var t={build_auto:a.data.action.build.auto.join(),build_manual:a.data.action.build.manual.join(),deploy_auto:a.data.action.deploy.auto.join(),deploy_online:a.data.action.deploy.online.join(),memory_using:a.data.memory.using,memory_free:a.data.memory.free,cpu_load_0:a.data.cpu.load_0_25,cpu_load_25:a.data.cpu.load_25_50,cpu_load_50:a.data.cpu.load_50_75,cpu_load_75:a.data.cpu.load_75_100,disk_using:a.data.disk.using,disk_free:a.data.disk.free,node_online:a.data.node.online,node_offline:a.data.node.offline,project_collection:a.data.project.collection,project_total:a.data.project.total,deploy_collection:a.data.deploy.collection,deploy_total:a.data.deploy.total,image_project:a.data.image.project,storage_total:a.data.storage.total,volume_total:a.data.volume.total,uuid:a.data.id,version:a.data.version};a.trackerUrl=Object.keys(t).map(function(a){return encodeURIComponent(a)+"="+encodeURIComponent(t[a])}).join("&"),a.trackerReady=!0}});var l=Date.now(),d=function(a){return null==a.x?"":"<strong>"+t("date")(new Date(l+864e5*(a.x-6)),"MM-dd")+"</strong>"+a.series.map(function(a,e){return'<br /><span style="font-weight: bold; color:'+["#31b0d5","#4bd396"][e]+';">'+a.labelHTML+"</span> "+a.yHTML}).join("")},u=function(a){return function(e){return'<div class="legend-content"><div class="legend-wrap"><div class="legend-center">'+a(e)+"</div></div></div>"}},c={strokeWidth:2,pointSize:6,xRangePad:1,drawPoints:!0,axisLabelFontSize:11,legend:"follow",includeZero:!0,axes:{x:{axisLineColor:"#fff",ticker:function(a,e,o){return[].concat(_toConsumableArray(Array(7))).map(function(a,e){return{v:e,label:t("date")(new Date(l+864e5*(e-6)),"MM-dd")}})}},y:{axisLineColor:"#fff",axisLabelWidth:20,valueRange:[0,null],ticker:function(a,e,t){var o=Math.max(Math.ceil(e/6),1),n=Math.ceil((e+1)/o),r=[].concat(_toConsumableArray(Array(n+1))).map(function(a,e){return e*o}).map(function(a){return{v:a,label:""+a}});return console.log(r),r}}},legendFormatter:u(d)};o.SimplePromise.all([n.get("build-chart"),r.project]).then(function(a){var e=_slicedToArray(a,2),t=e[0],o=e[1];t.updateData([].concat(_toConsumableArray(Array(7))).map(function(a,e){return[e,o.action.build.auto[e],o.action.build.manual[e]]})),t.updateOptions(angular.copy(c))}),o.SimplePromise.all([n.get("deploy-chart"),r.deployment]).then(function(a){var e=_slicedToArray(a,2),t=e[0],o=e[1],n=o.action.deploy.online_detail;t.updateData([].concat(_toConsumableArray(Array(7))).map(function(a,e){return[e,o.action.deploy.auto[e],o.action.deploy.online[e]]})),t.updateOptions(angular.merge({},c,{legendFormatter:u(function(a){return null==a.x?"":d(a)+'<div class="detail-count">      　　启动 '+(n[a.x]||{}).start+"<br />　　升级 "+(n[a.x]||{}).update+"<br />　　回滚 "+(n[a.x]||{}).rollback+"<br />　　扩容 "+(n[a.x]||{}).scale_up+"<br />　　缩容 "+(n[a.x]||{}).scale_down+"</div>"})}))})}])}(angular.module("LunarApp"));