/** 
 * This global variable sets a map to ajax method 
 * use the marked inputs to validation. 
 * Basically we have a server-side validate method like:
 * 
 * public void validate(String property) {
 * 
 * And a view with:
 * 
 * <input type="text" name="object.property">
 * 
 * So is needed pass this input value like 'property' to ajax method:
 * 
 * data : {name : 'some entry'};
 * 
 */
var COLLECTOR = (function() {

  // loads elements that needs validate in JSON
  var hashToValidate = {};
	
  hashToValidate.elements = {};
	
  hashToValidate.data = [];
	
  /*
   * It represents the relative path to current validation.
   * Initialized above, this should be set in page of form.
   */
  hashToValidate.serverSideValidatePath = null;
	
  /*
   * Save fields to verify
   */
  hashToValidate.validates = function(url, elements) {
	  console.log("Validates >>" + elements + "\nURL:"+url);
	  hashToValidate.serverSideValidatePath = url; 
	  hashToValidate.elements = elements;
  }
	
  // Update data with inputs to validates
  hashToValidate.update = function() {
		
    hashToValidate.data = [];
		
	var fields = hashToValidate.elements;
		
	for(var key in fields) {
	  var fieldToValidate = $('input[name="'+fields[key]+'"]');
	    
	  var parameterValue = fieldToValidate.val();
	    if (fieldToValidate.length) {
		  var data = {};
		  data[key] = parameterValue;
		  hashToValidate.data.push(data);
		} else {
		  alert("Invalid element to validation! Contact administrator...");
		  window.location.reload(true);
		}
	}
	
	console.log(JSON.stringify(hashToValidate.data));
  };
	
  // return data in JSON format
  hashToValidate.getJSON = function() {
    var ajaxData = {};
		
	$.each(hashToValidate.data, function(index, element) {
		$.each(element, function(param, value){
			ajaxData[param] = value;
		})
	});
		
	console.log(ajaxData);
		
	return ajaxData;
  };
	
  return {
    getDataInJSON: hashToValidate.getJSON,
	update : hashToValidate.update,
	validates: hashToValidate.validates,
	url : hashToValidate.serverSideValidatePath
  };
})(); 

/**
 * Throws error messages sented by server-side 
 */
function throwErrorMessages(data) {
  // [{'category':value , 'message':value}, ...others]
  $.each($.parseJSON(data), function(properties, values) {
    var category = null;
	$.each(values, function(property, value) {
	  if(category == null) {
	    category = value;
	  } else {
		// render error
		$('#' + category).show();
	    
		$('#' + category).html(value);
	    		
	    category = null;
	  }
	});
  });
}

$(document).ready(function() {
  $(".submit-create").click(function(ev) {
    // cancel submit
	ev.preventDefault();
		
	COLLECTOR.update();
		
	$.ajax({
		url: COLLECTOR.url,
		type: "POST",
		data: COLLECTOR.getDataInJSON(),
		// has some error
		success: function(result) {
			var data = JSON.stringify(result);
			console.log(data);

	    	throwErrorMessages(data);
		},
		// All is right, submit the form
		error: function() {
			// do something like load div
			$(".requirement-form").submit();
		}
	});
  });
});