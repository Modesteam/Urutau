/** 
 * This script controls some functions into 
 * page of projects.
 * 
 */
$(document).ready(function() {
	/*
	 * invokes modal to create requirement
	 */
	$(".link-create-r-modal").click(function() {

		/* Link of page that call an GET method. 
		 * Example: 
		 *	 link - requirement/generic
		 *	 method on Requirement: public Long generic(Long projectID)
		 */
		requirementFormUrl = $(this).attr("href") + "/" +page.projectID;
		
		$.ajax({
		     url: requirementFormUrl,
		     type:"GET",
		     success:function(result) {
		    	 $("#r-form").html(result);
	     }});
	});
	
	$("#link-show-kanban").click(function() {
		
		event.preventDefault();
		
		// children is <a> tag
		kanbanUrl = $(this).children().attr("href");
		
		$.ajax({
			url: kanbanUrl,
			type: "GET",
			success: function(result) {
				$(".panel-target").html(result);
			}
		})
	});
	
	/*
	 * Delete requirement and reload page
	 */
	$(".delete-req").click(function() {
		
		requirementID = $(this).attr("id");
		
		$.ajax({
		     url: 'requirement/'+requirementID,
		     type : "POST",
		     data : {_method : "DELETE"},
		     success: function() {
		    	 location.reload();
		     }
		});
	});
	
	
});

/**
 * Used by next and previous functions, 
 * get an page through get request.
 */
function paginate() {
	var requirements = $(".requirements");
	
	currentPaginate = page.projectID + "/paginate/" + page.number;
	
	$.ajax({
	     url: currentPaginate,
	     type:"GET",
	     success:function(result){
	    	 requirements.html(result);
     }});
}

/**
 * Get by ajax the next page of requirements
 */
function next() {
	page.number += 1;
	
	paginate();
}

/** 
 * Get by ajax the previous page of requirements
 */
function previous() {
	page.number -= 1;
	
	paginate();
}