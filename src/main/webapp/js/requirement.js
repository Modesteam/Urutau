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