/**
 *  Post booking data as json to BookingDataServlet
 */

function postBookingData() {
	var optionTexts = [];
	$('#selected-seats').each(function() {
		console.log($(this).text());
		optionTexts.push($(this).text());
	});
	
	var $totalFare = $('#total').text();
	
	var url = 'http://' + window.location.host + '/natwende/bookingData';
	
	var jqxhr = $.post(url, JSON.stringify({bookedSeats: optionTexts, total: $totalFare}));
	
	jqxhr.done(function() {
		console.log("booking data posted successfully ...");
		window.location.replace('/trips/bookingConfirmation.xhtml');
	});
	
	jqxhr.fail(function(xhr, status, error) {
		console.log("error occurred while posting booking data ... " + xhr.toString());
		console.log("Status: " + status);
		console.log("Error: " + error.toString());
	});
	
	jqxhr.always(function() {
		console.log('finished posting booking data.');
	});
}

function getJSessionId() {
	var jsId = document.cookie.match('/JSESSIONID=[^;]+/');
	if (jsId === null) {
		jsId = document.loaction.href.match('/JSESSIONID=[^;]+/');
	}
	if (jsId !== null) {
		if (jsId instanceof Array) {
			jsId = jsId[0].substring(11);
		}
		else {
			jsId = jsId.substring(11);
		}
	}
	return jsId;
}