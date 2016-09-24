/**
 * Used by next and previous functions, get an page through get request.
 */
function paginate() {
  var requirements = $(".requirements");

  var currentPaginate = page.projectID + "/paginate/" + page.number;

  $.ajax({
    url : currentPaginate,
    type : "GET",
    success : function(result) {
      requirements.html(result);
    }
  });
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
