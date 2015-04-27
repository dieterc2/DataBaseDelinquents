/* Ka 11/7/2014 */
/* smooth scrolling for scroll to top */
$('.scroll-top').click(function(){
  $('body,html').animate({scrollTop:0},1000);
})
/* smooth scrolling for scroll down */
$('.scroll-down').click(function(){
  $('body,html').animate({scrollTop:$(window).scrollTop()+800},1000);
})

/* highlight the top nav as scrolling occurs */
$('body').scrollspy({ target: '#navbar' })

/* Enter data of apps (database?)here*/
var generateData = function(){
	/*var arr = [];
  var appWords = ["Pandora","Terribleapp","Bestapp"]
  var letterWords = ["Google play","Chrome Web store","Apple Store"]
  for (var i=1;i<50;i++){
    var appName = appWords[Math.floor(Math.random()*appWords.length)];
    var developers = letterWords[Math.floor(Math.random()*letterWords.length)];
    arr.push({"appName":appName+i,"developers":developers,"description":"Description of item #"+i,"field3":"link","field4": "USD $"+i,"field5": i +"/25 Stars"});
  }*/
	
   /*$http.get("http://localhost:8080/AppList/AppDAO")
  .success(function(response) {arr = response;});*/
  
    var xmlHttp = null;
    xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "http://localhost:8080/AppList/AppDAO", false );
    xmlHttp.send( null );

    return JSON.parse(xmlHttp.responseText);
    /*return arr;*/
}

var sortingOrder = 'name'; //default sort

function initApp($scope, $filter, $http) {
 
  // init
  $scope.sortingOrder = sortingOrder;
  $scope.reverse = false;
  $scope.filteredItems = [];
  $scope.groupedItems = [];
  $scope.itemsPerPage = 15;
  $scope.pagedItems = [];
  $scope.currentPage = 0;
  $scope.items = generateData();
  
  var searchMatch = function (haystack, needle) {
    if (!needle) {
      return true;
    }
    return haystack.toLowerCase().indexOf(needle.toLowerCase()) !== -1;
  };
  
  // init the filtered items
  $scope.search = function () {
    $scope.filteredItems = $filter('filter')($scope.items, function (item) {
      for(var attr in item) {
        if (searchMatch(item[attr], $scope.query))
          return true;
      }
      return false;
    });
    // take care of the sorting order
    if ($scope.sortingOrder !== '') {
      $scope.filteredItems = $filter('orderBy')($scope.filteredItems, $scope.sortingOrder, $scope.reverse);
    }
    $scope.currentPage = 0;
    // now group by pages
    $scope.groupToPages();
  };
  
  // calculate page in place
  $scope.groupToPages = function () {
    $scope.pagedItems = [];
    
    for (var i = 0; i < $scope.filteredItems.length; i++) {
      if (i % $scope.itemsPerPage === 0) {
        $scope.pagedItems[Math.floor(i / $scope.itemsPerPage)] = [ $scope.filteredItems[i] ];
      } else {
        $scope.pagedItems[Math.floor(i / $scope.itemsPerPage)].push($scope.filteredItems[i]);
      }
    }
  };
  
  $scope.range = function (start, end) {
    var ret = [];
    if (!end) {
      end = start;
      start = 0;
    }
    for (var i = start; i < end; i++) {
      ret.push(i);
    }
    return ret;
  };
  
  $scope.prevPage = function () {
    if ($scope.currentPage > 0) {
      $scope.currentPage--;
    }
  };
  
  $scope.nextPage = function () {
    if ($scope.currentPage < $scope.pagedItems.length - 1) {
      $scope.currentPage++;
    }
  };
  
  $scope.setPage = function () {
    $scope.currentPage = this.n;
  };
  
  // functions have been describe process the data for display
  $scope.search();
  
  // change sorting order
  $scope.sort_by = function(newSortingOrder) {
    if ($scope.sortingOrder == newSortingOrder)
      $scope.reverse = !$scope.reverse;
    
    $scope.sortingOrder = newSortingOrder;
  };

};

initApp.$inject = ['$scope', '$filter'];

$(document).ready(function() {});
        

        

(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
ga('create', 'UA-40413119-1', 'bootply.com');
ga('send', 'pageview');