(function (LunarApp) {
  'use strict';

  LunarApp.controller('publicImageDetailCtr', ['$scope', '$state', 'api', function ($scope, $state, api) {
    api.image.public.detail({ name: $state.params.name }).then(image => {
      $scope.image = image;
    });
  }]);

  LunarApp.controller('publicImageDockerfileCtr', ['$scope', 'api', function ($scope, api) {
    $scope.loading = true;
    api.network($scope.value.url)
      .catch(function () { return null; })
      .then(function (dockerfile) {
        $scope.value.dockerfile = dockerfile;
        $scope.loading = false;
      });
  }]);

  LunarApp.controller('publicImageDetailTagsCtr', ['$scope', '$state', 'api', 'dialog', function ($scope, $state, api, dialog) {
    $scope.showDockerfile = function (url) {
        dialog.common({
          title: '查看dockerfile',
          buttons: dialog.buttons.BUTTON_OK_ONLY,
          value: { url: url },
          controller: 'publicImageDockerfileCtr',
          template: `
            <codearea ng-show="!loading && value.dockerfile" language="dockerfile" ng-model="value.dockerfile" readonly="readonly" height="5,20"></codearea>
            <div ng-show="!loading && !value.dockerfile">暂无相关dockerfile信息</div>
          `,
          size: 600
        });
    };
  }]);

})(angular.module('LunarApp'));
