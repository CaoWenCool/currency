package com.currency.qrcode.currency.model.request;

public class ListingLatestRequest {

    /**
     *
     * integer >= 1
     * 1
     * Optionally offset the start (1-based index) of the paginated list of items to return.
     */
    private Integer start;

    /**
     *
     * integer [ 1 .. 5000 ]
     * 100
     * Optionally specify the number of results to return. Use this parameter and the "start" parameter to determine your own pagination size.
     */
    private Integer limit;

    /**
     *
     * number [ 0 .. 100000000000000000 ]
     * Optionally specify a threshold of minimum USD price to filter results by.
     */
    private Number priceMin;

    /**
     *
     * number [ 0 .. 100000000000000000 ]
     * Optionally specify a threshold of maximum USD price to filter results by.
     */
    private Number priceMax;

    /**
     *
     * number [ 0 .. 100000000000000000 ]
     * Optionally specify a threshold of minimum market cap to filter results by.
     */
    private Number marketCapMin;

    /**
     *
     * number [ 0 .. 100000000000000000 ]
     * Optionally specify a threshold of maximum market cap to filter results by.
     */
    private Number marketCapMax;

    /**
     *
     * number [ 0 .. 100000000000000000 ]
     * Optionally specify a threshold of minimum 24 hour USD volume to filter results by.
     */
    private Number volumn24hMin;

    /**
     *
     * number [ 0 .. 100000000000000000 ]
     * Optionally specify a threshold of maximum 24 hour USD volume to filter results by.
     */
    private Number volumn24hMax;

    /**
     *
     * number [ 0 .. 100000000000000000 ]
     * Optionally specify a threshold of minimum circulating supply to filter results by.
     */
    private Number circulatingSupplyMin;

    /**
     *
     * number [ 0 .. 100000000000000000 ]
     * Optionally specify a threshold of maximum circulating supply to filter results by.
     */
    private Number circulatingSupplyMax;

    /**
     *
     * number >= -100
     * Optionally specify a threshold of minimum 24 hour percent change to filter results by.
     */
    private Number percentChange24hMin;

    /**
     *
     * number >= -100
     * Optionally specify a threshold of maximum 24 hour percent change to filter results by.
     */
    private Number percentChange24hMax;

    /**
     *
     * string
     * Optionally calculate market quotes in up to 120 currencies at once by passing a comma-separated list of cryptocurrency or fiat currency symbols. Each additional convert option beyond the first requires an additional call credit. A list of supported fiat options can be found here. Each conversion is returned in its own "quote" object.
     */
    private Number convert;

    /**
     *
     */
    private String convertId;

    private String sort;

    private String sortDir;

    private String cryptocurrencyType;

    private String tag;

    private String aux;


    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Number getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(Number priceMin) {
        this.priceMin = priceMin;
    }

    public Number getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Number priceMax) {
        this.priceMax = priceMax;
    }

    public Number getMarketCapMin() {
        return marketCapMin;
    }

    public void setMarketCapMin(Number marketCapMin) {
        this.marketCapMin = marketCapMin;
    }

    public Number getMarketCapMax() {
        return marketCapMax;
    }

    public void setMarketCapMax(Number marketCapMax) {
        this.marketCapMax = marketCapMax;
    }

    public Number getVolumn24hMin() {
        return volumn24hMin;
    }

    public void setVolumn24hMin(Number volumn24hMin) {
        this.volumn24hMin = volumn24hMin;
    }

    public Number getVolumn24hMax() {
        return volumn24hMax;
    }

    public void setVolumn24hMax(Number volumn24hMax) {
        this.volumn24hMax = volumn24hMax;
    }

    public Number getCirculatingSupplyMin() {
        return circulatingSupplyMin;
    }

    public void setCirculatingSupplyMin(Number circulatingSupplyMin) {
        this.circulatingSupplyMin = circulatingSupplyMin;
    }

    public Number getCirculatingSupplyMax() {
        return circulatingSupplyMax;
    }

    public void setCirculatingSupplyMax(Number circulatingSupplyMax) {
        this.circulatingSupplyMax = circulatingSupplyMax;
    }

    public Number getPercentChange24hMin() {
        return percentChange24hMin;
    }

    public void setPercentChange24hMin(Number percentChange24hMin) {
        this.percentChange24hMin = percentChange24hMin;
    }

    public Number getPercentChange24hMax() {
        return percentChange24hMax;
    }

    public void setPercentChange24hMax(Number percentChange24hMax) {
        this.percentChange24hMax = percentChange24hMax;
    }

    public Number getConvert() {
        return convert;
    }

    public void setConvert(Number convert) {
        this.convert = convert;
    }

    public String getConvertId() {
        return convertId;
    }

    public void setConvertId(String convertId) {
        this.convertId = convertId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public String getCryptocurrencyType() {
        return cryptocurrencyType;
    }

    public void setCryptocurrencyType(String cryptocurrencyType) {
        this.cryptocurrencyType = cryptocurrencyType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }
}
