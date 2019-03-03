/* globals Chart:false, feather:false */

(function () {
  'use strict'

  var url = $(".statistics").attr("data-url");
  
  feather.replace();

 
  
  var loadData=function(ctx){
	  $.ajax({
	       url : $(ctx).attr("data-url"),
	       type : 'GET',
	       dataType : 'json',
	       success : function(result){ 
	    	   var labels=[];
	    	   var data=[];
	    	   for(var i=0;i<result.length;i++) {
	    		   labels.push(result[i].date);
	    		   data.push(result[i].total);
	    	   }
	    	   
	    	   showChart(ctx,labels,data);
	       },

	       error : function(error){
	    	   console.log(error);
	       },

	    });
	   
  }
  
  var showChart=function(ctx,labels,data){
	  var myChart = new Chart(ctx, {
		    type: 'line',
		    data: {
		      labels: labels,
		      datasets: [{
		        data: data,
		        lineTension: 0,
		        backgroundColor: 'transparent',
		        borderColor: '#007bff',
		        borderWidth: 2,
		        pointBackgroundColor: '#007bff'
		      }]
		    },
		    options: {
		      scales: {
		        yAxes: [{
		          ticks: {
		            beginAtZero: false,
		            stepSize:1
		          }
		        }]
		      },
		      legend: {
		        display: false
		      }
		    }
		  })
  }
  
  var ctx1 = document.getElementById('chartY');   
  loadData(ctx1);
  var ctx2 = document.getElementById('chartMY');   
  loadData(ctx2);
  
}())