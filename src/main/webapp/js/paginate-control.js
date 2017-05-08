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