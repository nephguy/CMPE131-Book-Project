package cmpe131.cmpebookproject.search;

import android.os.Parcel;
import android.os.Parcelable;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;

import java.util.ArrayList;
import java.util.Collections;

import cmpe131.cmpebookproject.book.Book;
import cmpe131.cmpebookproject.book.Genre;
import cmpe131.cmpebookproject.database.DbHelper;

public class SearchCriteria implements Parcelable {

    public static final int DEFAULT_MAX_RESULTS = 30;
    public static final float SIMILARITY_INDEX_MIN = 0.2f;

    private String title;
    private String author;
    private int publishYearFloor;
    private int publishYearCeiling;
    private int pcountFloor;
    private int pcountCeiling;
    private ArrayList<Genre> preferredGenres;
    private ArrayList<Genre> excludedGenres;
    private transient ArrayList<Book> searchSpace = DbHelper.getInstance().getAllBooks();
    private transient ArrayList<Book> searchResults;
    private transient int numResults = DEFAULT_MAX_RESULTS;

    public SearchCriteria () {
        this.title = "";
        this.author = "";
        this.publishYearFloor = 0;
        this.publishYearCeiling = 0;
        this.pcountFloor = 0;
        this.pcountCeiling = 0;
        this.preferredGenres = new ArrayList<>();
        this.excludedGenres = new ArrayList<>();
    }

    public SearchCriteria(String title, String author, int publishYearFloor, int publishYearCeiling, int pcountFloor, int pcountCeiling, ArrayList<Genre> preferredGenres, ArrayList<Genre> excludedGenres) {
        this.title = title;
        this.author = author;
        this.publishYearFloor = publishYearFloor;
        this.publishYearCeiling = publishYearCeiling;
        this.pcountFloor = pcountFloor;
        this.pcountCeiling = pcountCeiling;
        this.preferredGenres = preferredGenres;
        this.excludedGenres = excludedGenres;
        validateSearchCriteria();
    }

    public SearchCriteria(String title, String author, String publishYearFloor, String publishYearCeiling, String pcountFloor, String pcountCeiling, ArrayList<Genre> preferredGenres, ArrayList<Genre> excludedGenres) {
        this.title = title;
        this.author = author;
        this.publishYearFloor = intFromFieldString(publishYearFloor);
        this.publishYearCeiling = intFromFieldString(publishYearCeiling);
        this.pcountFloor = intFromFieldString(pcountFloor);
        this.pcountCeiling = intFromFieldString(pcountCeiling);
        this.preferredGenres = preferredGenres;
        this.excludedGenres = excludedGenres;
        validateSearchCriteria();
    }

    private int intFromFieldString(String fieldData) {
        // this is called a ternary operator if you guys were wondering
        return fieldData.equals("") ? 0 : Integer.valueOf(fieldData);
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getPublishYearFloor() { return publishYearFloor; }
    public void setPublishYearFloor(int publishYearFloor) { this.publishYearFloor = publishYearFloor; }
    public int getPublishYearCeiling() { return publishYearCeiling; }
    public void setPublishYearCeiling(int publishYearCeiling) { this.publishYearCeiling = publishYearCeiling; }
    public int getPcountFloor() { return pcountFloor; }
    public void setPcountFloor(int pcountFloor) { this.pcountFloor = pcountFloor; }
    public int getPcountCeiling() { return pcountCeiling; }
    public void setPcountCeiling(int pcountCeiling) { this.pcountCeiling = pcountCeiling; }
    public ArrayList<Genre> getPreferredGenres() { return preferredGenres; }
    public void setPreferredGenres(ArrayList<Genre> preferredGenres) { this.preferredGenres = preferredGenres; }
    public ArrayList<Genre> getExcludedGenres() { return excludedGenres; }
    public void setExcludedGenres(ArrayList<Genre> excludedGenres) { this.excludedGenres = excludedGenres; }
    public int getNumResults() { return numResults; }
    public void setNumResults(int numResults) { this.numResults = numResults; }
    public ArrayList<Book> getSearchSpace() { return searchSpace; }
    public void setSearchSpace(ArrayList<Book> searchSpace) { this.searchSpace = searchSpace; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SearchCriteria))
            return false;
        SearchCriteria other = (SearchCriteria) obj;

        // intentionally did not include searchResults and numResults
        boolean sameTitle = this.title.equals(other.title);
        boolean sameAuthor = this.author.equals(other.author);
        boolean samePublishYearLow = this.publishYearFloor == other.publishYearFloor;
        boolean samePublishYearHigh = this.publishYearCeiling == other.publishYearCeiling;
        boolean samePcountLow = this.pcountFloor == other.pcountFloor;
        boolean samePcountHigh = this.pcountCeiling == other.pcountCeiling;
        boolean sameInclGenres = this.preferredGenres.equals(other.preferredGenres);
        boolean sameExclGenres = this.excludedGenres.equals(other.excludedGenres);
        return (sameTitle && sameAuthor && samePublishYearLow && samePublishYearHigh && samePcountLow && samePcountHigh && sameInclGenres && sameExclGenres);
    }

    /** For checking if the search criteria are invalid **/
    private void validateSearchCriteria() {
        if (this.equals(new SearchCriteria()))
            throw new InvalidSearchCriteriaException(InvalidSearchCriteriaException.SearchError.EMPTY);
        if (isGenreConflict())
            throw new InvalidSearchCriteriaException(InvalidSearchCriteriaException.SearchError.GENRE_CONFLICT);
        if (pcountFloor < 0 || pcountCeiling < 0)
            throw new InvalidSearchCriteriaException(InvalidSearchCriteriaException.SearchError.PCOUNT_NEGATIVE);
        if (publishYearFloor < 0 || publishYearCeiling < 0)
            throw new InvalidSearchCriteriaException(InvalidSearchCriteriaException.SearchError.PUBLISHYEAR_NEGATIVE);
        if (limitConflict(pcountFloor,pcountCeiling))
            throw new InvalidSearchCriteriaException(InvalidSearchCriteriaException.SearchError.PCOUNT_CONFLICT);
        if (limitConflict(publishYearFloor,publishYearCeiling))
            throw new InvalidSearchCriteriaException(InvalidSearchCriteriaException.SearchError.PUBLISHYEAR_CONFLICT);
    }

    private boolean isGenreConflict() {
        for (Genre g : preferredGenres) {
            if (excludedGenres.contains(g)) {
                return true;
            }
        }
        return false;
    }

    private boolean limitConflict(int floor, int ceiling) {
        if (floor == 0 || ceiling == 0)     // values are not specified
            return false;
        return floor > ceiling;             // values do not conflict
    }


    /** For search algorithm **/
    private boolean inPcountLimits(int pcount) {
        return inLimits(pcount, pcountFloor, pcountCeiling);
    }

    private boolean inPublishYearLimits(int publishYear) {
        return inLimits(publishYear, publishYearFloor, publishYearCeiling);
    }

    private boolean inLimits(int value, int floor, int ceiling) {
        return (
                (floor == 0 || value >= floor)      // no floor specified, or value is above floor
                        &&                          // and
                (ceiling == 0 || value <= ceiling)  // no ceiling specified, or value is below ceiling
        );
    }

    /** The big boy method **/
    public ArrayList<Book> getSearchResults() {
        if (searchResults != null)
            return searchResults;
        searchResults = new ArrayList<>();

        // do easy quantitative exclusions first - page count and publish year limits
        // but first check if they are even necessary
        boolean skipSimpleSearch = (publishYearFloor == 0) && (publishYearCeiling == 0) && (pcountFloor == 0) && (pcountCeiling == 0);
        if (skipSimpleSearch) {
            searchResults = searchSpace;
        }
        else {
            for (Book b : searchSpace) {
                if (inPcountLimits(b.getNumPages()) && inPublishYearLimits(b.getYearPublished()))
                    searchResults.add(b);
            }
        }

        // do more subjective, time-consuming comparisons last, on a smaller dataset
        // but again first check if it is even necessary
        boolean skipComplexSearch = (preferredGenres.size() == 0) && (excludedGenres.size() == 0) && (title.equals("")) && (author.equals(""));
        if (!skipComplexSearch) {
            Book b;
            float genreSimilarity, authorSimilarity, titleSimilarity;
            StringMetric stringMetric = StringMetrics.damerauLevenshtein();
            ArrayList<Book> markedForRemoval = new ArrayList<>();
            for (int i = 0; i < searchResults.size(); i++) {
                b = searchResults.get(i);

                // genre match check
                if (excludedGenres.contains(b.getGenre())) {
                    markedForRemoval.add(b);
                    continue;
                }
                if (preferredGenres.size() == 0)
                    genreSimilarity = -1f;
                else if (preferredGenres.contains(b.getGenre()))
                    genreSimilarity = 1f;
                else
                    genreSimilarity = 0f;

                // author and title similarity check. uses a Damerau-Levenshtein similarity test
                // no specific reason - it just happened to work the best in my limited testing
                // see these links for reference
                // https://stackoverflow.com/questions/955110/similarity-string-comparison-in-java
                // https://github.com/Simmetrics/simmetrics
                if (author.equals(""))
                    authorSimilarity = -1f;
                else
                    authorSimilarity = stringMetric.compare(author, b.getAuthor());
                if (title.equals(""))
                    titleSimilarity = -1f;
                else
                    titleSimilarity = stringMetric.compare(title, b.getTitle());

                float similarityIndex = averageSimilarityIndices(genreSimilarity, authorSimilarity, titleSimilarity);
                //System.out.println(similarityIndex + " " + b.getTitle());
                if (similarityIndex < SIMILARITY_INDEX_MIN)
                    markedForRemoval.add(b);
                else
                    b.setSimilarityIndex(similarityIndex);
            }


            searchResults.removeAll(markedForRemoval);
            Collections.sort(searchResults, new BookComparatorBySimilarityIndex());
        }


        if (searchResults.size() < numResults)
            return searchResults;
        else
            return new ArrayList<>(searchResults.subList(0,numResults));
    }

    private float averageSimilarityIndices(float... values) {
        float allValues = 0f;
        int numNullValues = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] < 0)
                numNullValues += 1;
            else
                allValues += values[i];
        }
        return allValues/(values.length - numNullValues);
    }

    
    
    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeInt(publishYearFloor);
        dest.writeInt(publishYearCeiling);
        dest.writeInt(pcountFloor);
        dest.writeInt(pcountCeiling);
        dest.writeTypedList(preferredGenres);
        dest.writeTypedList(excludedGenres);
        dest.writeInt(numResults);
    }

    protected SearchCriteria(Parcel in)
    {
        title = in.readString();
        author = in.readString();
        publishYearFloor = in.readInt();
        publishYearCeiling = in.readInt();
        pcountFloor = in.readInt();
        pcountCeiling = in.readInt();
        preferredGenres = in.createTypedArrayList(Genre.CREATOR);
        excludedGenres = in.createTypedArrayList(Genre.CREATOR);
        numResults = in.readInt();
    }

    public static final Parcelable.Creator<SearchCriteria> CREATOR
            = new Parcelable.Creator<SearchCriteria>() {
        public SearchCriteria createFromParcel(Parcel in) {
            return new SearchCriteria(in);
        }

        public SearchCriteria[] newArray(int size) {
            return new SearchCriteria[size];
        }
    };
}
