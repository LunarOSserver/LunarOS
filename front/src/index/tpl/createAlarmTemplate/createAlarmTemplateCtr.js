/*
 * @author ChandraLee
 */

(function (LunarApp, undefined) {
    'use strict';
    if (typeof LunarApp === 'undefined') return;
    LunarApp.controller('CreateAlarmTemplateCtr', ['$scope', '$LunarAlarm', 'dialog', '$state', function ($scope, $LunarAlarm, dialog, $state) {
        $scope.$emit('pageTitle', {
            title: '新建模板',
            descrition: '在这里您可以新建报警模板。',
            mod: 'monitor'
        });
        $scope.keywords = {
            hostgroup: ''
        };
        $scope.needValid = {
            valid: false
        };
        $scope.isLoading = false;
        $scope.alarmTemplateIns = $LunarAlarm.getInstance('AlarmTemplate');
        $scope.alarmTemplateIns.initHostGroupList();
        $scope.alarmTemplateIns.initGroupList();
        $scope.alarmTemplateIns.initDeployAndClusterList();
        $scope.config = $scope.alarmTemplateIns.config;
        $scope.toCreate = function () {
            $scope.isLoading = true;
            $scope.alarmTemplateIns.create().then(function () {
                dialog.alert('提示', '新建成功');
                $state.go('alarm.templates');
            }, function (res) {
                dialog.error('删除失败', res.data.resultMsg);
            }).finally(function () {
                $scope.isLoading = false;
            });
        };
    }]);
})(angular.module('LunarApp'));