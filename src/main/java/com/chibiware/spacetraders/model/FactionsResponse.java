package com.chibiware.spacetraders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FactionsResponse {
    private List<Faction> data;
    private Meta meta;

    public FactionsResponse() {}

    public List<Faction> getData() {
        return data;
    }
    public void setData(List<Faction> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {
        private int page;
        private int limit;
        private int total;

        public Meta() {}

        public int getPage() {
            return page;
        }
        public void setPage(int page) {
            this.page = page;
        }

        public int getLimit() {
            return limit;
        }
        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getTotal() {
            return total;
        }
        public void setTotal(int total) {
            this.total = total;
        }
    }
}
