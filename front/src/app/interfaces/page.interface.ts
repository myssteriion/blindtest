/**
 * Page.
 */
export interface Page<T> {

	// content and size
	content: T[],
	numberOfElements: number,

	// if 1st and/or last page
	first: boolean,
	last: boolean,

	// current and total number page
	number: number,
	totalPages: number,

	// total element (for all page)
	totalElements: number,

	// maximum number element per page
	size: number,

	// page is empty
	empty: boolean
}