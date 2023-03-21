window.addEventListener('load', function(){
 	
 //========================Sales ajax 받기
 	totalCount = 0;
 	cnt=1;
	var chartdata;
	
	$.ajax({
		type : "post",
		url :"saleslist",
		async : false,
		dataType:"json",
	 
		success : function(list){
			//console.log(list)
			chartdata = list;			
			totalCount = list.length-cnt;
			lastNum = list.length -cnt;
			//console.log(lastNum)
			const sales = list[lastNum].sales.toLocaleString();
		
			str = '<div class="ymdata">';		
			str += '<span>' + list[lastNum].year +"."+ list[lastNum].month+ '</span>';		
			str += '</div>';
			$('.sales-head').append(str);
			
			str1 ='<div class="ymdata">';
			str1 += '<div>' + sales +"원"+ '</div>';
			str1 += '</div>';						
			
			$('.sales-body').append(str1);
			
		},	
		error : function() {
			alert('error');
		}
	}) //ajax end


	//차트만들기
	var chart = document.getElementById("myChart").getContext('2d');
	var data = chartdata;
	var i = totalCount;
	var k = 0; var s = 0;

	arrM = new Array(); //chart x축 (월표시)
	for(j = 11; j>=0 ; j--){		
		arrM[k] = data[i-j].month+"월";
		k++;
	}     

	arrS = new Array(); //chart 그래프 (원표시)
	for(j = 11; j>=0 ; j--){
		arrS[s] = data[i-j].sales;
		s++;		
	}   

	arrB = new Array(); //backgroundColor
	for(j = 0; j<=11; j++){
		arrB[j] = 'rgba(0, 0, 0)';
		
	}
	var myChart = new Chart(chart, {
	    type: 'line',
	    data: {
	        labels: arrM ,
	        datasets: [{
	            label: '매출', //차트이름
	            fill:false, //채우기
	            data: arrS,
	            backgroundColor: arrB,
	            borderColor: ['rgba(0, 0, 0)'],
	            borderWidth: 1
	        }]
	    },
	    options: {
	        maintainAspectRatio: false, // default value. false일 경우 포함된 div의 크기에 맞춰서 그려짐.
	        scales:{
				yAxes: [{//y축콤마
					ticks:{
						beginAtZero: true,
						callback: function(value, index) {
							return value.toLocaleString("ko-KR");
						}
					}
				}]
			},
			tooltips:{
				callbacks:{
					label:function(tooltipItem, data){//그래프 콤마
						return tooltipItem.yLabel.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "원";
					}
				},
			},	    
			
	    },
			
	});

//데이터 이전
	document.querySelector(".prev").addEventListener("click", function(e){

		if(totalCount - cnt < 0){
			alert('마지막 매출데이터입니다.')		
		} else{
		$("div").remove('.ymdata');	
		cnt++;
		getlist();
		}
	});
	
	//데이터 이후
	document.querySelector(".next").addEventListener("click", function(e){

		if(cnt <= 1){
			alert('최근 매출 데이터입니다.')
		} else{
		$("div").remove('.ymdata');
		cnt--;
		getlist();
		}
	
	});	
	
//=====================================recipe ajax 받기

	$.ajax({
		type:"post",
		url:"recipelist",
		async: false,
		dataType:"json",
		success : function(result){
		
		totalcnt = result.length-1 //총 갯수 저장하기
		
		const rnum = Math.floor(Math.random()*totalcnt+1);
		//console.log(rnum)	
		
		str = '<div class="rdata">';
		if(result[rnum].rimg == null){
		str += '<img class="today-img" src="/img/noimg.jpg"/>';
		} else{
		str += '<img class="today-img" src="/view/'+result[rnum].rimg + '"></img>';
		}
		 
		str += '<div>' + result[rnum].rname +'</div>';
		str += '<div>' + result[rnum].rdesc +'</div>'; 
		str += '</div>';
		$('.today_recipe').append(str);
		},
		error:function(){
			alert('ajax레시피불러오기 실패')
		}
	})//recipe ajax end


	
}); //window.onload end


//좌우버튼 클릭시 sales데이터 다시받아서 뿌려주기
function getlist(){
		$.ajax({
		type : "post",
		url :"saleslist",
		async : false,
		dataType:"json",
	 
		success : function(list){
			//console.log(list)
			lastNum = list.length -cnt;
			//console.log(lastNum)
			const sales = list[lastNum].sales.toLocaleString();
			
			str = '<div class="ymdata">';		
			str += '<span>' + list[lastNum].year +"."+ list[lastNum].month+ '</span>';		
			str += '</div>';
			$('.sales-head').append(str);
			
			str1 ='<div class="ymdata">';
			str1 += '<div>' + sales +"원"+ '</div>';
			str1 += '</div>';						
			
			$('.sales-body').append(str1);
			
		},	
		error : function() {
			alert('error');
		}
	}) //ajax end
	
	
}
