var firstSeatLabel = 1;
//var oldJQuery = jQuery.noConflict(true), newJQuery = jQuery;
var jQuery_3_2_1 = $.noConflict(true);
var sc;
var bookedSeats;
var bookingMode = 1;

jQuery_3_2_1(document).ready(function () {
    displaySeats(bookedSeats);
});

function displaySeats(bookedSeats) {
    var $cart = jQuery_3_2_1('#selected-seats');
    var $counter = jQuery_3_2_1('#counter');
    var $total = jQuery_3_2_1('#total');
			
    sc = jQuery_3_2_1('#seat-map').seatCharts({
        map: ['e__ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee___'],
        seats: {
            f: {
                price   : 100,
                classes : 'first-class', //your custom CSS class
                category: 'First Class'
            },
            e: {
                price   : 130.00,
                classes : 'economy-class', //your custom CSS class
                category: ''
            }     
        },
        naming : {
            top : false,
            getLabel : function (character, row, column) {
                return firstSeatLabel++;
            },
        },
        legend : {
            node : jQuery_3_2_1('#legend'),
            items : [
                // [ 'f', 'available',   'First Class' ],
                [ 'e', 'available',   'Free'],
                [ 'f', 'unavailable', 'Booked']
                // ['e', 'Selected', 'Selected'],
            ],         
        },
		
		click: function () {
			if (this.status() == 'available') {
				if (bookingMode === 1) { // if in single booking mode
					$cart.empty();
					sc.find('selected').status('available'); // Free all selected seats
				}
							
				//let's create a new <li> which we'll add to the cart items
				jQuery_3_2_1('<li>'+this.data().category+' Seat # '+this.settings.label+': <b>$'+this.data().price+'</b> <a href="#" class="cancel-cart-item">[cancel]</a></li>')
						.attr('id', 'cart-item-'+this.settings.id)
						.data('seatId', this.settings.id)
						.appendTo($cart);
          
				/*
				* Lets up<a href="https://www.jqueryscript.net/time-clock/">date</a> the counter and total
				*
				* .find function will not find the current seat, because it will change its stauts only after return
				* 'selected'. This is why we have to add 1 to the length and the current seat price to the total.
				*/
				if (bookingMode === 1) { // If in single booking mode
					$counter.text(1);
					$total.text(this.data().price);
				}
				else {
					$counter.text(sc.find('selected').length+1);
					$total.text(recalculateTotal(sc)+this.data().price);
  				}
  				postBookingData();
  				sendData('selected_seat:' + this.settings.label);
				return 'selected';
			} 
			else if (this.status() === 'selected') {
				//update the counter
				if (bookingMode === 1) { // If in single booking mode
					$counter.text(0);
					$total.text(0);
				}
				else {
					$counter.text(sc.find('selected').length-1);
					//and total
					$total.text(recalculateTotal(sc)-this.data().price);
				}
				//remove the item from our cart				
				jQuery_3_2_1('#cart-item-'+this.settings.id).remove();

				//seat has been vacated
				// postBookingData();
				sendData('unselected_seat:' + this.settings.label);
				return 'available';
			} 
			else if (this.status() == 'unavailable') {
				//seat has been already booked
				return 'unavailable';
			} 
			else {
				return this.style();
			}
		}
		
	});

	//this will handle "[cancel]" link clicks
	jQuery_3_2_1('#selected-seats').on('click', '.cancel-cart-item', function () {
		//let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
		sc.get(jQuery_3_2_1(this).parents('li:first').data('seatId')).click();
	});

	//let's pretend some seats have already been booked
	//sc.get(['1_2', '4_1', '7_1', '7_2']).status('unavailable');

	//updateOccupiedSeats(sc, bookedSeats);
}

function recalculateTotal(sc) {
	if (sc === undefined) {
		alert('sc is undefined!');
	}

	var total = 0;

	//basically find every selected seat and sum its price
	sc.find('selected').each(function () {
		total += this.data().price;
	});
  
	return total;
}
		
function updateSeats(jsonObj) {
	var coordinates = jsonObj.coordinates;
	var action = jsonObj.action;
	sc.get(coordinates).status(action === 'reserve' ? 'unavailable' : 'available');
}

function freeSeats(seats) {
	var res = JSON.stringify(seats).replace(/ /g, '');
	var parseRes = JSON.parse(res);
	sc.get(parseRes).status('available');
}

function setTripPrice(tripPrice) { alert(tripPrice);
	if (tripPrice === undefined) {
		return;
	}
	this.data().price = tripPrice;
}