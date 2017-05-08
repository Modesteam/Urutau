/**
 * Used by next and previous functions, get an page through get request.
 */
function paginate() {
  // where the result is write
  var resultPlace = $(".members");

  /**
   * MemberController reply in DOMAIN/project/{projectId}/members/{page.number}
   * Where DOMAIN is root url
   */
  var currentPaginate = "../project/" + page.projectID + "/members/" + page.number;
  console.log("accessing " + currentPaginate);

  $.ajax({
    url : currentPaginate,
    type : "GET",
    success : function(result) {
      resultPlace.html(result);
    }
  });
}