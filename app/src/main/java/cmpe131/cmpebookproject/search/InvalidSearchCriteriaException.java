package cmpe131.cmpebookproject.search;

public class InvalidSearchCriteriaException extends RuntimeException {

    public enum SearchError {
        EMPTY("You must select at least one search criteria"),
        GENRE_CONFLICT("Your preferred and excluded genres overlap"),
        PCOUNT_CONFLICT("The maximum page count is below the minimum page count"),
        PCOUNT_NEGATIVE("You cannot have a negative page count"),
        PUBLISHYEAR_CONFLICT("The maximum publish year is below the minimum publish year"),
        PUBLISHYEAR_NEGATIVE("You cannot have a negative publish year");

        String errorMessage;

        SearchError(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public String toString() {
            return errorMessage;
        }
    }

    private SearchError searchError;

    public InvalidSearchCriteriaException (SearchError searchError) {
        super(searchError.toString());
        this.searchError = searchError;
    }

    public SearchError getSearchError() {
        return searchError;
    }
}
