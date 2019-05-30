
/**
 * DHTML date validation script for dd/mm/yyyy. Courtesy of SmartWebby.com (http://www.smartwebby.com/dhtml/)
 */
// Declaring valid date character, minimum year and maximum year
var dtCh= "/";

var minYear=1947;
var maxYear=2100;

function isInteger(s){
    for (var i = 0; i < s.length; i=i+1)
    {   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) 
        {
        return false;
        }
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag)
{
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (var i = 0; i < s.length; i=i+1){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) 
        {
        returnString += c;
        }
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 === 0) && ( (!(year % 100 === 0)) || (year % 400 === 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i=i+1) {
		this[i] = 31;
		if (i==4 || i==6 || i==9 || i==11) 
		{
		this[i] = 30;
		}
		if (i==2) 
		{
		this[i] = 29;
		}
   } 
   return this;
}

function isDate(dtStr)
{
	if(dtStr.length > 0)
	{	
		
		var daysInMonth = DaysArray(12);
		var pos1=dtStr.indexOf(dtCh);
		var pos2=dtStr.indexOf(dtCh,pos1+1);
		var strDay=dtStr.substring(0,pos1);
		var strMonth=dtStr.substring(pos1+1,pos2);
		var strYear=dtStr.substring(pos2+1);
		strYr=strYear;
		var date1=new Date();
		maxYear=date1.getFullYear();
		//swal(strYr);
		if (strDay.charAt(0)=="0" && strDay.length>1) 
		{
		strDay=strDay.substring(1);
		}
		if (strMonth.charAt(0)=="0" && strMonth.length>1) { strMonth=strMonth.substring(1); }
		for (var i = 1; i <= 3; i=i+1) {
			if (strYr.charAt(0)=="0" && strYr.length>1)  { strYr=strYr.substring(1); }
		}
		
		month=parseInt(strMonth);
		day=parseInt(strDay);
		year=parseInt(strYr);
		if (pos1==-1 || pos2==-1){
			swal("The date format should be : dd/mm/yyyy");
			return false;
		}
		if (strMonth.length<1 || month<1 || month>12){
			swal("Please enter a valid month");
			return false;
		}
		if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
			swal("Please enter a valid day");
			return false;
		}
		if (strYear.length != 4 || year===0 || year<minYear || year>maxYear){
			swal("Please enter a valid 4 digit year between "+minYear+" and "+maxYear);
			return false;
		}
		if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))===false)
		{
			swal("Please enter a valid date");
			return false;
		}
	}
	return true;
}

function isValidDate(data)
{
	//var dt=document.frmSample.txtDate
	if(data.value.length>0)
	{	
		var date1=new Date();
	
		var day=date1.getDate();
		if(day<10)
			day="0"+day;
		var month=date1.getMonth()+1;
		if(month<10)
			month="0"+month;
		var date3 = day+"/"+month+"/"+date1.getFullYear();
		//getDateObject(date3,"/");
		//getDateObject(data.value,"/");
			//swal("data "+date3);
			if (isDate(data.value)===false)
		{
			data.value="";		
			//data.focus();
			return false;
		}
			else if (!dateComparison(date3,data.value))
			{
				
				swal("Please Select Less Than or Equal to Todays Date");
				data.value="";
				return false;
			}
	}
	 
    return true;
}
function allowFutureDate(data)
{
	//var dt=document.frmSample.txtDate
	
	var date1=new Date();

	var day=date1.getDate();
	if(day<10)
		day="0"+day;
	var month=date1.getMonth()+1;
	if(month<10)
		month="0"+month;
	var date3 = day+"/"+month+"/"+date1.getFullYear();
	//getDateObject(date3,"/");
	//getDateObject(data.value,"/");
		//swal("data "+date3);
		if (isDate(data.value)===false)
	{
		data.value="";		
		//data.focus();
		return false;
	}
		/*else if (!dateComparison(date3,data.value))
		{
			swal("Please Select Less Than or Equal to Todays Date");
			data.value="";
			return false;
		}*/
	 
    return true;
}

function allowOnlyFutureDate(data)
{
	//var dt=document.frmSample.txtDate
	if(data.value.length>0)
	{	
		var date1=new Date();
	
		var day=date1.getDate();
		if(day<10)
			day="0"+day;
		var month=date1.getMonth()+1;
		if(month<10)
			month="0"+month;
		var date3 = day+"/"+month+"/"+date1.getFullYear();
		//getDateObject(date3,"/");
		//getDateObject(data.value,"/");
			//swal("data "+date3);
			if (isDate(data.value)===false)
		{
			data.value="";		
			//data.focus();
			return false;
		}
			else if (dateComparison(date3,data.value))
			{
				swal("Please Select Greater Than or Equal to Todays Date");
				data.value="";
				return false;
			}
	}
	 
    return true;
}

function getDateObject(dateString,dateSeperator)
{
	//This function return a date object after accepting 
	//a date string ans dateseparator as arguments
	/*var curValue=dateString;
	var sepChar=dateSeperator;
	var curPos=0;
	var cDate,cMonth,cYear;

	//extract day portion
	curPos=dateString.indexOf(sepChar);
	cDate=dateString.substring(0,curPos);
	
	//extract month portion				
	endPos=dateString.indexOf(sepChar,curPos+1);			
	cMonth=dateString.substring(curPos+1,endPos);

	//extract year portion				
	curPos=endPos;
	endPos=curPos+5;			
	cYear=curValue.substring(curPos+1,endPos);
	
	//Create Date Object
	dtObject=new Date(cYear,cMonth,cDate);	*/
	//swal("inside function");
	//swal("date "+dateString);
   var dt1   = parseInt(dateString.substring(0,2),10);
   var mon1  = parseInt(dateString.substring(3,5),10);
   var yr1   = parseInt(dateString.substring(6,10),10);
   
   if (parseInt(mon1)>0)
   {
   	mon1=parseInt(mon1)-1;
   }
   var date1 = new Date(yr1, mon1, dt1);
   
	return date1;
}
function dateComparison(date1,date2)
{
	dt1=getDateObject(date1,"/");
	dt2=getDateObject(date2,"/");
	
	if(dt1>=dt2)
		return true;
	else
		return false;
}

function buildDate(data)
{
	if (data.value.length==2)
	{
		data.value=data.value+"/";
	}
	else if (data.value.length==5)
	{
		data.value=data.value+"/";
	}
}
