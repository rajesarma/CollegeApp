function isAlphaNumeric(e)
{ // Alphanumeric only
	var k;
	document.all ? k=e.keycode : k=e.which;
	return((k>47 && k<58)||(k>64 && k<91)||(k>96 && k<123)||k==0);
}

function lettercharacter(e)
{
	var key;
	var keychar;
	if (window.event)
		key = window.event.keyCode;
	else if (e)
		key = e.which;
	else
	{
		window.alert("enter only characters");
		return false;
	}
	keychar = String.fromCharCode(key);
	keychar = keychar.toLowerCase();

	// control keys
	if ((key==0) || (key==8) || (key==9) || (key==13) || (key==27) || (key==127))
		return true;

	// alphas and numbers
	else if ((("0123456789.").indexOf(keychar) > -1))
		return false;
	else
		return true;
}

function checkmail(e)
{
	if (trim(e.value)=='')
	{
		return false;
	}
	else
	{
		var returnval=emailfilter.test(e.value);
		if (returnval==false){
			alert("Please enter a valid email address.");
			e.value="";
			e.select();
		}
		return returnval;
	}
}

function checkNumeric(data)
{
	var tempValue=data.value;

	if (isNaN(data.value))
		{
			alert("Enter Numerics Only");
			data.value="";
		}
}
function checkNumericWithOutZero(data)
{
	var tempValue=data.value;

	if (isNaN(data.value) || parseInt(data.value)===0)
		{
			alert("Enter Numerics Only and It should not be zero");
			data.value=tempValue.substr(0,(parseInt(tempValue.length)-1));
		}
}
function displayMsg(msg,url)
{
	jConfirm(msg, 'FMS', function(r) {
							if(r)
								window.location.href=url;
							}
					);
	//jAlert(""+msg);
}

function intOnly(i) 
{
	if(i.value.length>0) 
	{ 
		i.value = i.value.replace(/[^\d]+/g, ''); 
	}
}
function validChar(i) {
	if(i.value.length>0)
	{
		i.value = i.value.replace(/[^\s\dA-Za-z.\/-]+/g, '');
	}
}

function charOnly(i) {
	if(i.value.length>0)
	{
		i.value = i.value.replace(/[^\sA-Za-z.\/-]+/g, '');
	}
}

function validNumber(i)
{
	if(i.value.length>0)
	{
		i.value = i.value.replace(/[^\d\.]+/g, '');
	}
}

function validCharWithUnderScore(i)
{
	if(i.value.length>0)
	{
		i.value = i.value.replace(/[^\s\dA-Za-z._=''\/-]+/g, '');
	}
}

function validCharWithSymbols(i)
{
	if(i.value.length>0)
	{
		i.value = i.value.replace(/[^\s\dA-Za-z0-9.%,()]+/g, '');
	}

}

	/*function validNumber(i) {

	if(i.value.length>0)
	{

	i.value = i.value.replace(/[^\d\.]+/g, '');

	}

	}*/

function disableControl(control1,control2)
{
	var control1Value=document.getElementById(control1).value;
	if (isNaN(control1Value))
	{
		alert("Enter numerics only");
		document.getElementById(control1).value="";
	}
	else
	{
	if (control1Value!="")
		document.getElementById(control2).disabled=true;
	else
		document.getElementById(control2).disabled=false;
	}
}
function disableTextBox(control1,control2)
{
	var control1Value=document.getElementById(control1);
	if (isNaN(control1Value.value))
	{
		alert("Enter numerics only");
		document.getElementById(control1).value="";
	}
	else
	{
	if (control1Value.checked)
		document.getElementById(control2).disabled=true;
	else
		document.getElementById(control2).disabled=false;
	}
}
			
function LTrim( value ) {
	var re = /\s*((\S+\s*)*)/;
	return value.replace(re, "$1");
}

// Removes ending whitespaces
function RTrim( value ) {
	var re = /((\s*\S+)*)\s*/;
	return value.replace(re, "$1");
}

// Removes leading and ending whitespaces
function trim( value ) {
	return LTrim(RTrim(value));
}

/*function OpenWindow(url)
{

    closePopup("popup");

	var features="width=110,height=950,menubar=no,status=no,location=no,toolbar=no,scrollbars=yes ,top=10,left=50";
	popup =window.open(url,"_blank",features);
}

function closePopup(popupHandle){
	if (window[popupHandle] && !window[popupHandle].closed){
	window[popupHandle].close();
	}
}
*/
function PrintThisPage()
{
	var sOption="toolbar=yes,location=no,directories=yes,menubar=yes,";
	sOption+="scrollbars=yes,left=100,top=25";
	var sWinHTML = document.getElementById('PrintContent').innerHTML;	
	var sHeadingHTML = '';
	if(document.getElementById('headingContent')!=null)
	{
		sHeadingHTML = document.getElementById('headingContent').innerHTML;
	}
	var winprint=window.open("","",sOption);
	winprint.document.open();
	winprint.document.write('<html><LINK href=/apportal/appstyles.css rel=Stylesheet><body>');
	winprint.document.write('<table align=center>');
	if(sHeadingHTML!='')
	{
		winprint.document.write('<tr><td>' + sHeadingHTML + '</td></tr>');
	}
	winprint.document.write('<tr><td>' + sWinHTML + '</td></tr>');
	winprint.document.write('</table>');
	winprint.document.write('</body></html>');				
	winprint.document.close();
	winprint.focus();
	winprint.print();
	winprint.close();
}

function letternumber(e)
{
		var key;
		var keychar;

		if (window.event)
   			key = window.event.keyCode;
		else if (e)
   			key = e.which;
		else
   			return true;

		keychar = String.fromCharCode(key);
		keychar = keychar.toLowerCase();

// control keys
		if ((key==0) || (key==8) || (key==9) || (key==13) || (key==27) || (key==127))
   			return true;

// alphas and numbers
		else if ((("0123456789.").indexOf(keychar) > -1))
   			return true;
		else
   			return false;
}

function amountCheck(e,id)
{

	var key;
	var keychar;

	if (window.event)
	key = window.event.keyCode;
	else if (e)
	key = e.which;
	else
	return true;

	keychar = String.fromCharCode(key);
	keychar = keychar.toLowerCase();

	// control keys
		if ((key==0) || (key==8) || (key==9) || (key==13) || (key==27) || (key==127))
		return true;

	// alphas and numbers
		else if ((("0123456789.").indexOf(keychar) > -1))
		{
			if(keychar==".")
			{
				if(id.value.split(".").length>1)
				{
					return false;
				}
			}
			if(id.value.split(".").length==2)
			{
				if(id.value.split(".")[1].length>1)
				{
					return false;
				}
			}
			return true;
		}
		else
			return false;

}

function quantityCheck(e,id)	// Pattern like 123456.123.456
{
	
	var key;
	var keychar;

	if (window.event)
		key = window.event.keyCode;
	else if (e)
		key = e.which;
	else
		return true;

	keychar = String.fromCharCode(key);
	keychar = keychar.toLowerCase();
	
	// control keys
		if ((key==0) || (key==8) || (key==9) || (key==13) || (key==27) || (key==127))
			return true;

	// alphas and numbers
		else if ((("0123456789.").indexOf(keychar) > -1))
		{
				if(keychar==".")
				{
					if(id.value.split(".").length>1)
					{
						return false;
					}
				}
				if(id.value.split(".").length==2)
				{	
					if(id.value.split(".")[1].length>5)
					{
						return false;
					}
				}
				return true;
		}
		else
				return false;
	
}

function AppendZeros(text)
{
	var result;
	if (text.length>0)
	{
		switch (text.length)
		{
			case 1:
			{
				result = text + "00";
				break;
			}
			case 2:
			{
				result = text + "0";
				break;
			}
			default :
			{
				result = text.substring(0, 3);
				break;
			}
		 }
	 }
	 return result;
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

function dateComparisonforEmployeeRegistration(date1,date2)
{
	dt1=getDateObject(date1,"/");
	dt2=getDateObject(date2,"/");
	
	if(dt1>=dt2)
		return true;
	else
		return false;
}
function letterNumberForCodes(e)
{
		var key;
		var keychar;

		if (window.event)
   			key = window.event.keyCode;
		else if (e)
   			key = e.which;
		else
   			return true;

		keychar = String.fromCharCode(key);
		keychar = keychar.toLowerCase();

// control keys
		if ((key==0) || (key==8) || (key==9) || (key==13) || (key==27) || (key==127))
   			return true;

// alphas and numbers
		else if ((("0123456789.").indexOf(keychar) > -1))
   			return true;
		else
   			return false;
}
function checkEmail(data)
{
	var list=document.getElementById("email").value.split(',');
	//alert(list.length);
	for (y=0; y<list.length; y++) 
	{
		//alert('Validate this email:'+list[y]);
		var str=list[y];
		var at="@";
		var dot=".";
		var lat=str.indexOf(at);
		var lstr=str.length;
		var ldot=str.indexOf(dot);
		if (str.indexOf(at)==-1){
		   alert("Invalid E-mail ID "+list[y]);
		   document.getElementById("email").value="";
		   return false;
		}else if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   alert("Invalid E-mail ID "+list[y]);
		   document.getElementById("email").value="";
		   return false;
		}else if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		    alert("Invalid E-mail ID "+list[y]);
		    document.getElementById("email").value="";
		    return false;
		}else if (str.indexOf(at,(lat+1))!=-1){
		    alert("Invalid E-mail ID "+list[y]);
		    document.getElementById("email").value="";
		    return false;
		 }else if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    alert("Invalid E-mail ID "+list[y]);
		    document.getElementById("email").value="";
		    return false;
		 }else if (str.indexOf(dot,(lat+2))==-1){
		    alert("Invalid E-mail ID "+list[y]);
		    document.getElementById("email").value="";
		    return false;
		 }else if (str.indexOf(" ")!=-1){
		   alert("Invalid E-mail ID "+list[y]);
		   document.getElementById("email").value="";
		   return false;
		 }else
		 {
		 	return true;
		 }
	}
}

function letternumberforDays(e)
{
		var key;
		var keychar;

		if (window.event)
   			key = window.event.keyCode;
		else if (e)
   			key = e.which;
		else
   			return true;

		keychar = String.fromCharCode(key);
		keychar = keychar.toLowerCase();

// control keys
		if ((key==0) || (key==8) || (key==9) || (key==13) || (key==27) || (key==127))
   			return true;

// alphas and numbers
		else if ((("0123456789").indexOf(keychar) > -1))
   			return true;
		else
   			return false;
}

function cancelTransaction(url)
{
	window.location.href=url;
}
function checkColumn(data)
{
	var flag="false";
	for (var row=0;row<data.value.length;row++)
	{
		if ( (data.value[row]>="a" && data.value[row]<="z") || (data.value[row]>="A" && data.value[row]<="Z") || data.value[row]==" ")
		{
			flag="true";
		}
		else
		{
			flag="false";
		}
	}
	if (flag=="false")
	{
		alert("Please enter character only");
			data.value="";
			return false;
	}
}

function checkIfsc(data)
{
	//alert(" IFSC : "+data.value);
	var reg = new RegExp("[A-Z|a-z]{4}[0-9]{7}$");
	if(!(reg.test(data.value)))
	{
		alert("IFSC Code is not Valid");
		data.value="";
		return false;
	}	
}

function checkMobile(data)
{
	//alert(" IFSC : "+data.value);
	var sum=0;
	var value=data.value;
	while(value>0)
    {
      sum=sum+value%10;
      value=Math.floor(value/10);
    }
	if(sum==0)
	{
		alert("Please enter valid number");
		data.value="";
		return false;
	}
}
		
